package com.adp.vo;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Item")
public class Item {
	private String id;
	private String name;
	private String categoryId;
	private BigDecimal price;
	private int quantity;

	public String getId() {
		return id;
	}

	@XmlElement(name = "itemID")
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	@XmlElement(name = "itemName")
	public void setName(String name) {
		this.name = name;
	}

	public String getCategoryId() {
		return categoryId;
	}

	@XmlElement(name = "itemCategoryID")
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	@XmlElement(name = "unitPrice")
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	@XmlElement(name = "quantity")
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getTotalCost() {
		return this.price.multiply(new BigDecimal(this.quantity)).setScale(2, RoundingMode.CEILING);
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Item)) {
			return false;
		}
		Item item = (Item) object;

		if (this.id.equals(item.id)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

}
