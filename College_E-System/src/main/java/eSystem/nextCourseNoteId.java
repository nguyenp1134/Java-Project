package eSystem;
import javax.persistence.*;

@Entity
@Table(name = "nextCourseNoteId")
public class nextCourseNoteId {
	
	@Id
	@Column
	private int Id;
	
	private int nextId;
	
	public nextCourseNoteId()
	{
		
	}

	public nextCourseNoteId(int id, int nextId) {		
		this.Id = id;
		this.nextId = nextId;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getNextId() {
		return nextId;
	}

	public void setNextId(int nextId) {
		this.nextId = nextId;
	}

}
