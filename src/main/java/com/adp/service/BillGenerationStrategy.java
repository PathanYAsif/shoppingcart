package com.adp.service;

/**
 * Enum to indicate which bill generation strategies are available.
 * 
 * @author Asif Pathan
 *
 */
public enum BillGenerationStrategy {
	CATEGORY_SLAB_DISCOUNT("CSD"), NO_DISCOUNT("ND");

	private String name;

	public String getName() {
		return name;
	}

	private BillGenerationStrategy(String strategyName) {
		this.name = strategyName;
	}

	// TODO: Create a map(name, BillGenerationStrategy) to avoid looping
	public static BillGenerationStrategy getStrategyByName(String name) {
		if (null == name || "".equals(name.trim())) {
			throw new IllegalArgumentException("Invalid strategy name");
		}
		for (BillGenerationStrategy strategy : values()) {
			if (strategy.name.equals(name)) {
				return strategy;
			}
		}
		throw new IllegalArgumentException("No BillGenerationStrategy with name '" + name + "' found");
	}

}
