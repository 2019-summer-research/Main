package api.endpoint.PersonInfo;

import api.endpoint.Identify.IdentifyResponseElement;
import api.endpoint.MicrosoftApiService;
import api.parameters.RequestMethod;
import api.parameters.UrlParameter;
import okhttp3.MediaType;

public class PersonInfoAPIMethod extends MicrosoftApiService {

    public PersonInfoAPIMethod(String PersonID,String PersonGroupID) {

        // Set the base URL
        this.setUrlBase("https://eastus.api.cognitive.microsoft.com/face/v1.0/persongroups/"+PersonGroupID+"/persons/"+PersonID);

        // Set the URL parameters




        // This method will be using an octet-stream as a type, as we're uploading an image byte array instead of a URL
        MediaType jsonMediaType = MediaType.get("application/json; charset=utf-8");
        this.setMediaType(jsonMediaType);



        // Set the body to contain the Image

        // This method should be ran as POST
        this.setMethod(RequestMethod.GET);

        // Declare what the response should look like
        this.setReturnType(PersonInfoResponseElement.class);

    }
}
