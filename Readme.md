README

This program simulates a baseball season and calculates the ELO rating of all baseball teams after the season. Each team is assigned an original rating of 1200.

The ELO algorithm used by chess players is employed to evaluate baseball teams.

The program is actually a generally capable elo calculator. It reads input from a file (the formatting for which is documented below) and can remember up to 50 "teams" (this number can be increased). 

INPUT FORMATTING

On the third unempty line in a row, the program will look for a score in this format:

   Name1 score1 Name2 score2

Name1 and Name2 will be automatically added to the set of names if they are not already present. These will be interpreted as strings.
score1 and score2 are ints. A higher score is considered to have won. The difference between score1 and score2 does not affect the rating, only which is greater is considered. In the event of a tie, the current code will assume Name2 won. This will be amended in a later version.
       
example input:

asdasd
jkljkl
STL 4 LAD 3

The strange input form is a result of the convoluted process by which mlbtables2014.txt was created. It could be easily changed to be more logical and a subsequent version should implement this.