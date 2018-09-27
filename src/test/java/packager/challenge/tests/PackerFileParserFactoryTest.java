package packager.challenge.tests;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

import static org.hamcrest.core.IsInstanceOf.*;

import org.junit.Test;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.fileparser.PackerFileParser;
import com.mobiquityinc.packer.fileparser.PackerFileParserFactory;
import com.mobiquityinc.packer.fileparser.impl.PackerFileParserImpl;
import com.mobiquityinc.packer.model.PackerFileEntry;

public class PackerFileParserFactoryTest {

	@Test
	public void testGet() {
		assertThat(PackerFileParserFactory.get(), instanceOf(PackerFileParserImpl.class));
	}

	@Test
	public void testGetWithNewImplementation() throws Exception {
		PackerFileParserFactory.reset();
		ClassLoader loader = new ClassLoader() {
			@Override
			protected Enumeration<URL> findResources(String name) throws IOException {
				URLStreamHandler handler = new URLStreamHandler() {
					
					@Override
					protected URLConnection openConnection(URL u) throws IOException {
						return new URLConnection(u) {
							@Override
							public void connect() throws IOException {
							}
							
							@Override
							public InputStream getInputStream() throws IOException {
								return new ByteArrayInputStream(TestPackerFileParser.class.getName().getBytes());
							}
						};
					}
				};
				URL url = new URL("", "",0,"", handler);
				return Collections.enumeration(Arrays.asList(url));
			}
		};
		ClassLoader oldLoader = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(loader);
		try {
			assertThat(PackerFileParserFactory.get(), instanceOf(TestPackerFileParser.class));
		} finally {
			PackerFileParserFactory.reset();
			Thread.currentThread().setContextClassLoader(oldLoader);
		}		
	}

	
	public static class TestPackerFileParser implements PackerFileParser {
		@Override
		public PackerFileEntry[] parse(InputStream inputStream) throws APIException, IOException {
			return new PackerFileEntry[0];
		}		
	}
}
