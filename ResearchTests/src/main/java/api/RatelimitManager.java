package api;

/**
 * A class which handles enforcement of ratelimiting for API endpoints. Information such as how often requests may be made,
 * as well as time segment information are pulled from objects of this class
 */
public class RatelimitManager {

	/**
	 * An integer storing the number of allowed requests that you're able to make per time segment
	 */
	private Integer numberOfRequestsAllowed;

	/**
	 * How long each time segment is in milliseconds
	 */
	private Integer requestSegmentSize;

	/**
	 * A variable storing how many requests were sent for this current time segment
	 */
	private Integer numRequestsSentThisSegment = 0;

	/**
	 * At what time in milliseconds should the system refresh the time segment counter
	 */
	private Long timeSegmentRefreshTime;

	public RatelimitManager(Integer numberOfRequestsAllowed, Integer requestSegmentSizeInSeconds) {
		this.numberOfRequestsAllowed = numberOfRequestsAllowed;
		this.requestSegmentSize = requestSegmentSizeInSeconds * 1000;

		this.timeSegmentRefreshTime = System.currentTimeMillis() + requestSegmentSize;
	}

	/**
	 * A function which checks whether we are able to refresh our class and move to the next time segment.
	 */
	public void checkTimeSegment() {
		if(System.currentTimeMillis() > timeSegmentRefreshTime) {
			numRequestsSentThisSegment = 0;
			timeSegmentRefreshTime = System.currentTimeMillis() + requestSegmentSize;
		}
	}

	/**
	 * A function which returns if this method can be sent within this timeframe, or if it needs to stay in holding
	 * @return whether this Ratelimit is in a state where it can be sent safely.
	 */
	public boolean isTransactionAllowed() {
		if(numRequestsSentThisSegment <= numberOfRequestsAllowed) {
			return true;
		}
		return false;
	}

	/**
	 * A method called whenever a request is sent using this ratelimiter.
	 */
	public void incrementRequestsSentThisPeriod() {
		this.numRequestsSentThisSegment++;
	}
}
