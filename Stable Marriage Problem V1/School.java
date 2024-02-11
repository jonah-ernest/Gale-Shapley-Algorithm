import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class School {
	private String name;        // name
    private double alpha;       // GPA weight
    private int[] rankings;     // rankings of students
    private int student;        // index of matched student
    private int regret;         // regret
    
    // constructors
    public School(){
    	this.name = null;
    	this.alpha = 0.0;
    	this.student = -1;
    	this.regret = 0;
    	
    	this.rankings = new int[20];
    	/*for(int i = 0; i < this.rankings.length; i++) {
    		this.rankings[i] = 0;
    	}*/
    }
    public School(String name, double alpha, int nSchools){
    	this.name = name;
    	this.alpha = alpha;
    	this.student = -1; 
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
    public double getAlpha(){ // gets GPA weight
        return this.alpha;
    }
    public int getRanking(int i){ // gets the rank of the i-th student for this school
        return this.rankings[i];
    }
    public int getStudent(){ // gets the student index this school was matched with
        return this.student;
    }
    public int getRegret (){ // // gets the school's regret after matching
        return this.regret;
    }

    // setters
    public void setName(String name){ // sets name
    	this.name = name;
    }
    public void setAlpha(double alpha){ // sets GPA weight
    	this.alpha = alpha;
    }
    public void setRanking(int i, int r){ // sets the rank of the i-th student for this school as r
    	this.rankings[i] = r;
    }
    public void setStudent(int i){ // sets the student i as the student this school was matched with
    	this.student = i;
    }
    public void setRegret(int r){ // sets the school regret after matching
    	this.regret = r;
    }
    public void setNStudents(int n){ // sets and initializes the rankings array and sets it as zero
    	this.rankings = new int[n];
    	for(int i = 0; i < n; i++) {
    		this.rankings[i] = 0;
    	}
    }
    
    // find student ranking based on school ID
    public int findRankingByID(int ind){
    	return this.getRanking(ind-1);
    }

    // get new info from the user
    public void editInfo(ArrayList<Student> S, boolean canEditRankings) throws IOException{
    	System.out.print("\n");
    	String newName = Pro3_ernestjo.getString("Name: ");
    	this.setName(newName); //replace the old name with the new one
    	
    	double newAlpha = Pro3_ernestjo.getDouble("GPA weight: ", 0.00, 1.00);
    	this.setAlpha(newAlpha);
    	
    	if(canEditRankings) { // if rankings have already been established, we rerank all the students
    		for(int i = 0; i < this.rankings.length; i++) { // clear rankings array list 
    			this.rankings[i] = 0;
    		}
    		calcRankings(S); // recalculate the rankings based on the edited GPA weights and reassign the rankings to the array
    	}
    }

    // calculate rankings of each student for each school based on weight alpha
    public void calcRankings(ArrayList<Student> S){ 
    	ArrayList<Double> CS = new ArrayList<Double>(); // store unsorted composite scores in an array list
    	ArrayList<Double> copyCS = new ArrayList<Double>();
    	double compositeScore;
    	
    	for(int i = 0; i < S.size(); i++) { // calculate the composite score for each student and place it in the unsortedRankings array list
			compositeScore = ( ((this.getAlpha()) * (S.get(i).getGPA()) ) + ((1 - (this.getAlpha()) ) * (S.get(i).getES()) ) );
			CS.add(compositeScore);
    	}
    	
    	copyCS.addAll(CS); //make a copy of the unsorted composite score array list
    	Collections.sort(copyCS, Collections.reverseOrder()); // sort array list from largest to smallest composite scores for 
    	
    	for(int i = 0; i < CS.size(); i++) { // loop through the unsorted array of CS each of its entries to the copy
	    	for(int j = 0; j < copyCS.size(); j++) { 
	    		if(CS.get(i) == copyCS.get(j)) { // if an entry of the sorted is equal to an entry of the unsorted then set the i-th student's ranking for this school as j+1
	    			this.setRanking(i, j+1); 
	    		}
	    	}
    	}
    }

    // print school info and assigned student in tabular format
    public void print(ArrayList<Student> S, boolean rankingsSet){
    	System.out.print(this.getName());
    	for(int i = 0; i < (32 - (this.getName()).length()); i++ ) { // print spaces in between name and weight
    		System.out.print(" ");
    	}
    	System.out.format("%.2f  ", alpha); // print the weight
    
    	
    	// printing the assigned school
		boolean matchedOccured;
		if(this.getStudent() == -1) { // if matching hasn't happened
    		matchedOccured = false; // set to false if matching hasn't occurred
    	}
    	else {
    		matchedOccured = true;
    	}
		
		if(matchedOccured == true) {
			System.out.print(S.get(this.getStudent()).getName()); // print the assigned student for this school
			for(int i = 0; i < (27 - S.get(this.getStudent()).getName().length()); i++) {
				System.out.print(" ");
			}
		}
		else {
			System.out.print("-"); // if matching hasn't occurred print a dash
			for(int i = 0; i < 26; i++) {
				System.out.print(" ");
			}
		}
    }

    // print the rankings separated by a comma
    public void printRankings(ArrayList<Student> S){
    	ArrayList<String> sortedByRankH = new ArrayList<String>();
    	String currentNameH = "";
    	
    	for(int i = 0; i < S.size(); i++) {
    		for(int j = 0; j < S.size(); j++) {
    			if(this.getRanking(j) == i+1) { // index starts at 0, so add 1
    				currentNameH = S.get(j).getName();
    				sortedByRankH.add(currentNameH); // add the students' names in order of rank to the sorted array list
    			}
    		}
    	}
    	
    	for(int i = 0; i < S.size()-1; i++) { // print all students in ranking order except the last one 
    		System.out.print(sortedByRankH.get(i) + ", ");
    	}
    	System.out.print(sortedByRankH.get(this.rankings.length-1)); // print the last ranked student
    	System.out.print("\n");
    }
    
    // reset the rankings of students for this school
    public void resetRanks() {
    	if(this.rankings != null) { // if rankings have already been established
    		for(int i = 0; i < this.rankings.length; i++) {
    			this.setRanking(i, 0);
    		}
    	}
    }
    
    // reset the matched student for this school
    public void resetAssignedStudent() { // reset the student's assigned school
    	this.setStudent(-1);
    }

}