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

public class Packer {
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

	public Package pack(PackerFileEntry packerFileEntry) throws APIException {
		if(packerFileEntry==null||packerFileEntry.getPackageItems().isEmpty())
			throw new APIException(Messages.get("invalid.packer.entry"));
			
		List<Package> candidatePackages = Collections.synchronizedList(new ArrayList<>());
		CompletableFuture<?> future = findCandidates(packerFileEntry, candidatePackages, 0);
		
		try {
			future.get();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch( ExecutionException e) {
			throw new APIRuntimeException("Unable to process items", e);
		}
		
		if(candidatePackages.isEmpty())
			return new Package();

		if(candidatePackages.size()==1)
			return candidatePackages.get(0);

		Package returnPackage = candidatePackages.remove(0);
		
		for(Package candidatePackage:candidatePackages) {
			if(returnPackage.getTotalCost()<candidatePackage.getTotalCost())
				returnPackage = candidatePackage;
		}
		
		return returnPackage;
	}
	
	private CompletableFuture<?> findCandidates(PackerFileEntry packerFileEntry, 
			List<Package> candidatePackages, int i) {
		return CompletableFuture.runAsync(()->findCandidates(packerFileEntry, candidatePackages, i, null));
	}
	
	private void findCandidates(PackerFileEntry packerFileEntry,
			List<Package> candidatePackages, int i, Package currentPackage) {
		if(i==packerFileEntry.getPackageItems().size()) {
			if(!currentPackage.isEmpty())
				candidatePackages.add(currentPackage);
			
			return;
		}
		
		Package packageWithItem;
		Package packageWithoutItem;
	
		if(currentPackage==null) {
			packageWithItem = new Package();
			packageWithoutItem = new Package();
		} else {
			packageWithItem = currentPackage.createClone();
			packageWithoutItem = currentPackage.createClone();
		}
		
		PackageItem currentPackageItem = packerFileEntry.getPackageItems().get(i);
		List<CompletableFuture<?>> futures = new ArrayList<>();

		if(packageWithItem.getTotalWeight()+currentPackageItem.getWeight()<=packerFileEntry.getWeightLimit()) {
			packageWithItem.add(currentPackageItem);
			futures.add(CompletableFuture.runAsync(()->
				findCandidates(packerFileEntry, candidatePackages, i+1, packageWithItem)));
		}
		
		futures.add(CompletableFuture.runAsync(()->
			findCandidates(packerFileEntry, candidatePackages, i+1, packageWithoutItem)));

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
