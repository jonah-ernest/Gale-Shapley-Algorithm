
import java.util.*;

public class SMPSolver {
    private ArrayList<Student> S = new ArrayList<Student>(2000); // suitors
    private ArrayList<School> R = new ArrayList<School>(2000); // receivers
    private double avgSuitorRegret; // average suitor regret
    private double avgReceiverRegret; // average receiver regret
    private double avgTotalRegret; // average total regret
    private boolean matchesExist; // whether matches exist
    private long elapsedTime; // time elapsed for matching

    // constructors
    public SMPSolver(){
    	this.avgSuitorRegret = -1;
    	this.avgReceiverRegret = -1;
    	this.avgTotalRegret = -1;
    	this.matchesExist = false;
    	this.elapsedTime = -1;
    }
    public SMPSolver(ArrayList<Student> S, ArrayList<School> R){
    	setSuitorAndReceiverArrayList(S,R);
    	//setReceiverArrayList();
    	this.avgSuitorRegret = -1;
    	this.avgReceiverRegret = -1;
    	this.avgTotalRegret = -1;
    	this.matchesExist = false;
    	this.elapsedTime = -1;
    }

    // getters
    public double getAvgSuitorRegret(){
    	// get the avgSuitorRegret of the SMPSolver object
        return this.avgSuitorRegret;
    }

    public double getAvgReceiverRegret(){
    	// get the avgReceiverRegret of the SMPSolver object
        return this.avgReceiverRegret;
    }

    public double getAvgTotalRegret(){
    	// get the avgTotalRegret of the SMPSolver object
        return this.avgTotalRegret;
    }

    public boolean matchesExist(){
    	// get the matchesExist of the SMPSolver object
        return this.matchesExist;
    }
    
    public long getElapsedTime(){
    	// get the elapsedTime of the SMPSolver object
        return this.elapsedTime;
    }
    
    //setters
    public void setSuitorAndReceiverArrayList(ArrayList<Student> S, ArrayList<School> R) {
    	// add the student and school objects of the passed arraylists to the arraylists of the SMPSlover object
    	for (int i = 0; i < S.size(); i++) {
    		this.S.add(S.get(i));
    	}
    	for (int i = 0; i < R.size(); i++) {
    		this.R.add(R.get(i));
    	}
    	// set matches exist to true
    	this.matchesExist = true;
    }

    public void reset(ArrayList<Student> S, ArrayList<School> R){
    	// reset everything with new suitors and receivers
    	// set all attributes back to their original sate
    	this.avgSuitorRegret = -1;
    	this.avgReceiverRegret = -1;
    	this.avgTotalRegret = -1;
    	this.matchesExist = false;
    	this.elapsedTime = -1;
    	// clear the arraylists of objects 
    	this.S.clear();
    	this.R.clear();
    	// call the function to fill the arraylists
    	setSuitorAndReceiverArrayList(S,R);    	
    }

    // methods for matching
    public boolean match(){ // Gale-Shapely algorithm to match; students are suitors
    	long start; // initialize start variable
    	start = System.currentTimeMillis(); // get current time
    	// set matches exist to false
    	this.matchesExist = false;

    	boolean matchesToBeMade = true; //initialize and set matches to be made to true; this means there are still suitors to match
    	if (matchingCanProceed() == true) {
    		// while there are still suitors to be matched
    		while (matchesToBeMade) {
    			// loop through the suitors
    			for (int j = 0; j < this.S.size(); j++) {
    				// check if the suitor is free
    				if (this.S.get(j).getSchool() == -1) {
    					// if they are set matches to be made to true
    					matchesToBeMade = true;
    					// loop through the receivers
    					for (int k = 0; k < this.R.size(); k++) {
    						// check if the receiver is not already engaged by calling the make proposal method
    						if (makeProposal(j,this.S.get(j).getRanking(k) - 1)) {
    							// if it is not set the engagement by calling the make engagement method
    							makeEngagement(j,this.S.get(j).getRanking(k) - 1);
    							break;
    						}
    						else {
    							// otherwise if the receiver likes this the given suitor more then they like the suitor they are with right now
       							if (this.R.get(this.S.get(j).getRanking(k) - 1).getRanking(j) < this.R.get(this.S.get(j).getRanking(k) - 1).getRanking(this.R.get(this.S.get(j).getRanking(k) - 1).getStudent())) {
	    				    		// clear the match of the old suitor and turn it back to -1
       								this.S.get(this.R.get(this.S.get(j).getRanking(k) - 1).getStudent()).setSchool(-1);
       								// make the engagement of the new pair
	    				    		makeEngagement(j,this.S.get(j).getRanking(k) - 1);
	    				    		break;
       							}
    						}
    					}
    				}
    			}
    			// loop though the suitors
    			for (int i = 0; i < this.S.size(); i++) {
    				// if any of the suitors still have not been matched, set matches to be made to true
    				if (this.S.get(i).getSchool() == -1) {
    					matchesToBeMade = true;
    					break;
    				}
    				else {
    					// otherwise set matches to be made to false
    					matchesToBeMade = false;
    				}
    			}
    		}
    		//  set matches exist to true once matching is complete
    		this.matchesExist = true;
    	}
    	// stop the elapsed time of how long it took to do the matching
    	this.elapsedTime = System.currentTimeMillis()-start;
    	// return that matches have been set as true
        return matchesExist(); 
    }

