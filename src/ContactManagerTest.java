import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ContactManagerTest {
	
	private ContactManager cm;
	private String filename = "/Users/williamhogarth/Documents/workspace/Contact Manager/src/contactManagerTest.txt";
	
	@Before
	public void setUp(){
		
		cm = new ContactManagerImpl(filename);
		
		//add Future Meetings. This is not read from the file so that the test file
		//will not need updating.
		
		GregorianCalendar date = getFutureDate();
		date.roll(Calendar.MONTH, true);
		//meeting is to a year and a month in the future.
		LinkedHashSet<Contact> attendees = (LinkedHashSet<Contact>) cm.getContacts(4,3);
		cm.addFutureMeeting(attendees, date);
		
	}
	/*
	@After
	public void tearDown(){
		
		cm = null; 
	}
	**/
	public GregorianCalendar getFutureDate(){
		
		GregorianCalendar date = new GregorianCalendar();
		date.roll(Calendar.YEAR, true);
		return date;
	}
	
	public GregorianCalendar getPastDate(){
		
		GregorianCalendar date = new GregorianCalendar();
		date.roll(Calendar.YEAR, false);
		return date;
	}
	
	@Test
	public void testAddFutureMeeting() {
		
		int expected = ContactManagerImpl.IDnumbers;
		GregorianCalendar date = getFutureDate();
		LinkedHashSet<Contact> attendees = (LinkedHashSet<Contact>) cm.getContacts(4,3);
		assertEquals(expected, cm.addFutureMeeting(attendees, date));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddFutureMeetingExeptionPastDate(){
		
		GregorianCalendar date = getPastDate();
		System.out.println(date.getTime());
		LinkedHashSet<Contact> attendees = (LinkedHashSet<Contact>) cm.getContacts(1,2);
		cm.addFutureMeeting(attendees, date);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddFutureMeetingFakeContact(){
		
		GregorianCalendar date = getFutureDate();
		Set<Contact> cont = new LinkedHashSet<Contact>();
		cont.add(new ContactImpl("name", "notes", ContactManagerImpl.IDnumbers +1));
		cm.addFutureMeeting(cont, date);
	}
	
	@Test
	public void checkContacts(){
		
		Set<Contact> contact = cm.getContacts(1,2,3);
		assertTrue(((ContactManagerImpl) cm).checkContacts(contact));
	}
	
	@Test
	public void checkContactsNonExistantContact(){
		
		Set<Contact> contact = new LinkedHashSet<Contact>();
		contact.add(new ContactImpl("name", "notes", ContactManagerImpl.IDnumbers + 1));
		assertFalse(((ContactManagerImpl)cm).checkContacts(contact));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetPastMeetingFutureId() {
		
		cm.getPastMeeting(10);
	}

	@Test
	public void testGetPastMeeting(){
		
		PastMeeting meeting = cm.getPastMeeting(8);
		assertEquals(8, meeting.getID());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetFutureMeetingPastId(){
		
		cm.getFutureMeeting(8);
	}
	
	@Test
	public void testGetFutureMeeting() {
		
		Meeting m = cm.getFutureMeeting(11);
		assertEquals(11, m.getID());
	}

	@Test
	public void testGetMeetingNull() {
	
		assertNull(cm.getMeeting(ContactManagerImpl.IDnumbers + 1));
	}

	@Test
	public void testGetMeetingPastMeeting(){
		
		Meeting m = cm.getMeeting(8);
		assertEquals(8, m.getID());
	}
	
	@Test
	public void testGetMeetingFutureMeeting(){
		
		Meeting m = cm.getMeeting(9);
		assertEquals(9, m.getID());
	}
	
	@Test
	public void testQuickSort() {
		
		List<Meeting> testList = new ArrayList<Meeting>();
		for(Meeting meeting: ((ContactManagerImpl) cm).getFutureMeetings()){
			testList.add(meeting);
		}
		for(PastMeeting meeting:((ContactManagerImpl) cm).getPastMeetings()){
			testList.add((Meeting)meeting);
		}
		testList = ((ContactManagerImpl) cm).quickSort(testList);
		assertTrue(testList.get(0).getDate().before(testList.get(testList.size() - 1).getDate()));
	}

	@Test //Not sure what's going on.
	public void testGetFutureMeetingListContact() {
		
		String name = "Douglas Adams";
		String notes = "funniest writer dead";
		Contact douglas = new ContactImpl(name, notes, 4);
		List<Meeting> testList = cm.getFutureMeetingList(douglas);
		assertEquals(10, testList.get(0).getID());
	}

	@Test
	public void testGetFutureMeetingListCalendar() {
		
		GregorianCalendar date = getFutureDate();
		date.roll(Calendar.MONTH, true);
		List<Meeting> meetingList = cm.getFutureMeetingList(date);
		assertTrue(meetingList.size() == 1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testGetFutureMeetingListPastDate(){
		
		GregorianCalendar date = getPastDate();
		cm.getFutureMeetingList(date);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testGetPastMeetingListFakeContact(){
		
		Contact c = new ContactImpl("Asimov", "Robotic Laws", ContactManagerImpl.IDnumbers + 1);
		cm.getPastMeetingList(c);
	}
	
	@Test
	public void testGetPastMeetingList() {
		
		Set<Contact> cont = cm.getContacts(3);
		Contact[] contact = cont.toArray(new Contact[1]);
		Contact c = contact[0];
		List<PastMeeting> pmList = cm.getPastMeetingList(c);
		assertTrue(pmList.get(0).getID() == 8);
	}

	@Test
	public void testAddNewPastMeeting() {
		
		GregorianCalendar date = getPastDate();
		Set<Contact> contact = cm.getContacts(1,2,3);
		String notes = "some notes";
		cm.addNewPastMeeting(contact, date, notes);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddNewPastMeetingEmptyContacts(){
		
		GregorianCalendar date = getPastDate();
		Set<Contact> contact = new LinkedHashSet<Contact>();
		String notes = "some notes";
		cm.addNewPastMeeting(contact, date, notes);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddNewPastMeetingNonExistentContact(){
		
		GregorianCalendar date = getPastDate();
		Set<Contact> contact = new LinkedHashSet<Contact>();
		contact.add(new ContactImpl("name", "notes", ContactManagerImpl.IDnumbers));
		String notes = "some notes";
		cm.addNewPastMeeting(contact, date, notes);
		
	}
	
	@Test(expected = NullPointerException.class)
	public void testAddNewPastMeetingNullContacts(){
		
		GregorianCalendar date = getPastDate();
		Set<Contact> contact = null;
		String notes = "some notes";
		cm.addNewPastMeeting(contact, date, notes);
	}
	
	@Test(expected = NullPointerException.class)
	public void testAddNewPastMeetingNullDate(){
		
		GregorianCalendar date = null;
		Set<Contact> contact = cm.getContacts(1,2,3);
		String notes = "some notes";
		cm.addNewPastMeeting(contact, date, notes);
	}
	
	@Test(expected = NullPointerException.class)
	public void testAddNewPastMeetingNullNotes(){
		
		GregorianCalendar date = getPastDate();
		Set<Contact> contact = cm.getContacts(1,2,3);
		String notes = null;
		cm.addNewPastMeeting(contact, date, notes);
	}
	
	@Test
	public void testAddMeetingNotes() {
		
		cm.addMeetingNotes(10, "some notes");
		assertTrue(((ContactManagerImpl)cm).getPastMeetings().size() == 3);
		assertEquals("some notes", cm.getPastMeeting(10).getNotes());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddMeetingNotesNoMeeting(){
		
		cm.addMeetingNotes(ContactManagerImpl.IDnumbers + 1, "bla bla");
	}
	
	@Test (expected = IllegalStateException.class)
	public void testAddMeetingNotesFutureMeeting(){
		
		cm.addMeetingNotes(11, "bla");
	}
	
	@Test (expected = NullPointerException.class)
	public void testAddMeetingNotesNullNotes(){
		
		String notes = null;
		GregorianCalendar date = new GregorianCalendar();
		Set<Contact> contact = cm.getContacts(1,2,3);
		cm.addFutureMeeting(contact, date);
		cm.addMeetingNotes(11, notes);
		cm.addMeetingNotes(11, null);
	}

	@Test
	public void testAddNewContact() {
		
		String notes = "the new fella";
		String name = "a new fella";
		cm.addNewContact(name, notes);
		boolean added = false;
		for(Contact contact:((ContactManagerImpl)cm).getContacts()){
			if(contact.getId() == 12){
				added = true;
			}
		}
		assertTrue(added);
	}
	
	@Test(expected = NullPointerException.class)
	public void testAddContactNullNotes(){
		
		String name = "the new fella";
		String notes = null;
		cm.addNewContact(name, notes);
	}

	@Test
	public void testGetContactsIntArray() {
		
		Contact tomWolfe = new ContactImpl("Tom Wolfe", "Written nothing good for years", 1);
		Contact martinAmis = new ContactImpl("Martin Amis", "Martin my dad is Kingsley Amis Amis", 2);
		Set<Contact> expected = new LinkedHashSet<Contact>();
		expected.add(tomWolfe);
		expected.add(martinAmis);
		
		Set<Contact> result = cm.getContacts(1, 2);
		
		assertEquals(expected, result);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testGetContactsIntArrayFakeContact(){
		
		cm.getContacts(ContactManagerImpl.IDnumbers + 1);
	}

	@Test
	public void testGetContactsString() {
		
		Set<Contact> result = cm.getContacts("Joseph Heller");
		assertEquals(1, result.size());
	}
	
	@Test
	public void testGetContactsStringTwoHellers(){
		
		cm.addNewContact("Joseph Heller", "some notes");
		Set<Contact> result = cm.getContacts("Joseph Heller");
		assertEquals(2, result.size());
	}

	@Test
	public void testGenerateData() {
	
		((ContactManagerImpl) cm).generateData();
	}

}
