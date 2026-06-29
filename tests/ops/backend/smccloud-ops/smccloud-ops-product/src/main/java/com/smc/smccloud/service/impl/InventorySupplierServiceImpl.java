package com.smc.smccloud.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.FtpFileUtil;
import com.smc.smccloud.mapper.GPInventoryMapper;
import com.smc.smccloud.mapper.InventorySupplierMapper;
import com.smc.smccloud.model.inventory.GPInventoryDO;
import com.smc.smccloud.model.inventory.InventorySupplierDO;
import com.smc.smccloud.model.inventory.InventorySupplierVO;
import com.smc.smccloud.model.inventory.InventoryVO;
import com.smc.smccloud.service.InventorySupplierService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static java.util.stream.Collectors.toList;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author smc
 * @since 2022-01-25
 */
@Service
@Slf4j
public class InventorySupplierServiceImpl implements InventorySupplierService {

    @Resource
    private InventorySupplierMapper inventorySupplierMapper;
    @Resource
    private GPInventoryMapper gpInventoryMapper;

    @Value("${ftp.server}")
    private String server;
    @Value("${ftp.user}")
    private String user;
    @Value("${ftp.password}")
    private String password;

    @Value("${ops-file-upload-path.url}")
    private String filePath;

    /*@Override
    public int insertOrUpdate(InventorySupplierVO inventorySupplierVO) {

        LambdaQueryWrapper<InventorySupplierDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(InventorySupplierDO::getSupplierId, InventorySupplierDO::getModelNo);
        queryWrapper.eq(InventorySupplierDO::getSupplierId, inventorySupplierVO.getSupplierId())
                .eq(InventorySupplierDO::getModelNo, inventorySupplierVO.getModelNo());
        InventorySupplierDO inventory = inventorySupplierMapper.selectOne(queryWrapper);

        if (inventory != null) {
            LambdaUpdateWrapper<InventorySupplierDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(InventorySupplierDO::getQuantity, inventorySupplierVO.getQuantity())
//                    .set(InventorySupplierDO::getQuantityAssembly, inventorySupplierDO.getQuantityAssembly())
//                    .set(InventorySupplierDO::getQuantityProduce, inventorySupplierDO.getQuantityProduce())
                    .set(InventorySupplierDO::getUpdateTime, inventorySupplierVO.getUpdateTime());
            updateWrapper.eq(InventorySupplierDO::getModelNo, inventory.getModelNo())
                    .eq(InventorySupplierDO::getSupplierId, inventory.getSupplierId());
            return inventorySupplierMapper.update(null, updateWrapper);
        } else {
            return inventorySupplierMapper.insert(BeanCopyUtil.copy(inventorySupplierVO, InventorySupplierDO.class));
        }
    }*/

    @Override
    public ResultVo<String> importJPStockData(List<InventorySupplierVO> list) {
        log.info("进入库存供应商数据导入方法 =>  数据大小: {} ", list.size());
        if (CollectionUtils.isEmpty(list)) {
            return ResultVo.failure("供应商库存数据为空,导入失败!");
        }

        // 删除供应商为JP的数据
        LambdaUpdateWrapper<InventorySupplierDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(InventorySupplierDO::getSupplierId, "JP");
        inventorySupplierMapper.delete(updateWrapper);

        Set<String> modelSet = new HashSet<>(list.size());
        Date now = new Date();
        for (InventorySupplierVO item : list) {
            if (!modelSet.contains(item.getModelNo())) {
                item.setUpdateTime(now);
                inventorySupplierMapper.insert(BeanCopyUtil.copy(item, InventorySupplierDO.class));
                modelSet.add(item.getModelNo());
                //this.insertOrUpdate(item);
            }
        }

        return ResultVo.success("推送库存供应商数据完毕");
    }

