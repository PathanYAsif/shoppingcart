package com.adp.service;

import static org.junit.Assert.*;

import org.junit.Test;

import com.adp.vo.Categories;
import com.adp.vo.DiscountSlabs;
import com.adp.vo.ShoppingCart;

public class BillGeneratorFactoryTest {

	@Test
	public void testGetBillGenerator() {
		assertTrue(BillGeneratorFactory.getBillGenerator(BillGenerationStrategy.CATEGORY_SLAB_DISCOUNT,
				new ShoppingCart(), new Categories(), new DiscountSlabs()) instanceof ItemizedBillGenerator);
	}

}
