package com.adp.vo;

import java.math.BigDecimal;
import java.util.List;

public interface Bill {

	List<Item> getItems();

	BigDecimal getGrossBillAmount();

	BigDecimal getNetBillAmount();

	BigDecimal getItemDiscountPrice(Item item);

	BigDecimal getItemDiscountByCategory(Item item);

}
