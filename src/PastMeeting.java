/**
 * A meeting that was held in the past
 * 
 * It includes your notes about happened and was agreed
 */

public interface PastMeeting extends Meeting {
	/**
	 * Returns the notes from the meeting
	 * 
	 * If there are no notes the empty string is returned
	 */
	String getNotes();
	
	/**
	 * Adds the notes taken at the meeting
	 * 
	 * @param Notes A string containing notes from the meeting
	 */
	void addNotes(String notes);
}
