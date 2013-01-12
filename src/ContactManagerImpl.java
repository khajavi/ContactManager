import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.lang.Exception;

public class ContactManagerImpl implements ContactManager {

		public static int IDnumbers;
		private Set<Contact> Contacts;
		private List<Meeting> FutureMeetings;
		private List<Meeting> PastMeetings;
		private Calendar Date;
		
		/**
		 * The methods checks whether or not the meeting is scheduled for the future.
		 * If the date is in the past an IlegalArgumentException is thrown.
		 * If scheduled for the present time or later a new FutureMeeting is created
		 * and added to the List if future Meetings.
		 */
		public int addFutureMeeting(Set<Contact> attendees, Calendar date){
			
			if(date.before(Date)){
				throw new IllegalArgumentException("Unheld meetings can only be set in the future");
			}
			Meeting m = new FutureMeetingImpl(date, attendees);
			FutureMeetings.add(m);
			return m.getID();
		}
}
