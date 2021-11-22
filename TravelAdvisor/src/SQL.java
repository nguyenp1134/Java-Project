
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SQL implements Data {
	final String url = "jdbc:mysql://mis-sql.uhcl.edu/nguyenp1134?useSSL=false";
	final String db_id = "nguyenp1134";
	final String db_psw = "1234583";
	
	@Override
	public boolean CheckDuplicateID(String id)
	{
		Connection conn = null;		
		Statement stm = null;		
		ResultSet rs = null;
		boolean outcome = false;
		try
		{				
			conn = DriverManager.getConnection(url,db_id,db_psw);
			stm = conn.createStatement();
			String query = "SELECT LoginID FROM create_account";
			rs = stm.executeQuery(query);			
			while (rs.next())
			{				
				String loginID = rs.getString(1);
				if(loginID.equals(id))
				{
					outcome = true;
					break;
				}
				else
				{
					outcome = false;
				}							
			}			
			
		}		
		catch (SQLException e)
		{
			System.out.println ("Account creation failed");
			e.printStackTrace();
						
		}		
		//use FINALLY to close database connection so that you don't waste resources. No matter what happens, FINALLy is executed.
		finally
		{
			//close database connection no matter what happened
			try
			{
				conn.close();
				stm.close();				
				rs.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return outcome;					
	}
	@Override
	public void Registration(String id, String pass, String tag1, String tag2, String type) {
		// TODO Auto-generated method stub	
		Connection conn = null;
		Statement stm = null;
		PreparedStatement pre = null;	
        		
		try
		{				
			conn = DriverManager.getConnection(url,db_id,db_psw);
			stm = conn.createStatement();			
			String sql = "INSERT INTO create_account VALUES(?,?,?,?,?)";
			pre = conn.prepareStatement(sql);
			pre.setString(1, id);
		    pre.setString(2, pass);
		    pre.setString(3, tag1);
		    pre.setString(4, tag2);
		    pre.setString(5, type);
		    pre.executeUpdate();						
			
		}
		catch (SQLException e)
		{
			System.out.println ("Account creation failed");
			e.printStackTrace();
		}
		finally
		{
			try
			{
				conn.close();
				stm.close();				
				pre.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
			
	}
	@Override
	public String Login(String id, String pass)
	{
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		String type = "";
        		
		try
		{				
			conn = DriverManager.getConnection(url,db_id,db_psw);
			stm = conn.createStatement();			
			String query = "SELECT * FROM create_account WHERE LoginID = '" + id + "' and PassW = '" + pass + "'";
			rs = stm.executeQuery(query);
			if (rs.next())
			{
				type = rs.getString(5);
				System.out.println("Access Granted");
			}
			else 
			{
				System.out.println("Wrong ID or Password or Account not existed");
				System.out.println("Access Denied");
				
			}				
			
		}
		catch (SQLException e)
		{
			System.out.println ("Login Fail");
			e.printStackTrace();
		}
		
		finally
		{
			try
			{
				conn.close();
				stm.close();
				rs.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return type;
	}	

	@Override
	public void CreateAttraction(String name, String tag, String descript, String city) {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement stm = null;
		PreparedStatement pre = null;		
        		
		try
		{				
			conn = DriverManager.getConnection(url,db_id,db_psw);
			stm = conn.createStatement();
			String sql = "INSERT INTO attraction VALUES (?,?,?,?,?,?)";
			pre = conn.prepareStatement(sql);
			pre.setString(1, name);
		    pre.setString(2, tag);
		    pre.setString(3, descript);
		    pre.setString(4, city);	
		    pre.setString(5, "pending");
		    pre.setDouble(6,0);
		    pre.executeUpdate();								
			
		}
		catch (SQLException e)
		{
			System.out.println ("Attraction Creation Fails");
			e.printStackTrace();
		}
		
		finally
		{
			try
			{
				conn.close();
				stm.close();
				pre.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}


	@Override
	public void AdminApproval() {
		
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		PreparedStatement pre = null;		
        		
		try
		{				
			conn = DriverManager.getConnection(url,db_id,db_psw);
			stm = conn.createStatement();	
			String query1 = "SELECT COUNT(*) FROM attraction GROUP BY status HAVING status = '"+"pending"+"'";
			rs = stm.executeQuery(query1);			
			if(!rs.next())
			{
				System.out.println("You have no 'pending' attraction");
			}
			else 
			{
				int count = rs.getInt(1);
				System.out.println("You have " + count + " 'pending' attraction");
				String query2 = "SELECT * FROM attraction WHERE Status = '" + "pending" + "'";
				rs = stm.executeQuery(query2);			
				while (rs.next())
				{
					String name = rs.getString(1);
					System.out.println(name);
					String tag = rs.getString(2);
					System.out.println(tag);
					Scanner input = new Scanner(System.in);
					System.out.print("Please update the status to 'approve' or 'reject': ");
					String decision = input.nextLine();
					if(decision.equals("approve") || decision.equals("reject"))
					{					
						String update = "UPDATE attraction SET Status = ? where Title = ? and Tag = ? ";
					    pre = conn.prepareStatement(update);
					    pre.setString(1, decision);
					    pre.setString(2, name);
					    pre.setString(3, tag );
					    pre.executeUpdate();
					    count = count - 1;
					}
					else
					{
						continue;
					}
				      
				}
				System.out.println("You have " + count + " 'pending' attraction");
			}		
		}
		catch (SQLException e)
		{
			System.out.println ("Process Fails");
			e.printStackTrace();
		}
		
		finally
		{
			try
			{
				conn.close();
				stm.close();
				rs.close();				
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public boolean CheckApproval(String name)
	{
		Connection conn = null;
		Statement stm = null;		
		ResultSet rs = null;
		boolean permission = true;		
		try
		{				
			conn = DriverManager.getConnection(url,db_id,db_psw);
			stm = conn.createStatement();
			
			String query = "SELECT Title FROM attraction WHERE Title = '"+name+"' and Status = '" + "approve" + "'";
			rs = stm.executeQuery(query);
			if(!rs.next())
			{
				permission = false;
			}
			else
			{
				permission = true;				
			}
				
		}
		catch (SQLException e)
		{
			System.out.println ("Comment Fails");
			e.printStackTrace();
		}
		
		finally
		{
			try
			{
				conn.close();
				stm.close();
				rs.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return permission;
	}


	@Override
	public void Comment(String name, String id, String comment, double score, String datetime) {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement stm = null;		
		PreparedStatement pre = null;
					
		try
		{				
			conn = DriverManager.getConnection(url,db_id,db_psw);
			stm = conn.createStatement();		
			
			String insert = "INSERT INTO comment VALUES (?,?,?,?,?) ";
			pre = conn.prepareStatement(insert);
			pre.setString(1, name);
			pre.setString(2, id);
			pre.setString(3, comment);
			pre.setDouble(4, score);
			pre.setString(5, datetime);
			pre.executeUpdate();
			
				
		}
		catch (SQLException e)
		{
			System.out.println ("Comment Fails");
			e.printStackTrace();
		}
		
		finally
		{
			try
			{
				conn.close();
				stm.close();				
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}


	@Override	
	public void UpdateAverage(){
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		PreparedStatement pre = null;		
        		
		try
		{				
			conn = DriverManager.getConnection(url,db_id,db_psw);
			stm = conn.createStatement();			
			String query = "SELECT title, avg(score) FROM comment GROUP BY title";
			rs = stm.executeQuery(query);			
			while (rs.next())
			{
				String name = rs.getString(1);				
				Double score_avg = rs.getDouble(2);						
				String update = "UPDATE attraction SET score = ? WHERE title = ?";
			    pre = conn.prepareStatement(update);
			    pre.setDouble(1, score_avg);
			    pre.setString(2, name);			     
			    pre.executeUpdate();			      
			}		
		
		}
		catch (SQLException e)
		{
			System.out.println ("Process Fails");
			e.printStackTrace();
		}
		
		finally
		{
			try
			{
				conn.close();
				stm.close();
				rs.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}


	@Override
	public void Search(String name) {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;		
        		
		try
		{				
			conn = DriverManager.getConnection(url,db_id,db_psw);
			stm = conn.createStatement();			
			String query1 = "SELECT a.title, a.tag, a.description, a.score FROM attraction a WHERE a.title = '"+name+"' or (a.city = '"+name+"') or (a.tag = '"+name+"')";					
			rs = stm.executeQuery(query1);			
			if (rs.next())
			{
				String attraction = rs.getString(1);
				System.out.println("Name of Attraction: " + attraction);
				String tag = rs.getString(2);
				System.out.println("The attraction's tag: " + tag);
				String description = rs.getString(3);
				System.out.println("The attraction's description: " + description);
				Double rating = rs.getDouble(4);
				System.out.println("Overall Rating: " + rating);
				String query2 = "SELECT user,content,score FROM comment WHERE title = '"+name+"'";					
				rs = stm.executeQuery(query2);
				while(rs.next())
				{
					String user = rs.getString(1);					
					String comment = rs.getString(2);
					Double score = rs.getDouble(3);
					System.out.println("User: " + user + " review that:");
					System.out.println("The destination is " + comment);
					System.out.println("Rating: " + score);
				}
			}
			else {			
				
				System.out.println("Your search is not available");
			}		
		
		}
		catch (SQLException e)
		{
			System.out.println ("Search Fails");
			e.printStackTrace();
		}
		
		finally
		{
			try
			{
				conn.close();
				stm.close();
				rs.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}		
	}


	@Override
	public void Question (String askID, String name, String question) {
		
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement stm = null;		
		PreparedStatement pre = null;		
        		
		try
		{				
			conn = DriverManager.getConnection(url,db_id,db_psw);
			stm = conn.createStatement();		
			String insert = "INSERT INTO QandA VALUES (?,?,?,?,?,?) ";
			pre = conn.prepareStatement(insert);
			pre.setString(1, askID);
			pre.setString(2, name);
			pre.setString(3, question);
			pre.setString(4, "");
			pre.setString(5, "");
			pre.setString(6, "");
			pre.executeUpdate();  			
		
		}
		catch (SQLException e)
		{
			System.out.println ("Q&A Fails");
			e.printStackTrace();
		}
		
		finally
		{
			try
			{
				conn.close();
				stm.close();				
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}


	@Override
	public void DisplayQuestion(String answerID) {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		PreparedStatement pre = null;		
        		
		try
		{				
			conn = DriverManager.getConnection(url,db_id,db_psw);
			stm = conn.createStatement();
			int i = 1;
			String query = "SELECT DISTINCT asked_by, title, question FROM QandA";					
			rs = stm.executeQuery(query);			
			while (rs.next())
			{
				String askID = rs.getString(1);
				String attraction = rs.getString(2);
				String question = rs.getString(3);				
				System.out.println(i + ": " + attraction + ": " + question);
				i = i + 1;
			}		
		
		}
		catch (SQLException e)
		{
			System.out.println ("Q&A Fails");
			e.printStackTrace();
		}
		
		finally
		{
			try
			{
				conn.close();
				stm.close();
				rs.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}	
			
	}
	@Override
	public void Answer (int orderN, String answerID)
	{
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		PreparedStatement pre = null;		
        		
		try
		{				
			conn = DriverManager.getConnection(url,db_id,db_psw);
			stm = conn.createStatement();
			int i = 1;
			String thisQ = "";
			String query = "SELECT DISTINCT asked_by, title, question FROM QandA";					
			rs = stm.executeQuery(query);			
			while (rs.next())
			{
				String askID = rs.getString(1);
				String attraction = rs.getString(2);
				String question = rs.getString(3);			
							
				if(i == orderN)
				{
					thisQ = question;
					System.out.print("What is your answer? ");
					Scanner input = new Scanner(System.in);
					String new_answer = input.nextLine();
					stm = conn.createStatement();
					String query1 = "SELECT answer question FROM QandA Where question = '"+thisQ+"' and answer = '"+""+"'";					
					rs1 = stm.executeQuery(query1);			
					if(rs1.next())
					{								
						String update = "UPDATE QandA SET Answered_By = ?, Answer = ?, Status = ? where question = ?";
						pre = conn.prepareStatement(update);
						pre.setString(1, answerID);
						pre.setString(2, new_answer);
						pre.setString(3, "unread");
						pre.setString(4, thisQ);
						pre.executeUpdate();
					}
					else
					{
						String insert = "INSERT INTO QandA VALUES (?,?,?,?,?,?) ";
						pre = conn.prepareStatement(insert);
						pre.setString(1, askID);
						pre.setString(2, attraction);
						pre.setString(3, thisQ);
						pre.setString(4, answerID);
						pre.setString(5, new_answer);
						pre.setString(6, "unread");
						pre.executeUpdate();  	
					}
					
				}
				i = i + 1;	
			}
		
		}
		catch (SQLException e)
		{
			System.out.println ("Q&A Fails");
			e.printStackTrace();
		}
		
		finally
		{
			try
			{
				conn.close();
				stm.close();
				rs.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}	
		
	}
	@SuppressWarnings("resource")
	@Override
	public void Notification(String id) {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		ResultSet rs1 = null;		
		PreparedStatement pre = null;		
        		
		try
		{				
			conn = DriverManager.getConnection(url,db_id,db_psw);
			stm = conn.createStatement();
			String query1 = "SELECT COUNT(*) FROM QandA  WHERE asked_by = '"+id+"' GROUP BY status HAVING status = '"+"unread"+"'";			
			rs = stm.executeQuery(query1);
			if(!rs.next())
			{
				System.out.println("You have 0 unread answer");
			}
			else
			{
				int count1 = rs.getInt(1);
				System.out.println("You have " + count1 + " unread answer");
				Scanner input = new Scanner(System.in);
				System.out.print("Do you want to read all of them now? (y/n) ");									
				String selection = input.nextLine();
				if(selection.equals("y"))
				{
					String query2 = "SELECT distinct title, question FROM QandA  WHERE asked_by = '"+id+"' AND status = '"+"unread"+"'";
					rs = stm.executeQuery(query2);
					while(rs.next())
					{
						String attraction = rs.getString(1);
						String question = rs.getString(2);				
					
						System.out.println(attraction + " : " +  question);										
														
						String query3 = "SELECT answered_by, answer FROM QandA  WHERE question = '"+question+"' AND status = '"+"unread"+"'";
						stm = conn.createStatement();
						rs1 = stm.executeQuery(query3);
						while (rs1.next())
						{
							String anID = rs1.getString(1);
							String answer = rs1.getString(2);
							System.out.println(anID + ": " + answer);				
														
							String update = "UPDATE QandA SET Status = ? where answer = ?";
							pre = conn.prepareStatement(update);
							pre.setString(1, "read");					
							pre.setString(2, answer);
							pre.executeUpdate();							
						}
						System.out.println();
					}
				}
				else if (selection.equals("n"))
				{
					;
				}										
			}	
										
		}
		catch (SQLException e)
		{
			System.out.println ("Q&A Fails");
			e.printStackTrace();
		}
		
		finally
		{
			try
			{
				conn.close();
				stm.close();
				rs.close();	
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}	
		
	}
	
	@Override
	public void SaveFavorite(String id) {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		PreparedStatement pre = null;		
        		
		try
		{				
			conn = DriverManager.getConnection(url,db_id,db_psw);
			stm = conn.createStatement();
			String query1 = "SELECT * FROM attraction WHERE status = '"+"approve"+"'";
			rs = stm.executeQuery(query1);
			while(rs.next())
			{
				String name = rs.getString(1);				
				String tag = rs.getString(2);				
				String descript = rs.getString(3);				
				String city = rs.getString(4);				
				Double score = rs.getDouble(6);				
				System.out.println(name + "/ " + tag + "/ " + city + "/ " + descript + "/ " + score  );
				
				Scanner input = new Scanner(System.in);
				System.out.print("Do you want to save this destination as your favorite?(yes/no) ");
				String selection = input.nextLine();
				if(selection.equals("yes"))
				{						
					String query2 = "SELECT loginid, title FROM myfavorite WHERE loginid = '"+id+"' and title = '"+name+"'";
					stm = conn.createStatement();
					rs1 = stm.executeQuery(query2);
					if(!rs1.next())
					{
						String insert = "INSERT INTO myfavorite VALUES (?,?,?,?,?,?)";
						pre = conn.prepareStatement(insert);
						pre.setString(1, id);
						pre.setString(2, name );
						pre.setString(3, tag);
						pre.setString(4, descript);
						pre.setString(5, city);					
						pre.setDouble(6, score);
						pre.executeUpdate(); 
					}
					else
					{
						System.out.println("You already save this destination");
					}
					
					
				}
				else if (selection.equals("no"))
				{
					continue;
				}

			}			
		
		}
		catch (SQLException e)
		{
			System.out.println ("Save Fails");
			e.printStackTrace();
		}
		
		finally
		{
			try
			{
				conn.close();
				stm.close();
				rs.close();	
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}	
	}


	@Override
	public void DisplayFavorite(String id) {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement stm = null;		
		PreparedStatement pre = null;
		ResultSet rs = null;
        		
		try
		{				
			conn = DriverManager.getConnection(url,db_id,db_psw);
			stm = conn.createStatement();		
			String query = "SELECT * FROM myfavorite WHERE loginid = '"+id+"' ";
			rs = stm.executeQuery(query);
			while (rs.next())
				{
					String title = rs.getString(2);			
					System.out.println(title);
					
				}
										
		}
		catch (SQLException e)
		{
			System.out.println ("There is no saved favorite destination");
			e.printStackTrace();
		}
		
		finally
		{
			try
			{
				conn.close();
				stm.close();
				rs.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}		
	}


	@Override
	public void Recommendation(String id) {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement stm = null;		
		ResultSet rs = null;
        		
		try
		{				
			conn = DriverManager.getConnection(url,db_id,db_psw);
			stm = conn.createStatement();		
			String query1 = "SELECT tag1, tag2 FROM create_account WHERE loginid = '"+id+"' ";
			rs = stm.executeQuery(query1);
			if (rs.next())
				{
					String tag1 = rs.getString(1);					
					String tag2 = rs.getString(2);
					String query2 = "SELECT title FROM attraction WHERE status = '"+"approve"+"' and (tag = '"+tag1+"' or tag = '"+tag2+"') ORDER BY score desc LIMIT 3 " ;
					rs = stm.executeQuery(query2);
					while(rs.next())
					{
						System.out.println(rs.getString(1));
					}
				}
										
		}
		catch (SQLException e)
		{
			System.out.println ("There is no recommendation");
			e.printStackTrace();
		}
		
		finally
		{
			try
			{
				conn.close();
				stm.close();
				rs.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}		
	}
	
}

