import java.util.Calendar;
import java.util.Set;

public class PastMeetingImpl extends MeetingImpl implements PastMeeting {
	
	private String Notes;
	
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
	
	@Override
	public String toString(){
		
		String str = super.toString();
		String str2 = "Past" + str;
		str2 = str2 + "Notes:" + Notes;
		return str2;
	}
	
	@Override
	public boolean equals(Object o){
		
		if(o instanceof PastMeetingImpl){
			if(this.getID() == ((PastMeetingImpl)o).getID()){
				return true;
			}else{
				return false;
			}
		} else{
			return false;
		}
	}
}
