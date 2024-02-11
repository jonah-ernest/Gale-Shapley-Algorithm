import java.util.ArrayList;
import java.util.Collections;

public class SMPSolver {
	private ArrayList < Participant > S = new ArrayList < Participant >() ; // suitors
	private ArrayList < Participant > R = new ArrayList < Participant >() ; // receivers
	private double avgSuitorRegret ; // average suitor regret
	private double avgReceiverRegret ; // average receiver regret
	private double avgTotalRegret ; // average total regret
	private boolean matchesExist ; // whether or not matches exist
	private boolean stable ; // whether or not matching is stable
	private long compTime ; // computation time
	private boolean suitorFirst ; // whether to print suitor stats first
	private int worstSuitorRegret; // hold the worst suitor regret for the SMP object
	
	// constructor
	public SMPSolver () {
		this.avgSuitorRegret = -1;
    	this.avgReceiverRegret = -1;
    	this.avgTotalRegret = -1;
    	this.matchesExist = false;
    	this.stable = false;
    	this.compTime = -1;
    	this.suitorFirst = true;
    	this.worstSuitorRegret = -1;
	}
	
	// getters
	public double getAvgSuitorRegret () {
		// get the average suitor regret of the smp object
		return this.avgSuitorRegret;
	}
	public double getAvgReceiverRegret () {
		// get the average receiver regret of the smp object
		return this.avgReceiverRegret;
	}
	public double getAvgTotalRegret () {
		// get the average total regret of the smp object
		return this.avgTotalRegret;
	}
	public boolean matchesExist () {
		// get the matches exist boolean of the smp object
		return this.matchesExist;
	}
	public boolean isStable () {
		// get the astability boolean of the smp object
		return this.stable;
	}
	public long getTime () {
		// get the computation time of the smp object
		return this.compTime;
	}
	public int getNSuitorOpenings () {
		// get the get the number of suitor openings of the smp object
		return this.S.size();
	}
	public int getNReceiverOpenings () {
		// get the number of receiver openings of the smp object
			return this.R.size();
	}
	public int getWorstSuitorRegret () {
		return this.worstSuitorRegret;
	}
	
	// setters
	public void setMatchesExist ( boolean b ) {
		// set the matches exist boolean of the smp object
		this.matchesExist = b;
	}
	public void setSuitorFirst ( boolean b ) {
		// set the suitor first boolean of the smp object
		this.suitorFirst = b;
	}
	public void setTime (int i) {
		// set the computation time to i, of the smp object
		this.compTime = i;
	}
	public void setParticipants ( ArrayList <? extends Participant > S , ArrayList <?extends Participant > R ) {
		// add the suitor and receiver objects of the passed arraylists to the arraylists of the SMPSlover object
    	for (int i = 0; i < S.size(); i++) {
    		this.S.add(S.get(i));
    	}
    	for (int i = 0; i < R.size(); i++) {
    		this.R.add(R.get(i));
    	}
	}
	
	// methods for matching
	public void clearMatches () {
		// clear out existing matches
		for (int i = 0; i < S.size(); i++) {
    		this.S.get(i).setMatch(i);
    	}
    	for (int i = 0; i < R.size(); i++) {
    		this.R.get(i).setMatch(i);;
    	}
    	// set that matches exist to true
    	this.matchesExist = true;
		
	}
	
