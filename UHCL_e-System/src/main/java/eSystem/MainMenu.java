package eSystem;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {
	
	private String id;
	private String pass;
	private String type;
	private String major;	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub			
		
		MainFunction function = new MainFunction();
		function.setup();
		MainMenu menu = new MainMenu();		
		Scanner input = new Scanner(System.in);		
		String selection = "";
		while(!selection.equals("x"))
		{
			System.out.println("Go Hawks");
			System.out.println("1: e-Service");
			System.out.println("2: Blackboard");
			System.out.println("x: Leave");			
			selection = input.nextLine();
			
			//E-Service
			if(selection.equals("1"))
			{
				//Login
				ArrayList<String> userItem = function.Login();	
				if(userItem == null)
				{
					continue;
				}
				else
				{
					menu.setId(userItem.get(0));					
					menu.setPass(userItem.get(1));					
					menu.setMajor(userItem.get(2));					
					menu.setType(userItem.get(3));
					
				}
				System.out.println();
				
				//FACULTY E-SERVICE
				if(menu.getType().equals("Faculty"))
				{
					String facS = "";
					while(!facS.equals("x"))
					{
						System.out.println("Welcome to UHCL E-Service!");
						System.out.println("v: View My Course");						
						System.out.println("x: Logout");
						facS = input.nextLine();
						if(facS.equals("v"))
						{
							function.FacultyE(menu.getId());
						}
						else 
						{
							continue;
						}
					}
				}
				//STUDENT E-SERVICE
				else if(menu.getType().equals("Student"))
				{
					String stdS = "";
					while(!stdS.equals("x"))
					{
						System.out.println("Welcome to UHCL E-Service");
						System.out.println("v: View My Courses");
						System.out.println("r: Register A Course");
						System.out.println("x: Logout");						
						stdS = input.nextLine();						
						if(stdS.equals("v"))
						{
							function.StudentE_View(menu.getId());
							System.out.println();
						}
						else if (stdS.equals("r"))
						{
							System.out.println("Welcome to Register a New Course!");
							System.out.println("These are the courses available to you:");							
							ArrayList<Course> c = function.StudentE_CourseDisplay(menu.getId(), menu.getMajor());
							if(c.isEmpty())
							{
								System.out.println("None");
								System.out.println();
								continue;
							}
							else
							{
								System.out.print("");
								String a = input.nextLine();
								if(!a.equals("x"))
								{
									int b = Integer.parseInt(a);
									function.StudentE_Register(menu.getId(), menu.getMajor(), b);	
									System.out.println();
								}
								else
								{
									System.out.println();
									continue;
								}
							}
						}						
						
					}
				}
				else
				{
					continue;
				}
			}	
			
			//Blackboard
			else if (selection.equals("2"))
			{
				//Login
				ArrayList<String> userItem = function.Login();	
				if(userItem == null)
				{
					continue;
				}
				else
				{
					menu.setId(userItem.get(0));					
					menu.setPass(userItem.get(1));					
					menu.setMajor(userItem.get(2));					
					menu.setType(userItem.get(3));
					
				}
				System.out.println();
				//FACULTY BLACKBOARD
				if(menu.getType().equals("Faculty"))
				{
					String facS = "";
					while(!facS.equals("x"))
					{
						System.out.println("Welcome to UHCL Blackboard");
						System.out.println("Select Your Course");			
						function.FacultyBb_Display(menu.getId());
						System.out.println("x: Logout");
						facS = input.nextLine();						
						if(!facS.equals("x"))
						{
							int orderN = Integer.parseInt(facS);
							String courseId = function.FalcultyBb_Select(orderN, menu.getId());
							String choice = "";
							while(!choice.equals("x"))
							{
								System.out.println("Please Select Your Option");
								System.out.println("v: View Course Notes");
								System.out.println("p: Post New Course Notes");
								System.out.println("x: Leave The Course");
								choice = input.nextLine();
								if(choice.equals("v"))
								{
									function.FalcultyBb_View(courseId);
								}
								else if (choice.equals("p"))
								{
									System.out.println("Please Enter Your New Note:");
									String note = input.nextLine();
									String date = new DateTime().Date();
									function.FacultyBb_Post(courseId, menu.getId(), note, date);									
								}
								System.out.println();								
							}
						}			
					}
				}
				//STUDENT BLACKBOARD
				else if(menu.getType().equals("Student"))
				{
					String stdS = "";
					while(!stdS.equals("x"))
					{
						
						System.out.println("Welcome to UHCL Blackboard");
						System.out.println("Select Your Course");			
						function.StudentBb_Display(menu.getId());
						System.out.println("x: Logout");
						stdS = input.nextLine();						
						if(!stdS.equals("x"))
						{
							String choice = "";
							while(!choice.equals("x"))
							{
								System.out.println("Please Select Your Option");
								System.out.println("v: View Course Notes");								
								System.out.println("x: Leave The Course");
								choice = input.nextLine();
								if(choice.equals("v"))
								{									
									int orderN = Integer.parseInt(stdS);
									function.StudentBb_Select(orderN, menu.getId());	
								}
								else
								{
									continue;
								}
								System.out.println();
								
							}										
						}
					}
				}
				else
				{
					continue;
				}
					
			}
		}

	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}

}
