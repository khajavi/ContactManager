import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DataUtilitiesImpl implements DataUtilities {
	
	private String filename;
	private LinkedHashSet<Contact> Contacts;
	private ArrayList<Meeting> FutureMeetings;
	private ArrayList<PastMeeting> PastMeetings;
	
	public DataUtilitiesImpl(String filename){
		
		this.filename = filename;
		this.Contacts = new LinkedHashSet<Contact>();
		this.FutureMeetings = new ArrayList<Meeting>();
		this.PastMeetings = new ArrayList<PastMeeting>();
	}
	
	public void readData(){
		
		File file = new File(filename);
		BufferedReader in = null;
		try{
			in = new BufferedReader(new FileReader(file));
			String line;
			while((line = in.readLine()) != null){
				if(line.startsWith("IDnumber")){
					Pattern getId = Pattern.compile("[0-9]+");
					Matcher m = getId.matcher(line);
					while(m.find()){
						ContactManagerImpl.IDnumbers = Integer.parseInt(m.group());
					}
				}else if(line.startsWith("Name")){
					loadContact(line);
				}else if(line.startsWith("Meeting")){
					loadMeeting(line);
				}else if(line.startsWith("PastMeeting")){
					loadPastMeeting(line);
				}
			}
		}catch(FileNotFoundException ex){
			ex.printStackTrace();
		}catch(IOException ex){
			ex.printStackTrace();
		}finally{
			if(in != null){
				try{
				in.close();
				}catch (IOException ex){
					ex.printStackTrace();
				}
			}
		}
	}
		
	public String getContactName(String data){
		
		Pattern getName = Pattern.compile("Name:\\w[\\s\\w]*,");
		Pattern findName = Pattern.compile("\\w[\\s\\w]*");
		Matcher m = getName.matcher(data);
		String temp = "";
		while(m.find()){
			temp = m.group().substring(5);
		}
		String name = "";
		m = findName.matcher(temp);
			while(m.find()){
				name = m.group();
			}
			return name;
		}
	
	public int getContactID(String data){
		
		Pattern getId = Pattern.compile("Contact Id:\\d*");
		Pattern findId = Pattern.compile("[0-9]+");
		int id = 0;
		Matcher m = getId.matcher(data);
		
		while(m.find()){
			
			Matcher m2 = findId.matcher(m.group());
			while(m2.find()){
				id = Integer.parseInt(m2.group());
			}
		}
		return id;	
	}
	
	public String getNotes(String data){
		
		String notes = "";
		Pattern getNotes = Pattern.compile("Notes:");
		Matcher m = getNotes.matcher(data);
		while(m.find()){
			notes = data.substring(m.end());
		}
		return notes;
	}
	
	public void loadContact(String data){
		
		String name = getContactName(data);
		int id = getContactID(data);
		String notes = getNotes(data);
		Contacts.add(new ContactImpl(name, notes, id));
	}
	
	public int getMeetingID(String data){
		
		Pattern getId = Pattern.compile("Meeting Id:\\d*");
		Pattern findId = Pattern.compile("[0-9]+");
		int id = 0;
		Matcher m = getId.matcher(data);
		while(m.find()){
			
			Matcher m2 = findId.matcher(m.group());
			while(m2.find()){
				id = Integer.parseInt(m2.group());
			}
		}
		return id;
	}
	
	public LinkedHashSet<Contact> getAttendees(String data){
		
		LinkedHashSet<Contact> Ids = new LinkedHashSet<Contact>();
		Pattern ContactIds = Pattern.compile("Contact Id:(([0-9]*,)*)");
		Pattern findId = Pattern.compile("[0-9]+");
		ArrayList<Integer> listIds = new ArrayList<Integer>();
		
		Matcher m = ContactIds.matcher(data);
		while(m.find()){
			Matcher m2 = findId.matcher(m.group());
			while(m2.find()){
				int id = Integer.parseInt(m2.group());
				listIds.add(id);
			}
		}
		for(Integer id: listIds){
			for (Contact c: Contacts){
				if(c.getId() == id){
					Ids.add(c);
				}
			}
		}
		return Ids;
	}
	
	public GregorianCalendar getMeetingDate(String data){
		
		Pattern getDate = Pattern.compile("Date:([0-9]{4})/([0-9]{1,2})/([0-9]{1,2})");
		Pattern getMonth = Pattern.compile("/[0-9]{1,2}/");
		Pattern getDay = Pattern.compile("Date:([0-9]{4})/([0-9]{1,2})/");
		Matcher m = getDate.matcher(data);
		String date = "";
		while(m.find()){
			date = m.group();
		}
		int year = Integer.parseInt(date.substring(5,9));
		m = getMonth.matcher(date);
		String temp = "";
		while(m.find()){
			temp = m.group();
		}
		int month = Integer.parseInt(temp.substring(1, temp.length() - 1));
		m = getDay.matcher(date);
		int day = 0;
		while(m.find()){
			day = m.end();
		}
		day = Integer.parseInt(date.substring(day));
		Calendar meeting = new GregorianCalendar(year, month, day);
		return (GregorianCalendar) meeting;
	}
	
	public void loadMeeting(String data){
		
		int id = getMeetingID(data);
		LinkedHashSet<Contact> Ids = getAttendees(data);
		GregorianCalendar meetingDate = getMeetingDate(data);
		
		Calendar now = new GregorianCalendar();
		if(meetingDate.after(now)){
			FutureMeeting futureMeeting = new FutureMeetingImpl(Ids, meetingDate, id);
			FutureMeetings.add(futureMeeting);
		}else{
			String notes = "";
			PastMeeting pastMeeting = new PastMeetingImpl(meetingDate, Ids, notes, id);
			PastMeetings.add(pastMeeting);
		}		
	}
	
	public void loadPastMeeting(String data){
		
		int id = getMeetingID(data);
		String notes = getNotes(data);
		GregorianCalendar meetingDate = getMeetingDate(data);
		LinkedHashSet<Contact> attendees = getAttendees(data);
		
		PastMeeting pastMeeting = new PastMeetingImpl(meetingDate, attendees, notes, id);
		PastMeetings.add(pastMeeting);
	}

	public String getFilename() {
		return filename;
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
