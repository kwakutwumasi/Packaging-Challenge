package packager.challenge.tests;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;

import java.io.ByteArrayInputStream;
import java.io.File;

import org.junit.Test;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.fileparser.impl.PackerFileParserImpl;
import com.mobiquityinc.packer.model.PackerFileEntry;

public class PackerFileParserTest {

	PackerFileParserImpl parserImpl = new PackerFileParserImpl();
	
	@Test
	public void testParser() throws Exception {
		PackerFileEntry[] entries = parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"test.parserfile");
		assertThat(entries.length, is(6));
		assertThat(entries[0].getWeightLimit(), is(81d));
		assertThat(entries[0].getPackageItems().size(), is(6));
		assertThat(entries[0].getPackageItems().get(0).getIndex(), is(1));
		assertThat(entries[0].getPackageItems().get(0).getCost(), is(45d));
		assertThat(entries[0].getPackageItems().get(0).getWeight(), is(53.38d));
		//No need to test each entry. Just check the last entry. If the last entry is correct,
		//then all entries would have been parsed
		assertThat(entries[5].getWeightLimit(), is(50d));
		assertThat(entries[5].getPackageItems().size(), is(4));
		assertThat(entries[5].getPackageItems().get(3).getIndex(), is(4));
		assertThat(entries[5].getPackageItems().get(3).getCost(), is(70d));
		assertThat(entries[5].getPackageItems().get(3).getWeight(), is(8.70d));
	}
	
	//Test some parser edge cases	
	@Test
	public void testSpacedEntries() throws Exception {
		ByteArrayInputStream testStream = new ByteArrayInputStream("81 :	( 1 ,  53.38	,€45 )".getBytes());
		PackerFileEntry[] entries = parserImpl.parse(testStream);
		assertThat(entries.length, is(1));
		assertThat(entries[0].getWeightLimit(), is(81d));
		assertThat(entries[0].getPackageItems().size(), is(1));
		assertThat(entries[0].getPackageItems().get(0).getIndex(), is(1));
		assertThat(entries[0].getPackageItems().get(0).getCost(), is(45d));
		assertThat(entries[0].getPackageItems().get(0).getWeight(), is(53.38d));
	}

	@Test(expected=APIException.class)
	public void testUnSpacedLine() throws Exception {
		ByteArrayInputStream testStream = new ByteArrayInputStream("81:(1,53.38,€45)(2,88.62,€98)".getBytes());
		parserImpl.parse(testStream);
	}

	
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
