package api.endpoint.Train;

import api.endpoint.MicrosoftApiService;
import api.parameters.RequestMethod;
import okhttp3.MediaType;

public class TrainAPIMethod extends MicrosoftApiService {



    public TrainAPIMethod(String GroupID){
        this.setUrlBase("https://eastus.api.cognitive.microsoft.com/face/v1.0/persongroups/"+GroupID+"/train");
        MediaType JSON = MediaType.get("appliation/JSON; charset=utf-8");
        this.setMediaType(JSON);


        this.setMethod(RequestMethod.POST);
        this.setReturnType(Void.TYPE);
    }



}
