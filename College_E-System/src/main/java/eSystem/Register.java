package eSystem;

import javax.persistence.*;
@Entity
@Table(name = "Register")
public class Register {
	
	@Id
	@Column(name = "Id")
	private int Id;
	
	private String stdID;
	private String courseID;
	private String instructor;
	private String date;
	private String time;
	
	public Register()
	{
		
	}
	public Register(int id, String stdId, String courseId, String instructor, String date, String time)
	{
		this.Id = id;
		this.stdID = stdId;
		this.courseID = courseId;
		this.instructor = instructor;
		this.setDate(date);
		this.setTime(time);
	}
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getStdId() {
		return stdID;
	}
	public void setStdId(String stdId) {
		this.stdID = stdId;
	}
	public String getCourseID() {
		return courseID;
	}
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
	public String getInstructor() {
		return instructor;
	}
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	

}
