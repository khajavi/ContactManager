import java.util.Calendar;
import java.util.Set;

public class PastMeetingImpl extends MeetingImpl implements PastMeeting {
	
	private String Notes;
	
	
	public PastMeetingImpl(Calendar date, Set<Contact> attendees, String notes){
		super (date, attendees);
		this.Notes = notes;
	}
	
	//only to be used when a futureMeeting is being replaced with a past Meeting or when
	//loading at startup
	public PastMeetingImpl(Calendar date, Set<Contact> attendees, String notes, int id){
		super (date, attendees, id);
		this.Notes = notes;
	}
	
	public void addNotes(String notes){
		this.Notes = notes;
	}
	
	public String getNotes(){
		return Notes;
	}
}
