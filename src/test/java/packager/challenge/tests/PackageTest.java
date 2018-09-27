package packager.challenge.tests;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;

import java.util.Arrays;

import com.mobiquityinc.packer.model.Package;
import com.mobiquityinc.packer.model.PackageItem;

import org.junit.Test;

public class PackageTest {

	@Test
	public void testPackage() {
		Package packageTest = new Package();
		packageTest.addAll(Arrays.asList(new PackageItem().withCostAs(10d)
				.withIndexAs(1)
				.withWeightAs(20d),new PackageItem().withCostAs(11d)
				.withIndexAs(2)
				.withWeightAs(30d)));
		
		assertThat(packageTest.getTotalCost(), is(21d));
		assertThat(packageTest.getTotalWeight(), is(50d));
		assertThat(packageTest.toString(), is("2\n_\n1,2"));
		packageTest.addAll(Arrays.asList(new PackageItem().withCostAs(10d)
				.withIndexAs(3)
				.withWeightAs(20d),new PackageItem().withCostAs(11d)
				.withIndexAs(4)
				.withWeightAs(30d)));
		assertThat(packageTest.toString(), is("4\n_\n1,2\n3,4"));
		assertThat(new Package().toString(), is("0\n_"));
	}

	@Test
	public void testCreateClone() throws Exception {
		Package packageTest = new Package();
		packageTest.addAll(Arrays.asList(new PackageItem().withCostAs(10d)
				.withIndexAs(1)
				.withWeightAs(20d),new PackageItem().withCostAs(11d)
				.withIndexAs(2)
				.withWeightAs(30d)));
		
		Package packageClone = packageTest.createClone();
		
		assertEquals(packageClone, packageTest);
		assertFalse(packageClone == packageTest);
	}
}
