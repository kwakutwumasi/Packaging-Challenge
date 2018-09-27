package packager.challenge.tests;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;

import org.junit.Test;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.Packer;

public class PackerTest {

	@Test
	public void testPack() throws Exception {
		String results = Packer.pack("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"test.parserfile");

		BufferedReader bufferedReader = new BufferedReader(new StringReader(results));
		assertThat(bufferedReader.readLine(), is("1"));
		assertThat(bufferedReader.readLine(), is("_"));
		assertThat(bufferedReader.readLine(), is("4"));
		assertThat(bufferedReader.readLine(), is("0"));
		assertThat(bufferedReader.readLine(), is("_"));
		assertThat(bufferedReader.readLine(), is("2"));
		assertThat(bufferedReader.readLine(), is("_"));
		assertThat(bufferedReader.readLine(), is("2,7"));
		assertThat(bufferedReader.readLine(), is("1"));
		assertThat(bufferedReader.readLine(), is("_"));
		assertThat(bufferedReader.readLine(), is("6"));
		assertThat(bufferedReader.readLine(), is("0"));
		assertThat(bufferedReader.readLine(), is("_"));
		assertThat(bufferedReader.readLine(), is("4"));
		assertThat(bufferedReader.readLine(), is("_"));
		assertThat(bufferedReader.readLine(), is("1,2"));
		assertThat(bufferedReader.readLine(), is("3,4"));
	}

	@Test(expected=APIException.class)
	public void testParseEmpty() throws Exception {
		Packer.pack("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testEmpty.parserfile");
	}
	
	@Test(expected=APIException.class)
	public void testParseEmptyIndex() throws Exception {
		Packer.pack("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testEmptyIndex.parserfile");
	}

	@Test(expected=APIException.class)
	public void testParseEmptyPackageLimit() throws Exception {
		Packer.pack("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testEmptyPackageLimit.parserfile");
	}
	
	@Test(expected=APIException.class)
	public void testParseEmptyPrice() throws Exception {
		Packer.pack("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testEmptyPrice.parserfile");
	}

	@Test(expected=APIException.class)
	public void testParseEmptyWeight() throws Exception {
		Packer.pack("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testEmptyWeight.parserfile");
	}

	@Test(expected=APIException.class)
	public void testParseInvalidEntry() throws Exception {
		Packer.pack("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testInvalidEntry.parserfile");
	}

	@Test(expected=APIException.class)
	public void testParseInvalidIndex() throws Exception {
		Packer.pack("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testInvalidIndex.parserfile");
	}
	
	@Test(expected=APIException.class)
	public void testParseInvalidPackageLimit1() throws Exception {
		Packer.pack("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testInvalidPackageLimit1.parserfile");
	}

	@Test(expected=APIException.class)
	public void testParseInvalidPackageLimit2() throws Exception {
		Packer.pack("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testInvalidPackageLimit2.parserfile");
	}
	
	@Test(expected=APIException.class)
	public void testParseInvalidPrice1() throws Exception {
		Packer.pack("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testInvalidPrice1.parserfile");
	}

	@Test(expected=APIException.class)
	public void testParseInvalidPrice2() throws Exception {
		Packer.pack("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testInvalidPrice2.parserfile");
	}
	
	@Test(expected=APIException.class)
	public void testParseInvalidPrice3() throws Exception {
		Packer.pack("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testInvalidPrice3.parserfile");
	}

	@Test(expected=APIException.class)
	public void testParseInvalidPrice4() throws Exception {
		Packer.pack("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testInvalidPrice4.parserfile");
	}

	@Test(expected=APIException.class)
	public void testParseMissingEntry() throws Exception {
		Packer.pack("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testMissingEntry.parserfile");
	}

	@Test(expected=APIException.class)
	public void testParseMissingIndex() throws Exception {
		Packer.pack("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testMissingIndex.parserfile");
	}
	
	@Test(expected=APIException.class)
	public void testParseMissingPackageLimit() throws Exception {
		Packer.pack("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testMissingPackageLimit.parserfile");
	}

	@Test(expected=APIException.class)
	public void testParseMissingPrice() throws Exception {
		Packer.pack("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testMissingPrice.parserfile");
	}
	
	@Test(expected=APIException.class)
	public void testParseMissingWeight() throws Exception {
		Packer.pack("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"testMissingWeight.parserfile");
	}
	
}