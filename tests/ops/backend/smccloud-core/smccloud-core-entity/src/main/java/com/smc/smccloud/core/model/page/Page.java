package com.smc.smccloud.core.model.page;

import lombok.Data;
import java.io.Serializable;

@Data
public class Page implements Serializable
{
    private int pageNumber;
    private int pageSize = 2147483647;
}
