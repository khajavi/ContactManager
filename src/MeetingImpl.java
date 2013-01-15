import java.util.Calendar;
import java.util.Set;


public class MeetingImpl implements Meeting {

	protected int ID;
	protected Calendar Date;
	protected Set<Contact> Attendees;
	//private String Notes;
	
	public MeetingImpl(Calendar Date, Set<Contact> Attendees){
		ContactManagerImpl.IDnumbers++;
		ID = ContactManagerImpl.IDnumbers;
		this.Date = Date;
		this.Attendees = Attendees;
		//this.Notes = "";
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
	
	/**
	public String getNotes(){
		return Notes;
	}*/
}
