package com.smc.smccloud.model.receiveorder;

/**
 * 订单特殊出库方式
 */
public enum OrderSpecExpType {
    AddPreCharToPurechaeModel(8, "增加*号"),
    NotShipGoodReplaceBorrow(2, "不发货抵借条"),
    NotShipGoodReplaceReturn(1, "不发货抵退货"),
    ROSH10Product(64, "Rohs10"),
    SpecPackage(128, "特殊包装"),
    UseAirToTransport(16, "空运必保"),
    UseAssemleApplyStock(4, "关联组换调库"),
    WithinOneDayToShipping(32, "当日发货"),
    WholeOrderToAssemble(256, "整单组装"),
    OneItemToAssemble(512, "单项组装"),
    NotOrderToCSStock(1024, "不出服务备库"),
    MustOrderToCSStock(2048, "必须出服务备库"),
    AssChildToExport(4096, "子型号出库"),
    ApplySpecExport(8192, "特发"),
    MINPRICE(16384, "最低售价"),
    AIRTRANS(32768, "指定空运"),
    SPECIAL_WAREHOUSE(65536, "特殊仓库");

    private Integer code;
    private String specName;

    OrderSpecExpType(Integer code, String specName) {
        this.code = code;
        this.specName = specName;
    }

    public Integer code() {
        return code;
    }

    public String specName() {
        return specName;
    }

    public static OrderSpecExpType getSpecExpType(Integer code) {
        for (OrderSpecExpType ele : OrderSpecExpType.values()) {
            if (ele.code().compareTo(code) == 0) {
                return ele;
            }
        }
        return null;
    }

    public static String getSpecExpTypeDes(Integer value) {
        if (value == null || value == 0)
        {
            return null;
        }
        StringBuilder sbDes=new StringBuilder();
        for (OrderSpecExpType ele : OrderSpecExpType.values()) {
            if ((value&ele.code()) == ele.code()) {
                sbDes.append(ele.specName()).append(";");
                value= value- ele.code();
                if(value<=0)
                {
                    break;
                }
            }
        }
        return sbDes.toString();
    }

    public static boolean include(Integer value, OrderSpecExpType specExpType) {
        if (value == null || value <= 0) {
            return false;
        }
        if ((value & specExpType.code()) == specExpType.code()) {
            return true;
        }
        return false;
    }

    public  static int add(Integer curValue,OrderSpecExpType addSpecExpType)
    {
       return curValue|addSpecExpType.code();
    }


    public static void main(String[] args) {
        String specExpTypeDes = getSpecExpTypeDes(16387);
        System.out.println("specExpTypeDes = " + specExpTypeDes);

//        boolean include = include(12288, OrderSpecExpType.AssChildToExport);
//        System.out.println("include = " + include);

    }




}
