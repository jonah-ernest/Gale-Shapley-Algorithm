import java.io.IOException;
import java.util.ArrayList;

public class Student {
		
	private String name ; // name
	private double GPA ; // GPA
	private int ES ; // extracurricular score
	private int [] rankings ; // rankings of schools
	private int school ; // index of matched school
	private int regret ; // regret
	private int numOfSchools ;
	
	// constructors
	public Student () {
		// initialize the attributes in a constructor
		this.name = "";
		this.GPA = 0;
		this.ES = 1;
		this.school = -1;
		this.regret = 0;
		this.rankings = new int[2000];
		numOfSchools = 0;
	}
	public Student ( String name , double GPA , int ES , int nSchools ) {
		// initialize the attributes in a constructor where value are passed to it
		this.name = name;
		this.GPA = GPA;
		this.ES = ES;
		this.school = -1;
		this.regret = 0;
		this.rankings = new int[nSchools];
		numOfSchools = nSchools;
	}
	
	// getters
	public String getName () {
		// get the name of the student object
		return this.name;
	}
	
	public double getGPA () {
		// get the GPA of the student object
		return this.GPA;
	}
	
	public int getES () {
		// get the extracurricular scores of the student object
		return this.ES;
	}
	
	public int getRanking ( int i ) {
		// get the value of the rankings array at index i of the student object
		return this.rankings[i];
	}
	
	public int getSchool () {
		// get the index of the assigned school of the student object
		return this.school;
	}
	
	public int getRegret () {
		// get the regret of the student object
		return this.regret;
	}
	
	// setters
	public void setName ( String name ) {
		// set the name of the student object to the passed value
		this.name = name;
	}
	
	public void setGPA ( double GPA ) {
		// set the GPA of the student object to the passed value
		this.GPA = GPA;
	}
	
	public void setES ( int ES ) {
		// set the extracurricular score of the student object to the passed value
		this.ES = ES;
	}
	
	public void setRanking ( int i , int r ) {
		// set the value of the rankings array at index i of the student object to the passed value
		this.rankings[i] = r;
	}
	
	public void setSchool ( int i ) {
		// set the index of the school the student object was assigned to, to the passed value
		this.school = i;
	}
	
	public void setRegret ( int r ) {
		// set the regret of the student object to the passed value
		this.regret = r;
	}
	
	public void setNSchools ( int n ) {
		// set the number of school of the student object to the passed value
		this.school = n;
	}

	public int findRankingByID ( int ind ) {
		// find student index based on student ranking
		// takes in the rank of the student, and returns the index
		int x = -1 ; // index of student
		// loop through the rankings array of this school object
		for (int i = 0; i < this.rankings.length ; i++) {
			// if the array at i is equal to rank
			if (this.rankings[i] == ind) {
				// set the index of the rankings array at rank to x
				x = i;
				// break the loop
				break;
			}
		}
		// return the rank
		return x;
	}
	
