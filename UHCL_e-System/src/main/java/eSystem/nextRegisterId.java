package eSystem;
import javax.persistence.*;

@Entity
@Table(name = "nextRegisterId")
public class nextRegisterId {
	@Id
	@Column
	private int Id;	
	private int nextId;
	
	public nextRegisterId()
	{
		
	}
	public nextRegisterId(int id, int nextId) {
		
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
