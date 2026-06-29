package com.sales.ops.serviceimpl.dispatch.podispatch.domain;

import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.dto.purchase.PoToWmDto;
import com.sales.ops.dto.util.UserDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author C12961
 * @date 2023/3/3 14:48
 */

public class PurchaseAcceptContext {

    private PoToWmDto params;
    private OpsPurchaseorder purchase;
	private List<OpsRequestpurchase> requests;
	private int morePurchaseQty;
	private List<PurchaseAcceptContextItem> items;

	private UserDto creator;

    public PurchaseAcceptContext(PoToWmDto params) {
        this.params = params;
        this.creator = new UserDto("acceptForPo");
        this.purchase = params.getPurchase();
        this.requests = params.getRequests();
        int sumRequestQty = requests.stream().map(OpsRequestpurchase::getQuantity).reduce(Integer::sum).orElse(0);
        this.morePurchaseQty = this.purchase.getQuantity() - sumRequestQty;
        this.items = requests.stream().map(request -> PurchaseAcceptContextItem.create(request, purchase, creator))
                .collect(Collectors.toList());
    }

    public PoToWmDto getParams() {
        return params;
    }

    public void setParams(PoToWmDto params) {
        this.params = params;
    }

	public int getMorePurchaseQty() {
		return morePurchaseQty;
	}

	public OpsPurchaseorder getPurchase() {
		return purchase;
	}

	public List<PurchaseAcceptContextItem> getItems() {
		return items;
	}

	public UserDto getCreator() {
		return creator;
	}

}
