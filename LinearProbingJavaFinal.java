import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

class LinearProbingJavaFinal
{
    // List of 50 random words (group list) can be called on depending on the amount called
    static String[] wordsList = 
    {
        "hystazarin", "mules", "rebaled", "hereditaments", "apodioxis", "prefeudal",
        "galvanofaradization", "currachs", "gastrea", "Resuscitated", "near-ushering",
        "asteer", "bacteric", "abwatts", "rocktree", "faveolate", "ingratiatory",
        "unificator", "usurpment", "Steeks", "hopple", "well-manured", "ovaliform",
        "brontothere", "Tricholoma", "incircumspectly", "swoopers", "fumed", "tidecoach",
        "Sloane", "custumal", "fibrochondrosteal", "fullwords", "evadible", "tuffets",
        "shirtiness", "hydroxyapatite", "Fabe", "theropodous", "IX", "dulias", "rubeoloid",
        "home-sickness", "superconsecrated", "Nedi", "myomantic", "actinologous", "gelotherapy",
        "untechnical", "Blagoveshchensk"
    };

    // Convert words to ascii values 
    static int calculateAscii(String str) 
    {
        int total = 0;
        for (char c : str.toCharArray()) 
        {
            total += c;
        }
        return total;
    }

    // Hashing using Linear Probing (updated to always start at index 0)
    static void hashing(int[] table, int[] collisions, int tsize, String arr[], int N) 
    {
        int totalCollisions = 0;  // set to zero
        int maxCollisions = 0;    // set to 0
        int emptyPositions = tsize;  // set to table size

        // Initialize table and collisions arrays
        for (int i = 0; i < tsize; i++) 
        {
            table[i] = -1;
            collisions[i] = 0;
        }

        for (int i = 0; i < N; i++) 
        {
            // Force the starting index to be 0
            int hv = 0; // This forces all insertions to start at index 0

            // Perform linear probing to find an empty slot
            for (int j = 0; j < tsize; j++) 
            {
                int t = (hv + j) % tsize;  // Linear probing
                if (table[t] == -1)  // Found an empty spot
                {
                    table[t] = calculateAscii(arr[i]);
                    collisions[t]++;
                    totalCollisions++;
                    emptyPositions--;
                    break;  // Exit the loop once the word is inserted
                } 
                else 
                {
                    collisions[t]++;  // Increment collision count
                    totalCollisions++;  // Increment total collisions
                }
            }
        }

        // Find the maximum number of collisions at any index
        for (int i = 0; i < tsize; i++) 
        {
            if (collisions[i] > maxCollisions) 
            {
                maxCollisions = collisions[i];
            }
        }

        // Calculate average collisions
        double avgCollisions = (double) totalCollisions / N;

        // Print the final results
        printArray(table, collisions);
        System.out.println("Total Collisions: " + totalCollisions);
        System.out.println("Maximum Collisions: " + maxCollisions);
        System.out.println("Average Collisions: " + avgCollisions);
        System.out.println("Empty Positions: " + emptyPositions);
    }

    // Display the hash table 
    static void printArray(int[] arr, int[] collisions) 
    {
        System.out.println("Index" + "\t" + "Data" + "\t" + "Collisions");
        for (int i = 0; i < arr.length; i++) 
        {
            System.out.println(i + "\t" + arr[i] + "\t" + collisions[i]);
        }
    }

    // Searches for the random word and uses nano time to search hash table for how long it takes to be found
    static void searchWord(int[] hashTable, int[] collisions, String word) 
    {
        long startTime = System.nanoTime();  // start time
        int asciiValue = calculateAscii(word);  // convert the word to ascii value 
        int index = asciiValue % hashTable.length;
        boolean found = false;
        int searchCollisions = 0; // Initialize the collision counter for this search

        for (int i = 0; i < hashTable.length; i++) 
        {
            int t = (index + i) % hashTable.length;
            if (hashTable[t] == asciiValue) 
            {
                found = true;
                searchCollisions = i + 1; // If we found the word, the number of collisions is the number of probes
                break;
            }
            searchCollisions++; // Increment the collision counter for each probe
        }

        long endTime = System.nanoTime();  // set end time
        long timeTaken = (endTime - startTime);  // get the total time by subtracting the end time with start time 

        if (found) 
        {
            System.out.println(word + " found in " + timeTaken + " nanoseconds with " + searchCollisions + " collisions");
        } 
        else 
        {
            System.out.println(word + " not found after " + searchCollisions + " collisions");
        }
    }

