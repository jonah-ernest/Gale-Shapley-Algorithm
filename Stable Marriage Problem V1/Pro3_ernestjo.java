import java.io.*;
import java.util.ArrayList;

public class Pro3_ernestjo{
    public static BufferedReader input = new BufferedReader(new InputStreamReader(System.in)); //object used to get user input
    private static boolean matchingComplete = false;
    private static boolean rankingsSet = false;
	
	private static ArrayList<Student> S = new ArrayList<Student>();
	private static ArrayList<School> H = new ArrayList<School>();
    
    public static void main(String[] args) throws IOException{
	    
    	menuChoice();
    }
    // display main menu
    public static void displayMenu() throws IOException {
    	System.out.println("JAVA STABLE MARRIAGE PROBLEM v1");
    	System.out.print("\n");
    	System.out.println("S - Enter students");
    	System.out.println("H - Enter high schools");
    	System.out.println("A - Assign rankings");
    	System.out.println("E - Edit students and schools");
    	System.out.println("P - Print students and schools");
    	System.out.println("M - Match students and schools");
    	System.out.println("D - Display matches and statistics");
    	System.out.println("R - Reset database");
    	System.out.println("Q - Quit\n");
    }
    
    // display the edit sub menu
    public static void displayMenu2() {
    	System.out.println("Edit data");
    	System.out.println("---------");
    	System.out.println("S - Edit students");
    	System.out.println("H - Edit high schools");
    	System.out.println("Q - Quit\n");
    }
    
    // check if valid and call menu choice function
    public static void menuChoice() throws IOException {
 
    	String choice;
    	
    	
    	while(1!=2) {
    		displayMenu(); // print menu
    
    		choice = getString("Enter choice: "); // ask user for menu choice
    		System.out.print("\n");
    		
    		if(choice.equals("S") || choice.equals("s")){
    			
    			getStudents(S); // get all the students' info
    			
    		}
    		
    		else if(choice.equals("H") || choice.equals("h")) {
    			
    			getSchools(H); // get all the students' info
    		}
    		
    		else if(choice.equals("A") || choice.equals("a")) {
    			
    			rankingsSet = assignRankings(S, H, S.size(), H.size());  // gets student's rankings of schools, calculates school's rankings of students and returns if ranking occured
    		}
    		
    		else if(choice.equals("E") || choice.equals("e")) { // if choice is e, edit the data
    			editData(S, H, S.size(), H.size(), rankingsSet);
    		}
    		
    		else if(choice.equals("P") || choice.equals("p")) {
    			if(S.size() != 0) {
    				System.out.println("STUDENTS:"); // print the student table
    				printStudents(S, H, S.size(), rankingsSet);
    				System.out.print("\n");
    			}
    			
    			else { // if there aren't any students
    				System.out.println("ERROR: No students are loaded!\n");
    			}
    			
    	    	if(H.size() != 0) {
    	    		System.out.println("SCHOOLS:");
    	    		printSchools(S, H, H.size(), rankingsSet); // print the school table
    	    		System.out.print("\n");
    	    	}
    	    	
    	    	else { // if there aren't any schools
    	    		System.out.println("ERROR: No schools are loaded!\n");
    	    	}
    		}
    		
    		else if(choice.equals("M") || choice.equals("m")) {
    			matchingComplete = match(S, H, S.size(), H.size(), rankingsSet); // ask user to match the schools and students together
    		}
    		
    		else if(choice.equals("D") || choice.equals("d")) {
    			displayMatches(S, H, S.size(), H.size()); // display various statistics including regret and if the matches were stable
    		}
    		
    		else if(choice.equals("R") || choice.equals("r")) { // reset database
    			reset(S, H);
    		}
    		
    		else if(choice.equals("Q") || choice.equals("q")) { // quit program
    			System.out.println("Sayonara!");
    			return;
    		}
    		
    		else {
    			System.out.println("ERROR: Invalid menu choice!\n");
    		}
    	}
    }
    
