package api.endpoint.Person;

import api.endpoint.MicrosoftApiService;
import api.parameters.RequestMethod;
import okhttp3.MediaType;

public class PersonAPIMethod extends MicrosoftApiService {



    public PersonAPIMethod(String PersonGroupID, String Name, String Description){

        this.setUrlBase("https://eastus.api.cognitive.microsoft.com/face/v1.0/persongroups/"+PersonGroupID+"/persons");
        MediaType JSON = MediaType.get("application/JSON; charset=utf-8");
        this.setMediaType(JSON);
        String body = "{"
                + "'name':'" + Name + "',"
                + "'userData':'" + Description + "',"
                + "}";
        this.setBody(body.getBytes());
        this.setMethod(RequestMethod.POST);
        this.setReturnType(PersonResponseElement.class);

    }







}
