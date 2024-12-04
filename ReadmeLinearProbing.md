# Linear probing Java Final
This is my Data structures Final on Linear probing. 
Also keep in mind all the tables do take a while to display since our table sizes are very large
First I have all the imported java built in classes
Next is the wordslist string that holds all 50 words our group chose to search for but this is just a string of all the words that can be called.
The first method is to convert our words into ascii values and returns the total value of the word.
The next method is Hashing this method will hash our table so all the words follow a certain prbing method and my code fllows linear probing.
This method wil count the total collisions , maximum collisions,and empty positions.Then after keeping count it will calculate the average collisions. These will all be displayed at the end of the final hashtable.
The method printarray will display the hashtable and all its data found with in like index,Data and collisions.
The Searchword method is the part that we use to search for certain words within our hashtable so now those 50 words the group chose earlier will now help us because this code will take a word from that list and check if it can be found within my hashtable. Also it will display the word, nanotime to find the word, and how many collisions it took to find the word.
Next is the main method which holds all the call functions for all the methods I created.
first we have the print stream which will print all of our data into a txt file because it is much faster than printing to the terminal.
We also have file input which reads the file and it also makes sure that if the file can not be read or found to report there is an error.
Then we have our table sizes which are prime .7 765211, prime .5 998651, nonprime .7 689900 and Nonprime .5  998654. Each table has its own size based on its loadfactor and wheather its prime or not.
after we have our numtosearchword which can be changed depending on how many words you want to search with. So we hvae to find 10,20,30,40 then 50 so this calles to that method and initlizes how many of those 50 words are being called.
Next is all the displays for the hashtables so we have all the prime and Nonprime .7 and .5. These tables hold index, data, and collisions. At the end of every table they have all the collisions(max,totl,avg) and it will display empty positions.
If you are trying to look at a specific table I would put in comments the tables I would not want to view because it takes less time to find the specific one rather than looking up every table.
Finally theres all the displays for searchwords. These work by having all of them on or putting the ones you dont want in comments and the ones you do want to run not in comments. These also display all the prime and nonprime .7 and .5 its adjustable so you can check each table by having it on or off. But to view these fully you must make sure the corresponding hashtable displays too.
Also at the end we have a catch IO exception to catch if none of this data will display to the txt file if not this will catch it and report a error.
This is the full code for linear probing this readme file expalins how all the code works and any hidden features/ comments that can help the code run smoother.
