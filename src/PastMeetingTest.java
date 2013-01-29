import static org.junit.Assert.*;

import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;


public class PastMeetingTest {
	
	private PastMeeting pm;
	private int id = 1;
	GregorianCalendar date;
	private String notes = "some notes.";
	
	@Before
	public void setUp(){
		
		date = new GregorianCalendar(2012, 11, 10);
		Set<Contact> attendees = new LinkedHashSet<Contact>();
		attendees.add(new ContactImpl("Ed", "a note", 2));
		attendees.add(new ContactImpl("Nneka", "suffri", 3));
		pm = new PastMeetingImpl(date, attendees, notes, id);
	}

	@Test
	public void testToString() {
		
		String test = "PastMeeting Id:1,Date:2012/11/10,Contact Ids:2,3,Notes:some notes.";
		assertEquals(test, pm.toString());
	}

	@Test
	public void testGetNotes() {
		String test = "some notes.";
		assertEquals(test, pm.getNotes());
	}

}
