import static org.junit.Assert.*;

import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;


public class MeetingTest {

	private Meeting m;
	private int Id = 1;
	private GregorianCalendar date;
	private Set<Contact> Attendees;
	
	@Before
	public void setUp(){
		
		date = new GregorianCalendar(2150,11,25);
		Attendees = new LinkedHashSet<Contact>();
		Contact c = new ContactImpl("Ed", "philosophy", 2);
		Contact c2 = new ContactImpl("Ben", "architect", 3);
		Attendees.add(c);
		Attendees.add(c2);
		m = new MeetingImpl(date, Attendees,Id);
	}

	@Test
	public void testGetID() {
		assertEquals(1, m.getID());
	}

	@Test
	public void testGetDate() {
		assertEquals(date.getTime(), m.getDate().getTime());
	}

	@Test
	public void testGetContacts() {
		
		Contact c = new ContactImpl("Ed", "philosophy", 2);
		Contact c2 = new ContactImpl("Ben", "architect", 3);
		assertTrue(m.getContacts().contains(c));
		assertTrue(m.getContacts().contains(c2));
	}

	@Test
	public void testToString() {
		String str = "Meeting Id:1,Date:2150/11/25,Contact Ids:2,3,";
		assertEquals(str, m.toString());
	}

}
