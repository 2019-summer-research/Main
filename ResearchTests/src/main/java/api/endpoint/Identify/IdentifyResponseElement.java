package api.endpoint.Identify;

public class IdentifyResponseElement {
    String faceId;
        Candidate[] candidates;

    public String getFaceId() {
        return faceId;
    }

    public String getID() {
        return candidates[0].getPersonId();
    }
}
class Candidate {
    String personId;
    Float confidence;

    public String getPersonId() {
        return personId;
    }

    public Float getConfidence() {
        return confidence;
    }
}