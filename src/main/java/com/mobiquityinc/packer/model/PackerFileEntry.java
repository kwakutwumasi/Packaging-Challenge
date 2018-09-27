package com.mobiquityinc.packer.model;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.i18n.Messages;

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
	
	public PackerFileEntry add(PackageItem packageItem) throws APIException {
		if(packageItem.getIndex()!=packageItems.size()+1)
			throw new APIException(MessageFormat.format(Messages.get("invalid.index"), 
					packageItem.getIndex(),packageItems.size()+1));
		
		packageItems.add(packageItem);
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((packageItems == null) ? 0 : packageItems.hashCode());
		long temp;
		temp = Double.doubleToLongBits(weightLimit);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PackerFileEntry other = (PackerFileEntry) obj;
		if (!packageItems.equals(other.packageItems))
			return false;
		if (Double.doubleToLongBits(weightLimit) != Double.doubleToLongBits(other.weightLimit))
			return false;
		return true;
	}
	
	
}
