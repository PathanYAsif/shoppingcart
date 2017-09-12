package com.adp.service;

import com.adp.vo.Bill;
import com.adp.vo.Categories;
import com.adp.vo.DiscountSlabs;
import com.adp.vo.ShoppingCart;

public interface ShoppingCartService {
	Bill getBill(ShoppingCart shoppingCart, Categories categories, DiscountSlabs slabs,
			BillGenerationStrategy strategy);

}
