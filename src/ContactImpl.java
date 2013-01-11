
public class ContactImpl implements Contact {

	private int ID;
	private String Name;
	private String Notes;
	
	public ContactImpl(int ID, String Name, String Notes){
		this.ID = ID;
		this.Name = Name;
		this.Notes = Notes;
	}
	
	public ContactImpl(int ID, String Name){
		this.ID = ID;
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
