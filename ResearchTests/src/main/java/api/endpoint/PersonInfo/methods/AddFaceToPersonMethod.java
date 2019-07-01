package api.endpoint.PersonInfo.methods;

import api.endpoint.PersonInfo.response.AddFaceToPersongroupResponseElement;
import api.endpoint.MicrosoftApiService;
import api.parameters.RequestMethod;
import okhttp3.MediaType;

public class AddFaceToPersonMethod extends MicrosoftApiService {
    public AddFaceToPersonMethod(byte[] imageByteArray, String PersonGroup, String PersonID){

        this.setUrlBase("https://eastus.api.cognitive.microsoft.com/face/v1.0/persongroups/"+PersonGroup+"/persons/"+PersonID+"/persistedFaces");

        MediaType octetMediaType = MediaType.get("application/octet-stream; charset=utf-8");
        this.setMediaType(octetMediaType);

        // Set the body to contain the Image
        this.setBody(imageByteArray);

        // This method should be ran as POST
        this.setMethod(RequestMethod.POST);

        this.setReturnType(AddFaceToPersongroupResponseElement.class);
    }
}
