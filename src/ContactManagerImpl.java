import java.util.*;
import java.lang.Exception;
import java.lang.reflect.Array;

//Add a field meetings?
//need to change everything from meeting, etc to meetingImpl

public class ContactManagerImpl implements ContactManager {

		public static int IDnumbers;
		private LinkedHashSet<Contact> Contacts;
		private ArrayList<FutureMeetingImpl> FutureMeetings;
		private ArrayList<PastMeetingImpl> PastMeetings;
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
				if(PastMeetings.get(i).getID() == id){
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
		
		
		/**
		 * Checks FutureMeetings and PastMeetings for the meeting with the ID and then upcasts
		 * it to MeetingImpl
		 */
		public Meeting getMeeting(int id){
			for(int i = 0; i < FutureMeetings.size(); i++){
				if(FutureMeetings.get(i).getID() == id){
					return (Meeting)FutureMeetings.get(i);
				}	
			}
			for(int i = 0; i < PastMeetings.size(); i++){
				if(PastMeetings.get(i).getID() == id){
					return (Meeting)PastMeetings.get(i);
				}
			}
			return null;
		}
		
		/**
		 * Sorts a list of meetings chronologically 
		 * 
		 * @param List<Meeting> a list of Meetings
		 * @return List<Meeting> returns a sorted list of Meetings 
		 */
		
		public List<Meeting> quickSort(List<Meeting> list){
			if(list.size() == 1){
				return list;
			}
			else{
				List<Meeting> before = new ArrayList<Meeting>();
				List<Meeting> after = new ArrayList<Meeting>();
				Meeting pivot = list.get(0);
				for(int i = 1; i < list.size(); i++){
					if(list.get(i).getDate().before(pivot.getDate())){
						before.add(list.get(i));
					}
					else{
						after.add(list.get(i));
					}
				}
				if(before.size() > 1){
					quickSort(before);
				}
				if(after.size() > 1){
					quickSort(after);
				}
				List<Meeting> result = before;
				result.add(pivot);
				for(int i = 0; i < after.size(); i++){
					result.add(after.get(i));
				}
				return result;
			}
		}
		
		/**
		 * This method sorts the list of meetings being held with the contact by calling the 
		 * quickSort method. A lack of duplicates is ensured by using a set prior to sorting
		 * the list.
		 */
		
		public List<Meeting> getFutureMeetingList(Contact contact){
			if(!Contacts.contains(contact)){
				throw new IllegalArgumentException("This contact does not exist");
			}
			Set<Meeting> meetings = new LinkedHashSet<Meeting>();
			for(int i = 0; i < FutureMeetings.size(); i++){
				if(FutureMeetings.get(i).getContacts().contains(contact)){
					meetings.add(FutureMeetings.get(i));
				}
			}
			List<Meeting> result = new ArrayList<Meeting>();
			meetings.addAll(result); 
			quickSort(result);
			return result;
		}
		
		/**
		 * This method sorts the list of meetings being held on a certain day by calling
		 * the quickSort method. A lack of duplicates is ensured by using a set prior to sorting
		 * the list.
		 */
		public List<Meeting> getFutureMeetingList(Calendar date){
			if(date.before(Date)){
				throw new IllegalArgumentException("That's in the past");
			}
			Set<Meeting> meetings = new LinkedHashSet<Meeting>();
			for(int i = 0; i < FutureMeetings.size(); i++){
				if(FutureMeetings.get(i).getDate().compareTo(date) == 0){
					meetings.add(FutureMeetings.get(i));
				}
			}
			List<Meeting> result = new ArrayList<Meeting>();
			meetings.addAll(result);
			quickSort(result);
			return result;			
			}
		
		/**
		 * 
		 */
		
		public List<PastMeeting> getPastMeetingList(Contact contact){
			if(!Contacts.contains(contact)){
				throw new IllegalArgumentException("This contact does not exist");
			}
			Set<PastMeeting> meetings = new LinkedHashSet<PastMeeting>();
			for(int i = 0; i < PastMeetings.size(); i++){
				if(PastMeetings.get(i).getContacts().contains(contact)){
					meetings.add(PastMeetings.get(i));
				}
			}
			//need to cast the PastMeetings to meetings and back
			List<PastMeeting> result = new ArrayList<PastMeeting>();
			meetings.addAll(result);
			quickSort(result);
			return result;
		}
		
		/**
		 * 
		 */
		
		public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text){
			try{
				if(contacts.isEmpty() || !Contacts.contains(contacts)){
					throw new IllegalArgumentException();
				}
				PastMeetingImpl meeting = new PastMeetingImpl(date, contacts, text);
				PastMeetings.add(meeting);
			} catch (NullPointerException ex){
				ex.printStackTrace();
			}
		}
		/**
		 * 
		 * 
		 */
		
		public void addMeetingNotes(int id, String text){
			try {
				boolean illegalArgument = false;
				int index = 0;
				for(int i = 0; i < FutureMeetings.size(); i++){
					if(FutureMeetings.get(i).getID() == id){
						illegalArgument = true;
						if(Date.before(FutureMeetings.get(i).getDate())){
							throw new IllegalStateException();
						}
						index = i;
					}
				}
				if(!illegalArgument){
					throw new IllegalArgumentException();
				}
				PastMeetingImpl meeting = new PastMeetingImpl(FutureMeetings.get(index), text);
				PastMeetings.add(meeting);
				FutureMeetings.remove(index);
			} catch (NullPointerException ex){
				ex.printStackTrace();
			}
		}
		
		/**
		 * 
		 */
		
		public void addNewContact(String name, String notes){
			try{
				ContactImpl c = new ContactImpl(name, notes);
				Contacts.add(c);
			} catch (NullPointerException ex){
				ex.printStackTrace();
			}
		}
		
		/**
		 * 
		 */
		
		public Set<Contact> getContacts(int... id){
			Set<ContactImpl> result = new LinkedHashSet<ContactImpl>();
			Iterator it = Contacts.iterator();
			for(int i = 0; i < id.length; i++){
	
			}
		}
		
}
