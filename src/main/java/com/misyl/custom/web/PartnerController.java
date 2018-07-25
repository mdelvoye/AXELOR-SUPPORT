package com.misyl.custom.web;

import com.axelor.apps.base.db.Address;
import com.axelor.apps.base.db.Partner;
import com.axelor.i18n.I18n;
import com.axelor.inject.Beans;
import com.axelor.meta.db.repo.MetaSelectItemRepository;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.common.base.Joiner;

public class PartnerController {
	
	public void computeFullName(ActionRequest request, ActionResponse response)  {
		Partner partner = request.getContext().asType(Partner.class);
		String title ="";
		String firstName ="";
		String name="";
		
		if (partner.getTitleSelect() != 0) {
			title = I18n.get(
				    Beans.get(MetaSelectItemRepository.class) // You can @Inject it too
				        .all()
				        .filter(
				            "self.select.name = ? AND self.value = ?",
				            "partner.title.type.select",
				            partner.getTitleSelect())
				        .fetchOne().getTitle()
				) + " ";
		}
		
		/*
		Long idPartner = partner.getId();
		List<Partner> partners = Beans.get(PartnerRepository.class).all().filter("id=?",idPartner).fetch();
		System.out.println(partners.toString());
		*/
		
		if (partner.getFirstName()!=null) {
			firstName = partner.getFirstName() + " ";
		}
		if (partner.getName()!=null) {
			name = partner.getName();
		}
		
		if (partner.getPartnerTypeSelect()==1){
			response.setValue("fullName", name);
			response.setValue("firstName", null);
			//response.setValue("titleSelect", null);	
		}
		else if (partner.getPartnerTypeSelect()==2){
				response.setValue("fullName", title + firstName +  partner.getName());
		}
		else {
			response.setValue("fullName", "");
		}
	}

	public void computeAddressStr(ActionRequest request, ActionResponse response) {
		Partner partner = request.getContext().asType(Partner.class);
		Boolean uniqAddress = partner.getUniqAddress();
		Address invoiceAdresse = partner.getInvoiceAddress();
		Address deliveryAdresse = partner.getDeliveryAddress();
		Joiner joiner = Joiner.on("\n").skipNulls();
		String retourInvoice  = "";
		String retourDelivery  = "";
		
		String name = partner.getName();
		
		
		if (deliveryAdresse!=null) {
			retourDelivery = joiner.join(deliveryAdresse.getAddressL2(),deliveryAdresse.getAddressL3(),deliveryAdresse.getAddressL4(),
				deliveryAdresse.getAddressL5(),deliveryAdresse.getAddressL6(),deliveryAdresse.getAddressL7Country().getName());
		}
		
		if (invoiceAdresse!=null) {
			retourInvoice = joiner.join(invoiceAdresse.getAddressL2(),invoiceAdresse.getAddressL3(),invoiceAdresse.getAddressL4(),
				invoiceAdresse.getAddressL5(),invoiceAdresse.getAddressL6(),invoiceAdresse.getAddressL7Country().getName());
		}
		
		if (uniqAddress) {
			response.setValue("invoiceAddressStr", retourInvoice);
			response.setValue("deliveryAddressStr", retourInvoice);
			
			response.setValue("deliveryAddress", null);
		}
		else {
			response.setValue("invoiceAddressStr", retourInvoice);
			response.setValue("deliveryAddressStr", retourDelivery);
		}
			
	}
	
	
	

}
