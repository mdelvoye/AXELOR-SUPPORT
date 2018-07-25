package com.axelor.apps.sale.db;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axelor.apps.account.db.TaxEquiv;
import com.axelor.apps.account.db.TaxLine;
import com.axelor.apps.base.db.Partner;
import com.axelor.apps.base.db.Product;
import com.axelor.apps.base.db.Unit;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.VirtualColumn;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "SALE_SALE_ORDER_LINE", indexes = { @Index(columnList = "fullName"), @Index(columnList = "sale_order"), @Index(columnList = "product"), @Index(columnList = "tax_line"), @Index(columnList = "tax_equiv"), @Index(columnList = "unit"), @Index(columnList = "supplier_partner"), @Index(columnList = "parent_line") })
public class SaleOrderLine extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SALE_SALE_ORDER_LINE_SEQ")
	@SequenceGenerator(name = "SALE_SALE_ORDER_LINE_SEQ", sequenceName = "SALE_SALE_ORDER_LINE_SEQ", allocationSize = 1)
	private Long id;

	@NameColumn
	@VirtualColumn
	@Access(AccessType.PROPERTY)
	private String fullName;

	@Widget(title = "Sale order")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private SaleOrder saleOrder;

	@Widget(title = "Seq.")
	private Integer sequence = 0;

	@Widget(title = "Product")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Product product;

	@Widget(title = "Qty")
	private BigDecimal qty = new BigDecimal("1");

	@Widget(title = "Print subtotal / line")
	private Boolean isToPrintLineSubTotal = Boolean.FALSE;

	@Widget(title = "Displayed Product name", translatable = true)
	@NotNull
	private String productName;

	@Widget(title = "Unit price")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal price = BigDecimal.ZERO;

	@Widget(title = "Unit price discounted")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal priceDiscounted = BigDecimal.ZERO;

	@Widget(title = "Tax")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private TaxLine taxLine;

	@Widget(title = "Tax Equiv")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private TaxEquiv taxEquiv;

	@Widget(title = "Total W.T.")
	private BigDecimal exTaxTotal = BigDecimal.ZERO;

	@Widget(title = "Total A.T.I.")
	private BigDecimal inTaxTotal = BigDecimal.ZERO;

	@Widget(title = "Description")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String description;

	@Widget(title = "Unit")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Unit unit;

	@Widget(title = "Supplier")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner supplierPartner;

	@Widget(title = "Discount amount")
	@Digits(integer = 10, fraction = 10)
	private BigDecimal discountAmount = BigDecimal.ZERO;

	@Widget(title = "Discount Type", selection = "base.price.list.line.amount.type.select")
	private Integer discountTypeSelect = 3;

	@Widget(title = "Estimated delivery date")
	private LocalDate estimatedDelivDate;

	@Widget(title = "SubTotal cost price")
	private BigDecimal subTotalCostPrice = BigDecimal.ZERO;

	@Widget(title = "SubTotal gross margin")
	private BigDecimal subTotalGrossMargin = BigDecimal.ZERO;

	@Widget(title = "Sub margin rate")
	private BigDecimal subMarginRate = BigDecimal.ZERO;

	@Widget(title = "Delivery date")
	private LocalDate deliveryDate;

	@Widget(title = "Delivered quantity")
	private BigDecimal deliveredQty = BigDecimal.ZERO;

	@Widget(title = "Total W.T. in company currency", hidden = true)
	private BigDecimal companyExTaxTotal = BigDecimal.ZERO;

	@Widget(title = "Unit cost price in company currency", hidden = true)
	private BigDecimal companyCostPrice = BigDecimal.ZERO;

	@Widget(title = "Total A.T.I. in company currency", hidden = true)
	private BigDecimal companyInTaxTotal = BigDecimal.ZERO;

	@Widget(title = "Total cost in company currency", hidden = true)
	private BigDecimal companyCostTotal = BigDecimal.ZERO;

	@Widget(title = "Type", selection = "sale.order.line.type.select")
	private Integer typeSelect = 0;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private SaleOrderLine parentLine;

	@Widget(title = "Pack lines")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parentLine", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SaleOrderLine> subLineList;

	@Widget(title = "Total pack")
	private BigDecimal totalPack = BigDecimal.ZERO;

	@Widget(title = "Eco participation", massUpdate = true)
	@Digits(integer = 10, fraction = 10)
	private BigDecimal saleEcoPrice = BigDecimal.ZERO;

	@Widget(title = "Purchase price W.T.", massUpdate = true)
	@Digits(integer = 10, fraction = 10)
	private BigDecimal purchasePrice = BigDecimal.ZERO;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public SaleOrderLine() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		try {
			fullName = computeFullName();
		} catch (NullPointerException e) {
			Logger logger = LoggerFactory.getLogger(getClass());
			logger.error("NPE in function field: getFullName()", e);
		}
		return fullName;
	}

	protected String computeFullName() {
		String fullName = "";
		if(saleOrder != null && saleOrder.getSaleOrderSeq() != null){
			fullName += saleOrder.getSaleOrderSeq();
		}
		if(productName != null)  {
			fullName += "-";
			if(productName.length() > 100)  {
				fullName += productName.substring(1, 100);
			}
			else  {  fullName += productName;  }
		}
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public SaleOrder getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(SaleOrder saleOrder) {
		this.saleOrder = saleOrder;
	}

	public Integer getSequence() {
		return sequence == null ? 0 : sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public BigDecimal getQty() {
		return qty == null ? BigDecimal.ZERO : qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public Boolean getIsToPrintLineSubTotal() {
		return isToPrintLineSubTotal == null ? Boolean.FALSE : isToPrintLineSubTotal;
	}

	public void setIsToPrintLineSubTotal(Boolean isToPrintLineSubTotal) {
		this.isToPrintLineSubTotal = isToPrintLineSubTotal;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getPrice() {
		return price == null ? BigDecimal.ZERO : price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getPriceDiscounted() {
		return priceDiscounted == null ? BigDecimal.ZERO : priceDiscounted;
	}

	public void setPriceDiscounted(BigDecimal priceDiscounted) {
		this.priceDiscounted = priceDiscounted;
	}

	public TaxLine getTaxLine() {
		return taxLine;
	}

	public void setTaxLine(TaxLine taxLine) {
		this.taxLine = taxLine;
	}

	public TaxEquiv getTaxEquiv() {
		return taxEquiv;
	}

	public void setTaxEquiv(TaxEquiv taxEquiv) {
		this.taxEquiv = taxEquiv;
	}

	public BigDecimal getExTaxTotal() {
		return exTaxTotal == null ? BigDecimal.ZERO : exTaxTotal;
	}

	public void setExTaxTotal(BigDecimal exTaxTotal) {
		this.exTaxTotal = exTaxTotal;
	}

	public BigDecimal getInTaxTotal() {
		return inTaxTotal == null ? BigDecimal.ZERO : inTaxTotal;
	}

	public void setInTaxTotal(BigDecimal inTaxTotal) {
		this.inTaxTotal = inTaxTotal;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Partner getSupplierPartner() {
		return supplierPartner;
	}

	public void setSupplierPartner(Partner supplierPartner) {
		this.supplierPartner = supplierPartner;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount == null ? BigDecimal.ZERO : discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Integer getDiscountTypeSelect() {
		return discountTypeSelect == null ? 0 : discountTypeSelect;
	}

	public void setDiscountTypeSelect(Integer discountTypeSelect) {
		this.discountTypeSelect = discountTypeSelect;
	}

	public LocalDate getEstimatedDelivDate() {
		return estimatedDelivDate;
	}

	public void setEstimatedDelivDate(LocalDate estimatedDelivDate) {
		this.estimatedDelivDate = estimatedDelivDate;
	}

	public BigDecimal getSubTotalCostPrice() {
		return subTotalCostPrice == null ? BigDecimal.ZERO : subTotalCostPrice;
	}

	public void setSubTotalCostPrice(BigDecimal subTotalCostPrice) {
		this.subTotalCostPrice = subTotalCostPrice;
	}

	public BigDecimal getSubTotalGrossMargin() {
		return subTotalGrossMargin == null ? BigDecimal.ZERO : subTotalGrossMargin;
	}

	public void setSubTotalGrossMargin(BigDecimal subTotalGrossMargin) {
		this.subTotalGrossMargin = subTotalGrossMargin;
	}

	public BigDecimal getSubMarginRate() {
		return subMarginRate == null ? BigDecimal.ZERO : subMarginRate;
	}

	public void setSubMarginRate(BigDecimal subMarginRate) {
		this.subMarginRate = subMarginRate;
	}

	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public BigDecimal getDeliveredQty() {
		return deliveredQty == null ? BigDecimal.ZERO : deliveredQty;
	}

	public void setDeliveredQty(BigDecimal deliveredQty) {
		this.deliveredQty = deliveredQty;
	}

	public BigDecimal getCompanyExTaxTotal() {
		return companyExTaxTotal == null ? BigDecimal.ZERO : companyExTaxTotal;
	}

	public void setCompanyExTaxTotal(BigDecimal companyExTaxTotal) {
		this.companyExTaxTotal = companyExTaxTotal;
	}

	public BigDecimal getCompanyCostPrice() {
		return companyCostPrice == null ? BigDecimal.ZERO : companyCostPrice;
	}

	public void setCompanyCostPrice(BigDecimal companyCostPrice) {
		this.companyCostPrice = companyCostPrice;
	}

	public BigDecimal getCompanyInTaxTotal() {
		return companyInTaxTotal == null ? BigDecimal.ZERO : companyInTaxTotal;
	}

	public void setCompanyInTaxTotal(BigDecimal companyInTaxTotal) {
		this.companyInTaxTotal = companyInTaxTotal;
	}

	public BigDecimal getCompanyCostTotal() {
		return companyCostTotal == null ? BigDecimal.ZERO : companyCostTotal;
	}

	public void setCompanyCostTotal(BigDecimal companyCostTotal) {
		this.companyCostTotal = companyCostTotal;
	}

	public Integer getTypeSelect() {
		return typeSelect == null ? 0 : typeSelect;
	}

	public void setTypeSelect(Integer typeSelect) {
		this.typeSelect = typeSelect;
	}

	public SaleOrderLine getParentLine() {
		return parentLine;
	}

	public void setParentLine(SaleOrderLine parentLine) {
		this.parentLine = parentLine;
	}

	public List<SaleOrderLine> getSubLineList() {
		return subLineList;
	}

	public void setSubLineList(List<SaleOrderLine> subLineList) {
		this.subLineList = subLineList;
	}

	/**
	 * Add the given {@link SaleOrderLine} item to the {@code subLineList}.
	 *
	 * <p>
	 * It sets {@code item.parentLine = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addSubLineListItem(SaleOrderLine item) {
		if (getSubLineList() == null) {
			setSubLineList(new ArrayList<>());
		}
		getSubLineList().add(item);
		item.setParentLine(this);
	}

	/**
	 * Remove the given {@link SaleOrderLine} item from the {@code subLineList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeSubLineListItem(SaleOrderLine item) {
		if (getSubLineList() == null) {
			return;
		}
		getSubLineList().remove(item);
	}

	/**
	 * Clear the {@code subLineList} collection.
	 *
	 * <p>
	 * If you have to query {@link SaleOrderLine} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearSubLineList() {
		if (getSubLineList() != null) {
			getSubLineList().clear();
		}
	}

	public BigDecimal getTotalPack() {
		return totalPack == null ? BigDecimal.ZERO : totalPack;
	}

	public void setTotalPack(BigDecimal totalPack) {
		this.totalPack = totalPack;
	}

	public BigDecimal getSaleEcoPrice() {
		return saleEcoPrice == null ? BigDecimal.ZERO : saleEcoPrice;
	}

	public void setSaleEcoPrice(BigDecimal saleEcoPrice) {
		this.saleEcoPrice = saleEcoPrice;
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice == null ? BigDecimal.ZERO : purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getAttrs() {
		return attrs;
	}

	public void setAttrs(String attrs) {
		this.attrs = attrs;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!(obj instanceof SaleOrderLine)) return false;

		final SaleOrderLine other = (SaleOrderLine) obj;
		if (this.getId() != null || other.getId() != null) {
			return Objects.equals(this.getId(), other.getId());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", getId())
			.add("sequence", getSequence())
			.add("qty", getQty())
			.add("isToPrintLineSubTotal", getIsToPrintLineSubTotal())
			.add("productName", getProductName())
			.add("price", getPrice())
			.add("priceDiscounted", getPriceDiscounted())
			.add("exTaxTotal", getExTaxTotal())
			.add("inTaxTotal", getInTaxTotal())
			.add("discountAmount", getDiscountAmount())
			.add("discountTypeSelect", getDiscountTypeSelect())
			.add("estimatedDelivDate", getEstimatedDelivDate())
			.omitNullValues()
			.toString();
	}
}