    // get a string from user
    public static String getString(String prompt) throws IOException {
    	System.out.print(prompt);
    	String x = null;
    	x = input.readLine();
    	return x;
    }
    
    // reset the schools and students from the database which also resets the rankings and matching process
    public static void reset(ArrayList<Student> S, ArrayList<School> H) {
    	S.clear();
    	H.clear();
    	rankingsSet = false;
    	matchingComplete = false;
    	
    	System.out.println("Database cleared!\n");
    }
    
    // add new students and collect their info
    public static int getStudents(ArrayList<Student> S) throws IOException{
    	
    	int nStudents = getInteger("Number of students to add: ", 0, Integer.MAX_VALUE);
		
		for(int i = 0; i < nStudents; i++) { // loop through each student
			
			Student p = new Student(); // creates a new instance of the object student
			
			System.out.println("\nStudent " + (S.size()+1) + ":");
			
			String studentName = getString("Name: "); //assigning name to variable in main class
			p.setName(studentName); // assigning studentName to instance p
			
			double studentGPA = getDouble("GPA: ", 0.0, 4.0);
			p.setGPA(studentGPA);
			
			int studentES = getInteger("Extracurricular score: ", 0, 5);
			p.setES(studentES);
			
			p.setNSchools(H.size());
			
			S.add(p); // add each student to the array list of students
			
			}
		
		//reset rankings array and assigned schools for each student
		for(int i = 0; i < S.size(); i++) {
			S.get(i).resetRanks();
			S.get(i).resetAssignedSchool();
		}
		
		for(int i = 0; i < H.size(); i++) {
			H.get(i).setNStudents(S.size()); // reset the rankings array to zero
			H.get(i).resetRanks();
			H.get(i).resetAssignedStudent();
		}
		System.out.print("\n");
		
		rankingsSet = false; // set rankings to false again because they were reset
		return nStudents;
    }

    // get new schools and collect their info
    public static int getSchools(ArrayList<School> H) throws IOException {
    	
    	int nSchools = getInteger("Number of schools to add: ", 0, Integer.MAX_VALUE);
				
		for(int i = 0; i < nSchools; i++) { // loop through each school
			
			School q = new School(); // creates a new instance of the object school
			
			System.out.println("\nSchool " + (H.size()+1) + ":");
			
			String schoolName = getString("Name: "); //assigning name to variable in main class
			q.setName(schoolName); // assigning studentName to instance p
			
			double schoolAlpha = getDouble("GPA weight: ", 0.0, 1.0);
			q.setAlpha(schoolAlpha);
			
			q.setNStudents(nSchools);
			
			H.add(q); // add each school to the array list of schools
			
		}
		
		System.out.print("\n");
		
		//reset rankings array and assigned schools for each student
		for(int i = 0; i < S.size(); i++) {
			S.get(i).setNSchools(H.size()); // reset the array to zero
			S.get(i).resetRanks();
			S.get(i).resetAssignedSchool();
		}
		
		//RESET rankings array and each school's assigned students
		for(int i = 0; i < H.size(); i++) {
			H.get(i).resetRanks();
			H.get(i).resetAssignedStudent();
		}
		rankingsSet = false; // set rankings to false again because they were reset
		return nSchools;
    }
   
    // find out the student's ranking of each school
    public static boolean assignRankings(ArrayList<Student> S, ArrayList<School> H, int nStudents, int nSchools){
    	// clear the rankings array for all students and schools
    	for(int i = 0; i < S.size(); i++) { 
    		S.get(i).setNSchools(H.size());
    		S.get(i).resetRanks();
		}
    	rankingsSet = false;
		
		for(int i = 0; i < H.size(); i++) {
			H.get(i).setNStudents(S.size());
			H.get(i).resetRanks();
		}
    	rankingsSet = false;
  
    	if(nStudents == 0) { // can't assign rankings without students
        	System.out.println("ERROR: No students are loaded!\n");
        	return false;
        }
    	else if(nSchools == 0) { // can't assign rankings without students
    		System.out.println("ERROR: No schools are loaded!\n");
         	return false;
    	}
        
        else {
        	for(int i = 0; i < nStudents; i++) {
        		System.out.println("Student "+ S.get(i).getName() + "'s " + "rankings:");
        	
        		S.get(i).editRankings(H); //takes into account the error that rank r is already used, and take's each students rankings for each school
        	}
   		 	for(int i = 0; i < nSchools; i++) {
   		 		H.get(i).calcRankings(S); // calculate the school's rankings of each student 
   		 	}
        }
    	return true;
    }
    