    private boolean makeProposal(int suitor, int receiver){
    	// suitor proposes to receiver
    	// receiver check if the receiver is free
    	if (this.R.get(receiver).getStudent() != -1) {
    		// if it is not free return false
    		return false;
    	}
    	// otherwise return true
    	return true;
    }

    private void makeEngagement(int suitor, int receiver){
    	// make engagement of suitor to receiver and receiver to suitor
    	this.S.get(suitor).setSchool(receiver);
		this.R.get(receiver).setStudent(suitor);
    }

    public boolean matchingCanProceed(){ // check that matching rules are satisfied
    	//Check that the conditions to proceed with matching are satisfied.
		//takes in the number of schools, and students, and a boolean of if rankings are set or not
		// returns a boolean stating is a matching can proceed of not
		
		// if the number of students and schools are not equal, print the following error and return false
		if (this.S.size() != this.R.size()) {
			System.out.println("\nERROR: The number of suitors and receivers must be equal!\n");
			return false;
		}
		else {
			return true;
		}
    }
    
    public void calcRegret(){ // calculate regrets
		// loop through the suitors and receivers and sum up their total regret
    	double suitorSum = 0, receiverSum = 0, peopleSum = 0; // averages and sums of each category
		 
		 for (int j = 0 ; j < this.S.size(); j++) {
			 // find the regret by doing the ranks of the assigned receiver - 1
			 suitorSum += this.S.get(j).findRankingByID(this.S.get(j).getSchool()+1);
			 this.S.get(j).setRegret(this.S.get(j).findRankingByID(this.S.get(j).getSchool()+1));
		 }
		 // find the average regret for suitors by dividing the total by the # of suitors
		 this.avgSuitorRegret = suitorSum / this.S.size();
		 
		 // loop through the receivers and sum up their total regret
		 for (int k = 0 ; k < this.R.size(); k++) {
			// find the regret by doing the ranks of the assigned suitor - 1
			 receiverSum += this.R.get(k).findRankingByID(this.R.get(k).getStudent()) - 1;
			 this.R.get(k).setRegret(this.R.get(k).findRankingByID(this.R.get(k).getStudent()) - 1);
		 }
		 // find the average regret for receivers by dividing the total by the # of receivers
		 this.avgReceiverRegret = receiverSum / this.R.size();
		 
		 // find the total # of ppl
		 peopleSum = suitorSum + receiverSum;
		 
		 // find the total regret
		 this.avgTotalRegret = peopleSum/(this.S.size()+this.R.size());
    }
    
    public boolean isStable() {
    	// check if a matching is stable
		boolean stable = true; // if the match is stable or not
		int rank = 0; // rank of receiver a suitor had assigned to them
		
		// loop through the suitors
		for (int i = 0 ; i < this.S.size() ; i++) {
			// if the regret of the suitor is greater than zero; meaning they did not get matched with their first choice
			if ( 0 < this.S.get(i).getRegret()) {
				//find the rank of the school they where matched with
				rank = this.S.get(i).findRankingByID(this.S.get(i).getSchool()+1)+1;
				// loop through the receivers
				for (int j = 0 ; j < rank - 1; j++) {
					// if the would rather this suitor than the one he was matched with then the match is unstable, so break the loop and return false
					if (this.R.get(this.S.get(i).getRanking(j)-1).getRanking(this.R.get(this.S.get(i).getRanking(j)-1).getStudent()) > this.R.get(this.S.get(i).getRanking(j)-1).getRanking(i) ) {
						stable = false;
						break;
						
					}
				}
			}
			// if at the end of the first student's loop the match is unstable, break out of the loop, no need to keep going
			if (stable == false) {
				break;
			}
		}
		// return if the matching is stable or not
		return stable;
    }
    
    // print methods
    public void print() {
    	// print the matching results and statistics
    	String stableMatch = ""; // empty sting for stable match result; yes p\or no
    	// if match return true; if matches exist
    	if (match())
	        // if stable is true, print yes
	 		if (isStable() == true) {
	 			stableMatch = "Yes";
	 		}
	 		// otherwise, it is not stable, print no
	 		else {
	 			stableMatch = "No";
	 		}
	 		// print out the results of stability and all the averages
	 		System.out.println("\nStable matching? " + stableMatch);
	 		// call the print statistics method
	 		printStats();
	 	
    }
    
    public void printMatches() {
    	// prints matches
    	if (this.matchesExist == false) {
    		// if matches exist is false then print no matches exist
    		System.out.print("ERROR: No matches exist!\n");
    	}
    	else {
    		// call the match method
    		match();
    		// print matches format
			System.out.println("\nMatches:");
			System.out.println("--------");
			
			// loop through each choose and print the student they where matched with
			for (int i = 0 ; i < this.R.size(); i++) {
				System.out.println(this.R.get(i).getName() + ": " + this.S.get(this.R.get(i).getStudent()).getName());
			}
    	}
        
    }
    
    public void printStats() {
    	// print matching statistics
    	// call the function that calculates regret
        calcRegret();
        // print out the values
        System.out.format("Average student regret: %.2f", this.avgSuitorRegret);
		System.out.format("\nAverage school regret: %.2f", this.avgReceiverRegret);
		System.out.format("\nAverage total regret: %.2f", this.avgTotalRegret);
		// print a new line
		System.out.println("\n");
    }

}

