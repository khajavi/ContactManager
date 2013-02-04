import java.util.Calendar;
import java.util.Set;

public class PastMeetingImpl extends MeetingImpl implements PastMeeting {
	
	private String Notes;
	
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
	
	@Override
	public String toString(){
		
		String str = super.toString();
		String str2 = "Past" + str;
		str2 = str2 + "Notes:" + Notes;
		return str2;
	}
	
}
