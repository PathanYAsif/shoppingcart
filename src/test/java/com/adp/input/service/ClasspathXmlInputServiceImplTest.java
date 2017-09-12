package com.adp.input.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.adp.service.InputService;
import com.adp.service.InputServiceFactory;
import com.adp.vo.Categories;
import com.adp.vo.DiscountSlabs;
import com.adp.vo.ShoppingCart;

public class ClasspathXmlInputServiceImplTest {
	private static InputService inputService;
	private static final String CLASSPATH_XML_INPUT_SERVICE = "CP_XML";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		inputService = InputServiceFactory.getInputService(new String[] { CLASSPATH_XML_INPUT_SERVICE,
				"categories_test.xml", "slab_test.xml", "shoppingcart_test.xml" });
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		inputService = null;
	}

	@Test
	public void testGetCategoryDiscounts() {
		Categories categories = inputService.getCategoryDiscounts();
		assertNotNull("Categories object should not be null", categories);
		assertNotNull("Category list should not be null", categories.getCategoryList());
		assertTrue("Category list should contain 3 categories", categories.getCategoryList().size() == 3);
	}

	@Test
	public void testGetDiscountSlabs() {
		DiscountSlabs discountSlabs = inputService.getDiscountSlabs();
		assertNotNull("DiscountSlabs object should not be null", discountSlabs);
		assertNotNull("Discount slab list should not be null", discountSlabs.getSlabs());
		assertTrue("Discount slab list should contain 3 discount slabs", discountSlabs.getSlabs().size() == 3);
	}

	@Test
	public void testGetShoppingCart() {
		ShoppingCart cart = inputService.getShoppingCart();
		assertNotNull("ShoppingCart object must not be null", cart);
		assertNotNull("Item list should not be null", cart.getItems());
		assertTrue("Item list should contain 3 discount slabs", cart.getItems().size() == 3);
	}

	// TODO: Add negative scenarios
}
