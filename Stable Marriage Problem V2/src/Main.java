import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
	
	public static BufferedReader input = new BufferedReader( new InputStreamReader( System.in ));
	//private static SMPSolver problem;

	// global buffer reader used to collect inputs from user and can be accessed from anywhere in the main class, and referenced from any other class
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		// main function that runs the options of the program based on the choices of the users
		
		ArrayList<Student> studentList = new ArrayList<Student>(2000);
		// create an ArrayList that will hold student objects
		ArrayList<School> schoolList = new ArrayList<School>(2000);
		// create an ArrayList that will hold school objects
		
		SMPSolver problem = new SMPSolver(studentList,schoolList);
		//SMPSolver problem  = new SMPSolver();
		
		// declare string choice to hold the input of the user's choice
		String choice = "";
		// initialize the amount of schools and students to 0
		int studentNum = 0, schoolNum = 0, addedSchools = 0, addedStudents = 0;
		// initialize boolean variables to keep track of if rankings have been assigned, if matching is possible, if matching is possible, if user choice is valid, if the try catch loop should keep going
		boolean assign = false, match = false, ranksReturn = false, I = false, allow;
		// create an array of acceptable inputs from the user
		String[] inputs = {"L","E","P","M","D","R","Q","l","e","p","m","d","r","q"};
		
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
				case ("L"):
				case ("l"):
					addedSchools = loadSchools(schoolList);
					if (addedSchools != 0 ) {
						studentList.clear();	
					}
					schoolNum += addedSchools;
				
					studentNum += loadStudents(studentList,schoolList);
					//problem = new SMPSolver(studentList,schoolList);
					break;
				case ("E"):
				case ("e"):
					// if the choice is to edit the already existing students and schools, call the edit data function
					editData(studentList,schoolList);
					// print a new line
					System.out.print("\n");
					break;
				case ("P"):
				case ("p"):
					// if the choice is to print the current students and schools, call the print student and print school functions
					printStudents(studentList,schoolList);
					printSchools(studentList,schoolList);
					break;
				case ("M"):
				case ("m"):
					if (studentList.size() == 0) {
						System.out.print("ERROR: No suitors are loaded!\n");
					}
					else if (schoolList.size() == 0) {
						System.out.print("ERROR: No receivers are loaded!\n");
					}
					else {
						//SMPSolver problem = new SMPSolver(studentList,schoolList);
						//problem.match();
						problem.reset(studentList,schoolList);
						//problem.setSuitorAndReceiverArrayList(studentList,schoolList);
						problem.print();
						System.out.println(Integer.toString(studentList.size()) + " matches made in " + problem.getElapsedTime() + "ms!\n");
					}
					// if the choice is to match students with schools, call the matchingCanProceed function to see if matching is possible
//					match = matchingCanProceed(studentNum,schoolNum,assign);
//					// if match returns true, proceed with matching by calling the match function
//					if (match == true) {
//						ranksReturn = match(studentList,schoolList,studentNum,schoolNum,assign);
//					}
					break;
				case ("D"):
				case ("d"):
					if (problem.matchesExist() == false) {
						System.out.print("ERROR: No matches exist!\n");
					}
					else {
						problem.reset(studentList,schoolList);
						//problem.setSuitorAndReceiverArrayList(studentList,schoolList);
						problem.match();
						problem.printMatches();
						problem.print();
					}
