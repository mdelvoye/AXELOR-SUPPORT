package com.misyl.custom.web;

import com.axelor.apps.base.db.Product;
import com.axelor.apps.sale.db.SaleOrder;
import com.axelor.apps.sale.db.SaleOrderLine;
import com.axelor.apps.sale.web.SaleOrderLineController;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.axelor.rpc.Context;
import com.google.inject.Inject;
import com.axelor.apps.sale.service.saleorder.SaleOrderLineService;

public class MisylSaleOrderLineController extends SaleOrderLineController{
	 @Inject private SaleOrderLineService saleOrderLineService;
	
	public void getProductInformation(ActionRequest request, ActionResponse response) {
		super.getProductInformation(request, response);
		Context context = request.getContext();
	    SaleOrderLine saleOrderLine = context.asType(SaleOrderLine.class);
	    Product product = saleOrderLine.getProduct();
		
		try {
			 response.setValue("saleEcoPrice", product.getSaleEcoPrice());
			 response.setValue("purchasePrice", product.getPurchasePrice());
		 }
		 catch (Exception e) {
		      response.setFlash(e.getMessage());
		      this.resetProductInformation(response);
		 }
	}
}
