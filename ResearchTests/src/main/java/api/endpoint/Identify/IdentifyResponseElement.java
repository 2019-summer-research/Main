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
    public Float getConfidence(){
        return candidates[0].getConfidence();
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