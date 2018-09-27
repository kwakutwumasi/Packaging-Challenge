package com.mobiquityinc.packer.model;

import java.util.ArrayList;

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

	public double getTotalCost() {
		final Totaller totaller = new Totaller();
		forEach(item->totaller.add(item.getCost()));
		return totaller.getTotal();
	}
	
	public double getTotalWeight() {
		final Totaller totaller = new Totaller();
		forEach(item->totaller.add(item.getWeight()));
		return totaller.getTotal();
	}
		
	public Package createClone() {
		return (Package) super.clone();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "";
	}
}
