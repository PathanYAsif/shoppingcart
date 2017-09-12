package com.adp.vo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Categories")
public class Categories {
	// TODO: Parse to map instead of list to reduce lookup time for discount or
	// create a map after unmarshalling
	List<Category> categoryList = new ArrayList<>();

	public List<Category> getCategoryList() {
		return categoryList;
	}

	@XmlElement(name = "Category")
	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

}
