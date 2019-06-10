package api;

import api.parameters.HttpHeaderParameter;
import api.parameters.RequestMethod;
import api.parameters.UrlParameter;
import javafx.util.Pair;
import okhttp3.MediaType;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public abstract class ApiMethod {

	/**
	 * The base URL which is postfixed by any HTTP parameters following
	 */
	private String urlBase;

	/**
	 * The media type used by this request. Defaults to JSON and the UTF-8 Charset
	 */
	protected MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

	/**
	 * List containing all of the parameters to be inserted into the URL for the next request
	 */
	private final List<UrlParameter> urlParameters = new LinkedList<>();

	/**
	 * List containing all of the headers to be inserted into the URL for the next request
	 */
	private final List<HttpHeaderParameter> httpHeaderParameters = new LinkedList<>();

	/**
	 * Request method used by the API handler. Defaults to GET
	 */
	private RequestMethod method = RequestMethod.GET;

	/**
	 * The body for a given request
	 */
	private byte[] body = null;

	/**
	 * The return type for the request
	 */
	private Type returnType = null;

	/**
	 * A method which adds a header to the API request
	 * @param param the parameter to be used for the request
	 */
	protected void addHttpHeaderParameter(HttpHeaderParameter param) {
		httpHeaderParameters.add(param);
	}

	/**
	 * A method which adds a url parameter to the API request. note that this is inlined with the URL at the end
	 * unlike normal headers.
	 * @param param the parameter to be used.
	 */
	protected void addUrlParameter(UrlParameter param) {
		urlParameters.add(param);
	}

	/**
	 * Returns the request body to the requester
	 * @return the current apimethod body
	 */
	public byte[] getBody() {
		return this.body;
	}

	/**
	 * If this APIMethod is ratelimited to a certain frequency, an attached RatelimitManager will handle timing the
	 * send requests to appropriate levels.
	 */
	RatelimitManager ratelimitManager = null;

	public RatelimitManager getRatelimitManager() {
		return ratelimitManager;
	}

	public void setRatelimitManager(RatelimitManager ratelimitManager) {
		this.ratelimitManager = ratelimitManager;
	}

	/**
	 * Sets the request body to an input string
	 * @param body
	 */
	public void setBody(byte[] body) {
		this.body = body;
	}

	/**
	 * Gets the ReturnType of the APIMethod
	 */
	public Type getReturnType() {
		return this.returnType;
	}

	/**
	 * Method to retrieve all of the HttpHeaderParameters currently loaded into the method
	 */
	public List<HttpHeaderParameter> getHttpHeaderParameters() {
		return this.httpHeaderParameters;
	}

	/**
	 * Method to return what the request method is for this method
	 */
	public RequestMethod getMethod() {
		return this.method;
	}

	/**
	 * Method used to set a request method for this function call
	 */
	protected void setMethod(RequestMethod method) {
		this.method = method;
	}

	/**
	 * Method which sets the URL base.
	 */
	protected void setUrlBase(String urlBase) {
		this.urlBase = urlBase;
	}

	public MediaType getMediaType() {
		return mediaType;
	}

	public List<UrlParameter> getUrlParameters() {
		return urlParameters;
	}

	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}

	public void setReturnType(Type returnType) {
		this.returnType = returnType;
	}

	/**
	 * Method which gets the URL for this method including all of the added {@link UrlParameter}
	 * @return
	 * @throws MalformedURLException
	 */
	public URL getUrl() throws MalformedURLException {
		StringBuilder builder = new StringBuilder(urlBase);
		char connectorValue = '?';
		for(UrlParameter p : urlParameters) {
			builder.append(connectorValue).append(p.toString());
			connectorValue = '&';
		}
		return new URL(builder.toString());
	}

	/**
	 * Method which returns the base URL without any of the parameters
	 */
	public String getBaseUrl() {
		return this.urlBase;
	}

	/**
	 * Method which gets all of the URL parameters, without including them in the URL base.
	 * This is very useful for POST requests, which require these parameters to not be given as GET
	 * variables, but to be used in the POST value format. (Body?)
	 */
	public String getUrlParametersAsPostBody() {
		StringBuilder builder = new StringBuilder();
		char connectorValue = '&';
		boolean first = true;
		for(UrlParameter p : urlParameters) {
			if(first) {
				builder.append(p.toString());
				first = false;
			}
			else {
				builder.append(connectorValue).append(p.toString());
			}
		}
		return builder.toString();
	}

	/**
	 * As this is an abstract method, this override gets the name of the implementing class
	 * @return the classes simplename
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
