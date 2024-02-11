import java.io.IOException;
import java.util.ArrayList;

public class Student {
    private String name;        // name
    private double GPA;         // GPA
    private int ES;             // extracurricular score
    private int[] rankings;     // rankings of schools
    private int school;         // index of matched school
    private int regret;         // regret

    // constructors
    public Student(){
    	this.name = null;
    	this.GPA = 0.0;
    	this.ES = 0;
    	this.school = -1;
    	this.regret = 0;
    	this.rankings = new int[20];
    }
   
    public Student(String name, double GPA, int ES, int nSchools){
    	this.name = name;
    	this.GPA = GPA;
    	this.ES = ES;
    	this.school = -1;
    	this.regret = 0;
    	this.rankings = new int[20];
    	for(int i = 0; i < nSchools; i++) {
    		this.rankings[i] = 0;
    	}
    }

    // getters
    public String getName(){ // gets name
        return this.name;
    }
    public double getGPA(){ // gets student GPA
        return this.GPA;
    }
    public int getES(){ // gets student extracurricular score
        return ES;
    }
    public int getRanking(int i){ // gets the rank of the i-th school for this student
        return this.rankings[i];
    }
    public int getSchool(){ // gets the school this student was matched with
        return this.school;
    }
    public int getRegret(){ // gets the student regret after matching
        return this.regret;
    }

    // setters
    public void setName(String name){ // sets name
    	this.name = name;
    }
    public void setGPA(double GPA){ // sets student GPA
    	this.GPA = GPA;
    }
    public void setES(int ES){ // sets student extracurricular score
    	this.ES = ES;
    }
    public void setRanking(int i, int r){ // sets the rank of the i-th school for this student as r
    	this.rankings[i] = r;
    }
    public void setSchool(int i){ // sets the school i as the school this student was matched with
    	this.school = i;
    }
    public void setRegret(int r){ // sets the student regret after matching
    	this.regret = r;
    }
    public void setNSchools(int n){ // sets and initializes the rankings array and sets it as zero
    	this.rankings = new int[n];
    	for(int i = 0; i < n; i++) {
    		this.rankings[i] = 0;
    	}
    }
    
    // find school ranking based on school ID
    public int findRankingByID(int ind) {
    	return this.getRanking(ind-1);
    }

    // get new info from the user
    public void editInfo(ArrayList<School> H, boolean canEditRankings) throws IOException{
    	System.out.print("\n");
    	String newName = Pro3_ernestjo.getString("Name: "); //get new name from user
    	this.setName(newName); // replace the old name with the new one
    	
    	double newGPA = Pro3_ernestjo.getDouble("GPA: ", 0.0, 4.0);
    	this.setGPA(newGPA);
    	
    	int newES = Pro3_ernestjo.getInteger("Extracurricular score: ", 0, 5);
    	this.setES(newES);
    	
    	boolean notValid = true;
    	if(canEditRankings) { // change rankings
    		while(notValid) { // loop until user enters y/n
	    		String editRankings = Pro3_ernestjo.getString("Edit rankings (y/n): ");
	
	    		if(editRankings.equals("y") || editRankings.equals("Y") ){ // change rankings
	    			notValid = false;
	    			
	    	        	System.out.println("Student "+ this.getName() + "'s " + "rankings:");
	    	        	 
	    	        	for(int i = 0; i < this.rankings.length; i++) { //clear rankings array
	    	        		this.rankings[i] = 0;
	    	        	}
	    	        	this.editRankings(H); // rerank the schools
	    			
	    		}
	    		
	    		else if (editRankings.equals("n") || editRankings.equals("N")){
	    			notValid = false;
	    			return;
	    		}
	    		
	    		else {
	    			System.out.println("ERROR: Choice must be 'y' or 'n'!");
	    			notValid = true;
	    		}
    		}
    	}
    }

    // edit the student's rankings of schools
    public void editRankings(ArrayList<School> H){ // apart of assign rankings
    	int schoolRanking = 0;
    	
    	for(int j = 0; j < H.size(); j++) { // loops through each school 
    		
    		boolean notValid = true;
    		do {
    			schoolRanking = Pro3_ernestjo.getInteger("School " + H.get(j).getName() + ": ", 1, H.size());
        		
        		for(int rank = 0; rank < H.size(); rank++) { // compare the schoolRanking to all the other rankings to see if it has already been used
        			//check for the error that the rank is already used
        			if(schoolRanking == this.getRanking(rank)) {
        				System.out.println("ERROR: Rank " + this.getRanking(rank) + " already used!\n");
        				notValid = true;
        				break;
        			}
        			else {
        				notValid = false;
        			}
        		}
    		} while(notValid == true);
    		this.setRanking(j, schoolRanking); // set the student's ranking of each school as long as the rank was valid
    	}
    	System.out.print("\n");
    }

    // print student info and assigned school in tabular format
    public void print(ArrayList<School> H, boolean rankingsSet) {
    	System.out.print(this.getName());
    	for(int i = 0; i < (31 - (this.getName()).length()); i++ ) { // print spaces in between name and GPA
    		System.out.print(" ");
    	}
    	System.out.format("%.2f   %d  ", GPA, ES); // print the GPA and Extracurricular score
    	
    	// printing the assigned school
			boolean matchedOccured;
			if(this.getSchool() == -1) { // if matching hasn't happened
	    		matchedOccured = false; // set to false if matching hasn't occurred
	    	}
	    	else {
	    		matchedOccured = true;
	    	}
			
			if(matchedOccured == true) {
				System.out.print(H.get(this.getSchool()).getName()); // if match has already occurred, print the matched school for this student
				for(int i = 0; i < (27 - H.get(this.getSchool()).getName().length()); i++) { // print spaces after assigned school
					System.out.print(" ");
				}
			}
			else {
				System.out.print("-");
				for(int i = 0; i < 26; i++) {
					System.out.print(" "); // print the spaces after the assigned school
				}
			}
    }
    
    // print the rankings separated by a comma (under preferred school order)
    public void printRankings(ArrayList<School> H) {
    	ArrayList<String> sortedByRank = new ArrayList<String>();
    	String currentName = "";
	    	
    		for(int i = 0; i < H.size(); i++) { // for each school
	    		for(int j = 0; j < H.size(); j++) { //compare every school's ranking to the first entry in the array
	    			if(this.getRanking(j) == i+1) { // if the rank of the school is equal to the current rank
	    				currentName = H.get(j).getName();
	    				sortedByRank.add(currentName); // add the schools' names in order of rank to sorted array list
    				}
	    		}
	    	}
    		
    	// printing the preferred school order	
    	for(int i = 0; i < H.size()-1; i++) { // print the preferred school order
    		System.out.print(sortedByRank.get(i) + ", ");
    	}
    	System.out.print(sortedByRank.get(this.rankings.length-1));
    	System.out.print("\n");
    	
    }
    
    // reset the student rankings
    public void resetRanks() {
    	if(this.rankings != null) { // if rankings have already been established
    		for(int i = 0; i < this.rankings.length; i++) {
    			this.setRanking(i, 0);
    		}
    	}
    }
    
    // reset the matching
    public void resetAssignedSchool() { // reset the student's assigned school
    	this.setSchool(-1);
    }
}
