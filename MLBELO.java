import java.lang.Math;
import java.util.*;
import java.io.*;

public class MLBELO
{
	//ARGS: [1] file to read

	static int K_FACTOR = 200;

    public static void main(String[] args)
    {
		int teamArrayTail = -1;	//points to last initialized element of teams
		int lineCounter = 0; 	//for error message purposes
		int counter = 0; 		//counts number of consecutive unempty lines
		
		Scanner scan;

		LinkedList<Team> teams = new LinkedList<Team>();

		try {
			scan = new Scanner(new File(args[0]));
		}
		catch(FileNotFoundException e) {
			System.out.println("File Not Found");
			System.exit(1);

			//to avoid "scan might not have been initialized" error
			//this line should never run
			scan = new Scanner(System.in);
		}

		//main loop
		while(scan.hasNextLine()){

		    String currentLine = scan.nextLine();

		    double t1Score = 0; //team 1 score
		    double t2Score = 0; //team 2 score


		    lineCounter++;

		    if (currentLine.equals("")){
				counter = 0;
				continue;
		    }
			//currentline is unempty
		    counter++;

		    if (counter==3){
				//currentLine is game result
				//format: 'AAA 0, BBB 0' or 'postponed'

				if (currentLine.equals("Postponed")){
				    continue;
				}

				//PARSE LINE
				Scanner scan2 = new Scanner(currentLine);
				String name = scan2.next();

				if(!existsInTeams(name, teams)){
				    //add to teams
				    teams.add(new Team(name));
				}

				try {
				    t1Score = scan2.nextInt();
				} catch(NoSuchElementException e) {
				    System.out.println("no such element exception: " + currentLine + 
				    	": line " + lineCounter);
				    System.exit(2);
				}

				String name2 = scan2.next();
				
				if(!existsInTeams(name2, teams)){
				    //add to teams
				    teams.add(new Team(name2));
				}

				t2Score = scan2.nextInt();
				
				//find teams
				//probably more work to sort array, so linear search
				Team team1=null, team2=null;

				for (int i = 0; i<teams.size(); i++){

				    if (teams.get(i).getName().equals(name))
						team1 = teams.get(i);

				    else if (teams.get(i).getName().equals(name2))
						team2 = teams.get(i);
				}

				//we only are concerned with win or lose, not runs
				//we don't need to set t2score here since it is implicit
				if(t1Score > t2Score){
				    t1Score = 1;
				} else if ((int) t1Score == (int) t2Score) {
					t1Score = 0.5;
				} else {
				    t1Score = 0;
				}

				evaluateRatings(team1, team2, t1Score);
		    }
		}

		for (int i = 0; i < teams.size(); i++){
		    System.out.println(teams.get(i).getName() + " " + 
		    	(int) teams.get(i).getRating());
		}
	}


	private static boolean existsInTeams(String name, LinkedList<Team> teams)
	{
	    for (int i = 0; i < teams.size(); i++){
			if (teams.get(i).getName().equals(name)){
		    	return true;
			}
		}
		return false;
	}

	private static void evaluateRatings(Team teamA, Team teamB, double scoreA)
	{
		double scoreB;

		double ratingA; 
		double ratingB; 
		double expectedA; 
		double expectedB;
		
		scoreB = 1-scoreA;

		ratingA = teamA.getRating();
		ratingB = teamB.getRating();

		//elo algorithm
		expectedA = 1/(1+ Math.pow(10, (ratingB - ratingA)/400));
		expectedB = 1/(1+ Math.pow(10, (ratingA - ratingB)/400));
		teamA.setRating(ratingA + K_FACTOR * (scoreA - expectedA));
		teamB.setRating(ratingB + K_FACTOR * (scoreB - expectedB));
	}

}

class Team
{
    private double rating;
    private String name;
    
    Team(String inName)
    {
		name = inName;
		rating = 1200;
    }
    
    String getName()
    { 
    	return name;
    }

    void setRating(double newRating)
    { 
    	rating = newRating;
    }

    double getRating()
    {
    	return rating;
    }
}
