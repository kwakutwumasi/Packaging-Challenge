package packager.challenge.tests;

import java.io.File;

import org.junit.Test;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.fileparser.impl.PackerFileParserImpl;

public class PackerFileParserTest {

	PackerFileParserImpl parserImpl = new PackerFileParserImpl();
	
	@Test(expected=APIException.class)
	public void testParseEmpty() throws Exception {
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testEmpty.parserfile");
	}
	
	@Test(expected=APIException.class)
	public void testParseEmptyIndex() throws Exception {
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testEmptyIndex.parserfile");
	}

	@Test(expected=APIException.class)
	public void testParseEmptyPackageLimit() throws Exception {
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testEmptyPackageLimit.parserfile");
	}
	
	@Test(expected=APIException.class)
	public void testParseEmptyPrice() throws Exception {
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testEmptyPrice.parserfile");
	}

	@Test(expected=APIException.class)
	public void testParseEmptyWeight() throws Exception {
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testEmptyWeight.parserfile");
	}

	@Test(expected=APIException.class)
	public void testParseInvalidEntry() throws Exception {
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testInvalidEntry.parserfile");
	}

	@Test(expected=APIException.class)
	public void testParseInvalidIndex() throws Exception {
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testInvalidIndex.parserfile");
	}
	
	@Test(expected=APIException.class)
	public void testParseInvalidPackageLimit1() throws Exception {
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testInvalidPackageLimit1.parserfile");
	}

	@Test(expected=APIException.class)
	public void testParseInvalidPackageLimit2() throws Exception {
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testInvalidPackageLimit2.parserfile");
	}
	
	@Test(expected=APIException.class)
	public void testParseInvalidPrice1() throws Exception {
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testInvalidPrice1.parserfile");
	}

	@Test(expected=APIException.class)
	public void testParseInvalidPrice2() throws Exception {
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testInvalidPrice2.parserfile");
	}
	
	@Test(expected=APIException.class)
	public void testParseInvalidPrice3() throws Exception {
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testInvalidPrice3.parserfile");
	}

	@Test(expected=APIException.class)
	public void testParseInvalidPrice4() throws Exception {
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testInvalidPrice4.parserfile");
	}

	@Test(expected=APIException.class)
	public void testParseMissingEntry() throws Exception {
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testMissingEntry.parserfile");
	}

	@Test(expected=APIException.class)
	public void testParseMissingIndex() throws Exception {
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testMissingIndex.parserfile");
	}
	
	@Test(expected=APIException.class)
	public void testParseMissingPackageLimit() throws Exception {
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testMissingPackageLimit.parserfile");
	}

	@Test(expected=APIException.class)
	public void testParseMissingPrice() throws Exception {
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testMissingPrice.parserfile");
	}
	
	@Test(expected=APIException.class)
	public void testParseMissingWeight() throws Exception {
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testMissingWeight.parserfile");
	}
}
