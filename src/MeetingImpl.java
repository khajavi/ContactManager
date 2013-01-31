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
	
	@Override
	public String toString(){
		
		String str = "Meeting Id:" + ID + ",";
		str = str + "Date:" + Date.get(Calendar.YEAR) + "/" + Date.get(Calendar.MONTH) + "/" + Date.get(Calendar.DATE) + ",";
		str = str + "Contact Ids:";
		for(Contact c: Attendees){
			str = str + c.getId() + ",";
		}
		return str;
	}
}
