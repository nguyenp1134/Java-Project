import java.util.Scanner;

public class Admin extends User {
	
	Data s = new SQL();
	User u = new User();
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}	
		
	public void Approval()
	{
		s.AdminApproval();		
		
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


}


