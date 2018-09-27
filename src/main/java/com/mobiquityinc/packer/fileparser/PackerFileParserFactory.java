package com.mobiquityinc.packer.fileparser;

import java.util.Iterator;
import java.util.ServiceLoader;

import com.mobiquityinc.packer.fileparser.impl.PackerFileParserImpl;

/**Factory method for creating a parser. Searches the class path for a file
 * META-INF/services/com.mobiquityinc.packer.fileparser.PackerFileParser. It picks the first
 * entry in the file. The entry must be the name of a class that implements the {@linkplain PackerFileParser}
 * interface. It then loads this class using the current classloader
 * @author Kwaku Twumasi-Afriyie <kwaku.twumasi@quakearts.com>
 *
 */
public class PackerFileParserFactory {
	private PackerFileParserFactory() {}
	
	private static PackerFileParser parser;
	
	/**Get the configured parser
	 * @return the parser
	 */
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
	
	/**Convenience method for setting the current parser
	 * Setting the parser to null will force a new parser to be loaded
	 * by the service loader
	 * @param newParser the new parser to use
	 */
	public static void setParser(PackerFileParser newParser) {
		parser = newParser;
	}
}
