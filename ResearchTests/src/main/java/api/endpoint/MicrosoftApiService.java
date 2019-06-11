package api.endpoint;

import api.ApiMethod;
import api.RatelimitManager;
import api.parameters.HttpHeaderParameter;
import javafx.util.Pair;

public abstract class MicrosoftApiService extends ApiMethod {

	static RatelimitManager microsoftRatelimitManager = new RatelimitManager(15, 120);

	/**
	 * All services require a Ocp-Apim-Subscription-Key to function correctly. Ensure that all Microsoft services
	 * have this key pre-initialised.
	 */
	public MicrosoftApiService() {
		this.addHttpHeaderParameter(new HttpHeaderParameter("Ocp-Apim-Subscription-Key", "7dbbaea80ecf46dd85dfed140bd025a4"));

		// We are only allowed to make 15 requests every 15 seconds with this API. Attach the Microsoft ratelimit manager to all methods
		this.setRatelimitManager(microsoftRatelimitManager);
	}

}
