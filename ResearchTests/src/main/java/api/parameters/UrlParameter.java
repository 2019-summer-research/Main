package api.parameters;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Class which stores information of HTTP UrlParameters to be encoded in transactions
 */
public class UrlParameter {

	private final String key;
	private final String value;

	/**
	 * Parameters must be encoded as strings for HTTP transactions. This constructor stores such values.
	 * Each of the constructor overloads merely converts the data types into String and invokes this method
	 * @param key The key used for the header
	 * @param value The value used for the header
	 */
	public UrlParameter(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public UrlParameter(String key, int value) {
		this(key, String.valueOf(value));
	}

	public UrlParameter(String key, long value) {
		this(key, String.valueOf(value));
	}

	public UrlParameter(String key, boolean value) {
		this(key, value ? "true" : "false");
	}

	public UrlParameter(String key, Object value) {
		this(key, value.toString());
	}

	protected String getKey() {
		return this.key;
	}

	protected String getValue() {
		return this.value;
	}

	/**
	 * ToString needs to convert this parameter into the proper format which can be used in a URL.
	 * Example: Key = Shape, Value = Square to "Shape=Square" (In UTF-8 Encoding)
	 * @return the string representation of this URL parameter which can be inserted safely into a URL request
	 */
	@Override
	public String toString() {
		String parameter = null;
		try {
			parameter = URLEncoder.encode(getKey(), "UTF-8") + "=" + URLEncoder.encode(getValue(), "UTF-8");
		} catch (UnsupportedEncodingException ex) {
			//I'm really impressed you actually got to this bit of code, since the encoding is hardcoded...
			ex.printStackTrace();
		}
		return parameter;
	}

}
