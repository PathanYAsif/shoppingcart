package com.adp.vo;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Category")
@XmlType(propOrder = { "id", "name", "discountPercent" })
public class Category {
	private String id;
	private String name;
	private BigDecimal discountPercent;

	public String getId() {
		return id;
	}

	@XmlElement
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getDiscountPercent() {
		return discountPercent;
	}

	@XmlElement(name = "discPerc")
	public void setDiscountPercent(BigDecimal discountPercent) {
		this.discountPercent = discountPercent;
	}

	@Override
	public String toString() {
		return ("id: " + this.id + ", name: " + this.name + ", discount percent: " + this.discountPercent);
	}
}
