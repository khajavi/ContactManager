import static org.junit.Assert.*;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;


public class ContactTest {
	
	private Contact c;
	private int id = 1;
	private String name = "Zaphoid Beeblebrox";
	private String notes = "If there's anything around here more important than my ego, I want it caught and shot now!";
	
	@Before
	public void setUp() throws Exception{
		c = new ContactImpl(name, notes, id);
	}
	
	@Test
	public void testGetId() {
	
		assertEquals(id, c.getId()); 
	}

	@Test
	public void testGetName() {
	
		assertEquals(name, c.getName());
	}

	@Test
	public void testGetNotes() {

		assertEquals(notes, c.getNotes());
	}

	@Test
	public void testAddNotes() {
		
		Contact c2 = new ContactImpl(name, id);
		c2.addNotes(notes);
		assertEquals(notes, c2.getNotes());
	}
	
	@Test
	public void testToString(){
		String str = "Name:Zaphoid Beeblebrox,Contact Id:1,Notes:If there's anything around here more important than my ego, I want it caught and shot now!";
		assertEquals(str, c.toString());
	}
	
	@Test
	public void testEquals(){
		Set<Contact> contact = new LinkedHashSet<Contact>();
		contact.add(c);
		assertTrue(contact.contains(c));
	}

}
