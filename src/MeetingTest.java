import static org.junit.Assert.*;

import java.util.Calendar;
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
		Set<Contact> testSet = new LinkedHashSet<Contact>();
		Contact c = new ContactImpl("Ed", "philosophy", 2);
		Contact c2 = new ContactImpl("Ben", "architect", 3);
		testSet.add(c);
		testSet.add(c2);
		assertEquals(testSet, m.getContacts());
	}

	@Test
	public void testToString() {
		String str = "Meeting Id:1,Date:2150/11/25,Contact Ids:2,3,";
		System.out.println(m.toString());
		assertEquals(str, m.toString());
	}

}
