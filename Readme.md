# Linear probing Java Final
This is my Data structures Final on Linear probing. 
We first have all of the java imports to insert whch help with a lot of built in classes for this code
As we get into the main class linear probing java final we first have a list of words
This is the list of 50words that was slected by the group these words will be used to be searched and they will be used to help find search time and collisions to find the words
Also depending on how many you select there are 50 words so you can get any range of them by telling the code the amount you want
ConvertAscii this method takes the words from out txt file words_a and we convert them to be a ascii value this works by adding up each letter corisponding to there ascii value. We do this because it is simpler for the code and we can not mismatch convert int[] with string[]
hashing this method hashes our table and depending on what your hashing is it will varey since my project is on linear probing my hash table will hash by going down the table until a spot is found with in the table 
This method also contains the counts for maxcollisions, totalcollisions, and emptypositions. These are like counters it will add one everytime that portion of the code is exicuted but in the case for empty positions instead of adding it will subtract one since it is based off the table size
This code will also do the math for the collisions/emptyposition and displays each of there values depending on the size of the hashtable. Also we can get the average for collisions by taking the total collisions and dividing it by N
The Printarray method is the way to display our data in rows and columns. The way this is set up is by having three rows Index Data and collisions these keept track of there names and below them is the actual data that is inserted So it will show the index the data that corresponds with the index and then the amount of collisions that have happend in the spot
searchword this method will take the words from our 50 words list and depending on how many of them are called it will then search for that particular words they call for. Also the code will specifiy which hashtable it is searching from because each of them are diffrent since they have diffrent sizes
This method uses nano time to keep track of the amount of time it takes to find each word at first we used milliseconds but that was to slow we need a time that counts really fast because this search is so fast to find the specific word
Now we are at the main method this holds all of our call to methods and all of our outputs
