package src.com.pack.base;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.testng.Reporter;

public class CommonFunction {

	
	public String DateReturn(int days) throws Exception{
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(date);
		c.add(Calendar.DATE, days);
		date = c.getTime();
		System.out.println(formatter.format(date));
		return formatter.format(date);
		
	}
	public  int getRandomValue(int RecordData) {
	    int record = new Random().nextInt(RecordData);
	    if(record==0) {
	    	record++;
	    }
//	    System.out.println("RV-"+record);
	    return record;
	}
	
	public  int getRandomValueIncludingZero(int RecordData) {
	    int record = new Random().nextInt(RecordData);
//	    System.out.println("RV-"+record);
	    return record;
	}
	
	
	public  String getRandomValue(String[] emailDomain) {
	    int rnd = new Random().nextInt(emailDomain.length);
	    String emailD= (emailDomain[rnd]);
	    Reporter.log("Data is - "+emailD);
	    return emailD;
	}
}
