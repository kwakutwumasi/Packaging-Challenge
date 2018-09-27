package packager.challenge.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.model.PackageItem;
import com.mobiquityinc.packer.model.PackerFileEntry;

public class PackerFileEntryTest {

	@Test
	public void testHashCodeEquals() throws Exception {
		PackageItem item1 = new PackageItem().withCostAs(10d)
				.withIndexAs(1)
				.withWeightAs(20d),
				item2 = new PackageItem().withCostAs(11d)
				.withIndexAs(2)
				.withWeightAs(30d);
		
		PackerFileEntry entry1 = new PackerFileEntry().withWeightLimitAs(74)
				.add(item1)
				.add(item2),
				entry2 = new PackerFileEntry().withWeightLimitAs(74)
				.add(item1)
				.add(item2);
		assertEquals(entry1, entry1);
		assertEquals(entry1, entry2);
		assertEquals(entry1.hashCode(), entry2.hashCode());
		assertFalse(entry1.equals(null));
		assertFalse(entry1.equals(new PackerFileEntry() {}));
		assertFalse(entry1.equals(new PackerFileEntry()));
		assertFalse(entry1.equals(new PackerFileEntry().withWeightLimitAs(11)
				.add(item1)
				.add(item2)));
	}
	
	@Test(expected=APIException.class)
	public void testInvalidIndex() throws Exception {
		new PackerFileEntry().withWeightLimitAs(74)
		.add(new PackageItem().withCostAs(10d)
				.withIndexAs(1)
				.withWeightAs(20d))
		.add(new PackageItem().withCostAs(11d)
				.withIndexAs(3)
				.withWeightAs(30d));
	}
}
