package com.mobiquityinc.packer.model;

import java.util.ArrayList;

/**Model for holding items in a package
 * @author Kwaku Twumasi-Afriyie <kwaku.twumasi@quakearts.com>
 *
 */
public class Package extends ArrayList<PackageItem> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6352721023849576350L;

	class Totaller {
		double total;
		synchronized void add(double amount){
			total+=amount;
		}
		
		double getTotal() {
			return total;
		}
	}

	/**Get the total cost of the items in this package
	 * @return the total cost as a double value
	 */
	public double getTotalCost() {
		final Totaller totaller = new Totaller();
		forEach(item->totaller.add(item.getCost()));
		return totaller.getTotal();
	}
	
	/**Get the total weight of the items in this package
	 * @return the total weight as a double value
	 */
	public double getTotalWeight() {
		final Totaller totaller = new Totaller();
		forEach(item->totaller.add(item.getWeight()));
		return totaller.getTotal();
	}
		
	/**Convenience method for creating a clone of this package
	 * @return
	 */
	public Package createClone() {
		return (Package) super.clone();
	}
	
	@Override
	public String toString() {
		if(size()==0)
			return "_";
		
		StringBuilder builder = new StringBuilder();
		forEach(item->builder.append(builder.length()>0?",":"").append(item.getIndex()));
		
		return builder.toString();
	}
}
