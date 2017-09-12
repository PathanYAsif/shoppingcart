package com.adp.service;

import com.adp.vo.Categories;
import com.adp.vo.DiscountSlabs;
import com.adp.vo.ShoppingCart;

public interface InputService {
	public Categories getCategoryDiscounts();

	public DiscountSlabs getDiscountSlabs();

	public ShoppingCart getShoppingCart();
}
