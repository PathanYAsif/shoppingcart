package com.adp.vo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;
import java.util.ListIterator;

public class ItemizedBill extends Bill {

	private Categories categories;
	private DiscountSlabs discountSlabs;
	private ShoppingCart cart;

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

	@Override
	public void printBill() {
		List<Item> items = cart.getItems();
		BigDecimal sum = new BigDecimal(0).setScale(2, RoundingMode.CEILING);
		int count = 0;
		try (Formatter formatter = new Formatter()) {
			System.out.println(formatter.format("%1$4s   %2$-25s   %3$10s   %4$-8s   %5$-11s   %6$-13s", "#", "Name",
					"Price", "Quantity", "Total Price", "Discount Price"));
		}

		// TODO: check precision
		BigDecimal totalDiscount = new BigDecimal(0).setScale(2, RoundingMode.CEILING);
		for (Item item : items) {
			try (Formatter formatter = new Formatter()) {
				String categoryId = item.getCategoryId();
				BigDecimal categoryDiscountPercent = getCategoryDiscount(categoryId);
				BigDecimal discount = item.getTotalCost().multiply(categoryDiscountPercent).divide(new BigDecimal(100))
						.setScale(2, RoundingMode.CEILING);
				totalDiscount = totalDiscount.add(discount).setScale(2, RoundingMode.CEILING);
				;
				BigDecimal netTotalCost = item.getTotalCost().subtract(discount).setScale(2, RoundingMode.CEILING);
				System.out.println(formatter.format("%1$3s.   %2$-25s   %3$10s   %4$8s   %5$11s   %6$14s", ++count,
						item.getName(), item.getPrice(), item.getQuantity(), item.getTotalCost(), netTotalCost));
				sum = sum.add(netTotalCost);
			}
		}

		BigDecimal slabDiscount = new BigDecimal(0).setScale(2, RoundingMode.CEILING);
		BigDecimal eligibleSum = sum;
		// Flat slab assumption - slabs do not overlap and are continuous e.g. 0
		// - 100, 101 - 200
		Collections.sort(discountSlabs.getSlabs());
		ListIterator<Slab> iterator = discountSlabs.getSlabs().listIterator(discountSlabs.getSlabs().size());
		while (iterator.hasPrevious()) {
			Slab slab = iterator.previous();
			if ((0 <= eligibleSum.compareTo(slab.getRangeMin())) && (null == slab.getRangeMax())
					|| (0 <= eligibleSum.compareTo(slab.getRangeMin())
							&& 0 >= eligibleSum.compareTo(slab.getRangeMax()))) {
				eligibleSum = eligibleSum.subtract(slab.getRangeMin());
				BigDecimal discount = eligibleSum.multiply(slab.getDiscountPercent()).divide(new BigDecimal(100));
				slabDiscount = slabDiscount.add(discount);
			}
		}

		totalDiscount = totalDiscount.add(slabDiscount).setScale(2, RoundingMode.CEILING);

		BigDecimal netTotal = sum.subtract(slabDiscount).setScale(2, RoundingMode.CEILING);
		StringBuilder sb = new StringBuilder();
		try (Formatter formatter = new Formatter(sb)) {
			System.out.println(formatter.format("%1$70s  %2$15s", "Total(after category discount)", sum));
			sb.setLength(0);
			System.out.println(formatter.format("%1$70s  %2$15s", "Final Discount Price", netTotal));
			sb.setLength(0);
			System.out.println(
					formatter.format("%1$70s  %2$15s", "Total Applied discount(Category + Slab)", totalDiscount));
		}
	}

	private BigDecimal getCategoryDiscount(String id) {
		for (Category category : this.categories.getCategoryList()) {
			if (category.getId().equals(id)) {
				return category.getDiscountPercent();
			}
		}
		return new BigDecimal(0);
	}

}
