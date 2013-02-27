/*
 * Author - Niranjan Patil
 * Purpose - Rally Software Internship Assignment
 * Date - 02/26/2013
 * 
 */

package com.rally.json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
 
public class JsonParser {
	
	 JSONParser parser = new JSONParser();
	
     public static void main(String[] args) throws java.text.ParseException {
    	 
    	 //Create a class variable
    	 JsonParser json = new JsonParser();
    	 
    	 // Call method to parse a json file & return a json array
    	 JSONArray array = json.parseJsonFile("1000-snapshots-overlap-with-Feb-2012.json");
    	 
    	 //Call process json method 
    	 json.processJsonArray(array);
    	
    	
     }
     
    public JSONArray parseJsonFile(String filename)
    {
    	JSONArray array = null;
    	 try
    	 {
    	  array = (JSONArray) parser.parse(new FileReader(filename));
    	 } catch (FileNotFoundException e) {
    			e.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}
    	 
    	 
    	 return array;
    	
    }
     
     public void processJsonArray(JSONArray array)
     {
    	
    	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S");
    	 Calendar cal = Calendar.getInstance();
    	 String febStartString = "2012-02-01T00:00:00.132Z";
    	 String febEndString = "2012-03-1T00:00:00.132Z";
    	 Date febStart = sdf.parse(febStartString, new java.text.ParsePosition(0));
    	 Date febEnd = sdf.parse(febEndString, new java.text.ParsePosition(0));
    	 
    	 long  idea_count = 0;
    	 long  inprogess_count = 0;
    	 long  defined_count = 0;
    	 long  accepted_count = 0;
    	 long  completed_count = 0;
    	 long  released_count = 0;
    	 try
    	 {
    	
    	
    	  for (Object o : array)
    	  {
    	    JSONObject person = (JSONObject) o;
    	    

    	    String validfrom = (String) person.get("_ValidFrom");
    	    Date fromDate = sdf.parse(validfrom, new java.text.ParsePosition(0));
    	    cal.setTime(fromDate);
    	    if(fromDate.after(febStart))
    	    {
    	    	
    	    	febStart = fromDate;
    	    }
    	    
    

    	    String validto = (String) person.get("_ValidTo");
    	    
    	    Date toDate;
    	    
    	    if(validto.equals("9999-01-01T00:00:00.000Z") )
    	    		{
    	    			toDate = febEnd;
    	    		}
    	    else
    	    {
    	    	toDate = sdf.parse(validto, new java.text.ParsePosition(0));
    	    	if(toDate.after(febEnd))
    	    	{
    	    		toDate = febEnd;
    	    	}
    	    	
    	    	
    	    }
    	    
    	    cal.setTime(toDate);
    	    long diff = toDate.getTime() - febStart.getTime();
    	    
    
    	    long no_of_hours = diff / (1000 * 60 * 60 );
    	    
    	    long working_hours_diff=diff;
    	    
    	    Date start_date = febStart;
    	       	   
    	  
    	   // Print number of hours spent on projects in February
    	    
    	    
    	    if(no_of_hours > 0)
    	    {
    	    
    	    	System.out.println("No of hours spent on WorkID "+ (long) person.get("ObjectID")+" in Feb = "+no_of_hours);
    	    	
    	    	 while(start_date.before(toDate))
    	    	   {
    	    		 
    	    		   cal.setTime(start_date);
    	    		   if(cal.get(Calendar.DAY_OF_WEEK)==0 || cal.get(Calendar.DAY_OF_WEEK)==6 )
    	    		   {
    	    			   
    	    		
    	    			   working_hours_diff = working_hours_diff - (24*60*60*1000);
    	    			   
    	    		   }
    	    		   cal.add(Calendar.DATE, 1);
    	    		   start_date = cal.getTime();
    	    		  
    	    	   }
    	    	 
    	    	 long no_of_working_hours = working_hours_diff / (1000 * 60 * 60 );
    	    	 
    	    	 long no_of_working_hours_final = (no_of_working_hours/24)*8;
    	    	 long offset = (no_of_working_hours%24)-9;
    	    	 
    	    	 
    	    	 
    	    	 if(offset>17)
    	    	 {
    	    		no_of_working_hours_final = no_of_working_hours_final +8; 
    	    	 }
    	    	 else
    	    	 {
    	    		 no_of_working_hours_final=no_of_working_hours_final+offset - 9;
    	    	 }
    	    	 
    	    	// Print number of working hours spent on projects in February
    	    	 if(no_of_working_hours_final>0)
    	    	 {
    	    	 
    	    		 System.out.println("No of Working hours spent on WorkID "+ (long) person.get("ObjectID")+" in Feb = "+no_of_working_hours_final);
    	    		 

    	    		 if(person.get("ScheduleState").equals("Idea"))
    	    		 {
    	    			 
    	    			 idea_count = idea_count + no_of_working_hours_final;
    	    			 
    	    		 }
    	    		 if(person.get("ScheduleState").equals("In-Progress"))
    	    		 {
    	    			 inprogess_count = inprogess_count + no_of_working_hours_final;
    	    			 
    	    		 }
    	    		 if(person.get("ScheduleState").equals("Completed"))
    	    		 {
    	    			 completed_count = completed_count + no_of_working_hours_final;
    	    			 
    	    		 }
    	    		 if(person.get("ScheduleState").equals("Accepted"))
    	    		 {
    	    			 accepted_count = accepted_count + no_of_working_hours_final;
    	    		 }
    	    		 if(person.get("ScheduleState").equals("Released"))
    	    		 {
    	    			 released_count = released_count + no_of_working_hours_final;
    	    		 }
    	    		 if(person.get("ScheduleState").equals("Defined"))
    	    		 {
    	    			 defined_count = defined_count + no_of_working_hours_final;
    	    			 
    	    		 }
    	    		 
    	    	 }
    	    	 else
    	    	 {
    	    		 System.out.println("No of Working hours spent on WorkID "+ (long) person.get("ObjectID")+" in Feb = 0");
    	    	 }
    	    
    	    }
    	   

    	  }
    	  
    	  System.out.println("\nTotal working hours spent in schedule state Idea =" + idea_count);
    	  System.out.println("Total working hours spent in schedule state In-Progress =" + inprogess_count);
    	  System.out.println("Total working hours spent in schedule state Defined =" + defined_count);
    	  System.out.println("Total working hours spent in schedule state Accepted =" + accepted_count);
    	  System.out.println("Total working hours spent in schedule state Completed =" + completed_count);
    	  System.out.println("Total working hours spent in schedule state Released =" + released_count);
	} catch(Exception ex)
	{
		System.out.println("Exception Caught..."+ ex.getMessage());
	}
 
     }
     
 
}