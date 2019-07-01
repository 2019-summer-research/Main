package api.endpoint;

import api.ApiMethod;
import api.RatelimitManager;
import api.parameters.HttpHeaderParameter;

public abstract class MicrosoftApiService extends ApiMethod {

	static RatelimitManager microsoftRatelimitManager = new RatelimitManager(15, 120);

	/**
	 * All services require a Ocp-Apim-Subscription-Key to function correctly. Ensure that all Microsoft services
	 * have this key pre-initialised.
	 */
	public MicrosoftApiService() {
		this.addHttpHeaderParameter(new HttpHeaderParameter("Ocp-Apim-Subscription-Key", "13e5507a90f547cab4bc35b2675241da"));

		// We are only allowed to make 15 requests every 15 seconds with this API. Attach the Microsoft ratelimit manager to all methods
		this.setRatelimitManager(microsoftRatelimitManager);
	}

}
