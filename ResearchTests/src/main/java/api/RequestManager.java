package api;

import api.elements.ApiMethodErrorElement;
import api.parameters.HttpHeaderParameter;
import api.parameters.UrlParameter;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
	 * A list of API methods which have not been allowed to be sent due to ratelimiting concern.
	 * This is checked constantly until it is fit to be sent out.
	 */
	static Vector<ApiMethod> holdingPatternList;

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

	private RequestManager() {
		this.holdingPatternList = new Vector<>();

		// We want to be sure to check the holding pattern every (1) seconds and handle any available methods eligible
		final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.scheduleAtFixedRate(RequestManager::handleHoldingPattern, 0, 1, TimeUnit.SECONDS);
	}

	public <T> T makeApiRequest(ApiMethod method) {

		// If this method is not safe to run due to ratelimiting concerns, send it to a holding pattern
		if(method.getRatelimitManager() != null && !method.getRatelimitManager().isTransactionAllowed()) {
			holdingPatternList.add(method);

			//TODO: Remove debug line
			System.out.println("[Debug] - Method " + method.getBaseUrl() + " sent to holding pattern");

			return null;
		}

		T response = null;

		// Which request type are we using? Delegate it to the proper method
		switch(method.getMethod()) {
			case DELETE:
				break;
			case GET: response = this.makeGetRequest(method);
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

	private <T> T makeGetRequest(ApiMethod method) {
		
		Request.Builder request = new Request.Builder();

		// Append each of the headers for this method
		for (HttpHeaderParameter currentHeader : method.getHttpHeaderParameters()) {
			request.addHeader(currentHeader.getKey(), currentHeader.getValue());
		}

		try {
			// Append the request body
			Request builtRequest = request.url(method.getUrl())
					.get()
					.build();

			// Make the request
			Response response = okClient.newCall(builtRequest).execute();

			// Parse the response with Gson
			Gson gson = new Gson();
			String responseJsonString = Objects.requireNonNull(response.body()).string();
			System.out.println(responseJsonString);

			// If the response type for this is VOID (Meaning we are not expecting a response) do not try to use Gson
			if(method.getReturnType() == Void.TYPE)
				return (T) Void.TYPE;

			T data = gson.fromJson(responseJsonString, method.getReturnType());
			return data;

		} catch (IOException | NullPointerException ex) {
			// Spawn and print an error message for this function call
			System.err.println(new ApiMethodErrorElement(method));
		}

		return null;
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
			String responseJsonString = Objects.requireNonNull(response.body()).string();
			System.out.println(responseJsonString);

			// If the response type for this is VOID (Meaning we are not expecting a response) do not try to use Gson
			if(method.getReturnType() == Void.TYPE)
				return (T) Void.TYPE;

			T data = gson.fromJson(responseJsonString, method.getReturnType());
			return data;

		} catch (IOException | NullPointerException ex) {
			// Spawn and print an error message for this function call
			System.err.println(new ApiMethodErrorElement(method));
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
			String responseJsonString = Objects.requireNonNull(response.body()).string();

			System.out.println(responseJsonString);

			// If the response type for this is VOID (Meaning we are not expecting a response) do not try to use Gson
			if(method.getReturnType() == Void.TYPE)
				return (T) Void.TYPE;

			T data = gson.fromJson(responseJsonString, method.getReturnType());
			return data;

		} catch (IOException | NullPointerException ex) {
			// Spawn and print an error message for this function call
			System.err.println(new ApiMethodErrorElement(method));
		}

		return null;
	}

	/**
	 * A function which verifies if a given request can be fired immediately. Checks with any attached ratelimit
	 * manager.
	 * @param method An API Method which is ready to be sent
	 * @return Whether this APIMethod can be sent to the internet immediately
	 */
	private boolean canRequestSendImmediately(ApiMethod method) {

		// If there are no ratelimiting rules for this method, it may be sent right away
		if(method.getRatelimitManager() == null) {
			return true;
		}

		// If there is a ratelimit manager, check if it will allow us to send the request immediately
		if(method.getRatelimitManager().isTransactionAllowed()) {
			//Increment the number of requests made this time period, and allow the request to be sent
			method.getRatelimitManager().incrementRequestsSentThisPeriod();
			return true;
		}

		// Otherwise, this method is not safe to run.
		return false;

	}

	/**
	 * A method which goes through the current holding pattern of API methods that have not been sent out,
	 * and sends out the ones fit as determined through it's {@link RatelimitManager}
	 */
	static private void handleHoldingPattern() {
		// If there are no elements in the holding pattern, simply return
		if(holdingPatternList.isEmpty()) {
			return;
		}

		// Otherwise, check through all of the elements and see if any of them may be sent
		for(ApiMethod method : holdingPatternList) {
			method.getRatelimitManager().checkTimeSegment();
			if(method.getRatelimitManager().isTransactionAllowed()) {
				// The transaction may be sent, send it to the request manager
				RequestManager.getInstance().makeApiRequest(method);
				holdingPatternList.remove(method);
			}
		}
	}

}
