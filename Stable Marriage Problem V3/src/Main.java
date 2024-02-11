import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
	public static BufferedReader input = new BufferedReader( new InputStreamReader( System.in ));
	// global buffer reader used to collect inputs from user and can be accessed from anywhere in the main class, and referenced from any other class
	public static void main(String[] args) throws NumberFormatException, IOException {
		// main function that runs the options of the program based on the choices of the users
		
		ArrayList<Student> studentList = new ArrayList<Student>(2000);
		// create an ArrayList that will hold student objects
		ArrayList<School> schoolList = new ArrayList<School>(2000);
		// create an ArrayList that will hold school objects
		ArrayList<Student> studentList2 = new ArrayList<Student>(2000);
		// create an ArrayList that will hold copies of student objects
		ArrayList<School> schoolList2 = new ArrayList<School>(2000);
		// create an ArrayList that will hold copies of school objects
		
		SMPSolver studentsFirst = new SMPSolver();
		SMPSolver schoolsFirst = new SMPSolver();
		//create SMPSolver objects for suitor and recivers
		
		// declare string choice to hold the input of the user's choice
		String choice = "", statementString = "";
		// initialize the amount of schools and students to 0
		int studentNum = 0, schoolNum = 0, addedSchools = 0, addedStudents = 0;
		// initialize boolean variables to keep track of  if user choice is valid, if the try catch loop should keep going
		boolean I = false, allow;
		// create an array of acceptable inputs from the user
		String[] inputs = {"L","E","P","M","D","X","R","Q","l","e","p","m","d","x","r","q"};
		
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
					// add schools by calling the load schools function
					addedSchools = loadSchools(schoolList);
					// if schools are added clear the students array, and reset matches
					if (addedSchools != 0 ) {
						resetMatches(studentList,schoolList);
						studentList.clear();	
					}
					// add the number of loaded students to the number of schools
					schoolNum += addedSchools;
					// add students by calling the load students function
					addedStudents = loadStudents(studentList,schoolList);
					// add the amount to the total amount of students
					studentNum += addedStudents;
					// if no students have been added, set the school matches to zero
					if (addedStudents != 0) {
						for (int i = 0; i < schoolList.size(); i++) {
							for (int j = 0; j < schoolList.get(i).getMaxMatches(); j++) {
								schoolList.get(i).setMatch(j);
							}
						}
					}
					break;
				case ("E"):
				case ("e"):
					// if the choice is to edit the already existing students and schools, call the edit data function
					editData(studentList,schoolList);
					// copy the schools and students array to fill the duplicates that will be used for school optimal matching
					schoolList2 = copySchools(schoolList);
					studentList2 = copyStudents(studentList);
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
					// Consider both schools first and students first for matching
					// reset the student optimal smp object
					studentsFirst.reset();
					// rest the computation time for this object
					studentsFirst.setTime(-1);
					// reset the school optimal smp object
					schoolsFirst.reset();
					// rest the computation time for this object
					schoolsFirst.setTime(-1);
					// copy the schools and students array to fill the duplicates that will be used for school optimal matching
					schoolList2 = copySchools(schoolList);
					studentList2 = copyStudents(studentList);
					// set the participants, the student and school object array lists, for the two smp objects
					studentsFirst.setParticipants(studentList,schoolList);
					schoolsFirst.setParticipants(schoolList2,studentList2);
					
					// print the title for the type of match
					System.out.println("\nSTUDENT-OPTIMAL MATCHING");
					// check if matching can proceed for the student optimal option 
					if (studentsFirst.matchingCanProceed()) {
						// reset the matches of the array lists for student and school objects
						resetMatches(studentList,schoolList);
						// reset the student optimal smp object
						studentsFirst.reset();
						// rest the computation time for this object
						studentsFirst.setTime(-1);
						// set the participants, the student and school object array lists, for the student optimal smp object
						studentsFirst.setParticipants(studentList,schoolList);
						// set that existing matches are true
						studentsFirst.setMatchesExist(true);
						// call the print method that matches the suitors with the receivers and prints the stability and the regret
						studentsFirst.match();
						studentsFirst.print();
						// print the amount of time it took to complete the student optimal matches
						if (studentsFirst.matchesExist() == true) {
							System.out.println(Integer.toString(studentList.size()) + " matches made in " + studentsFirst.getTime() + "ms!");
						}
						// set that the data of the student and school object array lists' have been edited to false as they have bee correctly matched and all values they contain are up to date
						for (int i = 0 ; i < studentList.size() ; i++) {
							studentList.get(i).setIfDataEdited(false);
						}
						for (int i = 0 ; i < schoolList.size() ; i++) {
							schoolList.get(i).setIfDataEdited(false);
						}
					}
					
					// print the title for the type of match
					System.out.println("\nSCHOOL-OPTIMAL MATCHING");
					// check if matching can proceed for the student optimal option
					if (schoolsFirst.matchingCanProceed()) {
						// copy the schools and students array to fill the duplicates that will be used for school optimal matching
						schoolList2 = copySchools(schoolList);
						studentList2 = copyStudents(studentList);
						// reset the matches of the duplicate array lists for student and school objects
						resetMatches(studentList2,schoolList2);
						// reset the school optimal smp object
						schoolsFirst.reset();
						// rest the computation time for this object
						schoolsFirst.setTime(-1);
						// set the participants for smp object with high schools as suitors
						schoolsFirst.setParticipants(schoolList2,studentList2);
						// set that existing matches are true
						schoolsFirst.setMatchesExist(true);
						// call the print method that matches the suitors with the receivers and prints the stability and the regret
						schoolsFirst.match();
						schoolsFirst.print();
						
						// print the amount of time it took to complete the student optimal matches
						if (schoolsFirst.matchesExist() == true) {
							System.out.println(Integer.toString(studentList2.size()) + " matches made in " + schoolsFirst.getTime() + "ms!");
						}
						// set that the data of the duplicate student and school object array lists' have been edited to false as they have bee correctly matched and all values they contain are up to date
						for (int i = 0 ; i < studentList2.size() ; i++) {
							studentList2.get(i).setIfDataEdited(false);
						}
						for (int i = 0 ; i < schoolList2.size() ; i++) {
							schoolList2.get(i).setIfDataEdited(false);
						}
					}
					// print a new line
					System.out.print("\n");
					break;
				case ("D"):
				case ("d"):
					// if the user chooses to display the statistics
					// print the title for the type of match displayed
					System.out.println("\nSTUDENT-OPTIMAL SOLUTION");
					// if matches don't exist, or there have been edits made to the data without matching then print matches don't exist
					if (studentsFirst.matchesExist() == false || studentList.get(0).getIfDataEdited() == true || schoolList.get(0).getIfDataEdited() == true) {
						System.out.println("\nERROR: No matches exist!");
					}
					else {
						// if there are matches exist
						// call the match method for the SMPSolver object then the print matches method and the print method to display result						
						studentsFirst.printMatches();
						studentsFirst.print();
					}
					
					// if matches don't exist, or there have been edits made to the data without matching then print matches don't exist
					if (schoolsFirst.matchesExist() == false || studentList2.get(0).getIfDataEdited() == true || schoolList2.get(0).getIfDataEdited() == true) {
						// print the title for the type of match displayed
						System.out.println("\nSCHOOL-OPTIMAL SOLUTION");
						System.out.print("\nERROR: No matches exist!\n\n");
					}
					
					else {
						// print the title for the type of match displayed
						System.out.println("SCHOOL-OPTIMAL SOLUTION");
						// copy the schools and students array to fill the duplicates that will be used for school optimal matching
						schoolList2 = copySchools(schoolList);
						studentList2 = copyStudents(studentList);
						// clear and reset the matches array, the max matches and the edited data boolean of the duplicate array to the values form the original array of student objects
						for (int i = 0; i < studentList.size(); i++) {
							studentList2.get(i).clearMatches();
							studentList2.get(i).setIfDataEdited(studentList.get(i).getIfDataEdited());
							studentList2.get(i).setMaxMatches(studentList.get(i).getMaxMatches());
							for (int j = 0; j < studentList.get(i).getNMatches(); j++) {
								studentList2.get(i).addMatch(studentList.get(i).getMatch(j));
							}
						}
						// clear and reset the matches array, the max matches and the edited data boolean of the duplicate array to the values form the original array of school objects
						for (int i = 0; i < schoolList.size(); i++) {
							schoolList2.get(i).clearMatches();
							schoolList2.get(i).setIfDataEdited(schoolList.get(i).getIfDataEdited());
							for (int j = 0; j < schoolList.get(i).getNMatches(); j++) {
								schoolList2.get(i).addMatch(schoolList.get(i).getMatch(j));
							}
						}
						// reset the school optimal smp object
						schoolsFirst.reset();
						// set the participants for smp object with high schools as suitors
						schoolsFirst.setParticipants(schoolList2,studentList2);
						// set that existing matches are true
						schoolsFirst.setMatchesExist(true);
						// call the match method for the SMPSolver object then the print matches method and the print method to display result
						schoolsFirst.printMatches();
						schoolsFirst.print();
					}
				
					break;
				case ("X"):
				case ("x"):
					// if the user wants to print the matching stats and compare the two types
					// if matches do not yet exist print matches do not exist
					if (studentsFirst.matchesExist() == false) {
						System.out.print("\nERROR: No matches exist!\n\n");
					}
					// check if the matching can proceed for student optimal
					else if (studentsFirst.matchingCanProceed() == false) {
					}
					// if matches do not yet exist print matches do not exist
					else if (schoolsFirst.matchesExist() == false) {
						System.out.print("\nERROR: No matches exist!\n\n");
					}
					// check if the matching can proceed for school optimal
					else if (schoolsFirst.matchingCanProceed() == false) {
					}
					else {
						// copy the schools and students array to fill the duplicates that will be used for school optimal matching
						schoolList2 = copySchools(schoolList);
						studentList2 = copyStudents(studentList);
						// clear and reset the matches array, the max matches and the edited data boolean of the duplicate array to the values form the original array of student objects
						for (int i = 0; i < studentList.size(); i++) {
							studentList2.get(i).clearMatches();
							studentList2.get(i).setIfDataEdited(studentList.get(i).getIfDataEdited());
							studentList2.get(i).setMaxMatches(studentList.get(i).getMaxMatches());
							for (int j = 0; j < studentList.get(i).getNMatches(); j++) {
								studentList2.get(i).addMatch(studentList.get(i).getMatch(j));
							}
						}
						// clear and reset the matches array, the max matches and the edited data boolean of the duplicate array to the values form the original array of school objects
						for (int i = 0; i < schoolList.size(); i++) {
							schoolList2.get(i).clearMatches();
							schoolList2.get(i).setIfDataEdited(schoolList.get(i).getIfDataEdited());
							for (int j = 0; j < schoolList.get(i).getNMatches(); j++) {
								schoolList2.get(i).addMatch(schoolList.get(i).getMatch(j));
							}
						}
						// reset the school optimal smp object
						schoolsFirst.reset();
						// set the participants for smp object with high schools as suitors
						schoolsFirst.setParticipants(schoolList2,studentList2);
						// set that existing matches are true
						schoolsFirst.setMatchesExist(true);
						// call the calc rangings and stability functions on both types of optimal matching to get the updated statistics
						schoolsFirst.calcRegrets();
						studentsFirst.calcRegrets();
						schoolsFirst.determineStability();
						studentsFirst.determineStability();
						// call the print comparison method to compare the two types of matching
						printComparison(studentsFirst,schoolsFirst);
					}
					break;
				case ("R"):
				case ("r"):
					// if the choice is r, clear the array lists holding the students and school objects and the duplicate array lists, set the number of students and schools back to zero
					studentList.clear();
					schoolList.clear();
					studentList2.clear();
					schoolList2.clear();
					studentNum = 0;
					schoolNum = 0;
					addedStudents = 0;
					addedSchools = 0;
					// reset the SMPSolver object
					studentsFirst.reset();
					studentsFirst.setTime(-1);
					schoolsFirst.reset();
					schoolsFirst.setTime(-1);
					// print that the database is cleared
					System.out.println("\nDatabase cleared!\n");
					break;
				case ("Q"):
				case ("q"):
					// if the choice is to quit, print Hasta luego! and quit the program, breaking the infinite while loop
					System.out.println("\nHasta luego!");
					System.exit(0);
			}
		}	
	}
	
	public static void displayMenu() {
		// this function displays the menu of options, and the name of the game
		//it takes no arguments and returns nothing
		System.out.print("JAVA STABLE MARRIAGE PROBLEM v3\n");
		
		System.out.println("\nL - Load students and schools from file\nE - Edit students and schools\nP - Print students and schools\nM - Match students and schools using Gale-Shapley algorithm\nD - Display matches\nX - Compare student-optimal and school-optimal matches\nR - Reset database\nQ - Quit");
	}
	
	public static void resetMatches(ArrayList<Student> S, ArrayList<School> H) {
		// function will reset the matched indexes for student and school objects
		// first the students array list
		for (int i = 0 ; i < S.size(); i++) {
			// clear the matches
			S.get(i).clearMatches();
			for (int j = 0 ; j < S.get(i).getMaxMatches(); j++) {
				// set the matches and regrets to -1
				S.get(i).addMatch(-1);
				S.get(i).setRegret(-1);
			}
		}
		// second the schools array list
		for (int i = 0 ; i < H.size(); i++) {
			// clear the matches
			H.get(i).clearMatches();
			for (int j = 0 ; j < H.get(i).getMaxMatches(); j++) {
				// set the matches and regrets to -1
				H.get(i).addMatch(-1);
				H.get(i).setRegret(-1);
			}
		}
	}
	
	public static int loadStudents(ArrayList<Student> S, ArrayList<School> H) throws IOException, NumberFormatException {
		// initialize variables
		// this functions loads students from the student file
		BufferedReader fileInput; // for reading file
		int numStudentsLoaded = 0, loadingAttempts = 0, ES; // variables to hold the number of students loaded, loading attempts
		Double GPA; // holds student's gpa
		String studentFileName, line, studentName; // hold student name, filename, line string to read file lines
		boolean incorrectFileName = true; // boolean to check for correct file name
		
		// while incorrectInputFilename is true
		while (incorrectFileName) {
			// ask for file name
			System.out.print("Enter student file name (0 to cancel): ");
			// read the user input
			studentFileName =  input.readLine();
			// if the user entered zero cancel the loading process and break out of the loop
			if (studentFileName.equals("0")) {
				System.out.print("\nFile loading process canceled.\n\n");
				break;
			}
			else {
				// otherwise create a file object with that file name
				File schoolFile = new File(studentFileName);
				if (schoolFile.exists()) {
					// if the file exists read the file
					fileInput = new BufferedReader (new FileReader ( studentFileName ) ) ;
					do {
						// real the file line by line
						line = fileInput.readLine () ;
						if ( line != null ) {
							// if the line is not null increment the loading attempts
							loadingAttempts ++;
							// create an array of the line by spiting it at the commas
							String [] splitString = line.split(",") ;
							if ((splitString.length == H.size() + 3) && (splitString.length - 3 == H.size())) {
								// if the length of the array is = # of schools + 3
								// retrieve the student name, gpa, and es
								studentName = splitString[0];
								GPA = Double.parseDouble(splitString[1]);
								ES = Integer.parseInt(splitString[2]);
								// create a new school object with the given inputs
								Student st = new Student(studentName,GPA,ES,H.size());
								// loop thug the split string array to set the rankings
								for (int i = 0; i <= H.size() - 1 ; i++) {
									st.setRanking(i,Integer.parseInt(splitString[i+3]));
								}
								// check if all the inputs are valid and if so add the student object to the array of student objects
								if (st.isValid() == true) {
									// add the school with the given inputs to the arrayList of school objects
									S.add(st);
									// increment the number of loaded schools
									numStudentsLoaded ++ ;
								}
							}
						}
						// do this while the line is not empty
					} while ( line != null ) ;
					// close the file
					fileInput.close();
					// print the number of students loaded out of the amount that were in the file
					System.out.print("\n" + numStudentsLoaded + " of " + loadingAttempts + " students loaded!\n\n");
					// set incorrect file name to false
					incorrectFileName = false;
					// calculate the school rankings
					for (int i = 0; i <= H.size() - 1 ; i++) {
						H.get(i).calcRankings(S);
					}
				}
				else {
					// other wise print file not found
					System.out.print("\nERROR: File not found!\n\n");
				}
			}
		
		}
		// return the number of students loaded
		return numStudentsLoaded;
	}
	
	public static int loadSchools(ArrayList<School> H) throws IOException, NumberFormatException {
		// initialize variables
		// this functions loads schools from the school file
		BufferedReader fileInput; // for reading the file
		int numSchoolsLoaded = 0, loadingAttempts = 0, numSpots = 0; // to keep track of how many loading attempts there were and how many schools where actually loaded, and how many open spots a school has for students
		Double alpha; // to hold the school gpa score
		String schoolFileName, line,schoolName; // to hold the schoolFileName, the line string to hold each line of the file, to hold the school name
		boolean incorrectFileName = true; // to indicate an incorrect file name
		
		while (incorrectFileName) {
			// if incorrect file name equals true
			// ask for the school file name
			System.out.print("\nEnter school file name (0 to cancel): ");
			// read the user's input
			schoolFileName =  input.readLine();
			// if the user entered zero cancel the loading process and break
			if (schoolFileName.equals("0")) {
				System.out.print("\nFile loading process canceled.\n\n");
				break;
			}
			else {
				// otherwise create a file object for this file name
				File schoolFile = new File(schoolFileName);
				if (schoolFile.exists()) {
					// if the file exists read the file
					fileInput = new BufferedReader (new FileReader ( schoolFileName ) ) ;
					do {
						// read the file line by line
						line = fileInput.readLine () ;
						if ( line != null ) {
							// if the line is not empty increment the loading attempts
							loadingAttempts ++;
							// create an array of the values by spiting the line at the commas
							String [] splitString = line.split(",") ;
							// if the length of the array is 2, retrieve the values for school name and gpa and the number of students the school can accept
							if (splitString.length == 3) {
								schoolName = splitString[0];
								alpha = Double.parseDouble(splitString[1]);
								numSpots = Integer.parseInt(splitString[2]);
								// create a new school object with the given inputs
								School sh = new School(schoolName,alpha,numSpots,2000);
								// check if all the values provided by the file are valid and if they are add the school object to the array list of school objects
								if (sh.isValid() == true) {
									// add the school with the given inputs to the arrayList of school objects
									H.add(sh);
									// increment the number of loaded students
									numSchoolsLoaded ++ ;
								}
							}
						}
						// keep doing this while the line is not empty
					} while ( line != null ) ;
					// close the file
					fileInput.close();
					// print the number of schools loaded out of the number of schools originally in the file
					System.out.print("\n" + numSchoolsLoaded + " of " + loadingAttempts + " schools loaded!\n\n");
					//  set incorrectFileName to false
					incorrectFileName = false;
				}
				else {
					// otherwise print the file is not found
					System.out.print("\nERROR: File not found!\n");
				}
			}
		}
		// return the number of schools that where loaded
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
			System.out.println("Edit data\n---------\nS - Edit students\nH - Edit high schools\nQ - Quit\n");
			
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
		String spaceString1, GPA = ""; // strings to contain spaces for formating, and gpa of student as a string
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
				System.out.print(" #   Name                                         GPA  ES  Assigned school                         Preferred school order");
				System.out.print("\n---------------------------------------------------------------------------------------------------------------------------\n");
				
				// loop through the students
			 	for (int i = 0 ; i < S.size() ; i++) {
			 		// print the # of the student, the name and the gpa
			 		System.out.print(spaceString1.repeat(3-Integer.toString(i+1).length()) + Integer.toString(i+1) + ". " + S.get(i).getName());
			 		GPA =  String.format("%.2f", S.get(i).getGPA());
			 		// find the # of spaces needed after the name and gpa
					x = 48 - (S.get(i).getName()).length() - GPA.length();
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
				System.out.print("---------------------------------------------------------------------------------------------------------------------------\n");
				
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
					// loop though each school and call the calcRankings method from the school class to  update the rankings the schools give the students and set if the data has been edited to true
					for (int i = 0 ; i < H.size() ; i++) {
						H.get(i).setIfDataEdited(true);
						H.get(i).calcRankings(S);
					}
					// loop through each school and set if the data has been edited to true
					for (int i = 0 ; i < S.size() ; i++) {
						S.get(i).setIfDataEdited(true);
					}
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
		String spaceString1, GPA = ""; // strings to contain spaces for formating, and gpa of school as a string
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
			System.out.println(" #   Name                                     # spots  Weight  Assigned students                       Preferred student order");
			System.out.println("------------------------------------------------------------------------------------------------------------------------------");
			
			// loop through the schools
			for (int i = 0 ; i < H.size() ; i++) {
				// print the # of the school, the name and the gpa
				System.out.print(spaceString1.repeat(3-Integer.toString(i+1).length()) + Integer.toString(i+1) + ". " + H.get(i).getName());
		 		GPA =  String.format("%.2f", S.get(i).getGPA());
		 		// find the # of spaces needed after the name and the gpa
				x = 43 - ((H.get(i).getName()).length() - GPA.length());
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
			 	H.get(i).print(S);
			 	// reset formating variables
				x = 0;
				spaceString1 = " ";
			}
			// print table format
			System.out.print("------------------------------------------------------------------------------------------------------------------------------\n");
			
			// call the get integer function to get the # of which school to change
			value = getInteger("Enter school (0 to quit): ", 0, H.size());
			
			// if value is equal to 0, steps is set to false which will break the loop
			if (value == 0) {
				steps = false;
			}
			
			// otherwise
			else {
				
				// call the edit info method from the school class for the chosen school
				H.get(value-1).editSchoolInfo(S,true);
				// loop though each school and call the calcRankings method from the school class to  update the rankings the schools give the students and set if the data has been edited to true
				for (int i = 0 ; i < H.size() ; i++) {
					H.get(i).setIfDataEdited(true);
					H.get(i).calcRankings(S);
				}
				// loop through each school and set if the data has been edited to true
				for (int i = 0 ; i < S.size() ; i++) {
					S.get(i).setIfDataEdited(true);
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
		String spaceString1, GPA = ""; // strings to contain spaces for formating, and for the string of the gpa
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
			System.out.print("\n #   Name                                         GPA  ES  Assigned school                         Preferred school order");
			System.out.print("\n---------------------------------------------------------------------------------------------------------------------------\n");
			
			// loop through the students
		 	for (int i = 0 ; i < S.size() ; i++) {
		 		// print the # of the student, the name, and the gpa
		 		System.out.print(spaceString1.repeat(3-Integer.toString(i+1).length()) + Integer.toString(i+1) + ". " + S.get(i).getName());
		 		GPA =  String.format("%.2f", S.get(i).getGPA());
		 		// find the # of spaces needed after the name and the gpa
				x = 48 - (S.get(i).getName()).length() - GPA.length();
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
			System.out.print("---------------------------------------------------------------------------------------------------------------------------\n");
		}
	}
	
	public static void printSchools(ArrayList<Student> S, ArrayList<School> H) {
		//Print schools to the screen, including matched student (if one exists).
		//takes as arguments an arrayList of student objects, an arrayList of school objects, the number of schools, and a boolean of if rankings are set or not
		//returns nothing
		 
		// initialize variables
		String spaceString1, GPA = ""; // strings to contain spaces for formating and the string version of the gpa
		int x = 0; // # of spaces to add
		spaceString1 = " "; // string of a space
		boolean rankings; // boolean for rankings
		 
		// if there are no schools, print the following error message
		if (H.size() == 0) {
			System.out.println("ERROR: No schools are loaded!\n");
		}
		
		 // otherwise
		 else {
			// print title schools
			System.out.println("\nSCHOOLS:");
			
			// print table format
			System.out.println("\n #   Name                                     # spots  Weight  Assigned students                       Preferred student order");
			System.out.println("------------------------------------------------------------------------------------------------------------------------------");
			
			// loop through the schools
			for (int i = 0 ; i < H.size() ; i++) {
				// print the # of the school, the name, and the gpa
				System.out.print(spaceString1.repeat(3-Integer.toString(i+1).length()) + Integer.toString(i+1) + ". " + H.get(i).getName());
		 		GPA =  String.format("%.2f", S.get(i).getGPA());
			 	// find the # of spaces needed after the name and gpa
				x = 43 - ((H.get(i).getName()).length() - GPA.length());
				
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
			 	H.get(i).print(S);
			 	// reset formating variables
		 		x = 0;
		 		spaceString1 = " ";
			}
			// print table format
			System.out.println("------------------------------------------------------------------------------------------------------------------------------\n");
		}
	}
	
	public static void printComparison(SMPSolver GSS, SMPSolver GSH) {
		String spaceString = " ", stable = "", schoolRegret = "", studentRegret = "", totalRegret = "", time = ""; // declare string variables to contain resulting values of the mathing
		// print the format of the comparison table
		System.out.print("\nSolution              Stable    Avg school regret   Avg student regret     Avg total regret       Comp time (ms)\n");
		System.out.print("----------------------------------------------------------------------------------------------------------------");
		// call the function to display the summary of stats for student optimal matching
		GSS.printStatsRow("Student optimal");
		// call the function to display the summary of stats for school optimal matching
		GSH.printStatsRow("School optimal");
		System.out.print("\n----------------------------------------------------------------------------------------------------------------");
		// if the stability is the same, set stable to tie
		if (GSS.isStable() == GSH.isStable()) {
			stable = "Tie";
		}
		// if the stability is true for student optimal, set stable to student optimal
		else if (GSS.isStable() == true) {
			stable = "Student-opt";
		}
		// if the stability is true for school optimal, set stable to school optimal
		else if (GSH.isStable() == true) {
			stable = "School-opt";
		}
		// if the receiver average is the same as the suitor average, set school regret to tie
		if (GSS.getAvgReceiverRegret() == GSH.getAvgSuitorRegret()) {
			schoolRegret = "Tie";
		}
		// if the receiver average is the smaller than the suitor average, set school regret to student opt
		else if (GSS.getAvgReceiverRegret() < GSH.getAvgSuitorRegret()) {
			schoolRegret = "Student-opt";
		}
		// if the receiver average is the larger than the suitor average, set school regret to school opt
		else if (GSS.getAvgReceiverRegret() > GSH.getAvgSuitorRegret()) {
			schoolRegret = "School-opt";
		}
		// if the suitor average is the same as the receiver average, set student regret to tie
		if (GSS.getAvgSuitorRegret() == GSH.getAvgReceiverRegret()) {
			studentRegret = "Tie";
		}
		// if the suitor average is the smaller than the receiver average, set student regret to student opt
		else if (GSS.getAvgSuitorRegret() < GSH.getAvgReceiverRegret()) {
			studentRegret = "Student-opt";
		}
		// if the suitor average is the larger than the receiver average, set student regret to school opt
		else if (GSS.getAvgSuitorRegret() > GSH.getAvgReceiverRegret()) {
			studentRegret = "School-opt";
		}
		// if total average regret is the same, set total regret to tie
		if (GSS.getAvgTotalRegret() == GSH.getAvgTotalRegret()) {
			totalRegret = "Tie";
		}
		// if total average regret for student is smaller than for school, set total regret to student opt
		else if (GSS.getAvgTotalRegret() < GSH.getAvgTotalRegret()) {
			totalRegret = "Student-opt";
		}
		// if total average regret for student is larger than for school, set total regret to school opt
		else if (GSS.getAvgTotalRegret() > GSH.getAvgTotalRegret()) {
			totalRegret = "School-opt";
		}
		// if comp time is the same for both, set time to tie
		if (GSS.getTime() == GSH.getTime()) {
			time = "Tie";
		}
		// if the student comp time is smaller, set time to student opt
		else if (GSS.getTime() < GSH.getTime()) {
			time = "Student-opt";
		}
		// if the student comp time is larger, set time to school opt
		else if (GSS.getTime() > GSH.getTime()) {
			time = "School-opt";
		}
		// print all the comparison results
		System.out.print("\nWINNER" + spaceString.repeat(22 - stable.length()) + stable + spaceString.repeat(21 - schoolRegret.length()) + schoolRegret + spaceString.repeat(21 - studentRegret.length()) + studentRegret + spaceString.repeat(21 - totalRegret.length()) + totalRegret + spaceString.repeat(21 - time.length()) + time);
		System.out.print("\n----------------------------------------------------------------------------------------------------------------\n\n");
	}
	
	public static ArrayList <School> copySchools (ArrayList <School> P) {
		// create independent copy of School ArrayList
		ArrayList <School> newList = new ArrayList <School>();
		// copy all the information for every object in the array and create a new object with that information
		for (int i = 0; i < P.size(); i++) {
			String name = P.get(i).getName();
			double alpha = P.get(i).getAlpha();
			int maxMatches = P.get(i).getMaxMatches();
			int nStudents = P.get(i).getNParticipants();
			School temp = new School (name,alpha,maxMatches,nStudents);
			// copy over the rankings for that new object
			for (int j = 0; j < nStudents; j++) {
				temp.setRanking(j,P.get(i).getRanking(j));
			}
			// add the new object to the duplicate list
			newList.add(temp);
		}
		// return a duplicate list
		return newList;
	}
	
	public static ArrayList <Student> copyStudents (ArrayList <Student> P) {
		// create independent copy of Student ArrayList
		ArrayList < Student > newList = new ArrayList < Student >();
		// copy all the information for every object in the array and create a new object with that information
		for (int i = 0; i < P.size(); i++) {
			String name = P.get(i).getName();
			double GPA = P.get(i).getGPA();
			int ES = P.get(i).getES();
			int nStudents = P.get(i).getNParticipants();
			Student temp = new Student (name,GPA,ES,nStudents);
			temp.setMaxMatches(P.get(i).getMaxMatches());
			// copy over the rankings for that new object
			for (int j = 0; j < nStudents; j++) {
				temp.setRanking(j,P.get(i).getRanking(j));
			}
			// add the new object to the duplicate list
			newList.add(temp);
		}
		// return a duplicate list
		return newList;
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
