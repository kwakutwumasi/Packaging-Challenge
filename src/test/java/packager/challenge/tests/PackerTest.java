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

}
