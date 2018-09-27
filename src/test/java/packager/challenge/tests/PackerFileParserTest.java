package packager.challenge.tests;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;

import java.io.ByteArrayInputStream;
import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.fileparser.impl.PackerFileParserImpl;
import com.mobiquityinc.packer.model.PackerFileEntry;

public class PackerFileParserTest {

	PackerFileParserImpl parserImpl = new PackerFileParserImpl();
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Test
	public void testParser() throws Exception {
		PackerFileEntry[] entries = parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"test.parserfile");
		assertThat(entries.length, is(9));
		assertThat(entries[0].getWeightLimit(), is(81d));
		assertThat(entries[0].getPackageItems().size(), is(6));
		assertThat(entries[0].getPackageItems().get(0).getIndex(), is(1));
		assertThat(entries[0].getPackageItems().get(0).getCost(), is(45d));
		assertThat(entries[0].getPackageItems().get(0).getCurrency(), is("€"));
		assertThat(entries[0].getPackageItems().get(0).getWeight(), is(53.38d));
		//No need to test each entry. Just check the last entry. If the last entry is correct,
		//then all entries would have been parsed
		assertThat(entries[8].getWeightLimit(), is(50.80d));
		assertThat(entries[8].getPackageItems().size(), is(4));
		assertThat(entries[8].getPackageItems().get(3).getIndex(), is(4));
		assertThat(entries[8].getPackageItems().get(3).getCost(), is(100d));
		assertThat(entries[8].getPackageItems().get(3).getCurrency(), is("€"));
		assertThat(entries[8].getPackageItems().get(3).getWeight(), is(91d));
	}
	
	//Test some parser edge cases	
	@Test
	public void testSpacedEntries() throws Exception {
		expectedException.expect(APIException.class);
		expectedException.expectMessage(is("The item entry could not be parsed. "
				+ "Ensure that each entry follows the format '(INDEX, WEIGHT, COST)' where WEIGHT is a valid number, "
				+ "INDEX is a valid integer and COST is of the format CURRENCYSYMBOL+AMOUNT, ex $20. The error occurred on line 1 (()"));
		ByteArrayInputStream testStream = new ByteArrayInputStream("81 :	( 1 ,  53.38	,€45 )".getBytes());
		parserImpl.parse(testStream);
	}

	@Test
	public void testUnSpacedLine() throws Exception {
		expectedException.expect(APIException.class);
		expectedException.expectMessage(is("The weight limit could not be parsed. Ensure that each line follows the format "
				+ "'WEIGHTLIMIT : (INDEX, WEIGHT, COST) (INDEX, WEIGHT, COST) ...' where WEIGHTLIMIT and WEIGHT is a valid number, "
				+ "INDEX is a valid integer and COST is of the format CURRENCYSYMBOL+AMOUNT, ex $20. "
				+ "The error occurred on line 1 (81:(1,53.38,€45)(2,88.62,€98))"));
		ByteArrayInputStream testStream = new ByteArrayInputStream("81:(1,53.38,€45)(2,88.62,€98)".getBytes());
		parserImpl.parse(testStream);
	}

	@Test
	public void testEmptyMissingEntries() throws Exception {
		expectedException.expect(APIException.class);
		expectedException.expectMessage(is("A file line could not be parsed. Ensure that each line follows the format "
				+ "'WEIGHTLIMIT : (INDEX, WEIGHT, COST) (INDEX, WEIGHT, COST) ...' where WEIGHTLIMIT and WEIGHT is a "
				+ "valid number, INDEX is a valid integer and COST is of the format CURRENCYSYMBOL+AMOUNT, ex $20. "
				+ "The error occurred on line 1 (81)"));
		ByteArrayInputStream testStream = new ByteArrayInputStream("81".getBytes());
		parserImpl.parse(testStream);
	}

	@Test
	public void testParseEmpty() throws Exception {
		expectedException.expect(APIException.class);
		expectedException.expectMessage(is("The file passed to the pack method contained no entries"));
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testEmpty.parserfile");
	}
	
	@Test
	public void testParseEmptyIndex() throws Exception {
		expectedException.expect(APIException.class);
		expectedException.expectMessage(is("Invalid index for entry on line 1 (,53.38,€45). For input string: \"\""));
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testEmptyIndex.parserfile");
	}

	@Test
	public void testParseEmptyPackageLimit() throws Exception {
		expectedException.expect(APIException.class);
		expectedException.expectMessage(is("The weight limit could not be parsed. Ensure that each line follows "
				+ "the format 'WEIGHTLIMIT : (INDEX, WEIGHT, COST) (INDEX, WEIGHT, COST) ...' where WEIGHTLIMIT "
				+ "and WEIGHT is a valid number, INDEX is a valid integer and COST is of the format CURRENCYSYMBOL+AMOUNT, ex $20. "
				+ "The error occurred on line 1 (: (1,53.38,€45) (2,88.62,€98) (3,72.30,€76) (4,30.18,€9) (5,46.34,€48))"));
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testEmptyPackageLimit.parserfile");
	}
	
	@Test
	public void testParseEmptyPrice() throws Exception {
		expectedException.expect(APIException.class);
		expectedException.expectMessage(is("The cost entry could not be parsed. Ensure that it is of the format "
				+ "CURRENCYSYMBOL+AMOUNT, ex $20. The error occurred on line 1 (1,53.38,)"));
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testEmptyPrice.parserfile");
	}

	@Test
	public void testParseEmptyWeight() throws Exception {
		expectedException.expect(APIException.class);
		expectedException.expectMessage(is("The weight entry could not be parsed. Ensure that it is a valid number. "
				+ "The error occurred on line 1 (1,,53.38)"));
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testEmptyWeight.parserfile");
	}

	@Test
	public void testParseInvalidEntry() throws Exception {
		expectedException.expect(APIException.class);
		expectedException.expectMessage(is("The item entry could not be parsed. Ensure that each entry follows the format "
				+ "'(INDEX, WEIGHT, COST)' where WEIGHT is a valid number, INDEX is a valid integer and COST is of the "
				+ "format CURRENCYSYMBOL+AMOUNT, ex $20. The error occurred on line 1 (53.38,1,€45)"));
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testInvalidEntry.parserfile");
	}

	@Test
	public void testParseInvalidIndex() throws Exception {
		expectedException.expect(APIException.class);
		expectedException.expectMessage(is("Invalid index for entry on line 1 (Hello,53.38,€45). For input string: \"Hello\""));
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testInvalidIndex.parserfile");
	}
	
	@Test
	public void testParseInvalidPackageLimit1() throws Exception {
		expectedException.expect(APIException.class);
		expectedException.expectMessage(is("The weight limit could not be parsed. Ensure that each line follows the format "
				+ "'WEIGHTLIMIT : (INDEX, WEIGHT, COST) (INDEX, WEIGHT, COST) ...' where WEIGHTLIMIT and WEIGHT is a valid "
				+ "number, INDEX is a valid integer and COST is of the format CURRENCYSYMBOL+AMOUNT, ex $20. The error occurred "
				+ "on line 1 (Hello : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48))"));
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testInvalidPackageLimit1.parserfile");
	}

	@Test
	public void testParseInvalidPackageLimit2() throws Exception {
		expectedException.expect(APIException.class);
		expectedException.expectMessage(is("The weight limit could not be parsed. Ensure that each line follows the format "
				+ "'WEIGHTLIMIT : (INDEX, WEIGHT, COST) (INDEX, WEIGHT, COST) ...' where WEIGHTLIMIT and WEIGHT is a "
				+ "valid number, INDEX is a valid integer and COST is of the format CURRENCYSYMBOL+AMOUNT, ex $20. "
				+ "The error occurred on line 1 (81 (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) "
				+ "(6,46.34,€48))"));
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testInvalidPackageLimit2.parserfile");
	}
	
	@Test
	public void testParseInvalidPrice1() throws Exception {
		expectedException.expect(APIException.class);
		expectedException.expectMessage(is("The currency symbol was not recognized. The error occurred on line 1 (1,53.38,35)"));
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testInvalidPrice1.parserfile");
	}

	@Test
	public void testParseInvalidPrice2() throws Exception {
		expectedException.expect(APIException.class);
		expectedException.expectMessage(is("The cost entry could not be parsed. Ensure that it is of the format CURRENCYSYMBOL+AMOUNT,"
				+ " ex $20. The error occurred on line 1 (1,53.38,€)"));
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testInvalidPrice2.parserfile");
	}
	
	@Test
	public void testParseInvalidPrice3() throws Exception {
		expectedException.expect(APIException.class);
		expectedException.expectMessage(is("The currency symbol was not recognized. The error occurred on line 1 (1,53.38,\"€34\")"));
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testInvalidPrice3.parserfile");
	}

	@Test
	public void testParseInvalidPrice4() throws Exception {
		expectedException.expect(APIException.class);
		expectedException.expectMessage(is("The currency symbol was not recognized. The error occurred on line 1 (1,53.38,+34)"));
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testInvalidPrice4.parserfile");
	}

	@Test
	public void testParseMissingEntry() throws Exception {
		expectedException.expect(APIException.class);
		expectedException.expectMessage(is("Invalid index for entry on line 1 (4,72.30,€76). "
				+ "The index is not valid. Expected 3, but found 4"));
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testMissingEntry.parserfile");
	}

	@Test
	public void testParseMissingIndex() throws Exception {
		expectedException.expect(APIException.class);
		expectedException.expectMessage(is("The item entry could not be parsed. Ensure that each entry follows the format "
				+ "'(INDEX, WEIGHT, COST)' where WEIGHT is a valid number, INDEX is a valid integer and COST is of the "
				+ "format CURRENCYSYMBOL+AMOUNT, ex $20. The error occurred on line 1 (53.38,€45)"));
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testMissingIndex.parserfile");
	}
	
	@Test
	public void testParseMissingPackageLimit() throws Exception {
		expectedException.expect(APIException.class);
		expectedException.expectMessage(is("The weight limit could not be parsed. Ensure that each line follows the format "
				+ "'WEIGHTLIMIT : (INDEX, WEIGHT, COST) (INDEX, WEIGHT, COST) ...' where WEIGHTLIMIT and WEIGHT is a valid "
				+ "number, INDEX is a valid integer and COST is of the format CURRENCYSYMBOL+AMOUNT, ex $20. The error "
				+ "occurred on line 1 (: (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48))"));
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testMissingPackageLimit.parserfile");
	}

	@Test
	public void testParseMissingPrice() throws Exception {
		expectedException.expect(APIException.class);
		expectedException.expectMessage(is("The item entry could not be parsed. Ensure that each entry follows the format "
				+ "'(INDEX, WEIGHT, COST)' where WEIGHT is a valid number, INDEX is a valid integer and COST is of the "
				+ "format CURRENCYSYMBOL+AMOUNT, ex $20. The error occurred on line 1 (1,53.38)"));
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testMissingPrice.parserfile");
	}
	
	@Test
	public void testParseMissingWeight() throws Exception {
		expectedException.expect(APIException.class);
		expectedException.expectMessage(is("The item entry could not be parsed. Ensure that each entry follows the format "
				+ "'(INDEX, WEIGHT, COST)' where WEIGHT is a valid number, INDEX is a valid integer and COST is of the "
				+ "format CURRENCYSYMBOL+AMOUNT, ex $20. The error occurred on line 1 (1,53.38)"));
		parserImpl.parse("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testMissingWeight.parserfile");
	}
}
