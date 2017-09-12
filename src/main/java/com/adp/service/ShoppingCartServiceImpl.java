package com.adp.service;

import com.adp.vo.Bill;
import com.adp.vo.Categories;
import com.adp.vo.DiscountSlabs;
import com.adp.vo.ShoppingCart;

public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Override
	public Bill getBill(ShoppingCart shoppingCart, Categories categories, DiscountSlabs slabs,
			BillGenerationStrategy strategy) {
		BillGenerator billGenerator = BillGeneratorFactory.getBillGenerator(strategy, shoppingCart, categories, slabs);
		return billGenerator.generateBill();
	}

}
