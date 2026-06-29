package com.smc.smccloud.model.binorder;

/**
 * Task 5、Task 7A：
 * modelNo + warehouseCode 组合 key，避免大循环里反复拼接字符串作为缓存键。
 */
public final class ModelWarehouseKey {

    private final String modelNo;
    private final String warehouseCode;

    public ModelWarehouseKey(String modelNo, String warehouseCode) {
        this.modelNo = modelNo;
        this.warehouseCode = warehouseCode;
    }

    public String getModelNo() {
        return modelNo;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModelWarehouseKey)) {
            return false;
        }
        ModelWarehouseKey that = (ModelWarehouseKey) o;
        return equals(modelNo, that.modelNo)
                && equals(warehouseCode, that.warehouseCode);
    }

    @Override
    public int hashCode() {
        int result = modelNo == null ? 0 : modelNo.hashCode();
        result = 31 * result + (warehouseCode == null ? 0 : warehouseCode.hashCode());
        return result;
    }

    private static boolean equals(String left, String right) {
        return left == null ? right == null : left.equals(right);
    }
}