	public int getIndexOfArray (int rank) {
		// find student index based on student ranking
		// takes in the rank of the student, and returns the index
		int x = 0 ; // index of student
		// loop through the rankings array of this school object
		for (int i = 0; i < this.rankings.length ; i++) {
			// if the array at i is equal to rank
			if (this.rankings[i] == rank) {
				// set the index of the rankings array at rank to x
				x = i;
				// break the loop
				break;
			}
		}
		// return the rank
		return x;
	}
	public void editInfo ( ArrayList<School> H , boolean canEditRankings ) throws NumberFormatException , IOException {
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
		
		// ask the user for the new name
		System.out.print("\nName: ");
		this.setName(Main.input.readLine());
		// ask the user for the new GPA by calling the getDouble function from the main class
		this.setGPA(Main.getDouble("GPA: ", 0.0, 4.0));
		// ask the user for the new ES by calling the getInteger function from the main class
		this.setES(Main.getInteger("Extracurricular score: ", 0, 5));
		
		// while it is allowed to edit the rankings
		while(yn == true) {
			// ask the user if they want to edit the rankings
			System.out.print("Edit rankings (y/n): \n");
			y_or_n = Main.input.readLine();
			
			// if they want to edit the rankings
			if (y_or_n.equals("y")) {
				// call the edit rankings method in this class
				this.editRankings(H,canEditRankings);
				// print a new line and set yn to false to break the loop
				System.out.print("\n");
				yn = false;
			}
			// if not set yn to false to break the loop
			else if (y_or_n.equals("n")) {
				yn = false;
			}
			// if they do not enter y or n, print the following error message
			else {
				System.out.print("ERROR: Choice must be 'y' or 'n'!\n");
			}
		}
	}
	public void editRankings ( ArrayList < School > H , boolean rankingsSet ) {
		// edit rankings
		// takes in an arrayList of school objects
		// returns nothing
		
		// initialize variables
		int x, s = 0; // rank given to the schools
		boolean goAhead; // boolean that allows loop to move forwards
		
		// loop through the rankings array for this student object and set all the ranks to 0
		for (int i = 0; i < H.size(); i++) {
			this.rankings[i] = 0;
		}
		
		// print the student for which rankings will be assigned
		System.out.print("Student " + this.getName() +"'s rankings:\n");
		
		//loop through each school
		for (int j = 0; j < H.size(); j++) {
			// set the rank to zero
			x = 0;
			// set the boolean that controls the loop to true
			goAhead = true;
			
			// use a do while loop to get a valid rank
			do {
				// ask the user for a rank by calling the get integer function from the main class
				x = Main.getInteger("School " + (H.get(j)).getName() + ": ",1, H.size());
					// loop through the schools again
					for (int k = 0; k < H.size(); k++)	{
						// if the ranking has already been used in the rankings array of this student object
						if (0 != this.getRanking(x-1)) {
							// print the following error
							System.out.println("ERROR: Rank " + Integer.toString(x) + " already used!\n");
							// keep looping in the while and break the for loop
							goAhead = true;
							break;
						}
						// otherwise, stop the while loop
						else {
							s++;
							goAhead = false;
							break;
						}
					}
				} while(goAhead);
			// while loop will continue as long as goAhead is true
			
			// set the ranking of index x-1 to rank s
			this.setRanking(x-1,s);
			
			}
			
		}
	
	public void print ( ArrayList < School > H ) {
		// print student info and assigned school in tabular format
		// takes in an arrayList of school objects, and a boolean for if ranking has or has not happened
		// returns nothing
				
		// initialize variables
		String spaceString2; // strings to contain spaces for formating
		int y = 0; // # of spaces to add
 		spaceString2 = " ";
 		
		// print the line of the student's GPA
 		System.out.format("%.2f   " + Integer.toString(this.getES()) + "  ",this.getGPA()); 
 		
		// if the student has not been assigned to a school, print a dash in the place of the assigned school column
		if (this.school == -1) {
			System.out.print("-                          ");			
		}
		// otherwise
		else {
			// print the name of the assigned school
			System.out.print(H.get(this.school).getName());
			// print the correct # of spaces after it
			y = 27 - (H.get(this.school).getName()).length();
			if (y > 0) {
				System.out.print(spaceString2.repeat(y));
			}
			else{
				// do this if y is negative
				System.out.print("");
			}
		}
		// call the print method
		this.printRankings(H);
		 	
	}
	public void printRankings ( ArrayList < School > H ) {
		//print the rankings separated by a comma
		// takes in an arrayList of school objects
		// returns nothing
				
		// initialize variables
		String schoolRankNames = ""; // sting of the ranked school names
		// loop through the rankings array
		for(int k = 0 ; k < H.size() ; k++ ) {
			// add the name of the school at index i to the list of names
			schoolRankNames += (H.get(this.getRanking(k)-1).getName()) + ", ";
		}
		// at the end remove the last space and comma from the string of names
		schoolRankNames = schoolRankNames.substring(0, schoolRankNames.length() - 2);
		// print the ranked names
		System.out.println(schoolRankNames);
	}
	
	public boolean isValid () {
		// check if this student has valid info
		// declare booleans that will determine if the info is valid or not
		boolean infoIsValid = false, rankRanges = false;
		int rankHolder; // to hold ranks of schools
		// if the GPA and ES are in the correct range set infoIsValid to ture
		if ((0.00 <= this.GPA && this.GPA <= 4.00) && (0 <= this.ES && this.ES <= 5)) {
			infoIsValid = true;
		}
		// if the rankings repeat then set info valid to false
		for (int i = 0; i < rankings.length - 1; i++) {
			rankHolder = rankings[i];
			for (int j = i+1; j < rankings.length ; j++) {
				// check if the rank i repeating
				if (rankings[j] == rankHolder) {
					infoIsValid = false;
					break;
				}
			}
		}
		// if the rankings are in the correct range set rankRanges to true
		for (int i = 3; i <= rankings.length - 1 ; i++) {
			// check if the ranks go from 1 to the number od schools
			if (1 <= rankings[i] && rankings[i] <= this.numOfSchools) {
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
