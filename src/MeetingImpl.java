import java.util.Calendar;
import java.util.Set;


public class MeetingImpl implements Meeting {

	protected int ID;
	protected Calendar Date;
	protected Set<Contact> Attendees;
	
	public MeetingImpl(Calendar Date, Set<Contact> Attendees){
		ContactManagerImpl.IDnumbers++;
		ID = ContactManagerImpl.IDnumbers;
		this.Date = Date;
		this.Attendees = Attendees;
	}
	
	public MeetingImpl(Calendar Date, Set <Contact> Attendees, int id){
		this.ID = id;
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
