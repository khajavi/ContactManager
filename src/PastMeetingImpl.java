import java.util.Calendar;
import java.util.Set;

public class PastMeetingImpl implements PastMeeting {
	
	private int ID;
	private Calendar Date;
	private Set<Contact> Attendees;
	private String Notes;

	 
	public PastMeetingImpl(Calendar Date, Set<Contact> Attendees){
		ContactManagerImpl.IDnumbers++;
		ID = ContactManagerImpl.IDnumbers;
		this.Date = Date;
		this.Attendees = Attendees;
		this.Notes = "";
	}
	
	public PastMeetingImpl(Calendar Date, Set<Contact> Attendees, String Notes){
		ContactManagerImpl.IDnumbers++;
		ID = ContactManagerImpl.IDnumbers;
		this.Date = Date;
		this.Attendees = Attendees;
		this.Notes = Notes;
	}
	/**
	 * This constructor creates a PastMeeting from a futureMeeting. The FutureMeeting must
	 * be deleted in conjunction with the use of this constructor to preserve the uniqueness 
	 * of the IDnumbers.
	 * 
	 * @param MeetingImpl 
	 * @param notes
	 */
	
	public PastMeetingImpl(FutureMeetingImpl m, String notes){
		this.ID = m.getID();
		this.Date = m.getDate();
		this.Attendees = m.getContacts();
		this.Notes = notes;
	}
	
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
