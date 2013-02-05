import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class ContactManagerImpl implements ContactManager {
		
		private final String filename; 
		public static int IDnumbers;
		private LinkedHashSet<Contact> Contacts;
		private ArrayList<Meeting> FutureMeetings;
		private ArrayList<PastMeeting> PastMeetings;
		
		
		public ContactManagerImpl(String filename) {
			
			this.filename = filename;
			this.Contacts = new LinkedHashSet<Contact>();
			this.FutureMeetings = new ArrayList<Meeting>();
			this.PastMeetings = new ArrayList<PastMeeting>();
			
			DataUtilitiesImpl d = new DataUtilitiesImpl(filename);
			d.readData();
			
			this.Contacts = d.getContacts();
			this.FutureMeetings = d.getFutureMeetings();
			this.PastMeetings = d.getPastMeetings();
		}

		public int addFutureMeeting(Set<Contact> attendees, Calendar date){
			
			GregorianCalendar Date = new GregorianCalendar();
			if(date.before(Date)){
				throw new IllegalArgumentException();
			}
			boolean fakeContact = checkContacts(attendees);
			if(!fakeContact){
				throw new IllegalArgumentException();
			}
			Meeting m = new FutureMeetingImpl(attendees, date, IDnumbers);
			IDnumbers++;
			FutureMeetings.add(m);
			return m.getID();
		}
		
		public boolean checkContacts(Set<Contact> attendees){
			
			for(Contact contact: attendees){
				if(!Contacts.contains(contact)){
					return false;
				}
			}
			return true;
		}

		public PastMeeting getPastMeeting(int id){
			
			for(Meeting meeting: FutureMeetings){
				if(meeting.getID() == id){
					throw new IllegalArgumentException();
				}
			}
			for(PastMeeting meeting: PastMeetings){
				if(meeting.getID() == id){
					return meeting;
				}
			}
			return null;
		}

		public FutureMeeting getFutureMeeting(int id){
			
			for(PastMeeting meeting: PastMeetings){
				if(meeting.getID() == id){
					throw new IllegalArgumentException();
				}
			}
			for(Meeting meeting: FutureMeetings){
				if(meeting.getID() == id){
					return (FutureMeeting) meeting;
				}
			}
			return null;
		}
		
		public Meeting getMeeting(int id){
			
			for(Meeting meeting: FutureMeetings){
				if(meeting.getID() == id){
					return meeting;
				}
			}
			
			for(PastMeeting meeting: PastMeetings){
				if(meeting.getID() == id){
					return (Meeting)meeting;
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
			}else{
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
		
		public List<Meeting> getFutureMeetingList(Contact contact){
			if(!Contacts.contains(contact))
				throw new IllegalArgumentException();
			
			Set<Meeting> meetings = new LinkedHashSet<Meeting>();
			
			for(Meeting meeting: FutureMeetings){
				if(meeting.getContacts().contains(contact)){
					meetings.add(meeting);
				}
			}
			List<Meeting> result = new ArrayList<Meeting>();
			result.addAll(meetings);
			result = quickSort(result);
			return result;
		}
		
		public List<Meeting> getFutureMeetingList(Calendar date){
			
			Calendar Date = Calendar.getInstance();
			if(date.before(Date)){
				throw new IllegalArgumentException();
			}
			Set<Meeting> meetings = new LinkedHashSet<Meeting>();
			for(Meeting meeting: FutureMeetings){
				if(meeting.getDate().equals(date))
					meetings.add(meeting);
				
			}
			List<Meeting> result = new ArrayList<Meeting>();
			result.addAll(meetings);
			result = quickSort(result);
			return result;
		}
		
		public List<PastMeeting> getPastMeetingList(Contact contact){

			if(!Contacts.contains(contact)){
				throw new IllegalArgumentException("This contact does not exist");
			}
			Set<PastMeeting> meetings = new LinkedHashSet<PastMeeting>();
			
			for(PastMeeting meeting: PastMeetings){
				if(meeting.getContacts().contains(contact))
					meetings.add(meeting);
			}
			for(PastMeeting meeting: meetings){
				System.out.println(meeting.toString());
			}
			List<Meeting> temp = new ArrayList<Meeting>();
			
			for(PastMeeting meeting: meetings){
				System.out.println(meeting.toString());
				temp.add((Meeting)meeting);
			}
			for(Meeting meeting: temp){
				System.out.println(meeting.toString());
			}
			temp = quickSort(temp);
			
			List<PastMeeting> result = new ArrayList<PastMeeting>();
			
			for(Meeting meeting: temp){
				result.add((PastMeeting)meeting);
			}
			return result;
		}
		
		public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text){

			if(contacts == null || date == null || text == null){
				throw new NullPointerException();
			}else if(contacts.isEmpty() || !Contacts.containsAll(contacts)){
				throw new IllegalArgumentException();
			}else{
				PastMeeting meeting = new PastMeetingImpl(date, contacts, text, IDnumbers);
				IDnumbers++;
				PastMeeting result = (PastMeeting)meeting;
				PastMeetings.add(result);
			}
			
		}

		public void addMeetingNotes(int id, String text){
			
			if(text == null)
				throw new NullPointerException();
			
			boolean meetingExists = false;
			GregorianCalendar now = new GregorianCalendar();
			
			for(Meeting meeting: FutureMeetings){
				if(meeting.getID() == id){
					meetingExists = true;
					if(meeting.getDate().after(now)){
						throw new IllegalStateException();
					}else{
						PastMeetings.add(new PastMeetingImpl(meeting.getDate(), meeting.getContacts(), text, id));
						FutureMeetings.remove(meeting);
					}
				}
			}
			for(PastMeeting meeting: PastMeetings){
				if(meeting.getID() == id){
					meetingExists = true;
					meeting.addNotes(text);
				}
			}
			if(!meetingExists)
				throw new IllegalArgumentException();
		}
		
		public void addNewContact(String name, String notes){
			
			if(notes == null){
				throw new NullPointerException();
			}
			Contacts.add(new ContactImpl(name, notes, IDnumbers));
			IDnumbers++; 
		}
		
		public Set<Contact> getContacts(int... id){
			
			Set<Contact> result = new LinkedHashSet<Contact>();
			for(int ID: id){
				for(Contact contact: Contacts){
					if(ID == contact.getId()){
						result.add(contact);
					}
				}
			}
			if(result.size() != id.length){
				throw new IllegalArgumentException();
			}
			return result;
		}
		
		public Set<Contact> getContacts(String name){
			
			if(name == null){
				throw new NullPointerException();
			}
			Set<Contact> result = new LinkedHashSet<Contact>();
			for(Contact contact: Contacts){
				if(contact.getName().equals(name)){
					result.add(contact);
				}
			}
			return result;
		}

		public void flush() throws IOException {
		PrintWriter printwriter = null;
			try{
				File file = new File(filename);
				file.delete();
				file.createNewFile();
				printwriter = new PrintWriter(file);
				printwriter.println("IDnumbers:" + IDnumbers);
				List<String> data = generateData();
				for(int i = 0; i < data.size(); i++){
					printwriter.write(data.get(i));
					printwriter.println();
				}
			}catch(IOException ex){
				ex.printStackTrace();
			}finally{
				if(printwriter != null){
					printwriter.close(); 
				}
			}
		}
		
		/**
		 * Parses contacts, meetings and past meetings to strings and then adds them to a list of strings
		 * so that they can be written to file by the flush method.
		 */
		
		public List<String> generateData(){
			
			List<String> data = new ArrayList<String>();
			
			for(Contact c: Contacts){
				data.add(c.toString());
			}
			
			for(Meeting m: FutureMeetings){
				data.add(m.toString());
			}
			
			for(PastMeeting p: PastMeetings){
				data.add(p.toString());
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
			}catch (NullPointerException ex){
				ex.printStackTrace();
			}
		}
		
		public void Exit() throws IOException{
			try{
				flush();
				System.exit(0);//need to check what to put in here.
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}
		
		public static void main (String [] args){
			ContactManagerImpl cm = new ContactManagerImpl("/Users/williamhogarth/Documents/workspace/Contact Manager/src/contacts.txt");
			try {
				cm.launch();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		private void launch() throws IOException{
		
			addNewContact("Tom Wolfe", "Written nothing good for years");
			addNewContact("Martin Amis", "Martin my dad is Kingsley Amis Amis");
			addNewContact("Bret Easton Ellis", "There is some sick going on in my cranium");
			addNewContact("Douglas Adams", "funniest writer dead");
			addNewContact("Joseph Heller", "I'll catch you in Catch 22");
			addNewContact("Jonathan Franzen", "Freedom, the Corrections, Fuck off");
			addNewContact("Kingsley Amis", "Lucky Jim");
			
			Set<Contact> cont = new LinkedHashSet<Contact>();
			
			cont = getContacts(1,2,3);
			Calendar date = new GregorianCalendar(2011, 10, 8);
			
			
			addNewPastMeeting(cont, date, "Fun meeting");
			
			date = new GregorianCalendar(1988,8,8);
			cont = getContacts(4,5);
			addNewPastMeeting(cont, date, "Hilarious dudes");

			cont = getContacts(6,7);
			date = new GregorianCalendar();
			
			addFutureMeeting(cont, date);
			
			cont = getContacts(4,5,7);
			date = new GregorianCalendar(2013, 8, 8);
			addFutureMeeting(cont, date);
			
			Exit();
			
		}

		public LinkedHashSet<Contact> getContacts() {
			return Contacts;
		}

		public ArrayList<Meeting> getFutureMeetings() {
			return FutureMeetings;
		}

		public ArrayList<PastMeeting> getPastMeetings() {
			return PastMeetings;
		}
		
}
