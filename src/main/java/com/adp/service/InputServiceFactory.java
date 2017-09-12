package com.adp.service;

import java.util.Arrays;

/**
 * Factory implementation for cater for addition of new input methods.
 * 
 * @author Asif Pathan
 *
 */
public abstract class InputServiceFactory {

	public static InputService getInputService(String[] args) {
		// Initiated larger sized array to cater for future needs
		String[] inputParam = Arrays.copyOf(args, 10);
		if (inputParam[0] == null || inputParam[0].trim().isEmpty()) {
			throw new IllegalArgumentException("Invalid input, please see application usage document");
		}
		String type = inputParam[0];
		switch (type) {
		case "CP_XML":
		default:
			return new ClasspathXmlInputServiceImpl.Builder().setCategoryDiscountFilename(inputParam[1])
					.setDiscountSlabFilename(inputParam[2]).setShoppingCartFilename(inputParam[3]).build();
		}
	}
}
