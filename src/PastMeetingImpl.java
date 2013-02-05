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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PastMeetingImpl other = (PastMeetingImpl) obj;
		if (ID != other.ID)
			return false;
		return true;
	}
	
}
