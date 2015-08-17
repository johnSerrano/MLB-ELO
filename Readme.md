README

Version 2.0
Version 2.0 is a generally capable ELO calulator. Changes include:
	- The input file may be specified by the user
	- There is no limit on the amount of teams that may be entered
	- Code has been cleaned up (deperately needed).

This program simulates a baseball season and calculates the ELO rating of all
baseball teams after the season. Each team is assigned an original rating of 
1200. 

USAGE:
java MLB_ELO [File]


INPUT FORMATTING

Input is read from a user-specified file.  

On the third unempty line in a row, the program will look for a score in this format:

   Name1 score1 Name2 score2
   <string> <int> <string> <int>

For sample input, see mlbtables2014.txt