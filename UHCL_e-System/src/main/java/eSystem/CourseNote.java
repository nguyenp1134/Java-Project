package eSystem;
import java.util.Date;

import javax.persistence.*;
@Entity
@Table(name = "CourseNote")
public class CourseNote {
	
	@Id
	@Column (name = "Id")
	private int Id;
	
	private String courseId;
	private String instructor;
	private String content;
	private String date;
	
	public CourseNote()
	{
		
	}
	public CourseNote(int id, String courseId, String instructor, String content, String date) {
		
		this.Id = id;
		this.courseId = courseId;
		this.instructor = instructor;
		this.content = content;
		this.date = date;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getInstructor() {
		return instructor;
	}
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
	
	

}
