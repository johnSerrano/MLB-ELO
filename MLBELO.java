import java.lang.Math;
import java.util.*;
import java.io.*;

public class MLBELO{

    public static void main(String[] args){
	
	Team[] teams = new Team[50];
	int TAindex = 0;
	Scanner scan;
	int lineCounter = 0;
	teams[0] = new Team("Average: ");

	while(true){
	    try{
		scan = new Scanner(new File("mlbtables2014.txt"));
	    }
	    catch(FileNotFoundException e){
		System.out.println("FNF");
		continue;
	    }
	    break;
	}

	int counter = 0;

	//main loop
	while(scan.hasNextLine()){
	    String currentLine = scan.nextLine();
	    lineCounter++;
	    int t1Score = 0, t2Score = 0;
	    if (currentLine.equals("")){ //might be something other than "" ie "\n"
		counter = 0;
		continue;
	    }
	    counter++;
	    if (counter==3){
		//currentLine is game result
		// AAA 0, BBB 0 or postponed
		if (currentLine.equals("Postponed")){
		    continue;
		}
		Scanner scan2 = new Scanner(currentLine);
		String name = scan2.next();

		if(existsInTeams(name, teams)){
		    //do nothing
		}
		else{
		    //add to teams
		    TAindex++;
		    teams[TAindex] = new Team(name);
		}
		try{
		    t1Score = scan2.nextInt();
		}
		catch(NoSuchElementException e){
		    System.out.println("56 no such element cl: " + currentLine + lineCounter);
		}
		String name2 = scan2.next();
		
		if(existsInTeams(name2, teams)){
		    //do nothing
		}
		else{
		    //add to teams
		    TAindex++;
		    teams[TAindex] = new Team(name2);
		}
		t2Score = scan2.nextInt();
		Team t1=null, t2=null;
		for (int i = 0; teams[i]!=null; i++){
		    if (teams[i].getName().equals(name)){
			t1 = teams[i];
		    }
		    else if (teams[i].getName().equals(name2)){
			t2 = teams[i];
		    }		    
		}
		if(t1==null){
		    System.out.println("82 t1 " + name);
		}
		if(t2 == null){
		    System.out.println("85 t2 " + name + " " + lineCounter);
		}
		if(t1Score > t2Score){
		    t1Score = 1;
		} else {
		    t1Score = 0;
		}
		
		play(t1, t2, t1Score);
	    }
	}
	for (int i = 0; teams[i]!=null; i++){
	    System.out.println(teams[i].getName() + " " + (int) teams[i].getRating());
	}
    }

    private static boolean existsInTeams(String name, Team[] teams){
	if(teams[0]==null){
	    return false;
	}
	else {
	    for (int i = 0; teams[i]!=null; i++){
		if (teams[i].getName().equals(name)){
		    return true;
		}
	    }
	}
	return false;
    }

    private static void play(Team teamA, Team teamB, double scoreA){
	double scoreB, ratingA, ratingB, expectedA, expectedB;
	int k = 200; // ELO k factor can be tweaked to affect rate of rating change
	scoreB = 1-scoreA;
	ratingA = teamA.getRating();
	ratingB = teamB.getRating();

	//elo algorithm
	expectedA = 1/(1+ Math.pow(10, (ratingB - ratingA)/400));
	expectedB = 1/(1+ Math.pow(10, (ratingA - ratingB)/400));
	teamA.setRating(ratingA + k*(scoreA - expectedA));
	teamB.setRating(ratingB + k*(scoreB - expectedB));
    }

}

class Team{
    private double rating;
    private String name;
    
    Team(String inName){
	name = inName;
	rating = 1200;
    }
    
    String getName(){ return name; }
    void setRating(double newRating){ rating = newRating; }
    double getRating(){ return rating;  }
}
