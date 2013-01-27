
public class ContactImpl implements Contact {

	private int ID;
	private String Name;
	private String Notes;
	
	public ContactImpl(String Name, String Notes){
		ContactManagerImpl.IDnumbers++;
		ID = ContactManagerImpl.IDnumbers;
		this.Name = Name;
		this.Notes = Notes;
	}
	
	public ContactImpl(String name, String notes, int id){
		this.Name = name;
		this.Notes = notes;
		this.ID = id;
	}
	
	public ContactImpl(String name, int id){
		this.Name = name;
		this.Notes = "";
		this.ID = id;
	}
	
	public ContactImpl(String Name){
		ContactManagerImpl.IDnumbers++;
		ID = ContactManagerImpl.IDnumbers;
		this.Name = Name;
		this.Notes = "";
	}
	
	public int getId() {
		return ID;
	}

	public String getName() {
		return Name;
	}

	public String getNotes() {
		return Notes;
	}

	public void addNotes(String note) {
		this.Notes = note;
	}

}
