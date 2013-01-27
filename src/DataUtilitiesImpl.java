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
	
	public DataUtilitiesImpl(String filename){
		
		this.filename = filename;
	}
	
	public void readData(){
		
		try{
			
		}
	

	}
	
	public int getIDnumbers() {
		ContactManagerImpl.Contacts
		return IDnumbers;
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
