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
		 * The method checks whether or not a meeting with this ID has been scheduled for the future,
		 * by iterating through the list of FutureMeetings. If scheduled for the future an 
		 * IllegalArgumentException is thrown. If no meeting is scheduled in the future with this id
		 * the method iterates through the list of PastMettings checking whether or not a meeting with
		 * this id exists. If found the PastMeeting with this id is returned, else null is returned.
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
		
		/**
		 * Identical to the method getPastMeeting, except FutureMeeting
		 * and PastMeeting are inverted.
		 */
		public FutureMeeting getFutureMeeting(int id){
			for (int i = 0; i < PastMeetings.size(); i++){
				if(PastMeeting.get(i).getID() == id){
					throw new IllegalArgumentException("This meeting has already taken place");
				}
			}
			for (int i = 0; i < FutureMeetings.size(); i++){
				if(FutureMeetings.get(i).getID() == id){
					return FutureMeetings.get(i);
				}
			}
			return null;
		}
}
