package com.mobiquityinc.packer.model;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.i18n.Messages;

/**Model for holding entries for pack operations
 * @author Kwaku Twumasi-Afriyie <kwaku.twumasi@quakearts.com>
 *
 */
public class PackerFileEntry {

	private double weightLimit;
	List<PackageItem> packageItems = new ArrayList<>();
	
	/**Get the weight limit for the package
	 * @return the weight limit as a double value
	 */
	public double getWeightLimit() {
		return weightLimit;
	}

	/**Set the weight limit for the package
	 * @param weightLimit the limit to set
	 */
	public void setWeightLimit(double weightLimit) {
		this.weightLimit = weightLimit;
	}

	/**Fluid API method for setting weightLimit
	 * @param weightLimit the weightLimit
	 * @return this object for method call chaining
	 */
	public PackerFileEntry withWeightLimitAs(double weightLimit) {
		setWeightLimit(weightLimit);
		return this;
	}

	/**Get the list of items to be considered for packaging
	 * @return the package items as a list
	 */
	public List<PackageItem> getPackageItems() {
		return packageItems;
	}
	
	/**Add a package item to the list
	 * @param packageItem the package item to add
	 * @return this object for method call chaining
	 * @throws APIException if the index is more than one greater, or less than the current 
	 * expected index
	 */
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
		return Double.doubleToLongBits(weightLimit) == Double.doubleToLongBits(other.weightLimit);
	}
	
	
}