    public static void editData(ArrayList<Student> S, ArrayList<School> H, int nStudents, int nSchools, boolean rankingsSet) throws IOException{
    	String choice2; 
    	
    	while(1!=2) { // loop only ends if the choice is Q
    		displayMenu2();
    		
    		choice2 = getString("Enter choice: ");   	
    		
    		if(choice2.equals("S") || choice2.equals("s")){
    			editStudents(S, H, S.size(), H.size(), rankingsSet); // edit student info
    		}
    		
    		else if(choice2.equals("H") || choice2.equals("h")) {
    			editSchools(S, H, H.size(), rankingsSet); // edit high school info
    		}
    		
    		else if(choice2.equals("Q") || choice2.equals("q")) {
    			System.out.println("\n"); // quit
    			return;
    		}
    		
    		else {
    			System.out.println("\nERROR: Invalid menu choice!\n");
    		}
    	}
    }
    
    // edit the student info and rankings as well as print the student table
    public static void editStudents(ArrayList<Student> S, ArrayList<School> H, int nStudents, int nSchools, boolean rankingsSet) throws IOException{ //done?
    	if(nStudents == 0) {
    		System.out.println("\nERROR: No students are loaded!\n");
    		return;
    	}
    	
    	while(1!=2) {
    		printStudents(S, H, nStudents, rankingsSet); // print student table
    		int studentChoice = getInteger("Enter student (0 to quit): ", 0, nStudents);
	    	
	    	if(studentChoice == 0) {
	    		System.out.print("\n");
	    		break;
	    	}
	    	
	    	else {
	    		if(rankingsSet) {
	    			S.get(studentChoice-1).editInfo(H, true); // if they assigned rankings, ask if they want to edit the rankings
	    		}
	    		else {
	    			S.get(studentChoice-1).editInfo(H, false); // otherwise don't give the option to edit the rankings
	    		}
	    	}
    	}
    	
    	for(int i = 0; i < nSchools; i++) { //recalculate the school's rankings of students when a student has been edited
    		H.get(i).calcRankings(S);
    	}
    }

    // edit the school info and print the school table
    public static void editSchools(ArrayList<Student> S, ArrayList<School> H, int nSchools, boolean rankingsSet) throws IOException{
    	if(nSchools ==0) {
    		System.out.println("\nERROR: No schools are loaded!\n");
    		return;
    	}
    	
    	while(1!=2){
    		printSchools(S, H, nSchools, rankingsSet); // print school table
    		int studentChoice = getInteger("Enter school (0 to quit): ", 0, nSchools);
	    	
	    	if(studentChoice == 0) {
	    		System.out.print("\n");
	    		break;
	    	}
	    	
	    	else {
	    		if(rankingsSet) {
	    			H.get(studentChoice-1).editInfo(S, true); // if they assigned rankings, ask if they want to edit the rankings
	    		}
	    		else {
	    			H.get(studentChoice-1).editInfo(S, false); // otherwise don't give the option to edit the rankings
	    		}
	    	}
    	}
    	
    }

