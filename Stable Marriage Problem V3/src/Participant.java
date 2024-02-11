import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Participant {
	private String name ; // name
	private ArrayList <Integer> rankings = new ArrayList <Integer>(); // rankings of participants
	private ArrayList <Integer> matches = new ArrayList <Integer>();// match indices
	private int regret ; // total regret
	private int maxMatches ; // max # of allowed matches / openings
	private boolean dataHasBeenEdited; // if the data of the students has been edited or not
	
	// constructors
	public Participant () {
		this.name = null;
		this.regret = -1;
		this.maxMatches = -1;
		this.dataHasBeenEdited = false;
		for (int i = 0; i < this.getMaxMatches(); i++) {
			this.addMatch(-1);
		}
		for (int i = 0; i < 2000; i++) {
			this.addRanking(0);
		}
	}
	public Participant ( String name , int maxMatches , int nParticipants ) {
		// initialize the object with the values passed in the constructor
		this.name = name;
		this.regret = -1;
		this.maxMatches = maxMatches;
		this.dataHasBeenEdited = false;
		setNParticipants(nParticipants);
		// add -1's to the matches array
		for (int i = 0; i < this.getMaxMatches(); i++) {
			this.addMatch(-1);
		}
	}
	
	// getters
	public String getName () {
		// get the name of the participant object
		return this.name;
	}
	public int getRanking ( int i ) {
		// get the ranking of index i from the rankings array list of the participant object
		return this.rankings.get(i);
	}
	public int getMatch ( int i ) {
		// get the match of index i from the matches array list of the participant object
		return this.matches.get(i);
	}
	public int getRealMatch ( int i ) {
		// get the true matched match of index i from the matches array list of the participant object
		return this.matches.get(i);
	}
	public int getRegret () {
		// get the regret of the participant object
		return this.regret;
	}
	public int getMaxMatches () {
		// get the max matches of the participant object
		return this.maxMatches;
	}
	public int getNMatches () {
		// get the number of matches of the participant object; size of matches array
		return this.matches.size();
	}
	public int getNParticipants () {
		// return length of rankings array
		return this.rankings.size();
	}
	public boolean getIfDataEdited () {
		// get the boolean for if data has been edited of the participant object
		return this.dataHasBeenEdited;
	}
	public int getRankingByIndex (int n) {
		// get the index from the rankings array list of the value n of the participant object
		return this.rankings.indexOf(n);
	}
	public boolean isFull () {
		// check if there are any -1's in the object's matches array list
		for (int i = 0; i < this.getMaxMatches(); i++) {
			if (this.getMatch(i) == -1) {
				// return true if there is still matches to fill
				return true;
			}
		}
		// otherwise return false
		return false;
	}
	
	// setters
	public void setName ( String name ) {
		// set the name of the participant object
		this.name = name;
	}
	public void setRanking ( int i , int r ) {
		// set the ranking array list of index i to value of r for this participant object
		this.rankings.set(i, r);
	}
	public void addRanking ( int i ) {
		// add the ranking i to the array list of rankings of the participant object
		this.rankings.add(i);
	}
	public void setMatch ( int m ) {
		// set the match of index m in the array list of matches to -1 of the participant object
		this.matches.set(m, -1);
	}
	public void addMatch (int i) {
		// add the match i to the array list of matches of the participant object
		this.matches.add(i);
	}
	public void setRealMatch (int i, int j) {
		// set the real matched value of j in index i of the matches array list of the participant object
		this.matches.set(i, j);
	}
	public void setRegret ( int r ) {
		// set the regret of the participant object
		this.regret = r;
	}
	public void setNParticipants ( int n ) {
		// set rankings array size and fill it with zeros
		for (int i = 0; i < n; i++) {
			this.addRanking(0);
		}
	}
	public void setMaxMatches ( int n ) {
		// set the max number of matches of the participant object
		this.maxMatches = n;
	}
	public void setIfDataEdited (boolean b) {
		// set the data have been edited boolean of the participant object
		this.dataHasBeenEdited = b;
	}
	
	// methods to handle matches
	public void clearMatches () {
		// clear all matches
		this.matches.clear();
	}
	public boolean findIndexInMatch (int i) {
		// finds if the matches array contains the value i
		if (this.matches.contains(i)) {
			return true;
		}
		return false;
	}
	public int findRankingByID ( int k ) {
		// find rank of participant k
		return this.getRanking(k);
	}
	public int findRankingByIDAlternate ( int k ) {
		// find rank of participant k
		int x = -1 ; // index of student
		// loop through the rankings array of this school object
		for (int i = 0; i < this.rankings.size() ; i++) {
			// if the array at i is equal to rank
			if (this.getRanking(i) == k+1) {
				// set the index of the rankings array at rank to x
				x = i;
				// break the loop
				break;
			}
		}
		// return the rank
		return x+1;
	}
	public int getWorstMatch () {
		// find the worst - matched participant
		int regret = -1, singleMR = -1; // initialize regret and single match regret
		for (int i = 0; i < this.matches.size(); i++) {
			// call the single match regret function
			singleMR = getSingleMatchedRegret(i);
			if (regret < singleMR - 1) {
				// calculate the single match regret and if it is higher than the previous one that is the new regret the function will focus on
				regret = singleMR - 1;
			}
		}
		// returns the index of the worst matched participant
		return this.rankings.indexOf(regret+1);
	}
	public int getWorstMatchAlternate () {
		// find the worst - matched participant
		int regret = -1, singleMR = -1; // initialize regret and single match regret
		for (int i = 0; i < this.matches.size(); i++) {
			// call the single match alternate regret function
			singleMR = getSingleMatchedRegretAlternate(i);
			// calculate the single match regret and if it is higher than the previous one that is the new regret the function will focus on
			if (regret < singleMR - 1) {
				regret = singleMR - 1;
			}
		}
		// returns the index of the worst matched participant
		return this.rankings.get(regret)-1;
	}
	public void unmatch ( int k ) {
		// remove the match with participant k
		// clear the match of the old suitor and turn it back to -1
		this.setMatch(this.matches.indexOf(k));
	}
	public boolean matchExists ( int k ) {
		// check if match to participant k exists
		return !this.isFull();
	}
	public int existingMatches () {
		// returns how many -1's there are in the matches array list
		return Collections.frequency(this.matches,-1);
	}
	public boolean alreadyInMatchesArray (int i) {
		// checks if i is already in the matches array
		if (this.matches.contains(i)) {
			// if it is inside return false
			return false;
		}
		// otherwise return true
		return true;
	}
	public int getSingleMatchedRegret ( int k ) {
		// get regret from match with k
		return findRankingByID(this.matches.get(k));
	}
	public int getSingleMatchedRegretAlternate ( int k ) {
		// get regret from match with k
		return findRankingByIDAlternate(this.matches.get(k));
	}
	public void calcRegret () {
		// calculate total regret over all matches
		int regretSum = 0;
		// loop through the matches for this one object and calculate the regret
		for (int i = 0; i < this.matches.size(); i++) {
			regretSum += getSingleMatchedRegret(i)-1;
		}
		// set the regret
		this.regret = regretSum;
	}
	public void calcRegretAlternate () {
		// calculate total regret over all matches
		int regretSum = 0;
		// loop through the matches for this one object and calculate the regret
		for (int i = 0; i < this.matches.size(); i++) {
			regretSum += getSingleMatchedRegretAlternate(i)-1;
		}
		// set the regret
		this.regret = regretSum;
	}
	
	// methods to edit data from the user
	public void editInfo ( ArrayList <? extends Participant > P ) throws IOException, NumberFormatException {
		// get new info from the user
		// takes in an arrayList of participant objects
		// returns nothing
		
		// ask the user for the participant's new name
		System.out.print("\nName: ");
		this.setName(Main.input.readLine());
		
	}
	public void editRankings ( ArrayList <? extends Participant > P ) {
		// edit rankings
		// takes in an arrayList of school objects
		// returns nothing
		
		// initialize variables
		int x, s = 0; // rank given to the schools
		boolean goAhead; // boolean that allows loop to move forwards
		
		// loop through the rankings array for this student object and set all the ranks to 0
		for (int i = 0; i < P.size(); i++) {
			this.setRanking(i, 0);
		}
		
		// print the student for which rankings will be assigned
		System.out.print("Participant " + this.getName() +"'s rankings:\n");
		
		//loop through each school
		for (int j = 0; j < P.size(); j++) {
			// set the rank to zero
			x = 0;
			// set the boolean that controls the loop to true
			goAhead = true;
			
			// use a do while loop to get a valid rank
			do {
				// ask the user for a rank by calling the get integer function from the main class
				x = Main.getInteger("School " + (P.get(j)).getName() + ": ",1, P.size());
					// loop through the schools again
					for (int k = 0; k < P.size(); k++)	{
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
	
	// print methods
	public void print ( ArrayList <? extends Participant > P ) {
		// call the print rankings method in participants class
		this.printRankings(P);
	}
	public void printRankings ( ArrayList <? extends Participant > P ) {
		
		if (P.get(0) != null) {
			// if the object in S is a student object
			if (P.get(0) instanceof Student) {
				// print the rankings separated by a comma
				// takes in an arrayList of P if it is full of type student objects
				// returns nothing
								
				// initialize variables
				String studentRankNames = ""; // sting of the ranked student names
				
				// loop through the students
				for (int j = 0; j < P.size() ; j++) {
					// loop through the rankings array
					for (int k = 0; k < P.size() ; k++) {
						// if the ranking in the index k is equal to the index of the student + 1
						if ((this.getRanking(k)) == j+1) {
							// add the name of the student at index i to the list of names
							studentRankNames += P.get(k).getName() + ", " ;
						}
					}
					
				}
				// at the end remove the last space and comma from the string of names
				studentRankNames = studentRankNames.substring(0, studentRankNames.length() - 2);
				// print the ranked names
				System.out.println(studentRankNames);
			}
			
			// if the object in S is a school object
			if (P.get(0) instanceof School) {
				//print the rankings separated by a comma
				// takes in an arrayList of school objects
				// returns nothing
						
				// initialize variables
				String schoolRankNames = ""; // sting of the ranked school names
				// loop through the rankings array
				for(int k = 0 ; k < P.size() ; k++ ) {
					// add the name of the school at index i to the list of names
					schoolRankNames += (P.get(this.getRanking(k)-1).getName()) + ", ";
				}
				// at the end remove the last space and comma from the string of names
				schoolRankNames = schoolRankNames.substring(0, schoolRankNames.length() - 2);
				// print the ranked names
				System.out.println(schoolRankNames);
			}
		}
	}
	
	public String getMatchNames ( ArrayList <? extends Participant > P ) {
		// prints a string of matches for a participant
		String printString = "";
		for (int i = 0 ; i < P.size(); i++) {
			printString += P.get(i).getName() + ": ";
			for (int j = 0; j < P.get(i).getNMatches(); j++) {
				printString += P.get(P.get(i).getMatch(j)).getName() + ", ";
			}
		}
		return printString;
	}
	
	public boolean isValid () {
		// check if this participant has valid info
		// check and return if the max matches are under 1
		return this.maxMatches < 1;
	}
}

