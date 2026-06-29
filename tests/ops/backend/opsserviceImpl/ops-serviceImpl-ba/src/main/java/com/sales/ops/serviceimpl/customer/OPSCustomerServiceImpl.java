package com.sales.ops.serviceimpl.customer;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.common.until.OPSRedisUtils;
import com.sales.ops.db.extdao.CustomerShareAddressDao;
import com.sales.ops.db.dao.OpsCustomerAddressMapper;
import com.sales.ops.db.dao.OpsCustomerMapper;
import com.sales.ops.db.dao.OpsCustomerWarehouseMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.OPSCustomerDao;
import com.sales.ops.dto.ba.CustomerInfo;
import com.sales.ops.dto.customer.CustomerInformation;
import com.sales.ops.dto.customer.CustomerPostalAddress;
import com.sales.ops.dto.customer.CustomerShareAddress;
import com.sales.ops.dto.customer.NationalArea;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.service.customer.OPSCustomerService;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @description
 * @date 2021/10/29 15:18
 * @auther C12961
 */
@Service
public class OPSCustomerServiceImpl implements OPSCustomerService {

    @Autowired
    private OPSRedisUtils opsRedisUtils;
    @Autowired
    private OpsCustomerMapper opsCustomerMapper;
    @Autowired
    private OpsCustomerAddressMapper opsCustomerAddressMapper;
    @Autowired
    private OPSCustomerDao opsCustomerDao;
    @Autowired
    private CustomerShareAddressDao shareAddressMapper;
    @Autowired
    private OpsCustomerWarehouseMapper opsCustomerWarehouseMapper;


    @Override
    public List<String> refreshCustomerAddress(String mi) {
        Long time = Long.parseLong(mi);
        //productBomDetail
        List<String> customerNos = opsCustomerDao.refreshOpsCustomerData(time);
        for(String costomerNo : customerNos){
            opsRedisUtils.hDelete("ops:customer:customerNo",costomerNo);
        }
        if(customerNos.size() == 0){//没有更新内容
            return null;
        }
        return customerNos;
    }

    @Override
    public CustomerInfo findCustomerInfoByNo(String customerNo) {
        CustomerInfo customerInfo = getCustomer(customerNo);
       /* if (StringUtils.isBlank(customerNo)) {
            return null;
        }
        //查询redis
        Object obj = opsRedisUtils.hGet("ops:customer:customerNo", customerNo);
        //redis查询到了，返回redis解析对象

        if (obj != null) {
            customerInfo =  JSON.parseObject(obj.toString(),CustomerInfo.class);
        }
        //redis查询不到，更新redis，返回数据库查到的对象
        if(customerInfo == null){
            customerInfo = getCustomer(customerNo);
        }*/
        return customerInfo;
    }

    private CustomerInfo getCustomer(String customerNo) {
        OpsCustomer customer = opsCustomerMapper.selectByPrimaryKey(customerNo);
        if (customer==null){
            return null;
        }
        OpsCustomerAddressExample example = new OpsCustomerAddressExample();
        example.createCriteria().andDelflagEqualTo(0).andCustomerNoEqualTo(customerNo);
        List<OpsCustomerAddress> list = opsCustomerAddressMapper.selectByExample(example);
        CustomerInfo CustomerInfo = new CustomerInfo(customer, list);
        opsRedisUtils.hPut("ops:customer:customerNo", customerNo, JSON.toJSONString(CustomerInfo));
        return CustomerInfo;
    }

    @Override
    public List<OpsCustomer> findCustomerInfoByName(String customerName) {
        OpsCustomerExample example = new OpsCustomerExample();
        OpsCustomerExample.Criteria criteria = example.createCriteria();
        criteria.andNameLike("%"+customerName+"%");
        return opsCustomerMapper.selectByExample(example);
    }

    @Override
    public List<Customer> findCustomerInfoByNoOrName(String customerNo) {
        //customerNo为空则直接返回数据库前10条
//        if (customerNo == null || customerNo == ""){
           return opsCustomerDao.queryCustomer(customerNo);
//        }

        /*List<Customer> list = null;
        //查询redis
        Object obj = opsRedisUtils.hGet("ops:customer:customerNoOrName", customerNo);

        //redis查询到了，返回redis解析对象
        if (obj != null) {
            list  =  JSON.parseObject(obj.toString(),List.class);
        }

        //redis查询不到，更新redis，返回数据库查到的对象
        if(list == null){
            list = opsCustomerDao.queryCustomer(customerNo);

            if (CollectionUtils.isNotEmpty(list)){
                opsRedisUtils.hPut("ops:customer:customerNoOrName", customerNo , JSON.toJSONString(list));
            }

        }*/
//        return null;
    }