	public void worstSuitor (int i) {
		ArrayList < Integer > regrets = new ArrayList < Integer >();
		regrets.add(i);
		for (int j = 0; j < regrets.size(); j++) {
			if (regrets.get(j) < regrets.get(j - 1)) {
				this.worstSuitorRegret = regrets.get(j);
			}
			
		}
	}
	public boolean matchingCanProceed () {
		// check that the conditions to proceed with matching are satisfied
		int suitorsMatchOpenings = 0, reciversMatchOpenings = 0;
		// loop through and find the number of openings for suitors and receivers
		for (int i = 0; i < this.S.size(); i++) {
			suitorsMatchOpenings += this.S.get(i).getMaxMatches();
		}
		for (int i = 0; i < this.R.size(); i++) {
			reciversMatchOpenings += this.R.get(i).getMaxMatches();
		}
		// if the suitors array size is zero print the following error
		if (this.S.size() == 0) {
			System.out.print("\nERROR: No suitors are loaded!\n");
		}
		// if the receiver array size is zero print the following error
		else if (this.R.size() == 0) {
			System.out.print("\nERROR: No recivers are loaded!\n");
		}
		// if the number of openings for suitors and receivers are not equal print the following error
		else if (suitorsMatchOpenings != reciversMatchOpenings) {
			System.out.print("ERROR: The number of suitor and receiver openings must be equal!\n");
		}
		// other wise return true
		else {
			return true;
		}
		// if an error was printed it will return false
		return false;
	}	
	public boolean match () {
		// Gale - Shapley algorithm to match ; students are suitors
		int engagementsMade = 0, receiverPassed = -1;
		long start; // initialize start variable
    	start = System.currentTimeMillis(); // get current time
    	// set matches exist to false
    	this.matchesExist = false;

    	boolean matchesToBeMade = true, unmatched = true, proceed = false, proceed2 = false; //initialize and set matches to be made to true; this means there are still suitors to match
    	if (matchingCanProceed() == true) {
    		// while there are still suitors to be matched and they are bing matched first
    		if (this.suitorFirst == true) {
	    		while (matchesToBeMade) {
	    			// loop through the suitors
	    			for (int j = 0; j < this.S.size(); j++) {
	    				// check if the suitor is free
	    				unmatched = this.S.get(j).isFull();
	    				if (unmatched == true) {
	    					engagementsMade = 0;
	    					// if they are set matches to be made to true
	    					matchesToBeMade = true;
	    					// loop through the rankings
	    					for (int k = 0; k < this.R.size(); k++) {
	    						// if the suitor is of class student
	    						if (this.S.get(0) instanceof Student) {
       								receiverPassed = this.S.get(j).getRanking(k) - 1;
       								// get the index of the receiver
       							}
	    						// if the suitor is of class school
       							else if (this.S.get(0) instanceof School) {
       								receiverPassed = this.S.get(j).getRankingByIndex(k+1);
       								// get the index of the receiver
       							}
	    						// check if the receiver is not already engaged by calling the make proposal method
	    						if (makeProposal(j,receiverPassed)) {
	    							// if it is not set the engagement by calling the make engagement method
	    							makeEngagement(j,receiverPassed,-1);
	    							// check if all the matches for this suitor have been made, and if so break and go to the next suitor
	    							if (this.S.get(j).existingMatches() == 0) {
	    								break;
	    							}
	    						}
	    						else {
	    							// if the suitor is of class student
	       							if (this.S.get(0) instanceof Student) {
	       								proceed = false;
	       								worstSuitor (this.R.get(this.S.get(j).getRanking(k) - 1).getWorstMatch());
		    							// otherwise if the receiver likes this the given suitor more then they like the suitor they are with right now
		    							if (this.R.get(this.S.get(j).getRanking(k) - 1).getRanking(j) < this.R.get(this.S.get(j).getRanking(k) - 1).getRanking(this.R.get(this.S.get(j).getRanking(k) - 1).getWorstMatch())) {
		    								proceed = true;
		    							}
	       							}
	       							// if the suitor is of class school
	       							else if (this.S.get(0) instanceof School) {
	       								proceed2 = false;
	       								worstSuitor (this.R.get(this.S.get(j).getRankingByIndex(k+1)).getWorstMatchAlternate());
		    							// otherwise if the receiver likes this the given suitor more then they like the suitor they are with right now
	       								if (this.R.get(this.S.get(j).getRankingByIndex(k+1)).getRankingByIndex(j+1) < this.R.get(this.S.get(j).getRankingByIndex(k+1)).getRankingByIndex(this.R.get(this.S.get(j).getRankingByIndex(k+1)).getWorstMatchAlternate()+1)) {
		    								proceed2 = true;
		    							}
	       							}
	       							// if the proceed variable is true
		    						if (proceed == true) {
	       								// make the engagement of the new pair
		    				    		makeEngagement(j,receiverPassed,this.R.get(receiverPassed).getWorstMatch());
		    							// check if all the matches for this suitor have been made, and if so break and go to the next suitor
		    				    		if (this.S.get(j).existingMatches() == 0) {
		    								break;
		    							}
	       							}
		    						else if (proceed2 == true) {
	       								// make the engagement of the new pair
		    				    		makeEngagement(j,receiverPassed,this.R.get(receiverPassed).getWorstMatchAlternate());
		    							// check if all the matches for this suitor have been made, and if so break and go to the next suitor
		    				    		if (this.S.get(j).existingMatches() == 0) {
		    								break;
		    							}
		    						}
	    						}
	    					}
    					}
	    			}
	    			// loop though the suitors
	    			for (int i = 0; i < this.S.size(); i++) {
	    				for (int p = 0; p < this.S.get(i).getMaxMatches(); p++) {
	    				// if any of the suitors still have not been matched, set matches to be made to true
		    				if (this.S.get(i).getMatch(p) == -1) {
		    					matchesToBeMade = true;
		    					break;
		    				}
		    				else {
		    					// otherwise set matches to be made to false
		    					matchesToBeMade = false;
		    				}
	    				}
	    				if (matchesToBeMade == true) {
	    					break;
	    				}
	    			}
	    		}
    		}
    		//  set matches exist to true once matching is complete
    		this.matchesExist = true;
    	}
    	else {
    		this.matchesExist = false;
    	}
    	// stop the elapsed time of how long it took to do the matching
    	this.compTime = System.currentTimeMillis()-start;
    	// return that matches have been set as true
        return matchesExist();
	}
	private boolean makeProposal (int suitor , int receiver ) {
		// suitor proposes to receiver
		for (int i = 0; i < this.R.get(receiver).getMaxMatches(); i++) {
	    	// receiver check if the receiver is free
	    	if (this.R.get(receiver).getMatch(i) == -1) {
	    		// if it is not free return false
	    		return true;
	    	}
		}
    	// otherwise return true
    	return false;
	}
	private void makeEngagement (int suitor , int receiver , int oldSuitor ) {
		// make suitor - receiver engagement , break receiver - oldSuitor engagement
		// if the match has not already been included in the matches array list
		if (this.S.get(suitor).alreadyInMatchesArray(receiver)) {
			// if there is an old suitor to switch with; it is not -1
			if (oldSuitor != -1) {
				// unmatch the old suitors and receivers from each other
				this.S.get(oldSuitor).unmatch(receiver);
				this.R.get(receiver).unmatch(oldSuitor);
			}
			for (int i = 0; i < this.R.get(receiver).getMaxMatches(); i++) {
		    	// receiver check if the receiver is free
		    	if (this.R.get(receiver).getMatch(i) == -1) {
		    		// match the new suitor and receiver with each other
		    		this.R.get(receiver).setRealMatch(i,suitor);
		    		break;
		    	}
			}
			
			for (int j = 0; j < this.S.get(suitor).getMaxMatches(); j++) {
		    	// receiver check if the receiver is free
		    	if (this.S.get(suitor).getMatch(j) == -1) {
		    		// match the new suitor and receiver with each other
		    		this.S.get(suitor).setRealMatch(j,receiver);
		    		break;
		    	}
			}
		}
	}
	public void calcRegrets () {
		// calculate regrets
		// loop through the suitors and receivers and sum up their total regret
    	double suitorSum = 0, receiverSum = 0, peopleSum = 0; // averages and sums of each category
    	
		 // loop through the suitors and sum up their total regret
		for (int j = 0 ; j < this.S.size(); j++) {
			// find the regret by doing the ranks of the assigned receiver - 1
			// if the suitors are from class student
			if (this.S.get(0) instanceof Student) {
				this.S.get(j).calcRegretAlternate();
				// sum up the regrets per suitor
				suitorSum += this.S.get(j).getRegret();
			}
			// if the suitors are from class school
			if (this.S.get(0) instanceof School) {
				this.S.get(j).calcRegret();
				// sum up the regrets per suitor
				suitorSum += this.S.get(j).getRegret();
			}
		 }
		 
		 // find the average regret for suitors by dividing the total by the # of suitors
		 this.avgSuitorRegret = suitorSum / this.S.size();
		 
		 // loop through the receivers and sum up their total regret
		 for (int k = 0 ; k < this.R.size(); k++) {
			 
			// if the receivers are from class student
			if (this.R.get(0) instanceof Student) {
				this.R.get(k).calcRegretAlternate();
				// sum up the regrets per receiver
				receiverSum += this.R.get(k).getRegret();
			}
			// if the receivers are from class school
			if (this.R.get(0) instanceof School) {
				this.R.get(k).calcRegret();
				// sum up the regrets per receiver
				receiverSum += this.R.get(k).getRegret();
			}
		}
		// find the average regret for receivers by dividing the total by the # of receivers
		this.avgReceiverRegret = receiverSum / this.R.size();
		 
		// find the total # of suitors and receivers
		peopleSum = suitorSum + receiverSum;
		 
		// find the total regret
		this.avgTotalRegret = peopleSum/(this.S.size()+this.R.size());
		
	}
	
