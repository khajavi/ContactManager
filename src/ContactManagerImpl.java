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
			}else if (attendees == null){
				throw new NullPointerException();
			}else{
			Meeting m = new FutureMeetingImpl(attendees, date);
			FutureMeetings.add(m);
			return m.getID();
			}
		}

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
		
		public void addNewContact(String name, String notes){
			try{
				Contact c = new ContactImpl(name, notes);
				Contacts.add(c);
			} catch (NullPointerException ex){
				ex.printStackTrace();
			}
		}
		
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
