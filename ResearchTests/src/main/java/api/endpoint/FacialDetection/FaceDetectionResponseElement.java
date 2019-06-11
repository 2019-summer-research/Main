package api.endpoint.FacialDetection;

import java.util.HashMap;

public class FaceDetectionResponseElement {

	// If any error message is present
	HashMap<String, String> error;

	String faceId;
	String recgonitionModel;

	HashMap<String, Integer> faceRectangle;
	HashMap<String, HashMap<String, Float>> faceLandmarks;

	FaceAttributes faceAttributes;

	public HashMap<String, String> getError() {
		return error;
	}

	public String getFaceId() {
		return faceId;
	}

	public String getRecgonitionModel() {
		return recgonitionModel;
	}

	public HashMap<String, Integer> getFaceRectangle() {
		return faceRectangle;
	}

	public HashMap<String, HashMap<String, Float>> getFaceLandmarks() {
		return faceLandmarks;
	}

	public FaceAttributes getFaceAttributes() {
		return faceAttributes;
	}
}

class FaceAttributes {
	Float age;
	String gender;
	Float smile;
	HashMap<String, Float> facialHair;
	String glasses;
	HashMap<String, Float> headPose;
	HashMap<String, Float> emotion;

	//TODO: Add hair, ignoring section for now

	HashMap<String, Boolean> makeup;
	HashMap<String, Boolean> occlusion;

	//TODO: Add accessories, ignoring section for now
	//TODO: Add blur, ignoring section for now
	//TODO: Add exposure, ignoring section for now
	//TODO: Add noise, ignoring section for now
}