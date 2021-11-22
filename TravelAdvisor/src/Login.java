
import java.util.Scanner;

public class Login {
	
	User u = new User();
	Data s = new SQL();

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public void UserLogin()
	{
		Scanner input = new Scanner(System.in);
		System.out.print("Enter your ID: ");
		String logID = input.nextLine();
		u.setID(logID);
		System.out.print("Enter your password: ");
		String Password = input.nextLine();
		u.setPass(Password);
		String type = s.Login(logID, Password);
		u.setType(type);
				
	}
	

}
