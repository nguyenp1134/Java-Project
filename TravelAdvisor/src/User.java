
import java.util.Scanner;
import java.util.ArrayList;

	public class User {
		
		public static String loginID;
		public static String Pass;	
		public static String type;
		
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			Scanner input = new Scanner(System.in);
			String selection = "";										
			while(!selection.equals("x")) 
			{
				//display the menu
				System.out.println("WELCOME TO TRAVEL ADVISOR");
				System.out.println("Please make your selection: ");
				System.out.println("1: New Registration");
				System.out.println("2: Login");
				System.out.println("x: None of above");
							
				//get the selection from the user
				selection = input.nextLine();
							
				//based on the input, go to different function
				if(selection.equals("1"))
				{
					System.out.println();
					new RegularUser().Register();	
					
				}
				else if(selection.equals("2"))	
				{					
					new Login().UserLogin();										
					if(type.equals("admin"))
					{	
						System.out.println("WELCOME " + loginID);
						System.out.println();
						new Admin().Approval();
						System.out.print("Do you you to access Q&A section? Press y for Yes and x for No: ");
						selection = input.nextLine();
						if(selection.equals("y"))
						{
							new Admin().GiveAnswer();
						}
						else
						{
							continue;
						}
						
					}
					else if (type.equals("user"))
					{	
						System.out.println("WELCOME " + loginID);						
						new RegularUser().Notified();
						
						System.out.println();
						System.out.println("Recommended For You");
						new RegularUser().ViewRecommendation();
						System.out.println();
						System.out.println("My Favorite Destination");
						new RegularUser().DisplayMyFavorite();
						System.out.println();
						String choice = new UserMenu().MainMenu();						
						while(!choice.equals("x"))
						{
							if(choice.equals("1"))
							{
								new RegularUser().CreateAttract();								
							}
							else if (choice.equals("2"))
							{
								new RegularUser().Comment();
							}
							else if (choice.equals("3"))
							{
								new RegularUser().Search();
							}
							else if (choice.equals("4"))
							{
								new RegularUser().AskQuestion();
							}
							else if (choice.equals("5"))
							{
								new RegularUser().GiveAnswer();
								
							}
							else if(choice.equals("6"))
							{
								new RegularUser().SaveMyFavorite();
							}
							else
							{
								continue;
							}
							System.out.println();
							choice = new UserMenu().MainMenu();
						}
						
					}
				}
			}		
		}
		public void setID (String id)
		{
			this.loginID = id;
		}
		public void setPass (String pass)
		{
			this.Pass = pass;
		}
		public void setType(String type)
		{
			this.type = type;
		}		
		public String getID()
		{
			return this.loginID;
		}
		public String getPass()
		{
			return this.Pass;
		}
		public String getType()
		{
			return this.type;
		}
				
	}





