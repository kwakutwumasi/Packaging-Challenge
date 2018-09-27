package com.mobiquityinc.packer.fileparser.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.fileparser.PackerFileParser;
import com.mobiquityinc.packer.i18n.Messages;
import com.mobiquityinc.packer.model.PackageItem;
import com.mobiquityinc.packer.model.PackerFileEntry;

public class PackerFileParserImpl implements PackerFileParser {

	private static final Map<String, Boolean> currencyMap = new HashMap<>();
	static {
		currencyMap.put("$", Boolean.TRUE);
		currencyMap.put("€", Boolean.TRUE);
	}
	
	@Override
	public PackerFileEntry[] parse(InputStream inputStream) throws APIException, IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		List<PackerFileEntry> entries = new ArrayList<>();
		
		String line;
		int count=0;
		while ((line = reader.readLine())!=null) {
			processLine(line, entries,++count);
		}
		
		if(entries.isEmpty())
			throw new APIException(Messages.get("empty.file"));
		
		return entries.toArray(new PackerFileEntry[entries.size()]);
	}

	private void processLine(String line, List<PackerFileEntry> entries,int lineCount) throws APIException {
		String[] lineParts = line.split("\\s+:\\s+",2);
		Double weightLimit; 
		try {
			weightLimit = Double.parseDouble(lineParts[0]);
		} catch (NumberFormatException e) {
			throw new APIException(MessageFormat.format(Messages.get("invalid.weight.limit"), line, lineCount));
		}
		
		if(lineParts.length!=2)
			throw new APIException(MessageFormat.format(Messages.get("invalid.line"), line, lineCount));
		
		PackerFileEntry entry = new PackerFileEntry().withWeightLimitAs(weightLimit);
		entries.add(entry);
		processEntries(entry, lineParts[1], lineCount);
	}

	private void processEntries(PackerFileEntry entry, String itemsString, int lineCount) throws APIException {
		String[] items = itemsString.split("\\s+");
		for(String item:items) {
			if(item.startsWith("(")
					&& item.endsWith(")")) {				
				processEntry(entry,item.substring(1,item.length()-1), lineCount);
			} else {
				throw new APIException(MessageFormat.format(Messages.get("invalid.item"), item, lineCount));
			}
		}
	}

	private void processEntry(PackerFileEntry entry, String item, int lineCount) throws APIException {
		String[] itemParts = item.split(",",3);
		if(itemParts.length!=3)
			throw new APIException(MessageFormat.format(Messages.get("invalid.item"), item, lineCount));
			
		int index;
		try {
			index = Integer.parseInt(itemParts[0]);
		} catch (NumberFormatException e) {
			throw new APIException(MessageFormat.format(Messages.get("invalid.index.file"), item, lineCount,e.getMessage()));
		}
		
		double weight;
		try {
			weight = Double.parseDouble(itemParts[1]);
		} catch (NumberFormatException e) {
			throw new APIException(MessageFormat.format(Messages.get("invalid.weight"), item, lineCount));
		}

		if(itemParts[2].length()<2) {
			throw new APIException(MessageFormat.format(Messages.get("invalid.cost"), item, lineCount));
		}
			
		String currency = itemParts[2].substring(0, 1);
		
		if(!currencyMap.containsKey(currency))
			throw new APIException(MessageFormat.format(Messages.get("invalid.currency"), item, lineCount));
		
		String costString = itemParts[2].substring(1);
		
		double cost;
		try {
			cost = Double.parseDouble(costString);
		} catch (NumberFormatException e) {
			throw new APIException(MessageFormat.format(Messages.get("invalid.cost"), item, lineCount));
		}

		try {			
			entry.add(new PackageItem()
					.withIndexAs(index)
					.withWeightAs(weight)
					.withCurrencyAs(currency)
					.withCostAs(cost));
		} catch (APIException e) {
			throw new APIException(MessageFormat.format(Messages.get("invalid.index.file"), item, lineCount, 
					e.getMessage()));			
		}
	}

}