    // print the student table
    public static void printStudents(ArrayList<Student> S, ArrayList<School> H, int nStudents, boolean rankingsSet){ //done
    	System.out.println("\n #  Name                            GPA  ES  Assigned school            Preferred school order");
    	System.out.println("----------------------------------------------------------------------------------------------");
    	
    	for(int i = 0; i < nStudents; i++) {
    		if(i > 8) {
    			System.out.print((i+1) + ". "); // print the students numbered ten and higher without the space at the beginning
    		}
    		else {
    			System.out.print(" "+ (i+1) + ". "); // print the students number for student less than ten
    		}
    		S.get(i).print(H, rankingsSet); // prints the info for each student
    		
    		if(rankingsSet) {
    			S.get(i).printRankings(H); // print the rankings
    		}
    		else {
    			System.out.println("-"); // if rankings haven't been set then print a dash
        	}
    	}
    	System.out.println("----------------------------------------------------------------------------------------------");
    }

    // print the school table
    public static void printSchools(ArrayList<Student> S, ArrayList<School> H, int nSchools, boolean rankingsSet){ //done
    	System.out.println("\n #  Name                          Weight  Assigned student           Preferred student order");
    	System.out.println("--------------------------------------------------------------------------------------------");
    	
    	for(int i = 0; i < nSchools; i++) {
    		if(i > 8) {
    			System.out.print((i+1) + ". "); // print the schools numbered ten and higher without the space at the beginning
    		}
    		else {
    			System.out.print(" "+ (i+1) + ". "); // print the schools number for student less than ten
    		}
    		H.get(i).print(S, rankingsSet); // print the info for each school
    		
    		if(rankingsSet) {
    			H.get(i).printRankings(S); // print the rankings
    		}
    		else {
    			System.out.println("-"); // if rankings haven't been set then print a dash
    		}
    	}
    	System.out.println("--------------------------------------------------------------------------------------------");
    }

    public static boolean match(ArrayList<Student> S, ArrayList<School> H, int nStudents, int nSchools, boolean rankingsSet){
        if(matchingCanProceed(nStudents, nSchools, rankingsSet)) { // if matching is able to proceed
        	int schoolIndex;
  
        	for(int i = 0; i < S.size(); i++) {
        		H.get(i).resetAssignedStudent();
        		S.get(i).resetAssignedSchool(); // resetting the previous matches
        	}
        	//System.out.print("\n"); ? 11:55pm
        	for(int i = 0; i < S.size(); i++) { // for each student get their match
        		
        		boolean notValid = true;
        		do {
        			schoolIndex = getInteger("Enter school index for student " + (i+1) + " (" + S.get(i).getName() + "): ", 1, nStudents);
        			
        			for(int j = 0; j < S.size(); j++) { // loop through schools
	        				// check for the error that the school entered has already been matched 
	        				if(schoolIndex == S.get(j).getSchool()+1) { // if the entered index is equal to any of the other indexes
	        					System.out.println("ERROR: School " + (S.get(j).getSchool()+1) +" is already matched!\n");
	        					notValid = true;
	        					break;
	        				}
	        				else {
	        					notValid = false;
	        				}
        			} 	
        	
        		} while(notValid == true);
        		S.get(i).setSchool(schoolIndex-1); // set the student's school value to the index of the matched school
				H.get(schoolIndex-1).setStudent(i); // set the school's student value to the index of the matched student
        	}
        	System.out.print("\n");
        	return true;
        }
        
        else {
        	return false; // if the matching can't proceed then don't match
        }
    }

