package com.adp.service;

import static org.junit.Assert.*;

import org.junit.Test;

public class BillGenerationStrategyTest {

	@Test
	public void testGetStrategyByName() {
		assertEquals(BillGenerationStrategy.getStrategyByName("CSD"), BillGenerationStrategy.CATEGORY_SLAB_DISCOUNT);
		assertEquals(BillGenerationStrategy.getStrategyByName("ND"), BillGenerationStrategy.NO_DISCOUNT);
		assertNotEquals(BillGenerationStrategy.getStrategyByName("CSD"), BillGenerationStrategy.NO_DISCOUNT);
		assertNotEquals(BillGenerationStrategy.getStrategyByName("ND"), BillGenerationStrategy.CATEGORY_SLAB_DISCOUNT);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetStrategyByNameException() {
		BillGenerationStrategy.getStrategyByName("test");
	}

}
