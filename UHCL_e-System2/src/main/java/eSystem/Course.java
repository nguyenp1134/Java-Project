package eSystem;

import javax.persistence.*;

@Entity
@Table(name = "Course")
public class Course {
	
	@Id
	@Column(name = "courseID")
	private String courseID;
	
	private String major;	
	private String instructor;	
	private String date;	
	private String time;
	private int capacity;	
	private int enrolled;	
	private String status;
	
	public Course()
	{
		
	}
	public Course (String id, String major, String instructor, String date, String time, int capacity, int enrolled, String status)
	{
		this.courseID = id;
		this.major = major;
		this.instructor = instructor;
		this.setDate(date);
		this.setTime(time);
		this.capacity = capacity;
		this.enrolled = enrolled;
		this.status = status;		
	}
	
	public String getCourseID() {
		return courseID;
	}
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getInstructor() {
		return instructor;
	}
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getEnrolled() {
		return enrolled;
	}
	public void setEnrolled(int enrolled) {
		this.enrolled = enrolled;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
