import api.ApiMethod;
import api.RequestManager;
import api.endpoint.FacialDetection.FaceDetectionApiMethod;
import api.endpoint.FacialDetection.FaceDetectionResponseElement;
import api.endpoint.Identify.IdentifyApiMethod;
import api.endpoint.Identify.IdentifyResponseElement;
import api.endpoint.PersonGroup.CreatePersonGroupMethod;
import api.endpoint.PersonGroup.PersonGroupResponseElement;
import api.endpoint.PersonInfo.methods.AddFaceToPersonMethod;
import api.endpoint.PersonInfo.methods.AddPersonToPersongroupMethod;
import api.endpoint.PersonInfo.response.AddFaceToPersongroupResponseElement;
import api.endpoint.PersonInfo.response.PersonResponseElement;
import api.endpoint.Train.TrainAPIMethod;

import java.util.List;

public class ApiAdapter {

	/**
	 * The {@link RequestManager} class which handles all of the transactions run by this adapter
	 */
	RequestManager manager;

	public ApiAdapter() {
		this.manager = RequestManager.getInstance();
	}

	/**
	 * Creates a new Azure Person group
	 * @param personGroupId The internal ID (All lowercase, '_' as spaces) used for the person group
	 * @param personGroupName The human friendly name for the person group
	 * @param customData See API endpoint documentation for additional features, may be input here
	 */
	public PersonGroupResponseElement createPersonGroup(String personGroupId, String personGroupName, String customData) {
		ApiMethod method = new CreatePersonGroupMethod(personGroupId, personGroupName, customData);
		return manager.makeApiRequest(method);
	}

	/**
	 * Creates a new 'Person' and attaches it to the given persongroup.
	 * @param personGroupId The ID of the persongroup the person is being attached to. This is found through the response
	 *                      of the (@link ApiAdapter::createPersonGroup) method
	 * @param Name The name of the person which is being attached to this persongroup
	 * @param Description An optional field of extra information about this person
	 * @return
	 */
	public PersonResponseElement addPersonToPersonGroup(String personGroupId, String Name, String Description) {
		ApiMethod method = new AddPersonToPersongroupMethod(personGroupId, Name, Description);
		return manager.makeApiRequest(method);
	}

	/**
	 * Takes both a persongroup and a person, and appends a new face for that person
	 * @param personGroupId The PersonGroup ID which the person belongs to
	 * @param personId Which person in specific you are adding this face to
	 * @param image The bytes for the image which is to be attached
	 * @return
	 */
	public AddFaceToPersongroupResponseElement addFaceToPersongroup(String personGroupId, String personId, byte[] image) {
		ApiMethod method = new AddFaceToPersonMethod(image, personGroupId, personId);
		return manager.makeApiRequest(method);
	}

	/**
	 * Alerts Azure services to train a Persongroup's recognition model
	 * @param persongroupId
	 */
	public void trainPersongroup(String persongroupId) {
		ApiMethod method = new TrainAPIMethod(persongroupId);
		manager.makeApiRequest(method);
	}

	/**
	 * Feeds a face into the Azure system and creates an ID which can be used in other methods for identification.
	 * This method also returns bounding information for this person
	 * @param face An image of the face to be scanned
	 * @return
	 */
	public List<FaceDetectionResponseElement> getFaces(byte[] face) {
		ApiMethod method = new FaceDetectionApiMethod(face);
		return manager.makeApiRequest(method);
	}

	/**
	 * Using a list of faceIds retrieved from {@link ApiAdapter::getFaces} this checks all of the faces against
	 * a person group
	 * @param faceIds An array of faceIds which should be checked
	 * @param personGroup The PersonGroup ID which this should be checked against
	 * @return
	 */
	public List<IdentifyResponseElement> identifyFaces(String[] faceIds, String personGroup) {
		ApiMethod method = new IdentifyApiMethod(faceIds, personGroup);
		return manager.makeApiRequest(method);
	}



}
