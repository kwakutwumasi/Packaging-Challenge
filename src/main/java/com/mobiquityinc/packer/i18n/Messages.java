package com.mobiquityinc.packer.i18n;

import java.util.ResourceBundle;

/**Utility method for internationalization. Uses the default locale
 * to pull a file containing the error messages in the locale's language
 * defaults to en-US if no file matching the locale can be found
 * @author Kwaku Twumasi-Afriyie <kwaku.twumasi@quakearts.com>
 *
 */
public class Messages {
	private Messages() {}
	
	private static ResourceBundle resourceBundle;
	
	/**Get the current resource bundle, based on the default locale
	 * @return
	 */
	public static ResourceBundle getResourceBundle() {
		if(resourceBundle == null)
			resourceBundle = ResourceBundle.getBundle("messages");
		
		return resourceBundle;
	}
	
	/**Get an entry from the bundle
	 * @param key the key for the entry
	 * @return the value mapped to the key
	 */
	public static String get(String key) {
		return getResourceBundle().getString(key);
	}
}
