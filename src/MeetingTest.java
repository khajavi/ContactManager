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
		Set<Contact> Attendees = new LinkedHashSet<Contact>();
		Contact c = new ContactImpl("Ed", "philosophy", 2);
		Contact c2 = new ContactImpl("Ben", "architect", 3);
		Attendees.add(c);
		Attendees.add(c2);
		Meeting m = new MeetingImpl(date, Attendees,Id);
	}

	@Test
	public void testGetID() {
		assertEquals(1, m.getID());
	}

	@Test
	public void testGetDate() {
		assertEquals(2150, m.getDate().get(Calendar.YEAR));
		assertEquals(11, m.getDate().get(Calendar.MONTH));
		assertEquals(25, m.getDate().get(Calendar.DATE));
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
		String str = "Meeting Id:1,Date:2150/11/24,Contact Ids:2,3,";
		assertEquals(str, m.toString());
	}

}
