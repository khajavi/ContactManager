import java.util.GregorianCalendar;
import java.util.Set;


public interface DataUtilities {
	
	/*
	 * Reads data stored in a text file when the Contact Manager is constructed.
	 * 
	 * @throws FileNotFoundException if the data file is not found
	 * @throws IOException
	 */
	void readData();
	
	/*
	 * Returns the Contact's name from a String of data containing all the Contact's details
	 * 
	 * @param String a string containing all the contact's details
	 */
	String getContactName(String data);
	
	/*
	 * Returns the Contact's Id from a String of data
	 * 
	 * @param String a string containing all the contact's details
	 */
	int getContactID(String data);
	
	/*
	 * Returns Meeting or Contact notes
	 * 
	 * @param String containing all the contact's or the meeting's details
	 */
	String getNotes(String data);
	
	/*
	 * Create a new contact with the specified name and notes
	 * 
	 * @param String containing all the contact's details
	 */
	void loadContact(String data);
	
	/*
	 * Returns the meeting Id from a string containing all of the meeting's details
	 * 
	 * @param String containing all the contacts details
	 */
	int getMeetingID(String data);
	
	/*
	 * Returns a set of all the contacts that attended a meeting
	 * 
	 * @param String containing all of the meeting details
	 */
	Set<Contact> getAttendees(String data);
	
	/*
	 * Returns the Date of the Meeting
	 * 
	 * @param String a string containing all of the meeting's details
	 */
	GregorianCalendar getMeetingDate(String data);
	
	/*
	 * Creates a new meeting, or past meeting if it occurred in the past, with the specified
	 *  ID and attendees.
	 *  
	 *  @param String a string containing all of the meeting's details
	 */
	void loadMeeting(String data);
	
	/*
	 * Creates a new PastMeeting with the specified Id, notes and attendees
	 * 
	 * @param String a string containing all the meeting's details
	 */
	void loadPastMeeting(String data);
}
