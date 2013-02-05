
public class ContactImpl implements Contact {

	private int ID;
	private String Name;
	private String Notes;
	
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
	
	@Override
	public String toString(){
		
		String str = "Name:" + Name + ",";
		str = str + "Contact Id:" + ID + ",";
		str = str + "Notes:" + Notes;
		return str;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContactImpl other = (ContactImpl) obj;
		if (ID != other.ID)
			return false;
		return true;
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
