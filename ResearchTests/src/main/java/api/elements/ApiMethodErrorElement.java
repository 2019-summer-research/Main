package api.elements;

import api.ApiMethod;
import api.parameters.UrlParameter;

public class ApiMethodErrorElement {

	ApiMethod method;

	public ApiMethodErrorElement(ApiMethod method) {
		this.method = method;
	}

	@Override
	public String toString() {
		// Create a specific error message for this problem and print it to the err-out
		StringBuilder builder = new StringBuilder();
		builder.append("[Error] - POST request failed.\nBase URL:");
		builder.append(method.getBaseUrl());
		builder.append("Parameters used: ");
		for(UrlParameter param : method.getUrlParameters()) {
			builder.append(param.toString()).append("\n");
		}
		builder.append("\n");

		return builder.toString();
	}

}
