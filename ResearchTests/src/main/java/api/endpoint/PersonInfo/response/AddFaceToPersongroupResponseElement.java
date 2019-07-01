package api.endpoint.PersonInfo.response;

import java.util.HashMap;

public class AddFaceToPersongroupResponseElement {

    HashMap<String, String> error;
    String persistedFaceId;

    public String getPersistedFaceId() {
        return persistedFaceId;
    }

    public HashMap<String, String> getError() {
        return error;
    }
}
