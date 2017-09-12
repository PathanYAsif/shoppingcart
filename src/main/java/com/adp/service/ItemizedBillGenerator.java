package com.adp.service;

import com.adp.vo.Bill;
import com.adp.vo.Categories;
import com.adp.vo.DiscountSlabs;
import com.adp.vo.ItemizedBill;
import com.adp.vo.ShoppingCart;

public class ItemizedBillGenerator implements BillGenerator {

	private Categories categories;
	private DiscountSlabs slabs;
	private ShoppingCart cart;

	public ItemizedBillGenerator(ShoppingCart cart, Categories categories, DiscountSlabs slabs) {
		this.categories = categories;
		this.slabs = slabs;
		this.cart = cart;
	}

	@Override
	public Bill generateBill() {
		return new ItemizedBill.Builder().setCategories(categories).setDiscountSlabs(slabs).setShoppingCart(cart)
				.build();
	}

}
