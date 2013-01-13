import java.util.Calendar;
import java.util.Set;

public class FutureMeetingImpl implements FutureMeeting {
	
	private int ID;
	private Calendar Date;
	private Set<Contact> Attendees;
	
	public FutureMeetingImpl(Calendar Date, Set<Contact> Attendees){
		ContactManagerImpl.IDnumbers++;
		ID = ContactManagerImpl.IDnumbers;
		this.Date = Date;
		this.Attendees = Attendees;
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
}
