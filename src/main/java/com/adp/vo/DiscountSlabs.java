package com.adp.vo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FlatDiscountSlabs")
public class DiscountSlabs {
	private List<Slab> slabs = new ArrayList<>();

	public List<Slab> getSlabs() {
		return slabs;
	}

	@XmlElement(name = "Slab")
	public void setSlabs(List<Slab> slabs) {
		this.slabs = slabs;
	}
}
