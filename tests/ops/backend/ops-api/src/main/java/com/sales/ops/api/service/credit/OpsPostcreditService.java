package com.sales.ops.api.service.credit;

import com.sales.ops.common.opsexception.OpsException;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface OpsPostcreditService {

    Map<String, String> checkCredit(@RequestBody List<String> orderFnoList) throws OpsException;
}

