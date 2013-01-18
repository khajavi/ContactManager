import java.util.*;
import java.io.File; 

public class ContactManagerImpl implements ContactManager {
		
		private final String filename; 
		public static int IDnumbers;
		private LinkedHashSet<Contact> Contacts;
		private ArrayList<Meeting> FutureMeetings;
		private ArrayList<PastMeeting> PastMeetings;
		
		
		public ContactManagerImpl(String filename){
			this.filename = filename;
			this.Contacts = new LinkedHashSet<Contact>();
			this.FutureMeetings = new ArrayList<Meeting>();
			this.PastMeetings = new ArrayList<PastMeeting>();
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
					Meeting m = new FutureMeetingImpl(attendees, date);
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
						FutureMeeting m = (FutureMeeting)FutureMeetings.get(i);
						return m;
					}
				}
				return null;
			}catch (IllegalArgumentException ex){
				ex.printStackTrace();
				System.out.println("This meeting has already taken place");
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
				if(contacts.isEmpty() || !Contacts.containsAll(contacts)){
					throw new IllegalArgumentException();
				}
				PastMeeting meeting = new PastMeetingImpl(date, contacts, text);
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
		//need to account for circumstance when just adding notes
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
				PastMeeting meeting = new PastMeetingImpl(FutureMeetings.get(index).getDate(), FutureMeetings.get(index).getContacts(),text, id);
				PastMeetings.add(meeting); 
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
		 * Could improve method by parsing the array of ids to a set in case there are duplicate
		 * ids. 
		 */
		
		public Set<Contact> getContacts(int... id){
			try{
				Set<Contact> result = new LinkedHashSet<Contact>(); 
				for(int i = 0; i < id.length; i++){
					Iterator<Contact> itr = Contacts.iterator();
					while(itr.hasNext()){
						Contact c = itr.next();
						if(c.getId() == id[i]){
							result.add(c);
						}
					}
				}
				if(result.size() != id.length){
					throw new IllegalArgumentException();
				}
				return result;
			}catch(IllegalArgumentException ex){
				ex.printStackTrace();
				System.out.println("At least one of those contacts does not exist");
				return null;
			}
		}
		
		public Set<Contact> getContacts(String name){
			try{
				Set<Contact> result = new LinkedHashSet<Contact>();
				Iterator<Contact> itr = Contacts.iterator();
				while (itr.hasNext()){
					Contact c = itr.next();
					if(c.getName().equals(name)){
						result.add(c);
					}
				}
				return result;
			}catch (NullPointerException ex){
					ex.printStackTrace();
					return null;
				}
			}

		@Override
		public void flush() {
			deleteFile();
			try{
				File file = new File(filename);
				List<String> data = generateData();
			}
			
		}
		
		/**
		 * Converts the contacts and meetings held by the Contact Manager into a String format so
		 * that they can be written to the file created by the Flush method.
		 */
		//Change so add type, id, attendees, notes
		public List<String> generateData(){
			
			List<String> data = new ArrayList<String>();
			Iterator<Contact> itr = Contacts.iterator();
			String s;
			while(itr.hasNext()){
				s = "Contact" + "," + itr.next().getName() + "," +  itr.next().getId() + "," + itr.next().getNotes();
				data.add(s);
			}
			for(int i = 0; i < FutureMeetings.size(); i++){
				s = "Meeting" + "," + FutureMeetings.get(i).getID() + "," + FutureMeetings.get(i).getDate() + ",";
				itr = FutureMeetings.get(i).getContacts().iterator();
				while(itr.hasNext()){
					s = s + itr.next().getId() + ",";
				}
				data.add(s);
			}
			for(int i = 0; i < PastMeetings.size(); i++){
				s = "PastMeeting" + "," + PastMeetings.get(i).getID() + "," + PastMeetings.get(i).getDate() + ",";
				itr = PastMeetings.get(i).getContacts().iterator();
				while(itr.hasNext()){
					s = s + itr.next().getId() + ",";
				}
				s = s + "notes:" + PastMeetings.get(i).getNotes();
				data.add(s);
			}
			return data;
		}
		
		/**
		 * Deletes the file holding the data that will be used when the program is opened
		 * 
		 * @throws FileNotFoundException if the file cannot be found
		 * @throws IOException
		 */
		public void deleteFile(){
			try{
				File f = new File(filename);
				f.delete();
			}catch (FileNotFoundException ex){
			}catch (IOException ex){
			}
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
			
			System.out.println("Contacts added");
			
			Set<Contact> cont = new LinkedHashSet<Contact>();
			cont = getContacts("Joseph Heller");
			System.out.println("Checking for Heller");
			Iterator<Contact> itr = cont.iterator();
			do{
				Contact c = itr.next();
				System.out.println(c.getName());
				System.out.println("Why no name?");
			}while (itr.hasNext());
			System.out.println("Checked for Heller");
			
			cont = getContacts(1,2,4,8);
			System.out.println("Was supposed to throw illegArgsException");
			
			cont = getContacts(1,2,4);
			System.out.println("Was not supposed to throw one");
			Calendar date = new GregorianCalendar(2013, 7, 8);
			
			int i = addFutureMeeting(cont, date);
			System.out.println(i);//i should equal 7
			FutureMeeting m = getFutureMeeting(7);
			m = getFutureMeeting(8);
			
			Calendar date2 = new GregorianCalendar(2013, 6, 5);
			cont = getContacts(6,5,4);
			System.out.println("Was not supposed to throw one");
			
			i = addFutureMeeting(cont, date2);
			System.out.println(i); //i should equal 8
			
			Calendar pastm = new GregorianCalendar(2012, 6, 5);
			addNewPastMeeting(cont, pastm, "I see dead people");//meeting id should equal 9
			System.out.println("checkpoint 1");
			cont = getContacts(4,5);
			addNewPastMeeting(cont, pastm, "Hitchhiker's or Catch 22?");//meeting id should be 10
			System.out.println("Checkpoint 2");
			
			PastMeeting pm = getPastMeeting(9);
			String str = pm.getNotes();
			System.out.println(str);
			System.out.println("Checkpoint 3");
			
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
