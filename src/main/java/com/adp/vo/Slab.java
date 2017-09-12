package com.adp.vo;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Slab")
public class Slab implements Comparable<Slab> {
	private BigDecimal rangeMin;
	private BigDecimal rangeMax;
	private BigDecimal discountPercent;

	public BigDecimal getRangeMin() {
		return rangeMin;
	}

	@XmlElement(name = "RangeMin")
	public void setRangeMin(BigDecimal rangeMin) {
		this.rangeMin = rangeMin;
	}

	public BigDecimal getRangeMax() {
		return rangeMax;
	}

	@XmlElement(name = "RangeMax")
	public void setRangeMax(BigDecimal rangeMax) {
		this.rangeMax = rangeMax;
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
		return "Range min: " + this.rangeMin + ", Range max: " + this.rangeMax + ", discount Percent: "
				+ this.discountPercent;
	}

	@Override
	public int compareTo(Slab o) {
		return this.rangeMin.compareTo(o.rangeMin);
	}

}
