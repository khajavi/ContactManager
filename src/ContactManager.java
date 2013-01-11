import java.util.Calendar;
import java.util.List;
import java.util.Set;

 /**
 * A class to manage contacts and meetings
 **/

public interface ContactManager {
	
	/**
	 * Add a new meeting to be held in the future
	 *
	 * @param contacts a list if contacts that will participate in the meeting
	 * @param date date on which the meeting will take place
	 * @return the ID for the meeting
	 * @throws IllegalArgumentException if the time of the meeting is set in the past,
	 * 			or if contact is unknown/non-existent 
	 *
	 */
	int addFutureMeeting(Set <Contact> contacts, Calendar date);
	
	/**
	 * Returns PAST meeting with Requested ID or null if there was none
	 * 
	 * @param id ID for the requested meeting
	 * @return the meeting with the requested ID, or null if there was none
	 * @throws IllegalArgumentException if there is a meeting with that ID occurring in the future
	 */
	PastMeeting getPastMeeting(int id);
	
	/**
	 * Returns the FUTURE meeting with the requested id, or null if there is none
	 * 
	 * @param id the ID for the meeting
	 * @return the meeting with the requested ID or null if there is none
	 * @throws IllegalArgumentException if a meeting with that ID happened in the past
	 */
	FutureMeeting getFutureMeeting(int id);
	
	/**
	 * Returns the meeting with the requested ID, or null if there is none
	 * 
	 * @param id the ID of the meeting
	 * @return the meeting with the requested ID, or null if there is none
	 */
	Meeting getMeeting(int id);
	
	/**
	 * Returns the list of future meetings scheduled with this contact
	 * 
	 * If there are none the return list will be empty, if there are some
	 * the list will be chronologically ordered, and there will be no duplicates
	 * 
	 * @param contact one of the user's contacts
	 * @return the list of future meeting(s) with this contact (maybe empty)
	 */
	List<Meeting> getFutureMeetingList(Contact contact);
	
	/**
	 * Returns the list of meetings are scheduled for the specified date
	 * 
	 * If there are none the return list will be empty, if there are some
	 * the list will be chronologically ordered, and there will be no duplicates
	 * 
	 * @param date the date
	 * @return the list of meetings
	 * @throws IllegalArgumentException if the date is in the past
	 */
	List<Meeting> getFutureMeetingList(Calendar date);
	
	/**
	 * Returns the list of past meetings in which this contact has participated
	 * 
	 * If there are none the return list will be empty, if there are some
	 * the list will be chronologically ordered, and there will be no duplicates
	 * 
	 * @param contact one of the user's contacts
	 * @return the list of past meetings scheduled with this contact
	 * @throws IllegalArgumentException if the contact does not exist
	 */
	List<PastMeeting> getPastMeetingList(Contact contact);
	
	/**
	 * Create a new record for a meeting that took place in the past
	 * 
	 * @param contacts a set of participants
	 * @param date the date on which the meeting took place
	 * @param text messages to be added about the meeting
	 * @throws IllegalArgumentException if the set of contacts is empty or any of the contacts do not exist
	 * @throws NullPointerException if any of the arguments is null
	 */
	void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text);
	
	/**
	 * Add notes to a meeting
	 * 
	 * This method is used when a future meeting takes place and is then converted to a
	 * past meeting with notes
	 * 
	 * It can also be used to add notes to a past meeting at a later date
	 * 
	 * @param id the meeting ID
	 * @param text notes to be added regarding the meeting
	 * @throws IllegalArgumentException if the meeting does not exist
	 * @throws IllegalStateException if the meeting is set for a date in the future
	 * @throws NullPointerException if the notes are null
	 */
	void addMeetingNotes(int id, String text);
	
	/**
	 * Create a new Contact with the specified name and notes
	 * 
	 * @param name the name of the contact
	 * @param notes note to be added about the contact
	 * @throws NullPointerException if the notes are null
	 */
	void addNewContact(String name, String notes);
	
	/**
	 * Returns a list containing the contacts that correspond to the IDs
	 * 
	 * @param IDs an arbitrary number of contact IDs
	 * @return a list containing the contacts that correspond to the IDs
	 * @throws IllegalArgumentException if any of the IDs does not correspond to a real contact
	 */
	Set<Contact> getContacts(int... id);
	
	/**
	 * Returns a list with the contacts whose name contains that String
	 * 
	 * @param name the string to search for
	 * @return a list of the contacts whose name contains the string
	 * @throws NullPointerException if the parameter is null
	 */
	Set<Contact> getContacts(String name);
	
	/**
	 * Save all data to disk
	 * 
	 * This method must be executed when the program is closed or when the user requests it
	 */
	void flush();
}
