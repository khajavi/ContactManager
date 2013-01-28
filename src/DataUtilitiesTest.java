import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;


public class DataUtilitiesTest {
	
	private DataUtilities d;
	private String meeting = "Meeting Id:2,Date:2150/11/25,Contact Ids:4,Notes:this will be boring";
	private String meeting2 = "Meeting Id:2,Date:2009/11/25,Contact Ids:4,5,6,Notes:this will be boring";
	private String pastMeeting = "PastMeeting Id:2,Date:2009/11/25,Contact Ids:4,5,6,Notes:this will be boring";
	private String contact = "Name:BigJohn,Contact Id:4,Notes:he can load 15 tonnes";
	
	@Before
	public void setUp(){
		d = new DataUtilitiesImpl("/Users/williamhogarth/Documents/workspace/Contact Manager/src/DummyFile.txt");
	}

	@Test
	public void testReadData() {
		
		d.readData();
		assert(((DataUtilitiesImpl) d).getContacts().size() == 3);
		assert(((DataUtilitiesImpl)d).getFutureMeetings().size() == 1);
		assert(((DataUtilitiesImpl)d).getPastMeetings().size() == 2);
	}

	@Test
	public void testGetContactName() {
		assertEquals("BigJohn", d.getContactName(contact));
	}

	@Test
	public void testGetContactID() {
		assertEquals(4, d.getContactID(contact));
	}

	@Test
	public void testGetNotes() {
		assertEquals("this will be boring", d.getNotes(pastMeeting));
	}

	@Test
	public void testGetMeetingID() {
		assertEquals(2,d.getMeetingID(meeting2));
	}

	@Test
	public void testGetAttendees() {
		
		d.loadContact("Name:BigJohn,Contact Id:4,Notes:he can load 15 tonnes");
		Contact c = new ContactImpl("BigJohn", "he can load 15 tonnes", 4);
		Set<Contact> contact = new LinkedHashSet<Contact>();
		contact.add(c);
		assertEquals(contact, d.getAttendees(meeting));
		
	}

	@Test
	public void testGetMeetingDate() {
		
		GregorianCalendar testCal = new GregorianCalendar(2150, 11, 25);
		assertEquals(testCal.getTime(),d.getMeetingDate(meeting).getTime());
	}
}
