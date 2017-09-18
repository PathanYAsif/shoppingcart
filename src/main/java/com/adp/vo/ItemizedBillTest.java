package com.adp.vo;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.adp.service.BillGenerationStrategy;
import com.adp.service.BillGeneratorFactory;

public class ItemizedBillTest {

	private static Bill bill;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Item item1 = new Item();
		item1.setCategoryId("1");
		item1.setId("1");
		item1.setName("First Item");
		item1.setPrice(new BigDecimal(200));
		item1.setQuantity(2);

		Item item2 = new Item();
		item2.setCategoryId("2");
		item2.setId("2");
		item2.setName("Second Item");
		item2.setPrice(new BigDecimal(150));
		item2.setQuantity(5);

		List<Item> items = new ArrayList<>();
		items.add(item1);
		items.add(item2);

		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.setItems(items);

		Slab slab1 = new Slab();
		slab1.setDiscountPercent(new BigDecimal(2));
		slab1.setRangeMax(new BigDecimal(200));
		slab1.setRangeMin(new BigDecimal(0));

		Slab slab2 = new Slab();
		slab2.setDiscountPercent(new BigDecimal(5));
		slab2.setRangeMax(null);
		slab2.setRangeMin(new BigDecimal(201));

		List<Slab> slabs = new ArrayList<>();
		slabs.add(slab1);
		slabs.add(slab2);

		DiscountSlabs discountSlabs = new DiscountSlabs();
		discountSlabs.setSlabs(slabs);

		Category category1 = new Category();
		category1.setId("1");
		category1.setName("Category 1");
		category1.setDiscountPercent(new BigDecimal(1));

		Category category2 = new Category();
		category2.setId("2");
		category2.setName("Category 2");
		category2.setDiscountPercent(new BigDecimal(2));

		List<Category> categoriesList = new ArrayList<>();
		categoriesList.add(category1);
		categoriesList.add(category2);

		Categories categories = new Categories();
		categories.setCategoryList(categoriesList);

		bill = BillGeneratorFactory.getBillGenerator(BillGenerationStrategy.CATEGORY_SLAB_DISCOUNT, shoppingCart,
				categories, discountSlabs).generateBill();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		bill = null;
	}

	@Test
	public void testGetItemDiscountByCategory() {
		assertTrue("Category discount for item 1 should be 4.00", bill.getItemDiscountByCategory(bill.getItems().get(0))
				.equals(new BigDecimal(4).setScale(2, RoundingMode.CEILING)));
		assertTrue("Category discount for item 2 should be 15.00",
				bill.getItemDiscountByCategory(bill.getItems().get(1))
						.equals(new BigDecimal(15).setScale(2, RoundingMode.CEILING)));
	}

	@Test
	public void testGetItemDiscountPrice() {
		assertTrue("Category discount for item 1 should be 396.00", bill.getItemDiscountPrice(bill.getItems().get(0))
				.equals(new BigDecimal(396).setScale(2, RoundingMode.CEILING)));
		assertTrue("Category discount for item 2 should be 735.00", bill.getItemDiscountPrice(bill.getItems().get(1))
				.equals(new BigDecimal(735).setScale(2, RoundingMode.CEILING)));
	}

	@Test
	public void testGetGrossBillAmount() {
		assertTrue("Gross bill amount must be 1131.00",
				bill.getGrossBillAmount().equals(new BigDecimal(1131).setScale(2, RoundingMode.CEILING)));
	}

	// @Test
	// public void testGetNetBillAmount() {
	// fail("Yet to implement");
	// }

	@Test
	public void testGetItems() {
		assertTrue("Total items included in bill should be 2", bill.getItems().size() == 2);
		assertTrue("Category id of first item should be 1", bill.getItems().get(0).getCategoryId().equals("1"));
		assertTrue("Item id of second item should be 2", bill.getItems().get(1).getId().equals("2"));
	}

}
