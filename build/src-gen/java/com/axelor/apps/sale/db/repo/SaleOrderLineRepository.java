package com.axelor.apps.sale.db.repo;

import com.axelor.apps.sale.db.SaleOrder;
import com.axelor.apps.sale.db.SaleOrderLine;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class SaleOrderLineRepository extends JpaRepository<SaleOrderLine> {

	public SaleOrderLineRepository() {
		super(SaleOrderLine.class);
	}

	public Query<SaleOrderLine> findAllBySaleOrder(SaleOrder saleOrder) {
		return Query.of(SaleOrderLine.class)
				.filter("self.saleOrder = :saleOrder")
				.bind("saleOrder", saleOrder);
	}

	public static final int TYPE_NORMAL = 0;
	public static final int TYPE_TITLE = 1;
	public static final int TYPE_PACK = 2;
}

