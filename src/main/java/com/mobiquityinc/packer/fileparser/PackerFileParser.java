package com.mobiquityinc.packer.fileparser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.i18n.Messages;
import com.mobiquityinc.packer.model.PackerFileEntry;

public interface PackerFileParser {
	default PackerFileEntry[] parse(String file) throws APIException {
		try(FileInputStream fis=new FileInputStream(file)){
			return parse(fis);
		} catch (IOException e) {
			throw new APIException(MessageFormat.format(Messages.get("file.io.exception"), file), e);
		}
	}
	
	PackerFileEntry[] parse(InputStream inputStream) throws APIException, IOException;
}
