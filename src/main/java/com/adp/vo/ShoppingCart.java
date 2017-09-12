package com.adp.vo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ShoppingCart")
public class ShoppingCart {

	private List<Item> items = new ArrayList<>();

	public List<Item> getItems() {
		return items;
	}

	@XmlElement(name = "Item")
	public void setItems(List<Item> items) {
		this.items = items;
	}
}
