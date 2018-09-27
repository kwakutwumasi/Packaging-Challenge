package packager.challenge.tests;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;
import java.text.MessageFormat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.Packer;
import com.mobiquityinc.packer.model.PackerFileEntry;

public class PackerTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Test
	public void testPack() throws Exception {
		String results = Packer.pack("src"+File.separator+"test"
				+File.separator+"resources"
				+File.separator+"test.parserfile");

		BufferedReader bufferedReader = new BufferedReader(new StringReader(results));
		assertThat(bufferedReader.readLine(), is("4"));
		assertThat(bufferedReader.readLine(), is("_"));
		assertThat(bufferedReader.readLine(), is("1"));
		assertThat(bufferedReader.readLine(), is("2,7"));
		String next = bufferedReader.readLine();
		assertTrue(MessageFormat.format("Next is invalid: {0}", next), next.equals("6,9") || next.equals("8,9"));
		assertThat(bufferedReader.readLine(), is("_"));
		assertThat(bufferedReader.readLine(), is("1,2,3,4"));
		assertThat(bufferedReader.readLine(), is("5,8,9"));
		next = bufferedReader.readLine();
		assertTrue(MessageFormat.format("Next is invalid: {0}", next), 
				next.equals("1,2") || 
				next.equals("1,3") || 
				next.equals("1,4") || 
				next.equals("2,3") || 
				next.equals("2,4") || 
				next.equals("3,4"));
		next = bufferedReader.readLine();
		assertTrue(MessageFormat.format("Next is invalid: {0}", next), next.equals("1") 
				|| next.equals("2")
				|| next.equals("3")
				|| next.equals("4"));
	}

	@Test
	public void testPackNullEntry() throws Exception {
		expectedException.expect(APIException.class);
		expectedException.expectMessage("The packer entries passed to the pack method must not be empty");
		Packer packer = new Packer();
		PackerFileEntry entry = null;
		packer.pack(entry);
	}
	
	@Test
	public void testPackEmptyEntry() throws Exception {
		expectedException.expect(APIException.class);
		expectedException.expectMessage("The packer entries passed to the pack method must not be empty");
		Packer packer = new Packer();
		packer.pack(new PackerFileEntry());
	}

}
