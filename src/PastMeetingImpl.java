import java.util.Calendar;
import java.util.Set;

public class PastMeetingImpl implements PastMeeting {
	
	private int ID;
	private Calendar Date;
	private Set<Contact> Attendees;
	private String Notes;
	/**
	 * do I want a constructor like this? How will the system turn future meetings into past meetings
	 * 
	 * @param Date
	 * @param Attendees
	 
	public PastMeetingImpl(Calendar Date, Set<Contact> Attendees){
		ContactManagerImpl.IDnumbers++;
		ID = ContactManagerImpl.IDnumbers;
		this.Date = Date;
		this.Attendees = Attendees;
	}
	**/
	
	public int getID() {
		return ID;
	}


	public Calendar getDate() {
		return Date;
	}

	public Set<Contact> getContacts() {
		return Attendees;
	}
	
	public String getNotes(){
		return Notes;
	}
	
}
