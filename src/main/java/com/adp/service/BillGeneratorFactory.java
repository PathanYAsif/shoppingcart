package com.adp.service;

import com.adp.vo.Categories;
import com.adp.vo.DiscountSlabs;
import com.adp.vo.ShoppingCart;

/**
 * Factory implementation for getting appropriate bill generators as per
 * strategy
 * 
 * @author Asif Pathan
 *
 */
public abstract class BillGeneratorFactory {
	public static BillGenerator getBillGenerator(BillGenerationStrategy strategy, ShoppingCart cart,
			Categories categories, DiscountSlabs slabs) {
		switch (strategy) {
		case CATEGORY_SLAB_DISCOUNT:
			return new ItemizedBillGenerator(cart, categories, slabs);
		case NO_DISCOUNT:
		default:
			return new ItemizedBillGenerator(cart, null, null);
		}
	}
}
