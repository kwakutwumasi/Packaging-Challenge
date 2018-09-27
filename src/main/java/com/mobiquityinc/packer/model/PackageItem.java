package com.mobiquityinc.packer.model;

/**Model for holding package items
 * @author Kwaku Twumasi-Afriyie <kwaku.twumasi@quakearts.com>
 *
 */
public class PackageItem {
	private int index;
	private double weight;
	private double cost;
	private String currency;

	/**Get the item's index
	 * @return the index as an integer
	 */
	public int getIndex() {
		return index;
	}

	/**Set the item's index
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**Fluid API method for setting index
	 * @param index the index
	 * @return this object for method call chaining
	 */
	public PackageItem withIndexAs(int index) {
		setIndex(index);
		return this;
	}
	
	/**Get the item's weight
	 * @return the weight as a double value
	 */
	public double getWeight() {
		return weight;
	}
	
	/**Set the item's weight
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**Fluid API method for setting weight
	 * @param weight the weight value
	 * @return this object for method call chaining
	 */
	public PackageItem withWeightAs(double weight) {
		setWeight(weight);
		return this;
	}

	/**Get the item's cost
	 * @return the cost as a double value
	 */
	public double getCost() {
		return cost;
	}

	/**Set the item's cost
	 * @param cost the cost to set
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

	/**Fluid API method for setting cost
	 * @param cost the cost value
	 * @return this object for method call chaining
	 */
	public PackageItem withCostAs(double cost) {
		setCost(cost);
		return this;
	}
	
	/**Get the item's currency
	 * @return the currency as a string
	 */
	public String getCurrency() {
		return currency;
	}
	
	/**Set the item's currency
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	/**Fluid API method for setting currency
	 * @param currency the currency
	 * @return this object for method call chaining
	 */
	public PackageItem withCurrencyAs(String currency) {
		setCurrency(currency);
		return this;
	}
}
