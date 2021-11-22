
public interface Data {
	public boolean CheckDuplicateID(String id);
	public void Registration(String id, String pass, String tag1, String tag2, String type);
	public String Login(String id, String pass);	
	public void CreateAttraction(String name, String description, String tag, String city);
	public void AdminApproval ();
	public boolean CheckApproval(String name);
	public void Comment(String name, String id, String comment, double score, String datetime);
	public void UpdateAverage();
	public void Search (String name);
	public void Question (String askID, String name, String question);
	public void DisplayQuestion(String answerID);
	public void Answer (int orderN, String answerID);
	public void Notification (String id);
	public void SaveFavorite(String id);
	public void DisplayFavorite(String id);
	public void Recommendation (String id);

}

