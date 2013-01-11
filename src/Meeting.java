import java.util.Calendar;
import java.util.Set;

/**
 * A Class to represent meetings
 * 
 * Meetings have unique IDs, a date and attendees (Contacts)
 */

public interface Meeting {
	/**
	 * Returns the ID of the meeting
	 * 
	 * @return id Returns the meeting ID
	 */
	int getID();
	
	/**
	 * Returns the date of the meeting
	 * 
	 * @return the date of the meeting
	 */
	Calendar getDate();
	
	/**
	 *  Returns the details of the people attending the meeting 
	 *  
	 *  The returned list contains at least one person (if the meeting was only held between
	 *  the user and the contact) but may contain an arbitrary number of contacts
	 *  
	 *  @return the details of the people who attended the meeting
	 */
	Set<Contact> getContacts();
}
