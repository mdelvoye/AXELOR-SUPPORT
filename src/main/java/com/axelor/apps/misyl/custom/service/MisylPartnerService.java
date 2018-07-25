package com.axelor.apps.misyl.custom.service;

import com.axelor.apps.base.db.Address;
import com.axelor.apps.base.db.Partner;
import com.google.common.base.Joiner;

public class MisylPartnerService {
	
	public Address partnerAddress(Partner partner, String type) {
		Address retour=null;
		if (partner != null) {
			switch(type) {
				case "invoicingAddress":
					retour = partner.getInvoiceAddress();
					break;
				case "deliveryAddress":
					retour = partner.getDeliveryAddress();
					break;
				default:
					retour = null;
			}
		}
		return retour;
	}
	
	public String computeAddressStr(Address adresse) {
		String retour=null;
		Joiner joiner = Joiner.on("\n").skipNulls();
		if (adresse != null) {
			retour = joiner.join(adresse.getAddressL2(),adresse.getAddressL3(),adresse.getAddressL4(),
					adresse.getAddressL5(),adresse.getAddressL6(),adresse.getAddressL7Country().getName());
		}
		return retour;
	}
	

	
  
}
