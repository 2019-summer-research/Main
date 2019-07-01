package api.endpoint.Faces;

import java.util.HashMap;

public class FacesResponseElement {

    HashMap<String, String> error;
    String persistedFaceId;

    public String getPersistedFaceId() {
        return persistedFaceId;
    }

    public HashMap<String, String> getError() {
        return error;
    }
}
