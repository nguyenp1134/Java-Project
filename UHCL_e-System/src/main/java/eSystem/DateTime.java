package eSystem;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTime {
	
	public String Date(){
		
		LocalDateTime myDate = LocalDateTime.now();	      	      
	    DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss");	    
	    String date = myDate.format(myFormat); 	    
		return date;
	}
}
