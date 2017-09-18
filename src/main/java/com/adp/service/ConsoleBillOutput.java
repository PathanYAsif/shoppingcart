package com.adp.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Formatter;
import java.util.List;

import com.adp.vo.Bill;
import com.adp.vo.Item;

public class ConsoleBillOutput implements BillOutput {

	@Override
	public void outputBill(Bill bill) {
		List<Item> items = bill.getItems();
		int count = 0;
		try (Formatter formatter = new Formatter()) {
			System.out.println(formatter.format("%1$4s   %2$-25s   %3$10s   %4$-8s   %5$-11s   %6$-13s", "#", "Name",
					"Price", "Quantity", "Total Price", "Discount Price"));
		}
		// TODO: check precision
		BigDecimal categoryDiscount = new BigDecimal(0).setScale(2, RoundingMode.CEILING);
		for (Item item : items) {
			try (Formatter formatter = new Formatter()) {
				BigDecimal itemCategoryDiscount = bill.getItemDiscountPrice(item);
				categoryDiscount = categoryDiscount.add(item.getTotalCost().subtract(itemCategoryDiscount));
				System.out.println(
						formatter.format("%1$3s.   %2$-25s   %3$10s   %4$8s   %5$11s   %6$14s", ++count, item.getName(),
								item.getPrice(), item.getQuantity(), item.getTotalCost(), itemCategoryDiscount));
			}
		}
		BigDecimal totalDiscount = bill.getGrossBillAmount().subtract(bill.getNetBillAmount()).add(categoryDiscount)
				.setScale(2, RoundingMode.CEILING);

		StringBuilder sb = new StringBuilder();
		try (Formatter formatter = new Formatter(sb)) {
			System.out.println(
					formatter.format("%1$70s  %2$15s", "Total(after category discount)", bill.getGrossBillAmount()));
			sb.setLength(0);
			System.out.println(formatter.format("%1$70s  %2$15s", "Final Discount Price", bill.getNetBillAmount()));
			sb.setLength(0);
			System.out.println(
					formatter.format("%1$70s  %2$15s", "Total Applied discount(Category + Slab)", totalDiscount));
		}

	}

}
