package com.mobiquityinc.packer.model;

public class PackageItem {
	private int index;
	private double weight;
	private double cost;
	private String currency;

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
	
	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public PackageItem withCurrencyAs(String currency) {
		setCurrency(currency);
		return this;
	}
}