    // display the matches, average student, school and total regret, and whether all the matching was stable
    public static void displayMatches(ArrayList<Student> S, ArrayList<School> H, int nStudents, int nSchools){
    	if(!matchingComplete) {
    		System.out.println("ERROR: No matches exist!\n");
    		return;
    	}
    	else { // if matching has occurred display matches, regret, etc.
    		System.out.println("Matches:\n--------");
    		
    		for(int i = 0; i < H.size(); i++) { // (looping through the schools) print the name of school and the student they matched with
    			System.out.println(H.get(i).getName() + ": " + S.get(H.get(i).getStudent()).getName());
    		}
    		System.out.print("\n");
    	
    		
    	//calculate the regret for each student and school then add it to their respective array list
    		ArrayList<Double> studentRegret = new ArrayList<Double>();
        	ArrayList<Double> schoolRegret = new ArrayList<Double>();
        	
        	double studentValue = 0.0;
        	double schoolValue = 0.0;
        	
        	for(int i = 0; i < S.size(); i++){  // loop through all the students
        		for(int j = 0; j < H.size(); j++) { // calculate the regret and add it to the array list and student object
        			studentValue = (S.get(i).getRanking(S.get(i).getSchool()) - 1);
        			studentRegret.add(studentValue);
        			S.get(i).setRegret((int)studentValue);
        		}
        	}
        	
        	for(int i = 0; i < H.size(); i++) { //loop through all the schools
        		for(int j = 0; j < S.size(); j++) { // calculate the regret and add it to the array list  and school object
        			schoolValue = (H.get(i).getRanking(H.get(i).getStudent()) - 1);
        			schoolRegret.add(schoolValue);
        			H.get(i).setRegret((int)schoolValue);
        		}
        	}
        	
        	// calculate the average student regret
        	double studentSum = 0.0;
        	double averageStudentRegret = 0.0;
        	for(int i = 0; i < studentRegret.size(); i++) {
        		studentSum += studentRegret.get(i); // find the total student regret
        	}
        	averageStudentRegret = (studentSum / studentRegret.size()); // divide the total student regret by the number of students
    		
        	// calculate the average school regret
        	double schoolSum = 0.0;
        	double averageSchoolRegret = 0.0;
        	for(int i = 0; i < schoolRegret.size(); i++) {
        		schoolSum += schoolRegret.get(i); // find the total school regret
        	}
        	averageSchoolRegret = (schoolSum / schoolRegret.size()); // divide the total student regret by the number of students
        	
        	//calculate the total regret
        	double averageTotalRegret = 0;
        	averageTotalRegret = ((averageStudentRegret + averageSchoolRegret) / 2);
        	
    		System.out.print("Stable matching? "); // print whether the matching was stable or not based on the stable match function
    		if(stableMatch(S,H)) {
    			System.out.println("Yes");
    		}
    		else {
    			System.out.println("No");
    		}
    		// print all the statistics calculated above
    		System.out.format("Average student regret: %.2f\n", averageStudentRegret); 
    		
    		System.out.format("Average school regret: %.2f\n", averageSchoolRegret);
    		
    		System.out.format("Average total regret: %.2f", averageTotalRegret);
    		System.out.println("\n");
    	}
    }
    
    // determine if matching is stable
    public static boolean stableMatch(ArrayList<Student> S, ArrayList<School> H) {
    	int matchedSchool;
    	int matchedSchoolRank;
    	
    	int matchedStudent; 
    	int matchedStudentRank;
    	
    	for(int j = 0; j < S.size(); j++) { //loop through students
    		if(0 < S.get(j).getRegret()) { // if they didn't get their first choice
    			matchedSchool = S.get(j).getSchool(); // index of school j-th student is matched with
    			matchedSchoolRank = S.get(j).getRanking(matchedSchool); // define the rank of the matched school
    		
    			for(int k = 0; k < H.size(); k++) { // loop through schools
    				if(matchedSchoolRank > S.get(j).getRanking(k)) { // if the matched school ranking of the current student is worse than their ranking of the k-th school
    					matchedStudent = H.get(k).getStudent(); // define the index of student the k-th school was matched with
    					matchedStudentRank = H.get(k).getRanking(matchedStudent); // the rank that this school gave to their matched student
    				
    					if(H.get(k).getRanking(j) < matchedStudentRank) { // if the school's ranking of the student they were matched with was worse than the ranking of the j-th student
    						return false;
    					}
    				}
    			}
    		}
    	}
    	return true; // if none of the students returned false then every match was stable
    }
   
    
    // is called in the match function and checks initial conditions that must be met in order to match students and schools
    public static boolean matchingCanProceed(int nStudents, int nSchools, boolean rankingsSet){
    	if(nStudents == 0) { // if there aren't any students
        	System.out.println("ERROR: No students are loaded!\n");
        	return false;
        }
        
    	else if(nSchools == 0) { // if there aren't any schools
        	System.out.println("ERROR: No schools are loaded!\n");
        	return false;
        }
        
        else if(nStudents != nSchools){ // if the number of schools is different than the number of students
        	System.out.println("ERROR: The number of students and schools must be equal!\n");
        	return false;
        }
        
        else if(!rankingsSet) { // if the rankings haven't been established
        	System.out.println("ERROR: Student and school rankings must be set before matching!\n");
        	return false;
        }
        
        else { // otherwise matching can proceed
        	return true;
        }
    }

