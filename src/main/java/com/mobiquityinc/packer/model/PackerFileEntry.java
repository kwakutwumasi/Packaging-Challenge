package com.mobiquityinc.packer.model;

import java.util.ArrayList;
import java.util.List;

public class PackerFileEntry {

	private double weightLimit;
	List<PackageItem> packageItems = new ArrayList<>();
	
	public double getWeightLimit() {
		return weightLimit;
	}

	public void setWeightLimit(double weightLimit) {
		this.weightLimit = weightLimit;
	}

	public PackerFileEntry withWeightLimitAs(double weightLimit) {
		this.weightLimit = weightLimit;
		return this;
	}

	public List<PackageItem> getPackageItems() {
		return packageItems;
	}
	
	public PackerFileEntry add(PackageItem packageItem) {
		packageItems.add(packageItem);
		return this;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(weightLimit);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PackerFileEntry other = (PackerFileEntry) obj;
		return Double.doubleToLongBits(weightLimit) != Double.doubleToLongBits(other.weightLimit);
	}
	
}
