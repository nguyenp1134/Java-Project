package eSystem;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public class MainFunction {
	
	//SETUP FOR ALL HIBERNATE
	protected SessionFactory sessionFactory;	
	protected void setup()
	{
		//Function to configure settings from hibernate.cfg.xml
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		try
		{
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}
	
	//LOGIN FUNCTION FOR ALL USER
	protected ArrayList<String> Login()
	{
		Session session = sessionFactory.openSession();
		session.beginTransaction();
				
		Scanner input = new Scanner(System.in);
		System.out.print("Enter your id: ");
		String id = input.nextLine();
		System.out.print("Enter your password: ");
		String pass = input.nextLine();
		//COUNT is used for iteration. It allows only 2 login-trials for fail login
		int count = 2;		
		//Access Uhcl_user table where userId input
		Uhcl_User user = session.get(Uhcl_User.class, id);
		ArrayList<String> results = new ArrayList<String>();
		//if user == null means that no such userID is available in the table
		//OR if there is such user ID but the password input is wrong
		//All means login fails and access TRY AGAIN loop
		while (count > 0 && (user == null || !user.getPassword().equals(pass)))
		{
			System.out.println("Wrong Id or Password");
			System.out.print("Enter your id: ");
			id = input.nextLine();
			System.out.print("Enter your password: ");
			pass = input.nextLine();
			user = session.get(Uhcl_User.class, id);
			count = count - 1;
		}
		if(user == null || !user.getPassword().equals(pass) )
		{
			System.out.println("Login Fail");
			results = null;
		}
		else
		{	
			System.out.println("Access Granted");
			results.add(user.getId());
			results.add(user.getPassword());
			results.add(user.getMajor());
			results.add(user.getType());						
		}		
		
		session.getTransaction().commit();	
		session.close();
		return results;
	}
	
	//FACULTY FUNCTION
	//FACULTY ESERVICE
	//Faculty view their courses from COURSE table
	protected void FacultyE(String id)
	{
		Session session = sessionFactory.openSession();
		session.beginTransaction();		
		
		String hql = "From Course C where C.instructor = '"+id+"'";
		Query query = session.createQuery(hql);		
		List results = query.list();
		ArrayList<Course> courses = (ArrayList<Course>)results;
		for(Course c : courses)
		{
			System.out.println(c.getCourseID());
			System.out.println("     Schedule: " + c.getDate() + " " + c.getTime());
			System.out.println("     Number of Student Enrolled: " + c.getEnrolled());
			System.out.println("     Course Status: " + c.getStatus());
			String hql1 = "Select R.stdID From Register R where R.courseID = '"+c.getCourseID()+"'";
			Query query1 = session.createQuery(hql1);
			List stdL = query1.list();
			if (stdL.isEmpty())
			{
				System.out.println("     Student Enrollled: No students enrolled");
			}
			else
			{	
				System.out.print("     Student Enrolled: ");
				ArrayList<String> stdN = (ArrayList<String>)stdL;
				for(String s : stdN)
				{
					System.out.println(s + " ");
				}
			}
			System.out.println();
			
		}		
		session.getTransaction().commit();	
		session.close();		
	}
	//FACULTY BLACKBOARD
	protected void FacultyBb_Display(String id)
	{
		//Display course
		Session session = sessionFactory.openSession();
		session.beginTransaction();	
		
		String hql = "From Course C Where C.instructor = '"+id+"' ";
		Query query = session.createQuery(hql);
		
		List results = query.list();
		ArrayList<Course> courseInfo = (ArrayList<Course>) results;
		
		int i = 0;
		for(Course c : courseInfo)
		{
			i = i + 1;			
			System.out.println(i + ": " + c.getCourseID());			
		}
		
		session.getTransaction().commit();	
		session.close();
	}
	protected String FalcultyBb_Select(int orderN, String id)
	{
		Session session = sessionFactory.openSession();
		session.beginTransaction();	
		
		String hql1 = "From Course C Where C.instructor = '"+id+"' ";
		Query query1 = session.createQuery(hql1);
		
		List results = query1.list();
		ArrayList<Course> courseInfo = (ArrayList<Course>) results;
		
		int i = 0;
		String courseN = "";
		for(Course c : courseInfo)
		{
			i = i + 1;
			c.getCourseID();			
			if(i == orderN)
			{	
				courseN = c.getCourseID();		
			}
			
		}
		session.getTransaction().commit();	
		session.close();
		return courseN;
	}
	protected void FalcultyBb_View(String courseId)
	{
		Session session = sessionFactory.openSession();
		session.beginTransaction();	
		
		String hql = "From CourseNote C Where C.courseId = '"+courseId+"' ";
		Query query = session.createQuery(hql);
		
		List results = query.list();
		if(results.isEmpty())
		{
			System.out.println("None");
		}
		else
		{
			ArrayList<CourseNote> notes = (ArrayList<CourseNote>) results;
			for(CourseNote c : notes)
			{
				System.out.println(c.getDate() + ": " + c.getContent());
			}
		}
		session.getTransaction().commit();	
		session.close();		
	}
	protected void FacultyBb_Post(String courseId, String instructor,String content, String date)
	{
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		nextCourseNoteId nextId = session.load(nextCourseNoteId.class, 1);						
		CourseNote newNote = new CourseNote(nextId.getNextId(), courseId, instructor, content, date);
		session.save(newNote);
		System.out.println("Your note is added to the course. Your students can see it now");
		
		//Update next number
		int newId = nextId.getNextId() + 1;				
		nextId.setNextId(newId);
		session.update(nextId);		
		
		session.getTransaction().commit();	
		session.close();		
	}
	
	//STUDENT FUNCTION
	//STUDENT E-SERVICE
	protected void StudentE_Register(String id, String major)
	{
		Session session = sessionFactory.openSession();
		session.beginTransaction();		
		
		String hql1 = "From Course C where C.major = '"+major+"' and C.status = 'open'";
		Query query1 = session.createQuery(hql1);		
		List results = query1.list();
		
		if(results.isEmpty())
		{
			System.out.println("All courses of your major is now full and closed");
		}
		else
		{
			//Display available courses
			ArrayList<Course> courses = (ArrayList<Course>)results;
			int i = 1;
			for(Course c : courses)
			{
				
				System.out.println(i + ": " + c.getCourseID());
				i = i + 1;
			}
			//Register
			String hql2 = "Select R.courseID From Register R Where R.stdID = '"+id+"'";
			Query query2 = session.createQuery(hql2);
			List result2 = query2.list();
			ArrayList<String> register = (ArrayList<String>)result2;
			int count = 0;
			for (Course c : courses)
			{				
				//Check for duplicate courses
				if(register.contains(c.getCourseID()))
				{
					count = count + 1;
					continue;
				}				
				else
				{
					nextRegisterId nextId = session.get(nextRegisterId.class, 1);
					int next = nextId.getNextId();
					Register register1 = new Register(next,id, c.getCourseID(), c.getInstructor(), c.getDate(), c.getTime());
					session.save(register1);
					
					//Update Status at Course table
					Course currentC = session.get(Course.class, c.getCourseID());
					int cap = currentC.getCapacity();
					int currentEn = currentC.getEnrolled();		
					int newEn = currentEn + 1;					
					Course updateCourse = session.load(Course.class, c.getCourseID());
					if (newEn < cap)		
					{			
						updateCourse.setEnrolled(newEn);
						session.update(updateCourse);		
					}
					else if (newEn == cap)
					{			
						updateCourse.setEnrolled(newEn);
						updateCourse.setStatus("full");
						session.update(updateCourse);
					}					
					//Update the nextId at nextRegisterId table
					next = next + 1;
					nextId.setNextId(next);
					session.update(nextId);
				}
			}
			if(count == register.size())
			{
				System.out.println("You are already enrolled in all course");
			}
			else
			{
				System.out.println("All courses are added to your schedule");
			}			
		}
		
		session.getTransaction().commit();	
		session.close();
	}
	
	protected void StudentE_View(String id)
	{
		Session session = sessionFactory.openSession();
		session.beginTransaction();	
		
		String hql = "From Register R Where R.stdID = '"+id+"'";
		Query query = session.createQuery(hql);		
		List cList = query.list();
		if(cList.isEmpty())
		{
			System.out.println("You do not have any course registered");
		}
		else
		{
			ArrayList<Register> courseList = (ArrayList<Register>)cList;			
			for(Register r : courseList)
			{
				System.out.println(r.getCourseID() + ", " +  "Instructor: Dr. " + r.getInstructor());	
				System.out.println("     Schedule: " + r.getDate() + " " + r.getTime());
			}
		}	
		
		session.getTransaction().commit();	
		session.close();
	}
	//STUDENT BLACKBOARD
	protected void StudentBb_Display(String id)
	{
		//Display course
		Session session = sessionFactory.openSession();
		session.beginTransaction();	
		
		String hql = "From Register R Where R.stdID = '"+id+"'";
		Query query = session.createQuery(hql);		
		List results = query.list();
		ArrayList<Register> courseInfo = (ArrayList<Register>) results;
		
		int i = 0;		
		for(Register r : courseInfo)
		{
			i = i + 1;			
			System.out.println(i + ": " + r.getCourseID());			
		}		
		session.getTransaction().commit();	
		session.close();
	}
	
	protected void StudentBb_Select(int orderN, String id)
	{
		Session session = sessionFactory.openSession();
		session.beginTransaction();	
		
		String hql1 = "From Register R Where R.stdID = '"+id+"'";
		Query query1 = session.createQuery(hql1);		
		List results = query1.list();
		ArrayList<Register> courseInfo = (ArrayList<Register>) results;
		
		int i = 0;		
		for(Register r : courseInfo)
		{
			i = i + 1;
			r.getCourseID();
			//Select course
			if(i == orderN)
			{
				//Display note from selected course
				String hql2 = "From CourseNote C Where C.courseId = '"+r.getCourseID()+"' ";
				Query query2 = session.createQuery(hql2);
				List note = query2.list();
				if(note.isEmpty())
				{
					System.out.println("None");
				}
				else
				{
					ArrayList<CourseNote> noteInfo = (ArrayList<CourseNote>) note;
					for(CourseNote n : noteInfo)
					{
						System.out.println(n.getDate() + ": " + n.getContent());
					}
				}
			}			
		}
		session.getTransaction().commit();	
		session.close();		
	}	

}
