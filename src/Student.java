import java.util.*;


public class Student {
	
	private String id;
	private String [] quizresult = new String[10]; 
	public int score;
	
	public Student(){ //empty constructor
		
		id=" ";
		for(int i =0; i<quizresult.length; i++){
		     quizresult[i]="";
		     			
		}
		
		score =0;
	}
	
	
	public Student(String a, String[] answers){ //constructor for student id and student true/false answers
		
		id=a;
		
		for(int i=0; i<answers.length; i++){
			
			quizresult[i]=answers[i];
			
		}
		
		score = 0;
		
	}
	
	public String [] getAnwers(){ //accessor
		
		return quizresult;
		
	}
	
	public String getId(){ //accessor
		
		return id;
		
	}
	
	
	public void grade(String [] answerkey, String[] studentanswer){ // for every answer that is correct (matches answer key), add 10 points to the student's score
		
		int count=0;
		
				for(int i=0; i<answerkey.length; i++){
					
					if(answerkey[i].equals(studentanswer[i])){
						
						count = count+10;
						
					}
					
				}
		
				score = count;
		
		
	}
	
	public String toString(){ //takes the object's data and converts it into a string to print
		
		String a = "Student id: " + id + " " +  Arrays.toString(quizresult) + " The score for this quiz is:  " +  score + "\n" + "That score in letter grade is:  " + assignGrade();
		
		return a;
		
			}
	

	public String assignGrade() //assigns a grade to a student using score switch case
	{
	  switch(score)
	  {
	    case 0:
	    case 10:
	    case 20:
	    case 30:
	    case 40:
	      return "F";
	      
	    case 50:
	    case 60:
	      return "D";
	      
	    case 70:
	    case 80:
	      return "C";
	    
	    case 90: return "B";
	    case 100: return "A";
	  }
	  return "invalid";
	}
	 
}
	
	


