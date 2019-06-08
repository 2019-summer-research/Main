package api.endpoint.PersonGroup;

import api.endpoint.MicrosoftApiService;
import api.parameters.RequestMethod;
import okhttp3.MediaType;

public class PersonGroupAPIMethod extends MicrosoftApiService {


    public PersonGroupAPIMethod(String GroupID,String Name,String CustomData)
    {
        this.setUrlBase("https://eastus.api.cognitive.microsoft.com/face/v1.0/persongroups/" + GroupID);
        MediaType JSON = MediaType.get("appliation/JSON; charset=utf-8");
        this.setMediaType(JSON);
        String body = "{"
                + "'name':'" + Name + "',"
                + "'userData':'" + CustomData + "',"
                + "}";
        this.setBody(body.getBytes());
        this.setMethod(RequestMethod.PUT);
        this.setReturnType(Void.TYPE);
    }



}
