package api.endpoint.Identify;

import api.endpoint.FacialDetection.FaceDetectionResponseElement;
import api.endpoint.MicrosoftApiService;
import api.parameters.RequestMethod;
import api.parameters.UrlParameter;
import com.google.gson.reflect.TypeToken;
import okhttp3.MediaType;

import java.util.Collection;

public class IdentifyApiMethod extends MicrosoftApiService {

    public IdentifyApiMethod(String faceID,String PersonGroupID) {

        // Set the base URL
        this.setUrlBase("https://eastus.api.cognitive.microsoft.com/face/v1.0/identify");

        // Set the URL parameters
        this.addUrlParameter(new UrlParameter("returnFaceId", "true"));

        String body = "{" +
                "    \"personGroupId\": \""+PersonGroupID +"\"," +
                "    \"faceIds\": [" +
                "        \""+faceID+"\"" +
                "    ],\n" +
                "    \"maxNumOfCandidatesReturned\": 1," +
                "    \"confidenceThreshold\": 0.5" +
                "}";



        // This method will be using an octet-stream as a type, as we're uploading an image byte array instead of a URL
        MediaType jsonMediaType = MediaType.get("application/json; charset=utf-8");
        this.setMediaType(jsonMediaType);



        // Set the body to contain the Image
        this.setBody(body.getBytes());

        // This method should be ran as POST
        this.setMethod(RequestMethod.POST);

        // Declare what the response should look like
        this.setReturnType(new TypeToken<Collection<IdentifyResponseElement>>(){}.getType());
    }



}
