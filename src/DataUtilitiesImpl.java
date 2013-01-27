import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
	
	public int getID(String data){
		
		Pattern getId = Pattern.compile("Id:\\d*");
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
		int id = getID(data);
		String notes = getNotes(data);
		Contacts.add(new ContactImpl(name, notes, id));	
	}
	
	public void loadMeeting(String data){
		
	}
	
	public void loadPastMeeting(String data){
		
	}

	
}
