import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class School extends Participant {
	private double alpha ; // GPA weight
	
	// constructors
	public School () {
		super(); // calls super class
		this.alpha = -1;
	}
	public School ( String name , double alpha , int maxMatches , int nStudents ) {
		// initialize the object with the values passed in the constructor
		super(name , maxMatches , nStudents); // calls super class
		this.alpha = alpha;
	}
	
	// getters and setters
	public double getAlpha () {
		// get the alpha of the school object
		return this.alpha; 
	}
	public void setAlpha (double alpha) {
		// set the alpha of the school object
		this.alpha = alpha;
	}
	
	public void editSchoolInfo ( ArrayList < Student > S , boolean canEditRankings ) throws NumberFormatException, IOException {
		// get new info from the user ; cannot be inherited or overridden from parent
		// get new info from the user
		// takes in an arrayList of student objects
		// returns nothing
		// loop through the rankings array for this participant object and set all the ranks to 0
		for (int i = 0; i < S.size(); i++) {
			this.setRanking(i,0);
		}
		// call the super class to edit the info
		super.editInfo(S);	
		// call the get double function from the main class to ask the user for a new GPA weight
		this.setAlpha(Main.getDouble("GPA weight: ", 0.0, 1.0));
		// call the get integer function from the main class to ask the user for a new maxMAtches
		this.setMaxMatches(Main.getInteger("Maximum number of matches: ", 1, Integer.MAX_VALUE));
	}
	
	@SuppressWarnings("unchecked")
	public void calcRankings ( ArrayList < Student > S ) {
		// calculate rankings based on weight alpha
		// takes in an arrayList of school objects
		// returns nothing
		
		// initializing variables
		Double compScore; // variable for the calculated value of how much the school wants the student
		ArrayList<Double> csList = new ArrayList<Double>(S.size()); // array list to contain the compScores
		ArrayList<Double> csSortedList; // will be a sorted version of csList
		
		// loop through each student
		for (int j = 0; j < S.size(); j++) {
			// calculate the compScore for each student with for this school object and add it to the array list
			compScore =( ((this.getAlpha()) * (S.get(j).getGPA()) ) + ((1 - (this.getAlpha()) ) * (S.get(j).getES()) ) );
			csList.add(compScore);
		}
		// duplicate the array list of compScores
		csSortedList = (ArrayList<Double>) csList.clone();
		
		// sort the duplicate from largest to smallest
		Collections.sort(csSortedList,Collections.reverseOrder());
		
		// add the ranks to the rankings array for this school object according to their position in the sorted array list of compScores
    	for(int k = 0; k < csList.size(); k++) { 
    		// then loop though the sorted version
    		for(int p = 0; p < csSortedList.size(); p++) { 
    			// add to the rankings for the given index the index of the value in the sorted list + 1
    			if(csList.get(k) == csSortedList.get(p)) { 
	    			this.setRanking(k,p+1); 
	    		}
	    	}
    	}
		// clear both array lists
		csList.clear();
		csSortedList.clear();
	}
	
	public void print ( ArrayList <? extends Participant > S ) {
		// print school row
		// print school info and assigned student in tabular format
		// takes in an arrayList of student objects, and a boolean to allow to edit rankings
		// returns nothing
		
		// initialize variables
		String spaceString2, string3 = ""; // strings to contain spaces for formating
		int y = 0; // # of spaces to add
 		spaceString2 = " ";
		
 		// print the line of the school's name and its Alpha/GPA weight
 		System.out.format(Integer.toString(this.getMaxMatches()) + "    ");
 		
		// print the line of the school's name and its Alpha/GPA weight
		System.out.format("%.2f  " ,this.getAlpha());
		
		// if the school has not been assigned to a student, print a dash in the place of the assigned student column
		if (this.getMatch(0) == -1) {
			System.out.print("-                                       ");
		}
		// otherwise
		else {
			for(int i = 0; i < this.getNMatches(); i++) {
				// print the name of the assigned student
				System.out.print(S.get(this.getMatch(i)).getName());
				string3 += S.get(this.getMatch(i)).getName();
				if(i != this.getNMatches() - 1) {
					System.out.print(", ");
					string3 += ", ";
				}
			}
			// print the correct # of spaces after it
			y = 40 - (string3).length();
			if (y < 0) {
				spaceString2 = "";
			}
			else {
				spaceString2 = spaceString2.repeat(y);
			}
			System.out.print(spaceString2);
		}
		
		// if the rankings have been set call the print rankings method in this class
		if (S.size() != 0) {
			super.printRankings(S);
		}
		else {
			// otherwise print a dash
			System.out.println("-");
		}
	}
	
	public boolean isValid () {
		// check if this school has valid info
		boolean infoIsValid = true;
		// if alpha is in the correct range then the info is valid and return true, otherwise return false
		if ( 0.00 <= this.alpha && this.alpha <= 1.00) {
			infoIsValid =  true;
		}
		else {
			infoIsValid = false;
		}
		// if the maxMatches is less than one the info will not be valid
		if (this.getMaxMatches() < 1) {
			infoIsValid = false;
		}
		// return if the info is valid or not
		return infoIsValid;
	} 
}