package packager.challenge.tests;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;

import org.junit.Test;

import com.mobiquityinc.packer.model.PackageItem;

public class PackageItemTest {

	@Test
	public void test() {
		PackageItem item = new PackageItem()
				.withCostAs(10d)
				.withCurrencyAs("$")
				.withIndexAs(1)
				.withWeightAs(30d);
		
		assertThat(item.getCost(), is(10d));
		assertThat(item.getCurrency(), is("$"));
		assertThat(item.getIndex(), is(1));
		assertThat(item.getWeight(), is(30d));
	}

}
