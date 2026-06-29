package com.smc.smccloud.core.model.dto;

import lombok.Data;
import lombok.experimental.Delegate;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: B90034
 * Date: 2021-12-17 13:04
 * Description: 自定义包装List类型 用于集合较验
 */
@Data
public class ValidationList<E> implements List<E> {

    @Delegate
    @Valid
    public List<E> list = new ArrayList<>();

    @Override
    public String toString() {
        return list.toString();
    }
}
