package com.adp;

import com.adp.service.BillGenerationStrategy;
import com.adp.service.BillOutput;
import com.adp.service.ConsoleBillOutput;
import com.adp.service.InputService;
import com.adp.service.InputServiceFactory;
import com.adp.service.ShoppingCartService;
import com.adp.service.ShoppingCartServiceImpl;
import com.adp.vo.Bill;

/**
 * Dummy client / entry point to application.
 * 
 * @author Asif Pathan
 *
 */
public class ShoppingCartClient {
	public static void main(String[] args) {
		// if running through IDE need to set args eg. 'CP_XML' or 'CP_XML
		// categories.xml slab.xml shoppingcart.xml' in run configuration
		InputService inputService = InputServiceFactory.getInputService(args);

		ShoppingCartService scService = new ShoppingCartServiceImpl();
		Bill bill = scService.getBill(inputService.getShoppingCart(), inputService.getCategoryDiscounts(),
				inputService.getDiscountSlabs(), BillGenerationStrategy.CATEGORY_SLAB_DISCOUNT);

		BillOutput billOutput = new ConsoleBillOutput(); // need to move to
															// factory for
															// various outputs
		billOutput.outputBill(bill);
	}
}
