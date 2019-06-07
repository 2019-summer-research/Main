package api.endpoint.FacialDetection;

import api.endpoint.MicrosoftApiService;
import api.parameters.RequestMethod;
import api.parameters.UrlParameter;
import okhttp3.MediaType;

public class FaceDetectionApiMethod extends MicrosoftApiService {

	/**
	 * Constructor which prepares this API method to perform facial recognition on a target.
	 * @param imageByteArray a byte array representation of an image containing a face
	 */
	public FaceDetectionApiMethod(byte[] imageByteArray) {

		// Set the base URL
		this.setUrlBase("https://eastus.api.cognitive.microsoft.com/face/v1.0/detect");

		// Set the URL parameters
		this.addUrlParameter(new UrlParameter("returnFaceId", "true"));

		// This method will be using an octet-stream as a type, as we're uploading an image byte array instead of a URL
		MediaType octetMediaType = MediaType.get("appliation/octet-stream; charset=utf-8");
		this.setMediaType(octetMediaType);

		// Set the body to contain the Image
		this.setBody(imageByteArray);

		// This method should be ran as POST
		this.setMethod(RequestMethod.POST);

		// Declare what the response should look like
		this.setReturnType(FaceDetectionResponseElement.class);

	}


}