	public boolean determineStability () {
		// calculate if a matching is stable
		int rankWM = 0, suitorWorstMatch = 0, preferedReceiver = 0, receiverRankOfCurrentSuitor = 0, receiverWorstMatchINDX = 0, receiverWorstMatch = 0; // rank of receiver a suitor had assigned to them
		// loop through the suitors
		for (int i = 0; i < this.S.size(); i++) {
			// if the suitors are from class student
			if (this.S.get(0) instanceof Student) {
				// index of suitors worst matched receiver
				suitorWorstMatch = this.S.get(i).getWorstMatchAlternate();
				// rank of the worst matched receiver
				rankWM = this.S.get(i).getRankingByIndex(suitorWorstMatch + 1) + 1;
			}
			// if the suitors are from class school
			else if (this.S.get(0) instanceof School) {
				// index of suitor's worst matched receiver
				suitorWorstMatch = this.S.get(i).getWorstMatch();
				// rank of the suitor's worst matched receiver
				rankWM = this.S.get(i).getRanking(suitorWorstMatch);
			}
			// loop through it's matches
			for (int j = 0; j < rankWM - 1; j++) {
				// if the suitors are from class student
				if (this.S.get(0) instanceof Student) {
				// index of the current receiver wrt the array list of receiver objects
					preferedReceiver = this.S.get(i).getRanking(j) - 1;
				}
				// if the suitors are from class school
				else if (this.S.get(0) instanceof School) {
					// index of the current receiver wrt the array list of receiver objects
					preferedReceiver = this.S.get(i).getRankingByIndex(j+1);
				}
				// if the proffered receiver has been matched with this suitor
				if (this.S.get(i).findIndexInMatch(preferedReceiver) == false) {
					// if the suitors are from class student
					if (this.S.get(0) instanceof Student) {
						// the rank the receiver that was not in the, matches array has for the current students
						receiverRankOfCurrentSuitor = this.R.get(preferedReceiver).getRanking(i);
						// the index the receiver has for the suitor that it was worst matched with
						receiverWorstMatchINDX = this.R.get(preferedReceiver).getWorstMatch();
						// the rank the receiver has for the suitor that it was worst matched with
						receiverWorstMatch = this.R.get(preferedReceiver).getRanking(receiverWorstMatchINDX);
					}
					// if the suitors are from class school
					else if (this.S.get(0) instanceof School) {
						// the rank the receiver that was not in the, matches array has for the current students
						receiverRankOfCurrentSuitor = this.R.get(preferedReceiver).getRankingByIndex(i+1) + 1;
						// the index the receiver has for the suitor that it was worst matched with
						receiverWorstMatchINDX = this.R.get(preferedReceiver).getWorstMatchAlternate();
						// the rank the receiver has for the suitor that it was worst matched with
						receiverWorstMatch = this.R.get(preferedReceiver).getRankingByIndex(receiverWorstMatchINDX+1) + 1;
					}
					// if the receiver would rather this suitor than it's current matched suitor then stability is flase
					// set stable to false and return false
					if (receiverRankOfCurrentSuitor < receiverWorstMatch) {
						this.stable = false;
						return false;
					}
				}
			}
		}
		// otherwise set stable to true and return true
		this.stable = true;
		return true;
	}
	
