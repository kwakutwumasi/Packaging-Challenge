package com.mobiquityinc.packer.i18n;

import java.util.ResourceBundle;

public class Messages {
	private Messages() {}
	
	private static ResourceBundle resourceBundle;
	
	public static ResourceBundle getResourceBundle() {
		if(resourceBundle == null)
			resourceBundle = ResourceBundle.getBundle("messages");
		
		return resourceBundle;
	}
	
	public static String get(String key) {
		return getResourceBundle().getString(key);
	}
}
