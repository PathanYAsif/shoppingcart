package com.adp.vo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class ItemizedBill implements Bill {

	private Categories categories;
	private DiscountSlabs discountSlabs;
	private ShoppingCart cart;
	Map<Item, BigDecimal> itemDiscountMap = new HashMap<>();
	private BigDecimal netBillAmount = new BigDecimal(0).setScale(2, RoundingMode.CEILING);
	private BigDecimal grossBillAmount = new BigDecimal(0).setScale(2, RoundingMode.CEILING);

	public static class Builder {

		private Categories categories = new Categories();
		private DiscountSlabs discountSlabs = new DiscountSlabs();
		private ShoppingCart cart = new ShoppingCart();

		public Builder setCategories(Categories categories) {
			if (null != categories && null != categories.getCategoryList()) {
				this.categories = categories;
			}
			return this;
		}

		public Builder setDiscountSlabs(DiscountSlabs discountSlabs) {
			if (null != discountSlabs && null != discountSlabs.getSlabs()) {
				this.discountSlabs = discountSlabs;
			}
			return this;
		}

		public Builder setShoppingCart(ShoppingCart cart) {
			if (null != cart && null != cart.getItems()) {
				this.cart = cart;
			}
			return this;
		}

		public ItemizedBill build() {
			return new ItemizedBill(cart, discountSlabs, categories);
		}
	}

	private ItemizedBill(ShoppingCart cart, DiscountSlabs discountSlabs, Categories categories) {
		this.categories = categories;
		this.discountSlabs = discountSlabs;
		this.cart = cart;
	}

	private BigDecimal getCategoryDiscount(String id) {
		for (Category category : this.categories.getCategoryList()) {
			if (category.getId().equals(id)) {
				return category.getDiscountPercent();
			}
		}
		return new BigDecimal(0);
	}

	@Override
	public BigDecimal getItemDiscountByCategory(Item item) {
		BigDecimal discount = itemDiscountMap.get(item);
		if (null == discount) {
			discount = new BigDecimal(0).setScale(2, RoundingMode.CEILING);
			String categoryId = item.getCategoryId();
			BigDecimal categoryDiscountPercent = getCategoryDiscount(categoryId);
			discount = item.getTotalCost().multiply(categoryDiscountPercent).divide(new BigDecimal(100));
			itemDiscountMap.put(item, discount);
		}
		return discount;
	}

	@Override
	public BigDecimal getItemDiscountPrice(Item item) {
		BigDecimal discount = getItemDiscountByCategory(item);
		return item.getTotalCost().subtract(discount).setScale(2, RoundingMode.CEILING);
	}

	private BigDecimal getSlabDiscount(BigDecimal grossBillAmount) {
		Collections.sort(discountSlabs.getSlabs());
		ListIterator<Slab> iterator = discountSlabs.getSlabs().listIterator(discountSlabs.getSlabs().size());
		BigDecimal slabDiscount = new BigDecimal(0).setScale(2, RoundingMode.CEILING);
		while (iterator.hasPrevious()) {
			Slab slab = iterator.previous();
			if ((0 <= grossBillAmount.compareTo(slab.getRangeMin())) && (null == slab.getRangeMax())
					|| (0 <= grossBillAmount.compareTo(slab.getRangeMin())
							&& 0 >= grossBillAmount.compareTo(slab.getRangeMax()))) {
				grossBillAmount = grossBillAmount.subtract(slab.getRangeMin());
				BigDecimal discount = grossBillAmount.multiply(slab.getDiscountPercent()).divide(new BigDecimal(100));
				slabDiscount = slabDiscount.add(discount);
			}
		}
		return slabDiscount;
	}

	private BigDecimal getNetBillAmount(BigDecimal grossBillAmount) {
		netBillAmount = grossBillAmount.subtract(getSlabDiscount(grossBillAmount)).setScale(2, RoundingMode.CEILING);
		return netBillAmount;
	}

	@Override
	public BigDecimal getGrossBillAmount() {
		if (!cart.getItems().isEmpty() && grossBillAmount.equals(new BigDecimal(0).setScale(2, RoundingMode.CEILING))) {
			for (Item item : cart.getItems()) {
				grossBillAmount = grossBillAmount.add(getItemDiscountPrice(item)).setScale(2, RoundingMode.CEILING);
			}
		}
		return grossBillAmount;
	}

	@Override
	public BigDecimal getNetBillAmount() {
		return getNetBillAmount(getGrossBillAmount());
	}

	@Override
	public List<Item> getItems() {
		return cart.getItems();
	}
}