	// print methods
	public void print () {
		
		// print the matching results and statistics
    	String stableMatch = ""; // empty sting for stable match result; yes p\or no
    	// if match return true; if matches exist
    	if (this.matchesExist() == true) {
    		determineStability();
	        // if stable is true, print yes
	 		if (this.isStable() == true) {
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
	}
	
	public void printMatches () {
		// print matches
		String printString = "";
    	if (this.matchesExist == false) {
    		// if matches exist is false then print no matches exist
    		System.out.print("ERROR: No matches exist!\n");
    	}
    	// if the suitors are from class student
    	else if (this.S.get(0) instanceof Student) {
    		// print matches format
			System.out.println("\nMatches:");
			System.out.println("--------");
			
			// loop through each choose and print the student they where matched with
			for (int i = 0 ; i < this.R.size(); i++) {
				// add to print String the receiver name
				printString += this.R.get(i).getName() + ": ";
				for (int j = 0; j < this.R.get(i).getNMatches(); j++) {
					// add to printString the suitors the receiver was matched with
					printString += this.S.get(this.R.get(i).getMatch(j)).getName() + ", ";
				}
				// remove the comma after the last match and add a new line
				printString = printString.substring(0, printString.length() - 2) + "\n";
			}
			// print printString
			System.out.print(printString);
    	}
    	// if the suitors are from class school
    	else if (this.S.get(0) instanceof School) {
    		// print matches format
			System.out.println("\nMatches:");
			System.out.println("--------");
			
			// loop through each choose and print the student they where matched with
			for (int i = 0 ; i < this.S.size(); i++) {
				// add to print String the suitor name
				printString += this.S.get(i).getName() + ": ";
				// add to printString the receivers the suitor was matched with
				for (int j = 0; j < this.S.get(i).getNMatches(); j++) {
					printString += this.R.get(this.S.get(i).getMatch(j)).getName() + ", ";
				}
				// remove the comma after the last match and add a new line
				printString = printString.substring(0, printString.length() - 2) + "\n";
			}
			// print printString
			System.out.print(printString);
    	}
	}

	public void printStats () {
		// print matching statistics
    	// call the function that calculates regret
        calcRegrets();
        // print out the values
        System.out.format("Average suitor regret: %.2f", this.avgSuitorRegret);
		System.out.format("\nAverage receiver regret: %.2f", this.avgReceiverRegret);
		System.out.format("\nAverage total regret: %.2f", this.avgTotalRegret);
		// print a new line
		System.out.println("\n");
	}
	
	public void printStatsRow ( String rowHeading ) {
		String spaceString = " ", stableMatch = "", suitorRegret = "", receiverRegret = "", totalRegret = "", time = "";
		// if the stability boolean is true print yes
		if (this.isStable() == true) {
 			stableMatch = "Yes";
 		}
 		// otherwise, it is not stable, print no
 		else {
 			stableMatch = "No";
 		}
		// create string values for all the statistics being printed suitor regret, receiver regret, total regret, and comp time
		suitorRegret = String.format("%.2f", this.avgSuitorRegret);
		receiverRegret = String.format("%.2f", this.avgReceiverRegret);
		totalRegret = String.format("%.2f", this.avgTotalRegret);
		time = String.format("%o", this.compTime);

		// print stats as row
		// if the suitors are of object student
    	if (this.S.get(0) instanceof Student) {
    		// print all the stats in a line with the correct spacing
    		System.out.print("\n" + rowHeading + spaceString.repeat(28 - rowHeading.length() - stableMatch.length()) + stableMatch + spaceString.repeat(21 - receiverRegret.length()) + receiverRegret + spaceString.repeat(21 - suitorRegret.length()) + suitorRegret + spaceString.repeat(21 - totalRegret.length()) + totalRegret + spaceString.repeat(21 - time.length()) + time);
    	}
		// if the suitors are of object school
    	else if (this.S.get(0) instanceof School) {
    		// print all the stats in a line with the correct spacing
    		System.out.print("\n" + rowHeading + spaceString.repeat(28 - rowHeading.length() - stableMatch.length()) + stableMatch + spaceString.repeat(21 - suitorRegret.length()) + suitorRegret + spaceString.repeat(21 - receiverRegret.length()) + receiverRegret + spaceString.repeat(21 - totalRegret.length()) + totalRegret + spaceString.repeat(21 - time.length()) + time);
    	}
	}
	
	public void reset () {
		// reset everything
    	// set all attributes back to their original sate
    	this.avgSuitorRegret = -1;
    	this.avgReceiverRegret = -1;
    	this.avgTotalRegret = -1;
    	this.matchesExist = false;
    	this.suitorFirst = true;
    	this.stable = false;
    	this.worstSuitorRegret = -1;
    	// clear the arraylists of objects 
    	this.S.clear();
    	this.R.clear();	
	}
}