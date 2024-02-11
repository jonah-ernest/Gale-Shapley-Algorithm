
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
	
	public static BufferedReader input = new BufferedReader( new InputStreamReader( System.in ));
	// global buffer reader used to collect inputs from user and can be accessed from anywhere in the main class, and referenced from any other class
	
	public static void main(String[] args) throws NumberFormatException , IOException {
		// main function that runs the options of the program based on the choices of the users
		
		ArrayList<Student> studentList = new ArrayList<Student>();
		// create an ArrayList that will hold student objects
		ArrayList<School> schoolList = new ArrayList<School>();
		// create an ArrayList that will hold school objects
		
		// declare string choice to hold the input of the user's choice
		String choice = "";
		// initialize the amount of schools and students to 0
		int studentNum = 0, schoolNum = 0;
		// initialize boolean variables to keep track of if rankings have been assigned, if matching is possible, if matching is possible, if user choice is valid, if the try catch loop should keep going
		boolean assign = false, match = false, ranksReturn = false, I = false, allow;
		// create an array of acceptable inputs from the user
		String[] inputs = {"S","H","A","E","P","M","D","R","Q","s","h","a","e","p","m","d","r","q"};
		
		// use a while loop to loop through asking the user for input and executing their command
		while(true) {
			// use a do whlie to run through a try catch that will ask the user for an input until the input it recives is valid
			do {
				// set allow to true to make boolean variable that controls the loop true
				allow = true;
				
				try {
					// use the prompt to ask the user to input an choice of the menu
					// call the display menu to print the menu for the user
					displayMenu();
					// ask for their choice
					System.out.print("\nEnter choice: ");
					// take the input of the user as the variable choice
					choice = input.readLine();
					// check if the input is in the array of valid inputs
					I = Arrays.asList(inputs).contains(choice);
				}
				
				// catch a number format error
				catch(NumberFormatException e) {
					// print out an error message if this error occurs
					System.out.println("\nERROR: Invalid menu choice!\n");
					// turn the allow variable false
					allow = false;
				}
				
				// catch a io exception error
				catch(IOException e){
					// print out an error message if this error occurs
					System.out.println("\nERROR: Invalid menu choice!\n");
					// turn the allow variable false
					allow = false;
				}
				
				// if the allow variable is still true and the choice is not in the array of valid inputs, do the following:
				if ( allow && I == false){
					// print out an error message if this error occurs
					System.out.println("\nERROR: Invalid menu choice!\n");
					// turn the allow variable false
					allow = false;
				}
				// the loop will stop when the allow variable is true meaning at the end of the loop the entered choice is valid
			} while (!allow);
			
			// use a switch statement to go though the valid choices
			switch (choice) {
				case ("S"):
				case ("s"):
					// if the choice is to enter students, call the get students function
					studentNum = getStudents(studentList);
					// set the boolean for assigning rankings to false whenever a new student is added
					assign = false;
					break;
				case ("H"):
				case ("h"):
					// if the choice is to enter schools, call the get schools function
					schoolNum = getSchools(schoolList);
					// set the boolean for assigning rankings to false whenever a new school is added
					assign = false;
					break;
				case ("A"):
				case ("a"):
					// if the choice is to assign rankings of the schools for each student
					assign = assignRankings(studentList,schoolList,studentNum,schoolNum);
					break;
				case ("E"):
				case ("e"):
					// if the choice is to edit the already existing students and schools, call the edit data function
					editData(studentList,schoolList,studentNum,schoolNum,assign);
				// print a new line
					System.out.print("\n");
					break;
				case ("P"):
				case ("p"):
					// if the choice is to print the current students and schools, call the print student and print school functions
					printStudents(studentList,schoolList,studentNum,assign);
					printSchools(studentList,schoolList,schoolNum,assign);
					break;
				case ("M"):
				case ("m"):
					// if the choice is to match students with schools, call the matchingCanProceed function to see if matching is possible
					match = matchingCanProceed(studentNum,schoolNum,assign);
					// if match returns true, proceed with matching by calling the match function
					if (match == true) {
						ranksReturn = match(studentList,schoolList,studentNum,schoolNum,assign);
					}
					break;
				case ("D"):
				case ("d"):
					// if the choice is to display the matches and statistics, call the display matches function if there are matches made
					if (ranksReturn) {
						displayMatches(studentList,schoolList,studentNum,schoolNum);
					}
					else {
						// otherwise print that there are no matches made
						System.out.println("\nERROR: No matches exist!\n");
					}
					break;
				case ("R"):
				case ("r"):
					// if the choice is r, clear the arrays holding the students and school objects, set the number of students and schools back to zero
					// turn the boolean variables for assigning ranks, allowing matching students with schools, and for the actual matching being completed
					studentList.clear();
					schoolList.clear();
					studentNum = 0;
					schoolNum = 0;
					assign = false;
					match = false;
					ranksReturn = false;
					// print that the database is cleared
					System.out.println("\nDatabase cleared!\n");
					break;
				case ("Q"):
				case ("q"):
					// if the choice is to quit, print sayonara and quit the program, breaking the infinite while loop
					System.out.println("\nSayonara!");
					System.exit(0);
			}
		}
		
	}
	
	public static void displayMenu() {
		// this function displays the menu of options, and the name of the game
		//it takes no arguments and returns nothing
		System.out.print("JAVA STABLE MARRIAGE PROBLEM v1\n");
		
		System.out.println("\nS - Enter students\nH - Enter high schools\nA - Assign rankings\nE - Edit students and schools\nP - Print students and schools\nM - Match students and schools\nD - Display matches and statistics\nR - Reset database\nQ - Quit");
		
	}
	
	public static int getStudents(ArrayList<Student> S) throws NumberFormatException , IOException{
		//Get student information from user and return the number of students. Any existing assignments and rankings are erased
		// takes as argument an array list of student objects
		// returns the size of the array
		
		// initialize variables to hold the student's GPA, extracurricular score, name, and number of students the user is entering
		int x,ES; // number of students and extracurricular score
		double GPA; // GPA
		String name; // name
		
		// print a new line
		System.out.print("\n");
		
		// call the get integer function to get an integer for the number of students the user wants to add
		x = getInteger("Number of students to add: ", 0, Integer.MAX_VALUE);
		
		// go through a for loop x amount of times to get x amount of students from the user
		for (int i = 0; i < x; i++) {
			// print the number of the student
			System.out.print("\nStudent " + Integer.toString(S.size()+1) + ":\n");
			// ask for the name of the student
			System.out.print("Name: ");
			name = input.readLine();
			// call the getDouble function to get a double from the user for the value of the current student's GPA
			GPA = getDouble("GPA: ", 0.0, 4.0);
			// call the get integer function to get an integer from the user for the value of the current student's ES
			ES = getInteger("Extracurricular score: ", 0, 5);
			
			// create a new student object with the given inputs
			Student st = new Student(name,GPA,ES,20);
			// add the student with the given inputs to the arrayList of student objects
			S.add(st);			
		}
		
		// print a new line
		System.out.print("\n");
		// return the size of the arrayList of student objects
		return S.size();
	}

	public static int getSchools(ArrayList<School> H) throws NumberFormatException , IOException {
		//Get school information from user and return the number of schools. Any existing assignments and rankings are erased.
		// takes as argument an arrayList of school objects
		// returns the size of the arrayList
		
		// initialize variables
		int x; // number of schools
		double GPA; // GPA of the school
		String name; // name of the school
		
		// print a new line
		System.out.print("\n");
		
		// call the get integer function to get an integer for the number of schools the user wants to add
		x = getInteger("Number of schools to add: ", 0, Integer.MAX_VALUE);
		
		// go through a for loop x amount of times to get x amount of schools from the user
		for (int i = 0; i < x; i++) {
			// print the number of the school
			System.out.print("\nSchool " + Integer.toString(H.size()+1) + ":\n");
			// ask for the name of the school
			System.out.print("Name: ");
			name = input.readLine();
			// call the getDouble function to get the GPA value for this particular school
			GPA = getDouble("GPA weight: ", 0.0, 1.0);
			
			// create a new school object with the given inputs
			School sh = new School(name,GPA,20);
			// add the school with the given inputs to the arrayList of school objects
			H.add(sh);
		}
		
		// print a new line
		System.out.print("\n");
		// return the size of the arrayList of school objects
		return H.size();
	}
		
	public static boolean assignRankings(ArrayList<Student> S, ArrayList<School> H, int nStudents, int nSchools) {
		//Get each student’s school rankings and calculate each school’s rankings of students, and return whether or not ranking happened.
		// takes as arguments an arrayList of student objects, an arrayList of school objects, the number of schools, and students
		// returns if ranking has successfully occurred or not
		
		// initialize variable for if ranking has successfully occurred or not
		boolean results;
		
		// if there are no students
		if (nStudents == 0) {
			// print this error message
			System.out.println("\nERROR: No students are loaded!");
			// set results as false and print a new line
			results = false;
			System.out.print("\n");
		}
		
		// if there are no schools
		else if (nSchools == 0) {
			// print this error message
			System.out.println("\nERROR: No schools are loaded!");
			// set results as false and print a new line
			results = false;
			System.out.print("\n");
		}
		 // if there are schools and students
		else {
			// loop through each student
			for (int i = 0; i < nStudents; i++) {
				// print a new line
				System.out.print("\n");
				// call the edit rankings method from the student class for each student the loop goes through
				S.get(i).editRankings(H);
			}
			
			// print a new line
			System.out.print("\n");
			
			// loop though the schools
			for (int i = 0; i < nSchools; i++) {
				// call the calcRankings method from the school class on each school the loop goes though
				H.get(i).calcRankings(S);
				
			}
			// set the result of assigning rankings to true
			results = true;
		}
		// return the boolean variable results
		return results;
	 } 
		
	public static void editData(ArrayList<Student> S, ArrayList<School> H, int nStudents, int nSchools, boolean rankingsSet) throws NumberFormatException , IOException {
		//Sub-area menu to edit students and schools.
		// takes as arguments an arrayList of student objects, an arrayList of school objects, the number of schools, and students, and a boolean of if rankings are set or not
		//returns nothing
		
		// Initialize variables
		String edit = ""; // user's input choice
		boolean steps = true; // variable to keep the while loop going
		
		// print new line
		System.out.print("\n");
		
		// use a while loop to continuously display the editing menu until the use quits
		while(steps) {
			
			// print the menu choices for the user
			System.out.println("Edit data\n" + "---------\n" + "S - Edit students\n" + "H - Edit high schools\n" + "Q - Quit\n");
			
			//ask them for input
			System.out.print("Enter choice: ");
			edit = input.readLine();
			
			// if the choice is high schools, call the edit schools function
			if (edit.equals("h") || edit.equals("H")) {
				editSchools(S,H,nSchools,rankingsSet);
			}
			
			// if the choice is to quit, set steps = false and the loop will break
			else if (edit.equals("q") || edit.equals("Q")) {
				steps = false;
			}
			
			// if the choice is students, call the edit students function
			else if (edit.equals("s") || edit.equals("S")) {
				editStudents(S,H,nStudents,rankingsSet);
			}
			
			// otherwise, print the following error
			else {
				System.out.println("\nERROR: Invalid menu choice!");
			}
			
			// print a new line
			System.out.print("\n");
		}
		
	}
	
	 public static void editStudents(ArrayList<Student> S, ArrayList<School> H, int nStudents, boolean rankingsSet) throws NumberFormatException , IOException {
		 //Sub-area to edit students. The edited student’s regret is updated if needed. Any existing school rankings and regrets are re-calculated after editing a student.
		 // takes as arguments an arrayList of student objects, an arrayList of school objects, the number of schools, and students, and a boolean of if rankings are set or not
		 //returns nothing
		 
		 // initialize variables
		 int value = 0; // # of which student to edit
		 boolean steps = true; // variable to keep the while loop going
		 int x = 0; // # of spaces to add
		 String spaceString1; // strings to contain spaces for formating
		 spaceString1 = " ";
		 
		 // if there are no students, print the following error
		 if (nStudents == 0) {
			 System.out.print("\nERROR: No students are loaded!\n");
		 }
		 
		 //otherwise
		 else {
			 //print a new line
			 System.out.print("\n");
			 
			 // use a while loop to display the students edit table
			 while(steps) {
				 
				// print table format
				System.out.print(" #  Name                            GPA  ES  Assigned school            Preferred school order");
				System.out.print("\n----------------------------------------------------------------------------------------------\n");
				
				// loop through the students
			 	for (int i = 0 ; i < S.size() ; i++) {
			 		// print the # of the student
			 		if ( (i+1) < 10) {
			 			// if the student # is less than 10 add a space at the front, other wise just print the 2 digit #
			 			System.out.print(" " + Integer.toString(i+1) + ". " + S.get(i).getName());
			 		}
			 		else {
			 			System.out.print(Integer.toString(i+1) + ". " + S.get(i).getName());
			 		}
			 		// find the # of spaces needed after the name
					x = 31 - (S.get(i).getName()).length();
					// if the # of spaces needed is less than 4, then print 4 spaces after the name
					if (x < 4) {
						spaceString1 = spaceString1.repeat(4);
					}
					// otherwise print x spaces after the name
					else {
						spaceString1 = spaceString1.repeat(x);
					}
					// print the spacing format
					System.out.print(spaceString1);
			 		// call the print method in the student class for each student object as the loop loops
			 		S.get(i).print(H,rankingsSet);
			 		// reset formating variables
			 		x = 0;
			 		spaceString1 = " ";
			 		
			 	}
			 	
			 	
			 	// print table format
				System.out.print("----------------------------------------------------------------------------------------------\n");
				
				// call the get integer function to get the # of which student to change
				value = getInteger("Enter student (0 to quit): ", 0, S.size());
				
				// if value is equal to 0, steps is set to false which will break the loop
				if (value == 0) {
					steps = false;
				}
				
				// otherwise
				else {
					// call the edit info method from the student class on the chosen student
					S.get(value-1).editInfo(H, rankingsSet);
					// loop though each school and call the calcRankings method from the school class to  update the rankings the schools give the students
					for (int i = 0 ; i < H.size() ; i++) {
						H.get(i).calcRankings(S);
					}
					
					// print a new line
					System.out.print("\n");

				}
					
			}
		 }
		 
		 
	 }
	 
	 public static void editSchools(ArrayList<Student> S, ArrayList<School> H, int nSchools, boolean rankingsSet) throws NumberFormatException , IOException {
		 //Sub-area to edit schools. Any existing rankings and regret for the edited school are updated.
		 //takes as arguments an arrayList of student objects, an arrayList of school objects, the number of schools, and students, and a boolean of if rankings are set or not
		 //returns nothing
		 
		 // initialize variables
		 int value = 0; // # of which school to edit
		 boolean steps = true; // variable to keep the while loop going
		 String spaceString1; // strings to contain spaces for formating
		 int x = 0; // # of spaces to add
		 spaceString1 = " ";
		 
		 //print a new line
		 System.out.print("\n");
		 
		 // if there are no schools, print the following error
		 if (nSchools == 0) {
			 System.out.print("ERROR: No schools are loaded!\n");
		 }
		 
		 //otherwise
		 else {
			 // use a while loop to display the schools edit table
			 while(steps) {
				 
				// print table format
				System.out.println(" #  Name                          Weight  Assigned student           Preferred student order");
				System.out.println("--------------------------------------------------------------------------------------------");
				
				// loop through the schools
				for (int i = 0 ; i < H.size() ; i++) {
					// print the # of the school
					if ((i+1) < 10) {
						// if the school # is less than 10 add a space at the front, other wise just print the 2 digit #
						System.out.print(" " + Integer.toString(i+1) + ". " + H.get(i).getName());
					}
					else {
						System.out.print(Integer.toString(i+1) + ". " + H.get(i).getName());
					}
				 	// find the # of spaces needed after the name
					x = 32 - ((H.get(i).getName()).length());
					
					// if the # of spaces needed is less than 5, then print 4 spaces after the name
					if (x < 5) {
						spaceString1 = spaceString1.repeat(5);
					}
					// otherwise print x spaces after the name
					else {
						spaceString1 = spaceString1.repeat(x);
					}
					// print the spacing format
					System.out.print(spaceString1);
				 	// call the method print in the school class on each school
				 	H.get(i).print(S,rankingsSet);
				 	// reset formating variables
			 		x = 0;
			 		spaceString1 = " ";
				}
			 	
			 	// print table format
				System.out.print("--------------------------------------------------------------------------------------------\n");
				
				// call the get integer function to get the # of which school to change
				value = getInteger("Enter school (0 to quit): ", 0, H.size());
				
				// if value is equal to 0, steps is set to false which will break the loop
				if (value == 0) {
					steps = false;
				}
				
				// otherwise
				else {
					// call the edit info method from the school class for the chosen school
					H.get(value-1).editInfo(S, rankingsSet);
					// loop though each school and call the calcRankings method from the school class to  update the rankings the schools give the students
					for (int i = 0 ; i < H.size() ; i++) {
						H.get(i).calcRankings(S);
					}
					
					// print a new line
					System.out.print("\n");						
				}
					
			}
		 }
		 
	 }
	 
		 public static void printStudents(ArrayList<Student> S, ArrayList<School> H, int nStudents, boolean rankingsSet) {
			 //Print students to the screen, including matched school (if one exists).
			 //takes as arguments an arrayList of student objects, an arrayList of school objects, the number of students, and a boolean of if rankings are set or not
			 //returns nothing
			 
			 // initialize variables
			 int x = 0; // # of spaces to add
			 String spaceString1; // strings to contain spaces for formating
			 spaceString1 = " ";
					 
			 // if there are no students, print the following error message
			 if (nStudents == 0) {
				 System.out.println("\nERROR: No students are loaded!\n");
			 }
			 
			 // otherwise
			 else {
				// print title students
				System.out.println("\nSTUDENTS:");
				 
				// print table format
				System.out.println("\n #  Name                            GPA  ES  Assigned school            Preferred school order");
				System.out.println("----------------------------------------------------------------------------------------------");
				
				// loop through the students
			 	for (int i = 0 ; i < S.size() ; i++) {
			 		// print the # of the student
			 		if ( (i+1) < 10) {
			 			// if the student # is less than 10 add a space at the front, other wise just print the 2 digit #
			 			System.out.print(" " + Integer.toString(i+1) + ". " + S.get(i).getName());
			 		}
			 		else {
			 			System.out.print(Integer.toString(i+1) + ". " + S.get(i).getName());
			 		}
			 		// find the # of spaces needed after the name
					x = 31 - (S.get(i).getName()).length();
					// if the # of spaces needed is less than 4, then print 4 spaces after the name
					if (x < 4) {
						spaceString1 = spaceString1.repeat(4);
					}
					// otherwise print x spaces after the name
					else {
						spaceString1 = spaceString1.repeat(x);
					}
					// print the spacing format
					System.out.print(spaceString1);
			 		// call the print method in the student class for each student object as the loop loops
			 		S.get(i).print(H,rankingsSet);
			 		// reset formating variables
			 		x = 0;
			 		spaceString1 = " ";
			 		
			 	}
			 	
			 	// print table format
				System.out.println("----------------------------------------------------------------------------------------------\n");
			 
			}
			 
		 }
		 
		 public static void printSchools(ArrayList<Student> S, ArrayList<School> H, int nSchools, boolean rankingsSet) {
			 //Print schools to the screen, including matched student (if one exists).
			 //takes as arguments an arrayList of student objects, an arrayList of school objects, the number of schools, and a boolean of if rankings are set or not
			 //returns nothing
			 
			 // initialize variables
			 String spaceString1; // strings to contain spaces for formating
			 int x = 0; // # of spaces to add
			 spaceString1 = " ";
			 
			 // if there are no schools, print the following error message
			 if (nSchools == 0) {
				 System.out.println("ERROR: No schools are loaded!\n");
			 }
			 
			 // otherwise
			 else {
				// print title schools
				System.out.println("SCHOOLS:");
				
				// print table format
				System.out.println("\n #  Name                          Weight  Assigned student           Preferred student order");
				System.out.println("--------------------------------------------------------------------------------------------");
				
				// loop through the schools
				for (int i = 0 ; i < H.size() ; i++) {
					// print the # of the school
					if ((i+1) < 10) {
						// if the school # is less than 10 add a space at the front, other wise just print the 2 digit #
						System.out.print(" " + Integer.toString(i+1) + ". " + H.get(i).getName());
					}
					else {
						System.out.print(Integer.toString(i+1) + ". " + H.get(i).getName());
					}
				 	// find the # of spaces needed after the name
					x = 32 - ((H.get(i).getName()).length());
					
					// if the # of spaces needed is less than 5, then print 4 spaces after the name
					if (x < 5) {
						spaceString1 = spaceString1.repeat(5);
					}
					// otherwise print x spaces after the name
					else {
						spaceString1 = spaceString1.repeat(x);
					}
					// print the spacing format
					System.out.print(spaceString1);
				 	// call the method print in the school class on each school
				 	H.get(i).print(S,rankingsSet);
				 	// reset formating variables
			 		x = 0;
			 		spaceString1 = " ";
				}
				
				// print table format
				System.out.println("--------------------------------------------------------------------------------------------\n");
			 
			}	
			 
		 }

		 public static boolean match(ArrayList<Student> S, ArrayList<School> H, int nStudents, int nSchools, boolean rankingsSet) {
			 //Match students and schools, and return whether or not matching happened.
			 //takes as arguments an arrayList of student objects, an arrayList of school objects, the number of schools, and students, and a boolean of if rankings are set or not
			 //returns a boolean stating if a match has occurred or not
			 
			 // initialize variables
			 ArrayList<Integer> IDs = new ArrayList<Integer>(); // arrayList of #s that have already been matched in order to avoid double matching
			 int x = 0; // # of the school student is matched with
			 boolean proceed = true; // variable to keep the while loop going
			 
			 // if rankings have been set
			 if (rankingsSet == true){
				 
				 // print a new line
				 System.out.print("\n");
				 
				 // loop though the students
				 for (int i = 0 ; i < S.size() ; i++) {
					 // use a do while loop to get valid matching numbers
					 do {
						 // call the get integer function to get the # of the school to match the student with
						 x = getInteger("Enter school index for student " + Integer.toString(1+i) + " (" + S.get(i).getName() + "): ",1,S.size());
						 
						 // if x is already in the arrayList of matched schools, then print the following error and set proceed to true making the loop continue
						 if (IDs.contains(x)) {
							 System.out.println("ERROR: School " + Integer.toString(x) + " is already matched!\n");
							 proceed = true;
						 }
						 
						 // if school x has not been assigned yet
						 else {
							 // add x to the arrayList of #s of matched schools
							 IDs.add(x);
							 // set the index of the school of the assigned current student to its school attribute by calling the setSchool method in the student class
							 S.get(i).setSchool(x-1);
							 // set the index of the student of the the school it was assigned to, to its student attribute by calling the setStudent method in the school class
							 H.get(x-1).setStudent(i);
							 // set proceed to false to break the loop
							 proceed = false;
							 // break out of the loop
							 break;
						 }
						 
						 // loop will only continue while proceed is true
					 } while (proceed);
				 
				 }
				 // print a new line
				 System.out.print("\n");
				 // return that matching has occurred
				 return true;
			 }
			 
			 // otherwise return false
			 else {
				 return false;
			 }
		 }
		 
		 
		 public static void displayMatches(ArrayList<Student> S, ArrayList<School> H, int nStudents, int nSchools) {
			 //Display matches and statistics. 
			 //takes as arguments an arrayList of student objects, an arrayList of school objects, the number of schools, and students
			 //returns nothing
			 
			 // initialize variables
			 boolean stable = true; // if the match is stable or not
			 double avgStudentRegret = 0, avgSchoolRegret = 0, avgTotalRegret = 0, studentSum = 0, schoolSum = 0, peopleSum = 0; // averages and sums of each category
			 String stableMatch = ""; // empty sting for stable match result; yes p\or no
			 int rank = 0; // rank of school a student had assigned to them
			 
			 // print matches format
			 System.out.println("\nMatches:");
			 System.out.println("--------");
			 
			 // loop through each choose and print the student they where matched with
			 for (int i = 0 ; i < H.size(); i++) {
				 System.out.println(H.get(i).getName() + ": " + S.get(H.get(i).getStudent()).getName());
			 }
			 
			 // loop through the students and sum up their total regret
			 for (int j = 0 ; j < S.size(); j++) {
				 // find the regret by doing the ranks of the assigned school - 1
				 studentSum += S.get(j).findRankingByID(S.get(j).getSchool()) - 1;
			 }
			 // find the average regret for students by dividing the total by the # of students
			 avgStudentRegret = studentSum / nStudents;
			 
			 // loop through the schools and sum up their total regret
			 for (int k = 0 ; k < H.size(); k++) {
				// find the regret by doing the ranks of the assigned student - 1
				 schoolSum += H.get(k).findRankingByID(H.get(k).getStudent()) - 1;
			 }
			 // find the average regret for schools by dividing the total by the # of schools
			 avgSchoolRegret = schoolSum / nSchools;
			 
			 // find the total # of ppl
			 peopleSum = studentSum + schoolSum;
			 
			 // find the total regret
			 avgTotalRegret = peopleSum/(nStudents+nSchools);
			 
			 // to find if the matching is stable or not
			 
			 // loop through the students
			 for (int i = 0 ; i < S.size() ; i++) {
				 // see if the student's first choice of school also ranked this student 1st
				 if (H.get(S.get(i).getIndexOfRank(1)).findRankingByID(i) == 1) {
					 // if they are each other's first choices, then check if they where matched
					 if (S.get(i).getIndexOfRank(1) == S.get(i).getSchool()) {
						 // if they where matched the match is stable as they where both each other's first choices, so they should end up together
					 	stable = true;
					 }
					 // if they where not matched this makes the matching unstable as they would both rather be with each other than the other school/student they ended up with
					 else {
						 // stable becomes false
						 stable =  false;
						 // break out of this loop because the whole matching is unstable
						 break;
					 }
				 }
				 //// if they are not each other's first choices
				 else {
					 // find the rank that the given student assigned to the school it was matched with
					 rank = S.get(i).findRankingByID(S.get(i).getSchool());
					 // loop through the schools that it ranked above the school it was matched with
					 for (int j = 1 ; j < rank ; j++) {
						 // if the school ranked the given student lower than the student they where matched with
						 if (H.get(S.get(i).getIndexOfRank(j)).findRankingByID(H.get(S.get(i).getIndexOfRank(j)).getStudent()) < H.get(S.get(i).getIndexOfRank(j)).findRankingByID(i)) {
							 // the matching is still stable and looping continues for the next school
							 stable = true;
						 }
						// otherwise, the school ranked the given student higher than the student they where matched with
						 else {
							 // this means the given student and the school would both rather be paired with each other rather than who they where matched with
							 // loop is broken since the matching is unstable
							 stable = false;
							 break;
						 }
					 }
				// if at the end of the first student's loop the match is unstable, break out of the loop, no need to keep going
				if (stable == false) {
					break;
				}
				 }
			 }
			 
			 // if stable is true, print yes
			 if (stable == true) {
				 stableMatch = "Yes";
			 }
			 // otherwise, it is not stable, print no
			 else {
				 stableMatch = "No";
			 }
			 // print out the results of stability and all the averages
			 System.out.println("\nStable matching? " + stableMatch);
			 System.out.format("Average student regret: %.2f", avgStudentRegret);
			 System.out.format("\nAverage school regret: %.2f", avgSchoolRegret);
			 System.out.format("\nAverage total regret: %.2f", avgTotalRegret);
			 // print a new line
			 System.out.println("\n");
			 
		 }
		 
		 public static boolean matchingCanProceed(int nStudents, int nSchools, boolean rankingsSet) {
			 //Check that the conditions to proceed with matching are satisfied.
			 //takes in the number of schools, and students, and a boolean of if rankings are set or not
			 // returns a boolean stating is a matching can proceed of not
			 
			 // if there are no students, print the following error and return false
			 if (nStudents == 0 ) {
				 System.out.println("\nERROR: No students are loaded!\n");
				 return false;
			 }
			 
			 // if there are no schools, print the following error and return false
			 else if (nSchools == 0) {
				 System.out.println("\nERROR: No schools are loaded!\n");
				 return false;
			 }
			 
			 // if the number of students and schools are not equal, print the following error and return false
			 else if (nStudents != nSchools) {
				 System.out.println("\nERROR: The number of students and schools must be equal!\n");
				 return false;
			 }
			 
			 // if the rankings are not set, print the following error and return false
			 else if (rankingsSet == false) {
				 System.out.println("\nERROR: Student and school rankings must be set before matching!\n");
				 return false;
			 }
			 // otherwise, if none of the above conditions apply, return true
			 else {
				 return true;
			 }
			 
			 
		 }
		 
	 public static int getInteger(String prompt, int LB, int UB) {
			// this function asks the user to input an integer
			// it returns an integer and takes as argument a string to ask for an integer, as well as upper and lower bounds for the valid integer the function will accept
			// initialize the integer to carry the input, and a boolean to loop the try-catch statement
			int x = 0;
			String num = null;
			boolean allow;
			
			// if the upper bound is equal to the maximum value a double then instead of displaying UB the error message will have the upper bound as infinity
			if (UB == Integer.MAX_VALUE) {
				num = "infinity";
			}
			// otherwise the UB will be converted into a string but will be the number passed as the argument
			else {
				num = String.valueOf(UB);
			}
			
			// use a do-while loop to go through the try catch loop until the allow boolean is false
			do {
				allow = true;
				
				try {
					// use the prompt to ask the user to input an integer
					System.out.print(prompt);
					// take the number the user has entered as variable x
					x = Integer.parseInt(input.readLine());
				}
				
				// catch a number format error
				catch(NumberFormatException e) {
					// print out an error message if this error occurs
					System.out.println("\nERROR: Input must be an integer in ["+LB+", "+num+"]!\n");
					// turn the allow variable false
					allow = false;
				}
				
				// catch a io exception error
				catch(IOException e){
					// print out an error message if this error occurs
					System.out.println("\nERROR: Input must be an integer in ["+LB+", "+num+"]!\n");
					// turn the allow variable false
					allow = false;
				}
				
				// if the allow variable is still true and the integer is out of the bounds passed in the arguments, do the following:
				if ( allow && (x < LB || x > UB)){
					// print out an error message if this error occurs
					System.out.println("\nERROR: Input must be an integer in ["+LB+", "+num+"]!\n");
					// turn the allow variable false
					allow = false;
				}
			
				// the loop will stop when the allow variable is true meaning at the end of the loop the entered integer is valid
			} while (!allow);
			// the function returns a valid integer
			return x;
			}
		
		public static double getDouble(String prompt, double LB, double UB) {
			// this function will ask the user to input a double value
			// it takes as arguments a string to ask for the double, and upper and lower bounds that will restrict what a valid double is
			// initialize the double variable to carry the input, and string and boolean variables
			double x = 0;
			String num = null;
			boolean allow;
			
			// if the upper bound is equal to the maximum value a double then instead of displaying UB the error message will have the upper bound as infinity
			if (UB == Double.MAX_VALUE) {
				num = "infinity";
			}
			// otherwise the UB will be converted into a string but will be the number passed as the argument
			else {
				num = String.valueOf(UB) + "0";
			}
			
			// use a do-while loop to get a valid double
			do {
				// make the allow variable true
				allow = true;
				
				try {
					// use the prompt to ask for a double
					System.out.print(prompt);
					// take the double entered by the user into variable x
					x = Double.parseDouble(input.readLine());
				}
				
				// catch a number format error
				catch(NumberFormatException e) {
					// print out an error message if this error occurs
					System.out.format("\nERROR: Input must be a real number in [%.2f, "+num+"]!\n\n",LB);
					// turn the allow variable false
					allow = false;
				}
				
				// catch a io exception error
				catch(IOException e){
					// print out an error message if this error occurs
					System.out.format("\nERROR: Input must be a real number in [%.2f, "+num+"]!\n\n",LB);
					// turn the allow variable false
					allow = false;
				}
				
				// if the allow variable is still true and the double is out of the bounds passed in the arguments, do the following:
				if ( allow && (x < LB || x > UB)){
					System.out.format("\nERROR: Input must be a real number in [%.2f, "+num+"]!\n\n",LB);
					// turn the allow variable false
					allow = false;
				}
				
				// the loop will stop when the allow variable is true meaning at the end of the loop the entered double is valid
			} while (!allow);
			// the function returns a valid double
			return x;
		}


	
}


