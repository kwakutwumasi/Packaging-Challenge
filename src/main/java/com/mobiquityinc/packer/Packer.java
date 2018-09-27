package com.mobiquityinc.packer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.exception.APIRuntimeException;
import com.mobiquityinc.packer.fileparser.PackerFileParser;
import com.mobiquityinc.packer.fileparser.PackerFileParserFactory;
import com.mobiquityinc.packer.i18n.Messages;
import com.mobiquityinc.packer.model.PackerFileEntry;
import com.mobiquityinc.packer.model.Package;
import com.mobiquityinc.packer.model.PackageItem;

/**A packer that takes as input a the maximum package weight of a package
 * and a list items. Each item has an index, a weight and price. The packer returns a list of indexes
 * of items whose total weight falls below the maximum weight, and whose total cost is the highest
 * @author Kwaku Twumasi-Afriyie <kwaku.twumasi@quakearts.com>
 *
 */
public class Packer {
	/**A utility method for processing a test file. Each line of the file follows the format 
	 * ''WEIGHTLIMIT : (INDEX, WEIGHT, COST) (INDEX, WEIGHT, COST) ...'' where WEIGHTLIMIT and WEIGHT is a valid number, 
	 *  INDEX is a valid integer and COST is of the format CURRENCYSYMBOL+AMOUNT, ex $20
	 * @param testFile the file containing packer file entries
	 * @return A string containing the list of packages that 
	 * fall below the maximum weight and have the highest total cost
	 * @throws APIException
	 */
	public static String pack(String testFile) throws APIException {
		Packer packer = new Packer();
		PackerFileEntry[] entries = packer.readFromFile(testFile);
		StringBuilder builder = new StringBuilder();
		for(PackerFileEntry entry:entries) {
			Package packedPackage = packer.pack(entry);
			if(builder.length()!=0)
				builder.append("\n");
				
			builder.append(packedPackage);
		}
		return builder.toString();
	}
	
	private PackerFileParser parser = PackerFileParserFactory.get();
	
	private PackerFileEntry[] readFromFile(String fileName) throws APIException{
		return parser.parse(fileName);
	}

	/**Pack an set of items with that have a weight less then the {@linkplain PackerFileEntry#getWeightLimit()}
	 * and the highest cost
	 * @param packerFileEntry a {@linkplain PackerFileEntry}
	 * @return The package that has a list of items below the maximum weight, but with the highest cost
	 * @throws APIException if there is a problem with the entry file
	 */
	public Package pack(PackerFileEntry packerFileEntry) throws APIException {
		if(packerFileEntry==null||packerFileEntry.getPackageItems().isEmpty())
			throw new APIException(Messages.get("invalid.packer.entry"));
			
		//We need to partition the list of items. This will help us gather a list of candidate
		//entries. We use a synchronized list to make it possible to parallelize the recursive operations
		List<Package> candidatePackages = Collections.synchronizedList(new ArrayList<>());
		//We use recursion to build our candidate lists. We are creating a binary tree. We use a binary tree
		//because the path to the leaf nodes describe every possible way we can combine items from the input list. 
		//We need to do this in order to determine which selection meets the criteria. 
		//This is the first recursive call. We return a future so that we can wait for the entire tree
		//to be built and traversed. The index parameter is set to zero, to indicate that we are considering the first
		//item in the list
		CompletableFuture<?> future = findCandidates(packerFileEntry, candidatePackages, 0);
		
		try {
			//Call get() to wait for the asynchronous call to return
			future.get();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch( ExecutionException e) {
			throw new APIRuntimeException("Unable to process items", e);
		}
		
		//If no item met the criteria, our candidate list will be empty. Return an empty package
		if(candidatePackages.isEmpty())
			return new Package();

		//There is no need to check for the highest costing package if there is only one package
		if(candidatePackages.size()==1)
			return candidatePackages.get(0);
		
		//Remove the first package and set it as the highest costing package
		Package returnPackage = candidatePackages.remove(0);
		
		//Loop through the other candidates and find one that is better
		for(Package candidatePackage:candidatePackages) {
			//If the total cost is the same, don't change it. Only consider better candidates
			if(returnPackage.getTotalCost()<candidatePackage.getTotalCost())
				returnPackage = candidatePackage;
		}
		
		return returnPackage;
	}
	
	//This is the head of the recursive calls. Simply call through on an asynchronous thread
	private CompletableFuture<?> findCandidates(PackerFileEntry packerFileEntry, 
			List<Package> candidatePackages, int index) {
		return CompletableFuture.runAsync(()->findCandidates(packerFileEntry, candidatePackages, index, null));
	}
	
	private void findCandidates(PackerFileEntry packerFileEntry,
			List<Package> candidatePackages, int index, Package currentPackage) {
		//This is the terminal call. Once we get here, the package will contain a candidate list of items
		//to package
		if(index==packerFileEntry.getPackageItems().size()) {
			if(!currentPackage.isEmpty()) //One recursive chain will always return an empty package. We ignore it.
				candidatePackages.add(currentPackage);
			
			return;
		}
		
		//At each recursive call we clone the package.
		//This is because at each stage of the tree building/traversal there are two possibilities:
		//We can add the current item to the list, or ignore it. The next recursive calls will receive
		//one of the list. It will clone them as well, and consider whether to add the next item to one
		//list or not. Each time this is done, we are creating one of the 2^N possible combinations of
		//items in this list (N is the number of items in the list)
		Package packageWithItem;
		Package packageWithoutItem;
	
		//The first call always creates new objects
		if(currentPackage==null) {
			packageWithItem = new Package();
			packageWithoutItem = new Package();
		} else {
			//Clone the list that was passed in. Depending on which leg of the recursive call this is,
			//the list would either contain the previous item or not
			packageWithItem = currentPackage.createClone();
			packageWithoutItem = currentPackage.createClone();
		}
		
		//Pick the current item under consideration, using the index that was passed in
		PackageItem currentPackageItem = packerFileEntry.getPackageItems().get(index);
		List<CompletableFuture<?>> futures = new ArrayList<>();

		//Now we can do some optimization here. Since we are only considering items whose total weight is 
		//less than the specified weight limit, we check to see if the total weight + the weight of the current
		//item is greater than the limit. If it is, then we should not consider adding the item to a candidate list.
		//We simply bypass the item and forgo all possible combinations that would contain the item
		if(packageWithItem.getTotalWeight()+currentPackageItem.getWeight()<=packerFileEntry.getWeightLimit()) {
			packageWithItem.add(currentPackageItem);
			futures.add(CompletableFuture.runAsync(()->
				findCandidates(packerFileEntry, candidatePackages, index+1, packageWithItem)));
		}
		
		//This is called at each iteration, since all possible lists also include lists that do not contain the current item
		futures.add(CompletableFuture.runAsync(()->
			findCandidates(packerFileEntry, candidatePackages, index+1, packageWithoutItem)));

		//We wait for the other calls to return (we need to consider every possible item for this recursive call
		for(CompletableFuture<?> future:futures)
			try {
				future.get();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			} catch (ExecutionException e) {
				throw new APIRuntimeException("Unable to process item", e);
			}
	}
}
