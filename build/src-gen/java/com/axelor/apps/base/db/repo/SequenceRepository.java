package com.axelor.apps.base.db.repo;

import com.axelor.apps.base.db.Company;
import com.axelor.apps.base.db.Sequence;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class SequenceRepository extends JpaRepository<Sequence> {

	public SequenceRepository() {
		super(Sequence.class);
	}

	public Sequence findByCode(String code) {
		return Query.of(Sequence.class)
				.filter("self.code = :code")
				.bind("code", code)
				.fetchOne();
	}

	public Sequence findByName(String name) {
		return Query.of(Sequence.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	public Sequence find(String code, Company company) {
		return Query.of(Sequence.class)
				.filter("self.code = :code AND self.company = :company")
				.bind("code", code)
				.bind("company", company)
				.fetchOne();
	}

	//SEQUENCE SELECT
	public static final String PARTNER = "partner";
	public static final String PRODUCT = "product";

	//SEQUENCE SELECT
			public static final String SALES_ORDER = "saleOrder";
}

