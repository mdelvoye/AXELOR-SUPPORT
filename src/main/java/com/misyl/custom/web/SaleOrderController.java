package com.misyl.custom.web;

import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.common.base.Joiner;
import com.axelor.apps.base.db.Address;
import com.axelor.apps.sale.db.SaleOrder;

public class SaleOrderController {
	
	public void computeAddressStr(ActionRequest request, ActionResponse response) {
		SaleOrder order = request.getContext().asType(SaleOrder.class);
		Address mainInvoicingAddress = order.getMainInvoicingAddress();
		Address deliveryAddress = order.getDeliveryAddress();
		Joiner joiner = Joiner.on("\n").skipNulls();
		String retourInvoice  = "";
		String retourDelivery  = "";
		
		if (deliveryAddress!=null) {
			retourDelivery = joiner.join(deliveryAddress.getAddressL2(),deliveryAddress.getAddressL3(),deliveryAddress.getAddressL4(),
					deliveryAddress.getAddressL5(),deliveryAddress.getAddressL6(),deliveryAddress.getAddressL7Country().getName());
			response.setValue("deliveryAddressStr", retourDelivery);	
		}
		
		if (mainInvoicingAddress!=null) {
			retourInvoice = joiner.join(mainInvoicingAddress.getAddressL2(),mainInvoicingAddress.getAddressL3(),mainInvoicingAddress.getAddressL4(),
					mainInvoicingAddress.getAddressL5(),mainInvoicingAddress.getAddressL6(),mainInvoicingAddress.getAddressL7Country().getName());
			response.setValue("mainInvoicingAddressStr", retourInvoice);	
		}	
	}
	

}