    @Override
    public ResultVo<String> parseJPSTockFile(MultipartFile file) {
        if (file == null) {
            return ResultVo.failure("解析文件为NULL");
        }
        List<InventorySupplierVO> inventorySupplierVOList;
        InventorySupplierVO inventorySupplierVO;
        ResultVo<String> stringResultVo;
        String line;
        String supplierId = "JP";
        String modelNo;
        int quantity;

        try (InputStream fis = file.getInputStream();
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.US_ASCII);
             BufferedReader bf = new BufferedReader(isr)) {
            inventorySupplierVOList = new ArrayList<>();
            long startTime = System.currentTimeMillis();

            while ((line = bf.readLine()) != null) {
                if (StringUtils.isBlank(line)) {
                    continue;
                }
                modelNo = line.substring(1, 28).trim();
                quantity = Integer.valueOf(line.substring(28, 38).trim());
                if (StringUtils.isBlank(modelNo) || quantity == 0) {
                    continue;
                }
                inventorySupplierVO = new InventorySupplierVO();
                inventorySupplierVO.setSupplierId(supplierId);
                inventorySupplierVO.setModelNo(modelNo);
                inventorySupplierVO.setQuantity(quantity);
                inventorySupplierVOList.add(inventorySupplierVO);
            }
            if (CollectionUtils.isEmpty(inventorySupplierVOList)) {
                return ResultVo.success("", "无记录");
            }
            System.out.println("JPSTockDataFile list size => " + inventorySupplierVOList.size());

            stringResultVo = this.importJPStockData(inventorySupplierVOList);
            if (!stringResultVo.isSuccess()) {
                log.error(stringResultVo.getMessage());
                return ResultVo.failure(stringResultVo.getMessage());
            }

            long endTime = System.currentTimeMillis();
            log.info("解析文件程序运行时间： " + (endTime - startTime) + "ms");

        } catch (Exception e) {
            log.error("导入JPSTockDataFile文件数据失败", e);
            return ResultVo.failure("导入JPSTockDataFile文件数据失败");
        }
        return ResultVo.success("", "导入日本在库" + inventorySupplierVOList.size());
    }

    @Override
    public List<InventorySupplierDO> listModelSupplierInventory(List<String> modelNos) {
        LambdaQueryWrapper<InventorySupplierDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(InventorySupplierDO::getModelNo, modelNos);
        return inventorySupplierMapper.selectList(queryWrapper);
    }

    @Override
    public List<InventoryVO> listModelSupplierCanOrderQty(List<String> modelNos) {
        return inventorySupplierMapper.listModelSupplierCanOrderQty(modelNos);
    }

    @Override
    public ResultVo<String> paseJPInventorySupplier() {
        InputStream inputStream = null;
        BufferedInputStream bis = null;
        try {
//            String author = "Basic " + Base64.getEncoder().encodeToString("JP-CN:WZBbkgwY".getBytes());
//
//            URL url = new URL("http://192.168.168.4:9999/JP-CN/JPSTOCK.ZIP");
//
//            URLConnection connection = url.openConnection();
//
//            connection.setRequestProperty("Authorization", author);
//            connection.connect();
//            log.info("访问 http://192.168.168.4:9999/JP-CN/JPSTOCK.ZIP 成功");
//
//            inputStream = connection.getInputStream();
            String s = FtpFileUtil.dowmloadFile("JPSTOCK.ZIP", server, user, password, filePath);
            inputStream = new FileInputStream(s);
            //inputStream = new FileInputStream("C:\\\\Users\\\\C14717\\\\Desktop\\\\fsdownload\\\\JPSTOCK-20250416062626.ZIP");
            bis = new BufferedInputStream(inputStream);
            //进行文件解压
            return unzip(bis);
        } catch (Exception e) {
            log.error("下载失败: {}", e.getMessage(), e);
            return ResultVo.failure(e.getMessage());
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                    bis = null;
                }
                if (inputStream != null) {
                    inputStream.close();
                    inputStream = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ResultVo<String> impGPInventory() {
        // 先删除供应商为GZ的库存
        LambdaQueryWrapper<InventorySupplierDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InventorySupplierDO::getSupplierId, "GZ");
        int deleteCount;
        try {
            deleteCount = inventorySupplierMapper.delete(queryWrapper);
        } catch (Exception e) {
            return ResultVo.failure("导入广州制造库存=> 删除GZ供应商库存失败:" + e.getMessage());
        }
        log.info("导入广州制造库存=> 删除供应商库存 {} 条 :", deleteCount);
        LambdaQueryWrapper<GPInventoryDO> query = new LambdaQueryWrapper<>();
        List<GPInventoryDO> gpInventoryDOS = gpInventoryMapper.selectList(query);
        if (CollectionUtils.isEmpty(gpInventoryDOS)) {
            return ResultVo.failure("广州制造暂无库存可导入");
        }
        InventorySupplierDO inventorySupplierDO;
        Map<String, GPInventoryDO> map = new HashMap<>();
        for (GPInventoryDO item : gpInventoryDOS) {
            if (map.containsKey(item.getModelNo())) {
                GPInventoryDO gpInventoryDO = map.get(item.getModelNo());
                gpInventoryDO.setAssQty(gpInventoryDO.getAssQty() + item.getAssQty()); // 可组装数量
                gpInventoryDO.setAvaQty(gpInventoryDO.getAvaQty() + item.getAvaQty()); // 在库可用数量
                gpInventoryDO.setProdQty(gpInventoryDO.getProdQty() + item.getProdQty()); // 可生产数
                map.put(item.getModelNo(), gpInventoryDO);
            } else {
                map.put(item.getModelNo(), item);
            }
        }
        Date nowDate = new Date();
        StringBuilder errMsg = new StringBuilder();
        int count = 0;
        for (String key : map.keySet()) {
            GPInventoryDO gpInventoryDO = map.get(key);
            inventorySupplierDO = new InventorySupplierDO();
            inventorySupplierDO.setModelNo(gpInventoryDO.getModelNo());
            inventorySupplierDO.setUpdateTime(nowDate);
            inventorySupplierDO.setQuantityProduce(gpInventoryDO.getProdQty());
            inventorySupplierDO.setQuantity(gpInventoryDO.getAvaQty());
            inventorySupplierDO.setQuantityAssembly(gpInventoryDO.getAssQty());
            inventorySupplierDO.setSupplierId("GZ");
            inventorySupplierDO.setQuantityPrepare(0);
            try {
                inventorySupplierMapper.insert(inventorySupplierDO);
                count++;
            } catch (Exception e) {
                errMsg.append(key + "导入失败");
            }
        }
        String resultStr = "共计" + gpInventoryDOS.size() + ";根据型号合并数量之后共计" + map.keySet().size() + ";成功导入" + count + "; 导入失败数据:" + errMsg.toString();
        log.info(resultStr);
        return ResultVo.success(resultStr);
    }

    public ResultVo<String> unzip(BufferedInputStream bis) {

        try {
            // 读入流
            ZipInputStream zipInputStream = new ZipInputStream(bis);
            // 遍历每一个文件
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            String unzipFilePath = null;
            ResultVo<String> resultVo;
            while (zipEntry != null) {
                if (!zipEntry.isDirectory()) { // 是否为文件
                    // 文件
                    unzipFilePath = zipEntry.getName();
                    File file = new File(String.valueOf(zipEntry));
                    //如果是csv文件，则上传数据
                    if (unzipFilePath.endsWith(".CSV") || unzipFilePath.endsWith(".csv")) {
                        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), zipInputStream);
                        resultVo = saveJPInventorySupplier(multipartFile);
                        zipInputStream.close();
                        return resultVo;
                    }
                }
                zipEntry = zipInputStream.getNextEntry();
            }
        } catch (Exception e) {
            log.error("解压文件出错" + e);
            e.printStackTrace();
            return ResultVo.failure(e.getMessage());
        }
        return ResultVo.failure(" 解析 http://192.168.168.4:9999/JP-CN/JPSTOCK.ZIP 失败");
    }

    public ResultVo<String> saveJPInventorySupplier(MultipartFile file) {
        try {

            DataInputStream dataIn = new DataInputStream(file.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(dataIn, "utf-8"));
            String oneLine = null;
            InventorySupplierVO supplierVO;
            List<InventorySupplierVO> inventorySupplierList = new ArrayList<>();
            Map<String, InventorySupplierVO> map = new HashMap<>();
            while ((oneLine = reader.readLine()) != null) {
                if (StringUtils.isBlank(oneLine)) {
                    break;
                }
                try {
                    supplierVO = readJPInventoryLine(oneLine);
                } catch (Exception e) {
                    log.error("解析日本在库库存发生异常: 异常数据: {}, msg: {}",oneLine,e.getMessage(),e);
                    continue;
                }
                if (supplierVO != null) {
                    inventorySupplierList.add(supplierVO);
                }
            }
            reader.close();
            for (InventorySupplierVO item : inventorySupplierList) {
                if (map.containsKey(item.getModelNo())) {
                    ////bugid:17244 c14717 20250416 修改替换为 数量大的
                    //item.setQuantity(map.get(item.getModelNo()).getQuantity() + item.getQuantity());
                    if(item.getQuantity() !=0 && map.get(item.getModelNo()).getQuantity() != 0){
                        log.info("导入日本库存同一型号多个出库区分"+item.getModelNo());
                    }
                    if(item.getQuantity()> map.get(item.getModelNo()).getQuantity()){
                        map.put(item.getModelNo(), item);
                    }
                } else {
                    map.put(item.getModelNo(), item);
                }
            }
            // 删除供应商为JP的数据
            LambdaUpdateWrapper<InventorySupplierDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(InventorySupplierDO::getSupplierId, "JP");
            inventorySupplierMapper.delete(updateWrapper);

            Date now = new Date();
            for (String key : map.keySet()) {
                if (StringUtils.isBlank(key)) {
                    continue;
                }
                InventorySupplierVO vo = map.get(key);
                if (vo == null) {
                    continue;
                }
                vo.setUpdateTime(now);
                inventorySupplierMapper.insert(BeanCopyUtil.copy(vo, InventorySupplierDO.class));
            }

            return ResultVo.success("读取完毕,共推送" + map.size() + "条");

        } catch (Exception e) {
            log.error("解析日本供应商库存处理异常", e);
            return ResultVo.failure(e.getMessage());
        }
    }

    public InventorySupplierVO readJPInventoryLine(String line) {
        InventorySupplierVO info = new InventorySupplierVO();
        String[] arrs = line.split(" ");
        if (arrs.length <= 0) {
            return null;
        }
        if (StringUtils.isBlank(arrs[0])) {
            return null;
        }
        List<String> list = Arrays.asList(arrs);
        List<String> collect = list.stream().filter(StringUtils::isNotBlank).collect(toList());
        if (CollectionUtils.isEmpty(collect)) {
            return null;
        }
        info.setModelNo(collect.get(0));
        info.setQuantity(Integer.parseInt(StringUtils.isBlank(collect.get(1)) ? "0" : collect.get(1)));
        info.setSupplierId("JP");
        //bugid:17244 c14717 20250416 追加库存类别
        info.setInventoryClass(line.substring(40,42));//出库区分 WH F1 F2
        return info;
    }

}
