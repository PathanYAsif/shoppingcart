package com.adp.service;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.adp.exception.ShoppingCartException;
import com.adp.vo.Categories;
import com.adp.vo.DiscountSlabs;
import com.adp.vo.ShoppingCart;

/**
 * This implementation of <{@link InputService} will look up input files on
 * class path for input.
 * 
 * If the builder is setup with valid file names on class path those will be
 * picked up as input instead of defaults.
 * 
 * @author Asif Pathan
 *
 */
public class ClasspathXmlInputServiceImpl implements InputService {

	private String categoryDiscountFilename;
	private String discountSlabFilename;
	private String shoppingCartFilename;

	@Override
	public Categories getCategoryDiscounts() {
		Categories categories = new Categories();
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(categoryDiscountFilename)) {
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(Categories.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				categories = (Categories) jaxbUnmarshaller.unmarshal(is);
			} catch (JAXBException e) {
				// TODO log error
				throw new ShoppingCartException(e.getCause());
			}
		} catch (IOException e) {
			// TODO log error
			throw new ShoppingCartException(e.getCause());
		}
		return categories;
	}

	@Override
	public DiscountSlabs getDiscountSlabs() {
		DiscountSlabs slabs = new DiscountSlabs();
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(discountSlabFilename)) {
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(DiscountSlabs.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				slabs = (DiscountSlabs) jaxbUnmarshaller.unmarshal(is);
			} catch (JAXBException e) {
				// TODO log error
				e.printStackTrace();
				throw new ShoppingCartException(e.getCause());
			}
		} catch (IOException e) {
			// TODO log error
			throw new ShoppingCartException(e.getCause());
		}
		return slabs;
	}

	@Override
	public ShoppingCart getShoppingCart() {
		ShoppingCart cart = new ShoppingCart();
		try (InputStream ist = this.getClass().getClassLoader().getResourceAsStream(shoppingCartFilename)) {
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(ShoppingCart.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				cart = (ShoppingCart) jaxbUnmarshaller.unmarshal(ist);
			} catch (JAXBException e) {
				// TODO log error
				e.printStackTrace();
				throw new ShoppingCartException(e.getCause());
			}
		} catch (IOException e) {
			// TODO log error
			throw new ShoppingCartException(e.getCause());
		}
		return cart;
	}

	public static class Builder {
		private String categoryDiscountFilename = "categories.xml";
		private String discountSlabFilename = "slab.xml";
		private String shoppingCartFilename = "shoppingcart.xml";

		public Builder setCategoryDiscountFilename(String categoryDiscountFilename) {
			if (null != categoryDiscountFilename && !categoryDiscountFilename.trim().isEmpty()) {
				this.categoryDiscountFilename = categoryDiscountFilename;
			}
			return this;
		}

		public Builder setDiscountSlabFilename(String discountSlabFilename) {
			if (null != discountSlabFilename && !discountSlabFilename.trim().isEmpty()) {
				this.discountSlabFilename = discountSlabFilename;
			}
			return this;
		}

		public Builder setShoppingCartFilename(String shoppingCartFilename) {
			if (null != shoppingCartFilename && !shoppingCartFilename.trim().isEmpty()) {
				this.shoppingCartFilename = shoppingCartFilename;
			}
			return this;
		}

		public ClasspathXmlInputServiceImpl build() {
			return new ClasspathXmlInputServiceImpl(categoryDiscountFilename, discountSlabFilename,
					shoppingCartFilename);
		}
	}

	private ClasspathXmlInputServiceImpl(String categoryDiscountFilename, String discountSlabFilename,
			String shoppingCartFilename) {
		this.categoryDiscountFilename = categoryDiscountFilename;
		this.discountSlabFilename = discountSlabFilename;
		this.shoppingCartFilename = shoppingCartFilename;
	}
}