    // Main method with file redirection
    public static void main(String args[]) 
    {
        // Create a PrintStream to write output to a file
        PrintStream out = null;
        try {
            // Create a PrintStream that writes to a file
            out = new PrintStream(new FileOutputStream("output.txt"));
            
            // Display data/output in a txt file
            System.setOut(out);

            // Hash table sizes

            // Hash table prime .07
            int S = 765211; // table size
            int[] hashTablePrime7 = new int[S];
            int[] collisionPrime7 = new int[S];
            Arrays.fill(hashTablePrime7, -1);
            Arrays.fill(collisionPrime7, 0);

            // Hash table prime .05
            int H = 998651; // table size
            int[] hashTablePrime5 = new int[H];
            int[] collisionPrime5 = new int[H];
            Arrays.fill(hashTablePrime5, -1);
            Arrays.fill(collisionPrime5, 0);

            // Hash table nonprime .07
            int K = 689900; // table size
            int[] hashTableNonPrime7 = new int[K];
            int[] collisionNonPrime7 = new int[K];
            Arrays.fill(hashTableNonPrime7, -1);
            Arrays.fill(collisionNonPrime7, 0);

            // Hash table nonprime .05
            int O = 998654; // table size
            int[] hashTableNonPrime5 = new int[O];
            int[] collisionNonPrime5 = new int[O];
            Arrays.fill(hashTableNonPrime5, -1);
            Arrays.fill(collisionNonPrime5, 0);

            // Number of words to search you can change this number to test different sizes
            int numWordsToSearch = 50;  // change to 10, 20, 30, 40, or 50 for different search sizes as required 
            String[] randomWords = Arrays.copyOfRange(wordsList, 0, numWordsToSearch);

            // Read the words from the words_a file
            File inputFile = new File("words_a.txt");
            ArrayList<String> wordsList = new ArrayList<>();
            try 
            {
                Scanner in = new Scanner(inputFile);  // creating scanner to read file
                while (in.hasNextLine()) 
                {
                    String word = in.nextLine();
                    wordsList.add(word);
                }
                in.close();
            } 

            catch (FileNotFoundException e) //method to catch if the file could not be found
            {
                System.out.println("File not found!");  // displays if the file was not found
            }

            String[] words = wordsList.toArray(new String[0]);
 
            // Display hash table prime .07
            System.out.println("Hash table prime number with load factor .7 after hashing:");
            hashing(hashTablePrime7, collisionPrime7, S, words, words.length);

/* 
            // Display hash table prime .05
            System.out.println("Hash table prime number with load factor .5 after hashing:");
            hashing(hashTablePrime5, collisionPrime5, H, words, words.length);
 */           
 /*
            // Display hash table Nonprime .07
            System.out.println("Hash table Nonprime number with load factor .7 after hashing:");
            hashing(hashTableNonPrime7, collisionNonPrime7, K, words, words.length);

 */ 
/*
 
            // Display hash table Nonprime .05
            System.out.println("Hash table Nonprime number with load factor .5 after hashing:");
            hashing(hashTableNonPrime5, collisionNonPrime5, O, words, words.length);
*/

            // display Searching for words in the hash tables
            System.out.println("\nSearching for words in the hash tables:");
 
            // Loop through the selected words and search for each one
            for (String word : randomWords) 
            {
                searchWord(hashTablePrime7, collisionPrime7, word);  // searches table .7 prime
            }

/*           
            for (String word : randomWords) 
            {
                searchWord(hashTablePrime5, collisionPrime5, word);  // searches table .5 prime
            }

            for (String word : randomWords) 
            {
                searchWord(hashTableNonPrime7, collisionNonPrime7, word);  // searches table .7 nonprime
            }
*/ 
/* 
            for (String word : randomWords) 
            {
                searchWord(hashTableNonPrime5, collisionNonPrime5, word);  // searches table .5 nonprime
            }
*/            
        } 
        catch (IOException e) 
        {
            System.out.println("Error with output file.");
        }
    }
}







