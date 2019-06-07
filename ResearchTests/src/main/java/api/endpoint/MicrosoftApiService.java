package api.endpoint;

import api.ApiMethod;
import api.parameters.HttpHeaderParameter;

public abstract class MicrosoftApiService extends ApiMethod {

	/**
	 * All services require a Ocp-Apim-Subscription-Key to function correctly. Ensure that all Microsoft services
	 * have this key pre-initialised.
	 */
	public MicrosoftApiService() {
		this.addHttpHeaderParameter(new HttpHeaderParameter("Ocp-Apim-Subscription-Key", "7dbbaea80ecf46dd85dfed140bd025a4"));
	}

}
