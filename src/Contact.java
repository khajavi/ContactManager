/**
 * A contact is a person with whom business has, is or will be done
 * 
 * A contact has a unique ID (unique), a name (probably unique) and notes that the user
 * wishes to keep about them
 */
public interface Contact {

	/**
	 * Returns the ID of the contact
	 * 
	 * @return id returns the contacts ID
	 */
	int getId();
	
	/**
	 * Returns the contact's name 
	 * 
	 * @return name returns the contact's name
	 */
	String getName();
	
	/**
	 * Returns any notes there are about the contact
	 * 
	 * If there are no notes about the contact an empty String is returned
	 * 
	 * @return notes a String with notes about the contact
	 */
	String getNotes();
	
	/**
	 * Add notes about the contact 
	 * 
	 * @param the notes to be added
	 */
	void addNotes(String note);
}