    /**
     * 委托在库基础数据查询
     * @param customerNo
     * @return
     */
    @Override
    public List<OpsCustomerWarehouse> searchOpsCustomerWarehouse(String customerNo) {
        OpsCustomerWarehouseExample example = new OpsCustomerWarehouseExample();
        OpsCustomerWarehouseExample.Criteria criteria = example.createCriteria();
        criteria.andCustomerNoEqualTo(customerNo);
        criteria.andDelflagEqualTo(0);
        return opsCustomerWarehouseMapper.selectByExample(example);
        /*List<OpsCustomerWarehouse> list = new ArrayList<>();
        if(StringUtils.isBlank(customerNo)){
            return null;
        }
        //根据modelNo到redis内获取
        Object obj = opsRedisUtils.hGet("ops:customerWarehouse:customerNo",customerNo);
        if(null != obj){
            JSONArray arr = JSONArray.parseArray(obj.toString());
            int arrSize = arr.size();
            for(int i=0;i<arrSize;i++){
                list.add(JSON.parseObject(arr.getString(i),OpsCustomerWarehouse.class));
            }
        }
        //若redis内不存,则访问下游service
        if(list.isEmpty()){
            OpsCustomerWarehouseExample example = new OpsCustomerWarehouseExample();
            OpsCustomerWarehouseExample.Criteria criteria = example.createCriteria();
            criteria.andCustomerNoEqualTo(customerNo);
            criteria.andDelflagEqualTo(0);
            list = opsCustomerWarehouseMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(list)){
                JSONArray resultArr = JSONArray.parseArray(JSON.toJSONString(list));
                opsRedisUtils.hPut("ops:customerWarehouse:customerNo",customerNo,resultArr.toString());
            }
        }
        return list;*/
    }

    @Override
    public List<String> refreshOpsCustomerWarehouseData(String mi) {
        Long time = Long.parseLong(mi);
        // productBomDetail
        List<String> customerNos = opsCustomerDao.refreshOpsCustomerWarehouseData(time);
        for(String customerNo : customerNos){
            opsRedisUtils.hDelete("ops:customerWarehouse:customerNo",customerNo);
        }
        if(customerNos.size() == 0){//没有更新内容
            return null;
        }
        return customerNos;
    }

    @Override
    public PageInfo<CustomerInformation> findCustomerInfoByCustomerNo(PageModel<String> page) {
        // 对客户数据进行分页查询操作；
        PageInfo<CustomerInformation> pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                      .doSelectPageInfo(() -> opsCustomerDao.findCustomerInfoByCustomerNo(page.getCondition()));

        // 拼接检索到的客户所有的地址进行拼接，目前只包括通讯地址；
        if(!pageInfo.getList().isEmpty() && pageInfo.getList().size() > 0){
            // stream流抽取所有的客户集合，对地址进行拼接
            List<String> customerNos = pageInfo.getList().stream().map(CustomerInformation::getCustomerNo).distinct().collect(Collectors.toList());
            List<CustomerShareAddress> shareAddress = this.selectShareAddressByCustomerNo(customerNos);

            // 地址分组并且循环赋值，resultList结果集匹配地址
            Map<String, List<CustomerShareAddress>> addressGroupBy = shareAddress.stream()
                             .collect(Collectors.groupingBy(CustomerShareAddress :: getCustomerNo));
            pageInfo.getList().forEach(r -> {
                if (addressGroupBy.containsKey(r.getCustomerNo()))
                    r.setCustomerShareInfo(addressGroupBy.get(r.getCustomerNo()));
            });
        }
        return pageInfo;
    }

    /**
     * 封装客户查询接口中的全部地址，0：代表 通讯地址；
     * @param customerNos 客户代码集合
     * @return 组装后的地址
     */
    private List<CustomerShareAddress> selectShareAddressByCustomerNo(List<String> customerNos) {
        // 将所有的信息存放在List
        List<CustomerShareAddress> shareAddressList = new ArrayList<>();
        // 根据参数集合查询所有的通讯地址
        List<CustomerPostalAddress> postalAddress = shareAddressMapper.selectCustomerPostalAddressByCustomerNo(customerNos);
        List<String> areaCodes = Lists.newArrayList();
        for (CustomerPostalAddress address : postalAddress) {
            areaCodes.add(address.getProvince());
            areaCodes.add(address.getCity());
            areaCodes.add(address.getRegion());
        }
        // 通讯地址中行政代码去重，一次查询所有对应的名称
        List<NationalArea> areaNames = null;
        if (!postalAddress.isEmpty() && postalAddress.size() > 0) {
            areaCodes = areaCodes.stream().distinct().collect(Collectors.toList());
            areaNames = shareAddressMapper.selectAreaNameByCityCode(areaCodes);
        }
        for (CustomerPostalAddress address : postalAddress) {
            // stream流 比对代码，组装成完整的通讯地址
            Optional<NationalArea> province = areaNames.stream().filter(i -> i.getCode().equals(address.getProvince())).findFirst();
            Optional<NationalArea> city = areaNames.stream().filter(i -> i.getCode().equals(address.getCity())).findFirst();
            Optional<NationalArea> region = areaNames.stream().filter(i -> i.getCode().equals(address.getRegion())).findFirst();
            StringBuilder stringBuilder = new StringBuilder();
            if (province != null && province.isPresent()) {
                stringBuilder.append(province.get().getName());
            }
            if (city != null && city.isPresent()) {
                stringBuilder.append(city.get().getName());
            }
            if (region != null && region.isPresent()) {
                stringBuilder.append(region.get().getName());
            }
            stringBuilder.append(address.getAddress());

            CustomerShareAddress addressObj = new CustomerShareAddress();
            addressObj.setCustomerNo(address.getCustomerNo());
            addressObj.setType(CustomerShareAddress.POSTAL_TYPE);
            addressObj.setProvinceCode(address.getProvince());
            addressObj.setCityCode(address.getCity());
            addressObj.setRegionCode(address.getRegion());
            addressObj.setAddress(stringBuilder.toString());
            shareAddressList.add(addressObj);
        }
        return shareAddressList;
    }

    @Override
    public List<Map<String, String>> findCustomerByCustomerNoOrName(String customerNoOrName) {
        return opsCustomerDao.findCustomerByCustomerNoOrName(customerNoOrName);
    }
}
