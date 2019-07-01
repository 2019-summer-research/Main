package api.endpoint.PersonInfo.response;

public class PersonInfoResponseElement {
    String personId;
    String[] persistedFaceIds;
    String name;
    String userData;

    public String getPersonId() {
        return personId;
    }

    public String[] getPersistedFaceIds() {
        return persistedFaceIds;
    }

    public String getUserData() {
        return userData;
    }

    /*
        {
        "personId": "25985303-c537-4467-b41d-bdb45cd95ca1",
        "persistedFaceIds": [
            "015839fb-fbd9-4f79-ace9-7675fc2f1dd9",
            "fce92aed-d578-4d2e-8114-068f8af4492e",
            "b64d5e15-8257-4af2-b20a-5a750f8940e7"
        ],
        "name": "Ryan",
        "userData": "User-provided data attached to the person."
    }
         */
    public String getName() {

        return name;
    }
}
