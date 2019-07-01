package api.endpoint.Person;

import java.util.HashMap;

public class PersonResponseElement {
    HashMap<String, String> error;
    String personId;

    public String GetID()
    {
        return personId;
    }

    public HashMap<String, String> getError() {
        return error;
    }
}