    // gets a valid integer between the indicated lower and upper bounds
    public static int getInteger(String prompt, int LB, int UB){
    	int x = 0;
    	// valid is true until there is an invalid entry which causes the loop to repeat until valid is true 
    			boolean valid;
    			do {
    				System.out.print(prompt);
    				valid = true;
    				try {
    					x = Integer.parseInt(input.readLine());
    				}
    				catch (NumberFormatException e) {
    					if(UB == Integer.MAX_VALUE){
    						System.out.format("\nERROR: Input must be an integer in [%d, infinity]!\n", LB);
    					}
    					else {
    						System.out.format("\nERROR: Input must be an integer in [%d, %d]!\n", LB, UB);
    					}
    					valid = false;
    				}
    				catch (IOException e){
    					if(UB == Integer.MAX_VALUE) {
    						System.out.format("\nERROR: Input must be an integer in [%d, infinity]!\n", LB);
    					}
    					else {
    						System.out.format("\nERROR: Input must be an integer in [%d, %d]!\n", LB, UB );
    					}
    					valid = false;
    				}
    	// check that the entry is within the upper and lower bounds 
    				if (valid && (x < LB || x > UB)) {
    					valid = false;
    					if(UB == Integer.MAX_VALUE) {
    						System.out.format("\nERROR: Input must be an integer in [%d, infinity]!\n", LB);
    					}
    					else {
    						System.out.format("\nERROR: Input must be an integer in [%d, %d]!\n", LB, UB);
    					}
    				}
    				if(!valid) {
    					System.out.print("\n");
    				}
    			} while(!valid);
    			return x;
    }

 // gets a valid double between the indicated lower and upper bounds
    public static double getDouble(String prompt, double LB, double UB) {
    	double x = 0;
    			// valid is true until there is an invalid entry which causes the loop to repeat until valid is true 
    			boolean valid;
    			do {
    				System.out.print(prompt);
    				valid = true;
    				try {
    					x = Double.parseDouble(input.readLine());
    				}
    				catch (NumberFormatException e) {
    					if(UB == Double.MAX_VALUE) {
    						System.out.format("\nERROR: Input must be a real number in [%.2f, infinity]!\n", LB);
    					}
    					else {
    						System.out.format("\nERROR: Input must be a real number in [%.2f, %.2f]!\n", LB, UB);
    					}
    					valid = false;
    				}
    				catch(IOException e) {
    					if(UB == Double.MAX_VALUE) {
    						System.out.format("\nERROR: Input must be a real number in [%.2f, infinity]!\n", LB);
    					}
    					else {
    						System.out.format("\nERROR: Input must be a real number in [%.2f, %.2f]!\n\n", LB, UB);
    					}
    					valid = false;
    				}
    				// check that the entry is within the upper and lower bounds 
    				if(valid && (x < LB || x > UB)) {
    					valid = false;
    					if(UB == Double.MAX_VALUE) {
    						System.out.format("\nERROR: Input must be a real number in [%.2f, infinity]!\n", LB);
    					}
    					else {
    						System.out.format("\nERROR: Input must be a real number in [%.2f, %.2f]!\n", LB, UB);
    					}
    				}
    				if(!valid) {
    					System.out.print("\n");
    				}
    			} while(!valid);
    			return x;
    }
}