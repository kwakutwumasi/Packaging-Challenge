package com.mobiquityinc.packer.model;

import java.util.Currency;

public class PackageItem {
	private int index;
	private double weight;
	private double cost;
	private Currency currency;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public PackageItem withIndexAs(int index) {
		setIndex(index);
		return this;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}

	public PackageItem withWeightAs(double weight) {
		setWeight(weight);
		return this;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double money) {
		this.cost = money;
	}

	public PackageItem withCostAs(double cost) {
		setCost(cost);
		return this;
	}
	
	public Currency getCurrency() {
		return currency;
	}
	
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	
	public PackageItem withCurrencyAs(Currency currency) {
		setCurrency(currency);
		return this;
	}
}
