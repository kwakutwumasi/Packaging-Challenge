package packager.challenge.tests;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;

import org.junit.Test;

import com.mobiquityinc.packer.Packer;

public class PackerTest {

	@Test
	public void testPack() throws Exception {
		String results = Packer.pack("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"test.parserfile");

		BufferedReader bufferedReader = new BufferedReader(new StringReader(results));
		assertThat(bufferedReader.readLine(), is("4"));
		assertThat(bufferedReader.readLine(), is("_"));
		assertThat(bufferedReader.readLine(), is("2,7"));
		assertThat(bufferedReader.readLine(), is("8,9"));
		assertThat(bufferedReader.readLine(), is("_"));
		assertThat(bufferedReader.readLine(), is("1,2,3,4"));
		assertThat(bufferedReader.readLine(), is("2,5,9"));
		String next = bufferedReader.readLine();
		assertTrue(next.equals("1,2") || next.equals("2,3") || next.equals("3,4"));
		next = bufferedReader.readLine();
		assertTrue(next.equals("1") || next.equals("2"));
	}

}
