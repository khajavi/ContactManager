import java.util.*;
import java.lang.Exception;
import java.lang.reflect.Array;

//Add a field meetings?
//need to change everything from meeting, etc to meetingImpl
//don't want date field, get a new instance each time I think.
public class ContactManagerImpl implements ContactManager {

		public static int IDnumbers;
		private LinkedHashSet<Contact> Contacts;
		private ArrayList<Meeting> FutureMeetings;
		private ArrayList<PastMeeting> PastMeetings;
		//private Calendar Date;
		
		public ContactManagerImpl(){
			//This constructor is only temporary, it will work by reading the text document and then
			//initialising everything from there

			this.Contacts = new LinkedHashSet<Contact>();
			this.FutureMeetings = new ArrayList<Meeting>();
			this.PastMeetings = new ArrayList<PastMeeting>();
			//this.Date = Calendar.getInstance();
		}
		
		/**
		 * The methods checks whether or not the meeting is scheduled for the future.
		 * If the date is in the past an IlegalArgumentException is thrown. Returns 0 in this case.
		 * 
		 * If scheduled for the present time or later a new FutureMeeting is created
	.	 * and added to the List if future Meetings. The meeting id is returned.
		 */
		public int addFutureMeeting(Set<Contact> attendees, Calendar date){
			try{
					Calendar Date = Calendar.getInstance();
					if(date.before(Date)){
						throw new IllegalArgumentException();
					}
					Meeting m = new MeetingImpl(date, attendees);
					FutureMeetings.add(m);
					return m.getID();
			} catch (IllegalArgumentException ex){
				ex.printStackTrace();
				System.out.println("That meeting is shceduled for the future");
				return 0;
			}
		}
		
		/**
		 * The method checks whether or not a meeting with this ID has been scheduled for the future,
		 * by iterating through the list of FutureMeetings. If scheduled for the future an 
		 * IllegalArgumentException is thrown. If no meeting is scheduled in the future with this id
		 * the method iterates through the list of PastMettings checking whether or not a meeting with
		 * this id exists. If found the PastMeeting with this id is returned, else null is returned.
		 */
		
		public PastMeeting getPastMeeting(int id){
			try{
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
			} catch (IllegalArgumentException ex){
				ex.printStackTrace();
				System.out.println("This meeting is scheduled for the future");
				return null;
			}
		}
		
		/**
		 * The method checks whether a meeting with this id has been scheduled in the past by iterating
		 * through PastMeetings checking each Id. If a match is found the IllegalArgumentException is thrown
		 * and null is returned.
		 * 
		 * The method then iterates through FutureMeetings checking each Id, if a match is found that
		 * that meeting is returned, else null is returned.
		 * 
		 */
		public FutureMeeting getFutureMeeting(int id){
			try{
				for (int i = 0; i < PastMeetings.size(); i++){
					if(PastMeetings.get(i).getID() == id){
						throw new IllegalArgumentException();
					}
				}
				for (int i = 0; i < FutureMeetings.size(); i++){
					if(FutureMeetings.get(i).getID() == id){
						FutureMeeting m = (FutureMeeting) FutureMeetings.get(i);
						return m;
					}
				}
				return null;
			}catch (IllegalArgumentException ex){
				ex.printStackTrace();
				System.out.println("");
				return null;
			}
			
		}
		
