import java.util.Calendar;
import java.util.*;
import java.util.Set;
import java.lang.Exception;

public class ContactManagerImpl implements ContactManager {

		public static int IDnumbers;
		private Set<Contact> Contacts;
		private ArrayList<FutureMeeting> FutureMeetings;
		private ArrayList<PastMeeting> PastMeetings;
		private Calendar Date;
		
		/**
		 * The methods checks whether or not the meeting is scheduled for the future.
		 * If the date is in the past an IlegalArgumentException is thrown.
		 * If scheduled for the present time or later a new FutureMeeting is created
	.	 * and added to the List if future Meetings.
		 */
		public int addFutureMeeting(Set<Contact> attendees, Calendar date){
			if(date.before(Date)){
				throw new IllegalArgumentException("Unheld meetings can only be set in the future");
			}
			Meeting m = new FutureMeetingImpl(date, attendees);
			FutureMeetings.add(m);
			return m.getID();
		}
		
		/**
		 * Checks whether or not a meeting with this ID is scheduled for the future.
		 * If scheduled for the future an IllegalArgumentException is thrown, else the meeting 
		 * with that ID (possibly none) is returned.
		 */
		
		public PastMeeting getPastMeeting(int id){
			for(int i = 0; i < FutureMeetings.size(); i++){
				if(FutureMeetings.get(i).getID() == id){
					throw new IllegalArgumentException("This meeting has not taken place yet");
				}
			}
			for(int i = 0; i < PastMeetings.size(); i++){
				if(PastMeetings.get(i).getID() == id){
					return PastMeetings.get(i);
				}
			}
			return null;
		}
}
