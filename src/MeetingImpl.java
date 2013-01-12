import java.util.Calendar;
import java.util.Set;

public class MeetingImpl {

	private int ID;
	private Calendar Date;
	private Set<Contact> Attendees;
	private String Notes;
	
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
