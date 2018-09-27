package packager.challenge.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ PackageItemTest.class, PackageTest.class, PackerFileEntryTest.class, PackerFileParserFactoryTest.class,
		PackerFileParserTest.class, PackerTest.class })
public class AllPackerTests {}
