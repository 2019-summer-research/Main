package api;

import api.parameters.HttpHeaderParameter;
import api.parameters.UrlParameter;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

/**
 * A class which handles making API transactions to outside sources.
 * This class is implemented as Singleton to ensure that requests are made in a synchronous fashion, thus negating
 * any concerns about some requests externally processing faster than others which may cause errors depending
 * on the cloud APIs which are used.
 */
public class RequestManager {

	/**
	 * Singleton instance of this class
	 */
	private static RequestManager instance = null;

	/**
	 * The OkHttp API instance used to execute the managed API calls
	 */
	OkHttpClient okClient = new OkHttpClient();

	/**
	 * Singleton accessor to the API Request Manager
	 * @return the only active instance of this class
	 */
	public static RequestManager getInstance() {
		if(RequestManager.instance == null) {
			RequestManager.instance = new RequestManager();
		}
		return RequestManager.instance;
	}

	public <T> T makeApiRequest(ApiMethod method) {
		T response = null;

		// Which request type are we using? Delegate it to the proper method
		switch(method.getMethod()) {
			case DELETE:
				break;
			case GET:
				break;
			case HEAD:
				break;
			case OPTIONS:
				break;
			case POST: response = this.makePostRequest(method);
				break;
			case PUT:  response = this.makePutRequest(method);
				break;
			case TRACE:
				break;
			default:
				break;

		}

		return response;
	}

	/**
	 * Method which uses OKHTTP to send a POST request to the specified URL saved within the @link{ApiMethod} parameter
	 */
	private <T> T makePostRequest(ApiMethod method) {

		RequestBody body = RequestBody.create(method.getMediaType(), method.getBody());
		Request.Builder request = new Request.Builder();

		// Append each of the headers for this method
		for (HttpHeaderParameter currentHeader : method.getHttpHeaderParameters()) {
			request.addHeader(currentHeader.getKey(), currentHeader.getValue());
		}

		try {
			// Append the request body
			Request builtRequest = request.url(method.getUrl())
					.post(body)
					.build();

			// Make the request
			Response response = okClient.newCall(builtRequest).execute();

			// Parse the response with Gson
			Gson gson = new Gson();
			String responseJsonString = response.body().string();

			// If the response type for this is VOID (Meaning we are not expecting a response) do not try to use Gson
			if(method.getReturnType() == Void.TYPE)
				return (T) Void.TYPE;

			T data = gson.fromJson(responseJsonString, method.getReturnType());
			return data;

		} catch (IOException ex) {
			// Create a specific error message for this problem and print it to the err-out
			StringBuilder builder = new StringBuilder();
			builder.append("[Error] - POST request failed.\nBase URL:");
			builder.append(method.getBaseUrl());
			builder.append("Parameters used: ");
			for(UrlParameter param : method.getUrlParameters()) {
				builder.append(param.toString()).append("\n");
			}
			builder.append("\n");

			System.err.println(builder.toString());

		}

		return null;
	}

	/**
	 * Method which uses OKHTTP to send a POST request to the specified URL saved within the @link{ApiMethod} parameter
	 */
	private <T> T makePutRequest(ApiMethod method) {
		RequestBody body = RequestBody.create(method.getMediaType(), method.getBody());
		Request.Builder request = new Request.Builder();

		// Append each of the headers for this method
		for (HttpHeaderParameter currentHeader : method.getHttpHeaderParameters()) {
			request.addHeader(currentHeader.getKey(), currentHeader.getValue());
		}

		try {
			// Append the request body
			Request builtRequest = request.url(method.getUrl())
					.put(body)
					.build();

			// Make the request
			Response response = okClient.newCall(builtRequest).execute();

			// Parse the response with Gson
			Gson gson = new Gson();
			String responseJsonString = response.body().string();

			// If the response type for this is VOID (Meaning we are not expecting a response) do not try to use Gson
			if(method.getReturnType() == Void.TYPE)
				return (T) Void.TYPE;

			T data = gson.fromJson(responseJsonString, method.getReturnType());
			return data;

		} catch (IOException ex) {
			// Create a specific error message for this problem and print it to the err-out
			StringBuilder builder = new StringBuilder();
			builder.append("[Error] - POST request failed.\nBase URL:");
			builder.append(method.getBaseUrl());
			builder.append("Parameters used: ");
			for(UrlParameter param : method.getUrlParameters()) {
				builder.append(param.toString()).append("\n");
			}
			builder.append("\n");

			System.err.println(builder.toString());

		}

		return null;
	}

}
