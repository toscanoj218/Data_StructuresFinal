import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class LinearProbingJavaFinal 
{

    // method to convert words to ascii values
    static int calculateAscii(String str) 
    {
        int total = 0;
        for (char c : str.toCharArray()) 
        {
            total += c;
        }
        return total;  // returns the total value of the word
    }

    // method to hash data into the table
    static void hashing(int[] table, int[] collisions, int tsize, String[] arr, int N) 
    {
        // Initialize the table and collision count
        for (int i = 0; i < tsize; i++) {
            table[i] = -1;  
            collisions[i] = 0;
        }

        // add data to the table
        for (int i = 0; i < N; i++) {
            int asciiValue = calculateAscii(arr[i]);
            int hashValue = asciiValue % tsize;

            // use linear probing to insert data into a different hash value
            int j = 0;
            while (table[(hashValue + j) % tsize] != -1) {
                j++;
                collisions[(hashValue + j - 1) % tsize]++; 
            }

            // insert the word to the corresponding index
            table[(hashValue + j) % tsize] = asciiValue;
        }
    }

    // Display the hash table
    static void displayTable(int[] table, int[] collisions, int tsize, PrintStream out) {
        out.println("Index | Data | Collisions");//this will display the names of the columns 
        out.println("-------------------------------------");//a line to seperate the column names from the data,index, and the collisions being counted 

        for (int i = 0; i < tsize; i++) 
        {
            out.printf("%5d | %12d | %9d\n", i, table[i], collisions[i]);//will display the index, data, and collisons but they will be seperated by |  |   | 
        }
    }

    // Displays collision statistics in the hashtable 
    static void displayCollisions(int[] table, int[] collisions, int tsize, PrintStream out) 
    {
        int totalCollisions = 0;//set total collisions to zero
        int maxCollisions = 0;//set max collisions to zero
        int emptyPositions = 0;//set empty positions to zero

        // Calculate total collisions, max collisions, and empty positions within my table
        for (int i = 0; i < tsize; i++) {
            totalCollisions += collisions[i];
            if (collisions[i] > maxCollisions) {
                maxCollisions = collisions[i];
            }
            if (table[i] == -1) {
                emptyPositions++;
            }
        }

        // Calculate average collisions
        double avgCollisions = totalCollisions / (double) tsize;

        // Display/print
        out.println("Total Collisions: " + totalCollisions);//display the total collisions
        out.println("Maximum Collisions: " + maxCollisions);//display the maximum collisions
        out.println("Average Collisions: " + avgCollisions);//display the average percentage of collisions
        out.println("Empty Positions: " + emptyPositions);//display the empty positions
    }

    // Method to search for specific words in the table
    static void searchWords(int[] table, String[] words, int tsize, int searchCount, PrintStream out) 
    {
        out.println("\nSearch Results:");//display header for search results
        out.println("Word                  | Search Time (ns)");//seperates the two column names word and searchtime
        out.println("-------------------------|--------------------");//adds a | to seperate the data

        // Search only the first 'searchCount' words
        for (int i = 0; i < searchCount; i++) 
        {
            String word = words[i];
            int asciiValue = calculateAscii(word);//calls method to convert our word into its asciivalue
            int hashValue = asciiValue % tsize;//key mod table size to find the key(word) hashvalue

            long startTime = System.nanoTime(); // timer start

            // Linear probing to find the word in the table
            int j = 0;
            boolean found = false;
            while (table[(hashValue + j) % tsize] != -1) 
            {
                if (table[(hashValue + j) % tsize] == asciiValue) 
                {
                    found = true; // Word is found
                    break;//ends the loop
                }
                j++;
            }

            long endTime = System.nanoTime(); // End the timer
            long totaltime = endTime - startTime; // subtract the endtime and starttime to get the totaltime which is how long it took to find the word within our hashtable

            // Display the results
            out.printf("%-20s | %15d ns\n", word, totaltime);
        }
    }

    // Main method
    public static void main(String[] args) 
    {
        ArrayList<String> wordsList = new ArrayList<>();

         // set to how many words you want to be found in search
         int searchCount = 10; // Change this value to search 10, 20, or all 50 words

         // 50 words to search made by group
         String[] searchWords = 
         {
             "hystazarin", "mules", "rebaled", "hereditaments", "apodioxis", "prefeudal",
             "galvanofaradization", "currachs", "gastrea", "Resuscitated", "near-ushering", "asteer",
             "bacteric", "abwatts", "rocktree", "faveolate", "ingratiatory", "unificator", "usurpment",
             "Steeks", "hopple", "well-manured", "ovaliform", "brontothere", "Tricholoma", "incircumspectly",
             "swoopers", "fumed", "tidecoach", "Sloane", "custumal", "fibrochondrosteal", "fullwords", "evadible",
             "tuffets", "shirtiness", "hydroxyapatite", "Fabe", "theropodous", "IX", "dulias", "rubeoloid",
             "home-sickness", "superconsecrated", "Nedi", "myomantic", "actinologous", "gelotherapy", "untechnical",
             "Blagoveshchensk"
         };

        // read words from txt file words_a
        try {
            File inputFile = new File("words_a.txt");
            Scanner in = new Scanner(inputFile);

            while (in.hasNextLine()) 
            {
                wordsList.add(in.nextLine().trim());  // add words to list
            }
            in.close();
        } 
        //method to catch if the file was not found 
        catch (FileNotFoundException e) 
        {
            // display if the file could not be found
            System.out.println("Error: The file 'words_a.txt' was not found.");
            return; // break when the file was not found
        }

        // Convert the list to an array for further processing
        String[] words = wordsList.toArray(new String[0]);

        // Define the hash table size
        //prime .7
        int S = 765211;  // size of table
        int[] hashTableprime7 = new int[S];
        int[] collisionCountsprime7 = new int[S];

        //prime.5
        int H = 765211;  // size of table
        int[] hashTableprime5 = new int[H];
        int[] collisionCountsprime5 = new int[H];

        //Nonprime.7
        int K = 765211;  // size of table
        int[] hashTablenonprime7 = new int[K];
        int[] collisionCountsnonprime7 = new int[K];

        //Nonprime.5
        int O = 765211;  // size of table
        int[] hashTablenonprime5 = new int[O];
        int[] collisionCountsnonprime5 = new int[O];

        // Create output file
        PrintStream out = null;
        try 
        {
            out = new PrintStream(new FileOutputStream("outputprime7.txt"));//creates the file output that your data will end up in you can name your file to anything 
            // Redirect System.out to file
            System.setOut(out);
            System.out.println("Hashtable prime .7: ");
            //prime.7
            // Hash words into the table using linear probing
            hashing(hashTableprime7, collisionCountsprime7, S, words, words.length);

            // Print the final table and collision information to the file
            displayTable(hashTableprime7, collisionCountsprime7, S, out);

            // Print the statistics after the table is displayed
            displayCollisions(hashTableprime7, collisionCountsprime7, S, out);
/* 
            //prime.5
            // Hash words into the table using linear probing
            hashing(hashTableprime5, collisionCountsprime5, H, words, words.length);

            // Print the final table and collision information to the file
            displayTable(hashTableprime5, collisionCountsprime5, H, out);

            // Print the statistics after the table is displayed
            displayCollisions(hashTableprime5, collisionCountsprime5, H, out);
             
            //nonprime.7
            // Hash words into the table using linear probing
            hashing(hashTablenonprime7, collisionCountsnonprime7, K, words, words.length);

            // Print the final table and collision information to the file
            displayTable(hashTablenonprime7, collisionCountsnonprime7, K, out);

            // Print the statistics after the table is displayed
            displayCollisions(hashTablenonprime7, collisionCountsnonprime7, K, out);

            //nonprime.5
            // Hash words into the table using linear probing
            hashing(hashTablenonprime5, collisionCountsnonprime5, O, words, words.length);

            // Print the final table and collision information to the file
            displayTable(hashTablenonprime5, collisionCountsnonprime5, O, out);

            // Print the statistics after the table is displayed
            displayCollisions(hashTablenonprime5, collisionCountsnonprime5, O, out);

*/
            //turn on or off to search for words in specific table sizes and prime or not primes
            // Perform the search for these words
            //prime.7
            searchWords(hashTableprime7, searchWords, S, searchCount, out);

/* 
            //prime.5
            searchWords(hashTableprime5, searchWords, H, searchCount, out);

            //nonprime.7
            searchWords(hashTablenonprime7, searchWords, K, searchCount, out);
            
            //nonprime.5
            searchWords(hashTablenonprime5, searchWords, O, searchCount, out);
*/
        } 
        catch (FileNotFoundException e) 
        {
            System.out.println("Error: Output file not found or could not be created.");
        } 
        finally 
        {
            if (out != null) 
            {
                out.close();
            }
        }
    }
}






