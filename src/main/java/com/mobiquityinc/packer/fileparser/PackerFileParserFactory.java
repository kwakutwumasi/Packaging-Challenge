package com.mobiquityinc.packer.fileparser;

import java.util.Iterator;
import java.util.ServiceLoader;

import com.mobiquityinc.packer.fileparser.impl.PackerFileParserImpl;

public class PackerFileParserFactory {
	private PackerFileParserFactory() {}
	
	private static PackerFileParser parser;
	
	public static PackerFileParser get() {
		if(parser==null) {
			Iterator<PackerFileParser> packerFileParseIterator = 
					ServiceLoader.load(PackerFileParser.class).iterator();
			
			if(packerFileParseIterator.hasNext()) {
				parser = packerFileParseIterator.next();
			} else {
				parser = new PackerFileParserImpl();
			}
		}
		
		return parser;
	}
}
