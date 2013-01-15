import java.util.Calendar;
import java.util.Set;

public class PastMeetingImpl extends MeetingImpl implements PastMeeting {
	
	private String Notes;
	
	public PastMeetingImpl(Calendar date, Set<Contact> attendees, String notes){
		super (date, attendees);
		this.Notes = notes;
	}
	
	public void addNotes(String notes){
		this.Notes = notes;
	}
	
	public String getNotes(){
		return Notes;
	}
}
