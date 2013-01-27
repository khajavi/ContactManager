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
			try{
				in.close();
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}
	}
	
	public String getContactName(String data){
		
		Pattern getName = Pattern.compile("Name:\\w[\\s[[w]]*,");
		Matcher m = getName.matcher(data);
		String name = "";
		while(m.find()){
			name = m.group().substring(5);
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
			notes = m.group().substring(6);
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
	
	public LinkedHashSet<Contact> getAttendeeIds(String data){
		
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
		
		Pattern getDate = Pattern.compile("(Date:([0-9]{4})/([0-9]{2})/([0-9]{2}))");
		Matcher m = getDate.matcher(data);
		String date = "";
		while(m.find()){
			date = m.group();
		}
		int year = Integer.parseInt(date.substring(5,9));
		date = date.substring(10);
		int month = Integer.parseInt(date.substring(0,2));
		date = date.substring(3);
		int day = Integer.parseInt(date.substring(0, 2));
		date = date.substring(3);
		Calendar meeting = new GregorianCalendar(year, month, day);
		return (GregorianCalendar) meeting;
	}
	
	public void loadMeeting(String data){
		
		int id = getMeetingID(data);
		LinkedHashSet<Contact> Ids = getAttendeeIds(data);
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
		LinkedHashSet<Contact> attendees = getAttendeeIds(data);
		
		PastMeeting pastMeeting = new PastMeetingImpl(meetingDate, attendees, notes, id);
		PastMeetings.add(pastMeeting);
	}

	
}
