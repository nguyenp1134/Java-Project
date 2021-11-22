
import java.util.Date;
import java.util.Scanner;
public class RegularUser extends User {
	
	Data s = new SQL();
	User u = new User();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}			
	public void Register()
	{	//Ask regular user to enter new loginID
		
		String type = "user";
		u.setType(type);		
		Scanner input = new Scanner(System.in);
		System.out.print("Enter your new ID: ");
		String logID = input.nextLine();
		//Check if the new ID is already used
		boolean duplicate = s.CheckDuplicateID(logID);			
		while(duplicate == true)
		{
			System.out.println("ID Already Used");			
			System.out.print("Enter your new ID: ");
			logID = input.nextLine();
			duplicate = s.CheckDuplicateID(logID);
		}
		u.setID(logID);
		
		//Ask regular user to enter new password
		System.out.print("Enter you new password: ");
		String Password = input.nextLine();		
		while(Password.equals("admin") || Password.equals(logID))
		{
			System.out.println("Already use. Try again");
			System.out.print("Enter your new password: ");
			Password = input.nextLine();
		}
		u.setPass(Password);
		
		//Ask new regular user to enter tag1 and tag2
		System.out.println("Destination Tag include history, shopping, beach, urban, nature, and family.");
		System.out.print("Enter your tag1: ");
		String tag1 = input.nextLine();
		System.out.print("Enter your tag2: ");
		String tag2 = input.nextLine();
		
		s.Registration(u.getID(),u.getPass(), tag1, tag2, u.getType());
	}
	
	
	public void CreateAttract()
	{
		
		Scanner input = new Scanner(System.in);
		System.out.print("Enter attract name: ");
		String name = input.nextLine();
				
		System.out.print("Enter attract description: ");
		String des = input.nextLine();
		
		System.out.println("Destination Tag include history, shopping, beach, urban, nature, and family.");
		System.out.print("Enter attract tag: ");
		String tag = input.nextLine();
				
		System.out.print("Enter attract city: ");
		String city = input.nextLine();
				
		s.CreateAttraction(name, tag, des, city);
	}
	
	public void Comment()
	{
		u.getID();
		
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		System.out.print("Enter attract name: ");
		String name = input.nextLine();
		boolean permission = s.CheckApproval(name);
		if(permission == false)
		{
			System.out.println("The destination is not approved");
		}
		else
		{
			System.out.print("Enter your review: ");
			String comment = input.nextLine();
					
			System.out.print("Scale from 1 to 5, enter your score: ");
			Double score = input.nextDouble();
					
			Date datetime = new Date();
			String date = datetime.toString();
					
			s.Comment(name, u.getID(),comment, score, date);			
			s.UpdateAverage();
			
		}
	}
	
	public void Search ()
	{
		Scanner input = new Scanner(System.in);
		System.out.print("Enter your search: ");
		String attraction = input.nextLine();
		boolean permission = s.CheckApproval(attraction);
		if(permission == false)
		{
			System.out.println("The destination is not approved");
		}
		else
		{
			s.Search(attraction);
		}
		
		
	}
	public void AskQuestion()
	{
		u.getID();		
		Scanner input = new Scanner(System.in);
		System.out.print("Enter the attraction you want to ask about: ");
		String attraction = input.nextLine();
		boolean permission = s.CheckApproval(attraction);
		if(permission == false)
		{
			System.out.println("The destination is not approved");
		}
		else
		{
			System.out.print("Enter you question: ");
			String question = input.nextLine();						
			s.Question(u.getID(),attraction,question);
		}
		
	}
	public void GiveAnswer()
	{
		u.getID();		
		s.DisplayQuestion(u.getID());
		Scanner input = new Scanner(System.in);
		System.out.print("Which question do you want to answer? ");
		int pick = input.nextInt();
		s.Answer(pick,u.getID());
	}
	public void Notified()
	{
		u.getID();
		s.Notification(u.getID());
	}
	public void SaveMyFavorite ()
	{
		u.getID();
		s.SaveFavorite(u.getID());
	}
	public void DisplayMyFavorite()
	{
		u.getID();
		s.DisplayFavorite(u.getID());
	}
	public void ViewRecommendation()
	{
		u.getID();
		s.Recommendation(u.getID());
	}
	


}

