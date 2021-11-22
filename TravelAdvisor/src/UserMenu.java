import java.util.Scanner;

public class UserMenu {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	public String MainMenu()
	{
		Scanner input = new Scanner(System.in);		
		System.out.println("Please make your selection");
		System.out.println("1: Create New Attraction");
		System.out.println("2: Create Review");
		System.out.println("3: Search For Attraction");
		System.out.println("4: Ask Question");
		System.out.println("5: Give your answer");
		System.out.println("6: Save Your Favorite");
		System.out.println("x: None of above");
		String selection = input.nextLine();
		return selection;
	}

}
