import java.util.Calendar;
import java.util.Set;

public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting {
	
	public FutureMeetingImpl(Set<Contact> attendees, Calendar date, int id){
		super(date, attendees,id);
	}
}