		/**
		 * Checks FutureMeetings and PastMeetings for the meeting with the ID. Upcasts
		 * a PastMeeting to meeting.
		 */
		public Meeting getMeeting(int id){
			for(int i = 0; i < FutureMeetings.size(); i++){
				if(FutureMeetings.get(i).getID() == id){
					return FutureMeetings.get(i);
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
			try{
				if(!Contacts.contains(contact)){
					throw new IllegalArgumentException();
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
			}catch (IllegalArgumentException ex){
				ex.printStackTrace();
				System.out.println("This contact does not exist");
				return null;
			}
		}
		
		/**
		 * This method sorts the list of meetings being held on a certain day by calling
		 * the quickSort method. A lack of duplicates is ensured by using a set prior to sorting
		 * the list.
		 */
		public List<Meeting> getFutureMeetingList(Calendar date){
			try{
				Calendar Date = Calendar.getInstance();
				if(date.before(Date)){
					throw new IllegalArgumentException();
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
			} catch (IllegalArgumentException ex){
				System.out.println("This date is in the past");
				return null;
			}
			}
		
		/**
		 * This method retrieves all of the meetings previously held with a contact, by first checking
		 * whether or not the contact exists. The method then iterates through PastMeetings
		 * adding each meeting held with the contact to the set. These are added to a list that is sorted
		 * using the quicksort method. The meetings are downcast to pastMeetings and the list is returned.
		 */
		
		public List<PastMeeting> getPastMeetingList(Contact contact){
			try{
				if(!Contacts.contains(contact)){
					throw new IllegalArgumentException("This contact does not exist");
				}
				Set<Meeting> meetings = new LinkedHashSet<Meeting>();
				for(int i = 0; i < PastMeetings.size(); i++){
					if(PastMeetings.get(i).getContacts().contains(contact)){
						Meeting m = (Meeting) PastMeetings.get(i);
						meetings.add(m);
					}
				}
				List<Meeting> temp = new ArrayList<Meeting>();
				meetings.addAll(temp);
				quickSort(temp);
				List<PastMeeting> result = new ArrayList<PastMeeting>();
				for(int i = 0; i < temp.size(); i++){
					PastMeeting m = (PastMeeting) temp.get(i);
					result.add(m);
				}
				return result;
			} catch(IllegalArgumentException ex){
				ex.printStackTrace();
				System.out.println(ex);
				return null;
			}
		}
		
		/**
		 * Creates a new past meeting.
		 */
		
		public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text){
			try{
				if(contacts.isEmpty() || !Contacts.contains(contacts)){
					throw new IllegalArgumentException();
				}
				PastMeetingImpl meeting = new PastMeetingImpl(date, contacts, text);
				PastMeeting result = (PastMeeting)meeting;
				PastMeetings.add(result);
			} catch (IllegalArgumentException ex){
				if(contacts.isEmpty()){
					ex.printStackTrace();
					System.out.println("The set of contacts is empty");
				} else{
					ex.printStackTrace();
					System.out.println("At least one of the contacts does not exist");
				}
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
				Calendar Date = Calendar.getInstance();
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
				PastMeetingImpl meeting = (PastMeetingImpl)FutureMeetings.get(index);
				meeting.addNotes(text);
				PastMeeting result = (PastMeeting) meeting;
				PastMeetings.add(result); 
				FutureMeetings.remove(index);
			} catch (NullPointerException ex){
				ex.printStackTrace();
			} catch (IllegalStateException ex){
				ex.printStackTrace();
			}
		}
		
		/**
		 * 
		 */
		
		public void addNewContact(String name, String notes){
			try{
				Contact c = new ContactImpl(name, notes);
				Contacts.add(c);
			} catch (NullPointerException ex){
				ex.printStackTrace();
			}
		}
		
		/**
		 * may need to upcast everything to a contact.
		 */
		
		public Set<Contact> getContacts(int... id){
			Set<Contact> result = new LinkedHashSet<Contact>();
			ArrayList<ContactImpl> temp = new ArrayList<ContactImpl>();
			Contacts.addAll(temp);
			boolean contactExists = false;
			for(int i = 0; i < id.length; i++){
				contactExists = false;
				for(int j = 0; j < temp.size(); j++){
					if(temp.get(j).getId() == id[i]){
						result.add(temp.get(j));
						contactExists = true;
					}
				}
				if(!contactExists){
					throw new IllegalArgumentException("There is no contact with that ID");
				}
			}
			return result;
		}
		
		public Set<Contact> getContacts(String name){
			try{
				ArrayList<ContactImpl> temp = new ArrayList<ContactImpl>();
				Contacts.addAll(temp);
				Set<Contact> result = new LinkedHashSet<Contact>();
				for (int i = 0; i < temp.size(); i++){
					if(temp.get(i).getName().equals(name)){
						Contact c = (Contact) temp.get(i);
						result.add(c);
					}
					
				} 
				return result;
			}	catch (NullPointerException ex){
					ex.printStackTrace();
				}
			return null;
			}

		@Override
		public void flush() {

			// TODO Auto-generated method stub
			
		}
		
		public void Exit(){
			flush();
			System.exit(0);//need to check what to put in here.
		}
		
		public static void main (String [] args){
			ContactManagerImpl cm = new ContactManagerImpl();
			cm.launch();
		}
		
		private void launch(){
			
			//contact numbers 1-6
			addNewContact("Tom Wolfe", "Written nothing good for years");
			addNewContact("Martin Amis", "Martin my dad is Kingsley Amis Amis");
			addNewContact("Bret Easton Ellis", "There is some sick going on in my cranium");
			addNewContact("Douglas Adams", "funniest writer dead");
			addNewContact("Joseph Heller", "I'll catch you in Catch 22");
			addNewContact("Jonathan Franzen", "Freedom, the Corrections, Fuck off");
			
			Set<Contact> cont = new LinkedHashSet<Contact>();
			cont = getContacts(1,2,4,8);
			
			cont = getContacts(1,2,4);
			Calendar date = new GregorianCalendar(2013, 7, 8);
			
			int i = addFutureMeeting(cont, date);
			System.out.println(i);//i should equal 7
			FutureMeeting m = getFutureMeeting(7);
			m = getFutureMeeting(8);
			
			Calendar date2 = new GregorianCalendar(2013, 6, 5);
			cont = getContacts(6,5,4);
			i = addFutureMeeting(cont, date2);
			System.out.println(i); //i should equal 8
			
			Calendar pastm = new GregorianCalendar(2012, 6, 5);
			addNewPastMeeting(cont, pastm, "I see dead people");//meeting id should equal 9
			
			cont = getContacts(4,5);
			addNewPastMeeting(cont, pastm, "Hitchhiker's or Catch 22?");//meeting id should be 10
			
			PastMeeting pm = getPastMeeting(9);
			String str = pm.getNotes();
			System.out.println(str);
			
			pm = getPastMeeting(10);
			str = pm.getNotes();
			System.out.println(str);
			
			Calendar d = new GregorianCalendar();
			addFutureMeeting(cont, d);//id should be 11
			
			addMeetingNotes(11,"Discussions ongoing");
			pm = getPastMeeting(11);
			str = pm.getNotes();
			System.out.println(str);
		
			
			
		}
	
}
