package com.sales.ops.common.until;

/**
 * @author C12961
 * @date 2023/3/29 16:10
 */
public class Counter {

    private int count;

    public static Counter start() {
        return new Counter(0);
    }

    private Counter(int count) {
        this.count = count;
    }


    public int getCount() {
        return count;
    }

    public Counter setCount(int count) {
        this.count = count;
        return this;
    }

    public void increment() {
        count++;
    }

    @Override
    public String toString() {
        return String.valueOf(this.count);
    }

}
