package eSystem;
import javax.persistence.*;
@Entity
@Table(name = "Uhcl_User")
public class Uhcl_User {
	
	//Make column id in Uhcl_User as primary key
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "major")
	private String major;
	
	@Column(name = "type")
	private String type;
	
	public Uhcl_User()
	{
		
	}
	public Uhcl_User(String id, String password, String major, String type)
	{
		this.id = id;
		this.password = password;
		this.major = major;
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
