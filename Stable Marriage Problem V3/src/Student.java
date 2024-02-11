import java.io.IOException;
import java.util.ArrayList;

public class Student extends Participant {
	private double GPA ; // GPA
	private int ES ; // extracurricular score
	private int numOfSchools ; // number of schools
	
	// constructors
	public Student () {
		super(); // calls super class
		this.GPA = -1;
		this.ES = -1;
		this.numOfSchools = -1;
		
	}
	public Student ( String name , double GPA , int ES , int nSchools ) {
		// initialize the object with the values passed in the constructor
		super(name , 1 ,nSchools); // calls super class
		this.GPA = GPA;
		this.ES = ES;
		this.numOfSchools = nSchools;
	}
	
	// getters and setters
	public double getGPA () {
		// get the GPA of the student object
		return this.GPA;
	}
	public int getES () {
		// get the ES of the student object
		return this.ES;
	}
	public void setGPA ( double GPA ) {
		// set the GPA of the student object
		this.GPA = GPA;
	}
	public void setES ( int ES ) {
		// set the ES of the student object
		this.ES = ES;
	}
	
	public void editInfo ( ArrayList < School > H , boolean canEditRankings ) throws IOException, NumberFormatException {
		// user info
		// get new info from the user
		// takes in an arrayList of school objects, and a boolean to allow to edit rankings
		// returns nothing
		
		// initialize variables
		String y_or_n; // input for if the user wants to edit rankings or not
		boolean yn = false; // if edit to the rankings is allowed
		
		// if canEditRankings is true, we can edit the rankings, yn = true
		if (canEditRankings) {
			 yn = true;
		}
		// call the super  class to edit info
		super.editInfo(H);
		// ask the user for the new GPA by calling the getDouble function from the main class
		this.setGPA(Main.getDouble("GPA: ", 0.0, 4.0));
		// ask the user for the new ES by calling the getInteger function from the main class
		this.setES(Main.getInteger("Extracurricular score: ", 0, 5));
		// call the get integer function from the main class to ask the user for a new maxMAtches
		this.setMaxMatches(Main.getInteger("Maximum number of matches: ", 1, Integer.MAX_VALUE));
		
		// while it is allowed to edit the rankings
		while(yn == true) {
			// ask the user if they want to edit the rankings
			System.out.print("Edit rankings (y/n): ");
			y_or_n = Main.input.readLine();
			
			// if they want to edit the rankings
			if (y_or_n.equals("y") || y_or_n.equals("Y")) {
				System.out.print("\n");
				// call the edit rankings method in this class
				super.editRankings(H);
				// print a new line and set yn to false to break the loop
				System.out.print("\n\n");
				yn = false;
			}
			// if not set yn to false to break the loop
			else if (y_or_n.equals("n") || y_or_n.equals("N")) {
				System.out.print("\n");
				yn = false;
			}
			// if they do not enter y or n, print the following error message
			else {
				System.out.print("ERROR: Choice must be 'y' or 'n'!\n");
			}
		}
		
	}
	public void print ( ArrayList <? extends Participant > H ) {
		// print student row
		// print student info and assigned school in tabular format
		// takes in an arrayList of school objects, and a boolean for if ranking has or has not happened
		// returns nothing
				
		// initialize variables
		String spaceString2, string3 = ""; // strings to contain spaces for formating
		int y = 0; // # of spaces to add
 		spaceString2 = " ";
 		
		// print the line of the student's GPA
 		System.out.format("%.2f   " + Integer.toString(this.getES()) + "  ",this.getGPA()); 
 		
		// if the student has not been assigned to a school, print a dash in the place of the assigned school column
		if (this.getMatch(0) == -1) {
			System.out.print("-                                       ");			
		}
		// otherwise
		else {
			for(int i = 0; i < this.getNMatches(); i++) {
				// print the name of the assigned school
				System.out.print(H.get(this.getMatch(i)).getName());
				string3 += H.get(this.getMatch(i)).getName();
				if(i != this.getNMatches() - 1) {
					System.out.print(", ");
					string3 += ", ";
				}
			}
			// print the correct # of spaces after it
			y = 40 - (string3).length();
			if (y > 0) {
				System.out.print(spaceString2.repeat(y));
			}
			else{
				// do this if y is negative
				System.out.print("");
			}
			
		}
		// call the print method
		super.printRankings(H);
	}
	public boolean isValid () {
		// check if this student has valid info
		// declare booleans that will determine if the info is valid or not
		boolean infoIsValid = false, rankRanges = false;
		int rankHolder; // to hold ranks of schools
		// if the GPA and ES are in the correct range set infoIsValid to true
		if ((0.00 <= this.GPA && this.GPA <= 4.00) && (0 <= this.ES && this.ES <= 5)) {
			infoIsValid = true;
		}
		// if the rankings repeat then set info valid to false
		for (int i = 0; i < this.numOfSchools - 1; i++) {
			rankHolder = this.getRanking(i);
			for (int j = i+1; j < this.numOfSchools ; j++) {
				// check if the rank i repeating
				if (this.getRanking(j) == rankHolder) {
					infoIsValid = false;
					break;
				}
			}
		}
		// if the rankings are in the correct range set rankRanges to true
		for (int i = 0; i <= this.numOfSchools - 1 ; i++) {
			// check if the ranks go from 1 to the number of schools
			if (1 <= this.getRanking(i) && this.getRanking(i) <= this.numOfSchools) {
				rankRanges = true;
			}
			else {
				rankRanges = false;
				break;
			}
		}
		// if both these booleans are true then the info is valid and return true, otherwise it is not and return false
		if (rankRanges == true && infoIsValid == true) {
			return true;
		}
		else {
			return false;
		}
	}
}
