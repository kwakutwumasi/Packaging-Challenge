package com.mobiquityinc.packer.fileparser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.i18n.Messages;
import com.mobiquityinc.packer.model.PackerFileEntry;

/**Interface implemented by file parsers
 * @author Kwaku Twumasi-Afriyie <kwaku.twumasi@quakearts.com>
 *
 */
public interface PackerFileParser {
	/**The default parse method. Opens a file input stream and passes it to the implementation
	 * of {@link #parse(InputStream)}
	 * @param file The name of the file
	 * @return The parsed entries of the packer file
	 * @throws APIException If the file contains errors
	 */
	default PackerFileEntry[] parse(String file) throws APIException {
		try(FileInputStream fis=new FileInputStream(file)){
			return parse(fis);
		} catch (IOException e) {
			throw new APIException(MessageFormat.format(Messages.get("file.io.exception"), file), e);
		}
	}
	
	/**Parse the data pulled from this input stream
	 * @param inputStream The input stream to process
	 * @return The parsed entries of the packer file
	 * @throws APIException If the file contains errors
	 * @throws IOException If an exception occurs during input operations
	 */
	PackerFileEntry[] parse(InputStream inputStream) throws APIException, IOException;
}