//					//if the choice is to display the matches and statistics, call the display matches function if there are matches made
//					if (ranksReturn) {
//						displayMatches(studentList,schoolList,studentNum,schoolNum);
//					}
//					else {
//						// otherwise print that there are no matches made
//						System.out.println("\nERROR: No matches exist!\n");
//					}
					break;
				case ("R"):
				case ("r"):
					// if the choice is r, clear the arrays holding the students and school objects, set the number of students and schools back to zero
					// turn the boolean variables for assigning ranks, allowing matching students with schools, and for the actual matching being completed
					studentList.clear();
					schoolList.clear();
					problem.reset(studentList,schoolList);
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
					System.out.println("\nArrivederci!");
					System.exit(0);
			}
		}
				
	}
	public static void displayMenu() {
		// this function displays the menu of options, and the name of the game
		//it takes no arguments and returns nothing
		System.out.print("JAVA STABLE MARRIAGE PROBLEM v2\n");
		
		System.out.println("\nL - Load students and schools from file\nE - Edit students and schools\nP - Print students and schools\nM - Match students and schools using Gale-Shapley algorithm\nD - Display matches and statistics\nR - Reset database\nQ - Quit");
		
	}
	
	public static int loadStudents(ArrayList<Student> S, ArrayList<School> H) throws IOException, NumberFormatException {
		BufferedReader fileInput; 
		int numStudentsLoaded = 0, loadingAttempts = 0, ES, rankHolder;
		Double GPA;
		String studentFileName, line, studentName;
		boolean incorrectFileName = true, uniqueRanks, rankRanges;
		
		while (incorrectFileName) {
			System.out.print("Enter student file name (0 to cancel): ");
			studentFileName =  input.readLine();
			if (studentFileName.equals("0")) {
				System.out.print("\nFile loading process canceled.\n\n");
				break;
			}
			else {
				File schoolFile = new File(studentFileName);
				if (schoolFile.exists()) {
					fileInput = new BufferedReader (new FileReader ( studentFileName ) ) ;
					do {
						line = fileInput.readLine () ;
						if ( line != null ) {
//							uniqueRanks = true;
//							rankRanges = false;
							loadingAttempts ++;
							String [] splitString = line.split(",") ;
							if ((splitString.length == H.size() + 3) && (splitString.length - 3 == H.size())) {
								studentName = splitString[0];
								GPA = Double.parseDouble(splitString [1]);
								ES = Integer.parseInt(splitString [2]);
								// create a new school object with the given inputs
								Student st = new Student(studentName,GPA,ES,H.size());
								for (int i = 0; i <= H.size() - 1 ; i++) {
									st.setRanking(i, Integer.parseInt(splitString[i+3]));
								}
//								for (int i = 3; i < splitString.length - 1 ; i++) {
//									rankHolder = Integer.parseInt(splitString[i]);
//									for (int j = i+1; j < splitString.length ; j++) {
//										if (Integer.parseInt(splitString[j]) == rankHolder) {
//											uniqueRanks = false;
//											//loadingAttempts --;
//											break;
//										}
//									}
//									if (uniqueRanks == false) {
//										break;
//									}
// 								}
//								for (int i = 3; i <= splitString.length - 1 ; i++) {
//									if (1 <= Integer.parseInt(splitString[i]) && Integer.parseInt(splitString[i]) <= H.size()) {
//										rankRanges = true;
//									}
//									else {
//										rankRanges = false;
//									}
//								}
//								if (st.isValid() == true && uniqueRanks == true && rankRanges == true) {
								if (st.isValid() == true) {
									// add the school with the given inputs to the arrayList of school objects
									S.add(st);
//									for (int i = 0; i <= H.size() - 1 ; i++) {
//										st.setRanking(i, Integer.parseInt(splitString[i+3]));
//									}
									numStudentsLoaded ++ ;
								}
							}
						}
					} while ( line != null ) ;
					fileInput.close();
					System.out.print("\n" + numStudentsLoaded + " of " + loadingAttempts + " students loaded!\n\n");
					incorrectFileName = false;
					for (int i = 0; i <= H.size() - 1 ; i++) {
						H.get(i).calcRankings(S);
					}
				}
				else {
					System.out.print("\nERROR: File not found!\n\n");
				}
			}
		
		}
		return numStudentsLoaded;
	}
	
	public static int loadSchools(ArrayList<School> H) throws IOException, NumberFormatException {
		BufferedReader fileInput; 
		int numSchoolsLoaded = 0, loadingAttempts = 0;
		Double alpha;
		String schoolFileName, line,schoolName;
		boolean incorrectFileName = true;
		
		while (incorrectFileName) {
			System.out.print("\nEnter school file name (0 to cancel): ");
			schoolFileName =  input.readLine();
			if (schoolFileName.equals("0")) {
				System.out.print("\nFile loading process canceled.\n\n");
				break;
			}
			else {
				File schoolFile = new File(schoolFileName);
				if (schoolFile.exists()) {
					fileInput = new BufferedReader (new FileReader ( schoolFileName ) ) ;
					do {
						line = fileInput.readLine () ;
						if ( line != null ) {
							loadingAttempts ++;
							String [] splitString = line.split(",") ;
							if (splitString.length == 2) {
								schoolName = splitString[0];
								alpha = Double.parseDouble( splitString [1]) ;
								// create a new school object with the given inputs
								School sh = new School(schoolName,alpha,2000);
								if (sh.isValid() == true) {
									// add the school with the given inputs to the arrayList of school objects
									H.add(sh);
									numSchoolsLoaded ++ ;
								}
							}
						}
					} while ( line != null ) ;
					fileInput.close();
					System.out.print("\n" + numSchoolsLoaded + " of " + loadingAttempts + " schools loaded!\n\n");
					incorrectFileName = false;
				}
				else {
					System.out.print("\nERROR: File not found!\n");
				}
			}
		}
		
		return numSchoolsLoaded;
	}
	public static void editData(ArrayList<Student> S, ArrayList<School> H) throws NumberFormatException , IOException {
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
						editSchools(S,H);
						System.out.print("\n");
					}
					
					// if the choice is to quit, set steps = false and the loop will break
					else if (edit.equals("q") || edit.equals("Q")) {
						steps = false;
					}
					
					// if the choice is students, call the edit students function
					else if (edit.equals("s") || edit.equals("S")) {
						editStudents(S,H);
						System.out.print("\n");
					}
					
					// otherwise, print the following error
					else {
						System.out.println("\nERROR: Invalid menu choice!\n");
					}
					
					// print a new line
					//System.out.print("\n");
				}
	}
	
	public static void editStudents(ArrayList<Student> S, ArrayList<School> H) throws NumberFormatException , IOException  {
		//Sub-area to edit students. The edited student's regret is updated if needed. Any existing school rankings and regrets are re-calculated after editing a student.
		 // takes as arguments an arrayList of student objects, an arrayList of school objects, the number of schools, and students, and a boolean of if rankings are set or not
		 //returns nothing
		 
		 // initialize variables
		 int value = 0; // # of which student to edit
		 boolean steps = true; // variable to keep the while loop going
		 int x = 0; // # of spaces to add
		 String spaceString1; // strings to contain spaces for formating
		 spaceString1 = " ";
		 
		 // if there are no students, print the following error
		 if (S.size() == 0) {
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
			 		S.get(i).print(H);
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
					S.get(value-1).editInfo(H,true);
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
	public static void editSchools(ArrayList<Student> S, ArrayList<School> H) throws NumberFormatException , IOException {
		//Sub-area to edit schools. Any existing rankings and regret for the edited school are updated.
		//takes as arguments an arrayList of student objects, an arrayList of school objects, the number of schools, and students, and a boolean of if rankings are set or not
		//returns nothing
		
		// initialize variables
		int value = 0; // # of which school to edit
		boolean steps = true, rankings; // variable to keep the while loop going
		String spaceString1; // strings to contain spaces for formating
		int x = 0; // # of spaces to add
		spaceString1 = " ";
		
		//print a new line
		System.out.print("\n");
		
		// if there are no schools, print the following error
		if (H.size() == 0) {
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
				if (S.size() == 0) {
					rankings = false;
				}
				else {
					rankings = true;
				}
			 	H.get(i).print(S,rankings);
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
				H.get(value-1).editInfo(S,true);
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
	public static void printStudents(ArrayList<Student> S, ArrayList<School> H) throws NumberFormatException , IOException  {
		//Print students to the screen, including matched school (if one exists).
		//takes as arguments an arrayList of student objects, an arrayList of school objects, the number of students, and a boolean of if rankings are set or not
		//returns nothing
		
		// initialize variables
		int x = 0; // # of spaces to add
		String spaceString1; // strings to contain spaces for formating
		spaceString1 = " ";
			 
		// if there are no students, print the following error message
		if (S.size() == 0) {
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
		 		S.get(i).print(H);
		 		// reset formating variables
		 		x = 0;
		 		spaceString1 = " ";
		 		
		 	}
		 	
		 	// print table format
			System.out.println("----------------------------------------------------------------------------------------------\n");
		 
		}
	}
	public static void printSchools(ArrayList<Student> S, ArrayList<School> H) {
		//Print schools to the screen, including matched student (if one exists).
		 //takes as arguments an arrayList of student objects, an arrayList of school objects, the number of schools, and a boolean of if rankings are set or not
		 //returns nothing
		 
		 // initialize variables
		 String spaceString1; // strings to contain spaces for formating
		 int x = 0; // # of spaces to add
		 spaceString1 = " ";
		 boolean rankings;
		 
		 // if there are no schools, print the following error message
		 if (H.size() == 0) {
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
				
				if (S.size() == 0) {
					rankings = false;
				}
				else {
					rankings = true;
				}
			 	// call the method print in the school class on each school
			 	H.get(i).print(S,rankings);
			 	// reset formating variables
		 		x = 0;
		 		spaceString1 = " ";
			}
			
			// print table format
			System.out.println("--------------------------------------------------------------------------------------------\n");
		 
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
