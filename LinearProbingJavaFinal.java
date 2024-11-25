import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

class LinearprobingjavaFinalexample 
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

    // Hashing using Linear Probing
    static void hashing(int[] table, int[] collisions, int tsize, String arr[], int N) 
    {
        int totalCollisions = 0;  // set to zero
        int maxCollisions = 0;    // set to 0
        int emptyPositions = tsize;  // set to table size

        for (int i = 0; i < tsize; i++) 
        {
            table[i] = -1;
            collisions[i] = 0;
        }

        for (int i = 0; i < N; i++) 
        {
            int hv = calculateAscii(arr[i]) % tsize;
            if (table[hv] == -1) 
            {
                table[hv] = calculateAscii(arr[i]);
            } 
            else 
            {
                for (int j = 0; j < tsize; j++) 
                {
                    int t = (hv + j) % tsize;
                    if (table[t] == -1) {
                        table[t] = calculateAscii(arr[i]);
                        collisions[t]++;
                        totalCollisions++;
                        emptyPositions--;
                        break;
                    } 
                    else 
                    {
                        collisions[t]++;
                        totalCollisions++;
                    }
                }
            }
        }

        for (int i = 0; i < tsize; i++) 
        {
            if (collisions[i] > maxCollisions) 
            {
                maxCollisions = collisions[i];
            }
        }

        double avgCollisions = (double) totalCollisions / N; // get the average percentage of collisions
        printArray(table, collisions); // display table and collisions
        System.out.println("Total Collisions: " + totalCollisions); // display total collisions
        System.out.println("Maximum Collisions: " + maxCollisions); // display maximum collisions
        System.out.println("Average Collisions: " + avgCollisions); // display average collisions
        System.out.println("Empty Positions: " + emptyPositions); // display empty positions
    }

    // Display the hash table 
    static void printArray(int[] arr, int[] collisions) 
    {
        System.out.println("Index" + "\t" + "Data" + "\t" + "Collisions");
        for (int i = 0; i < arr.length; i++) 
        {
            System.out.println(i + "\t" + arr[i] + "\t" + collisions[i]);
        }
        System.out.println("Hello");
    }

    // Searches for the random word and uses nano time to search hash table for how long it takes to be found
    static void searchWord(int[] hashTable, int[] collisions, String word) 
    {
        long startTime = System.nanoTime();  // start time
        int asciiValue = calculateAscii(word);  // convert the word to ascii value 
        int index = asciiValue % hashTable.length;
        boolean found = false;

        for (int i = 0; i < hashTable.length; i++) 
        {
            int t = (index + i) % hashTable.length;
            if (hashTable[t] == asciiValue) 
            {
                found = true;
                break;
            }
        }
        long endTime = System.nanoTime();  // set end time
        long timeTaken = (endTime - startTime);  // get the total time by subtracting the end time with start time 
        if (found) 
        {
            System.out.println(word + " found in " + timeTaken + " nanoseconds"); // display the word and time
        } 
        else
        {
            System.out.println(word + " not found"); // if not found
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

            //Hash table prime .07
            //int S = 1000;//testsize
            int S = 765211;//table size
            int[] hashTablePrime7 = new int[S];
            int[] collisionPrime7 = new int[S];
            Arrays.fill(hashTablePrime7, -1);
            Arrays.fill(collisionPrime7, 0);

            //Hash table prime .05
            //int H = 5;//testsizze
             int H = 998651;//table size
            int[] hashTablePrime5 = new int[H];
            int[] collisionPrime5 = new int[H];
            Arrays.fill(hashTablePrime5, -1);
            Arrays.fill(collisionPrime5, 0);

            //Hash table nonprime .07
            //int K = 6;//test size
            int K = 689900;//table size
            int[] hashTableNonPrime7 = new int[K];
            int[] collisionNonPrime7 = new int[K];
            Arrays.fill(hashTableNonPrime7, -1);
            Arrays.fill(collisionNonPrime7, 0);

            //Hash table nonprime .05
            //int O = 8;//test size
            int O = 998654;//table size
            int[] hashTableNonPrime5 = new int[O];
            int[] collisionNonPrime5 = new int[O];
            Arrays.fill(hashTableNonPrime5, -1);
            Arrays.fill(collisionNonPrime5, 0);

            // Number of words to search (you can change this number to test different sizes)
            int numWordsToSearch = 10;  // change to 10,20,30,40 or 50 for different search sizes
            String[] randomWords = Arrays.copyOfRange(wordsList, 0, numWordsToSearch);

            // Read the words from the words_a file
            File inputFile = new File("words_a.txt");
            ArrayList<String> wordsList = new ArrayList<>();
            try 
            {
                Scanner in = new Scanner(inputFile);//creating scanner to read file 
                while (in.hasNextLine()) 
                {
                    String word = in.nextLine();
                    wordsList.add(word);
                }
                in.close();
            } 
            //catch exception in case file can not be read
            catch (FileNotFoundException e) 
            {
                System.out.println("File not found!");//displays if the file was not found
            }

            String[] words = wordsList.toArray(new String[0]);

            // Hash the words into the hash tables
            //display hashtable prime .07
            System.out.println("Hash table prime number with load factor .7 after hashing:");
            hashing(hashTablePrime7, collisionPrime7, S, words, words.length);

            //display hashtable prime .05
            System.out.println("\nHash table prime number with load factor .5 after hashing:");
            hashing(hashTablePrime5, collisionPrime5, H, words, words.length);

            //display hashtable nonprime .07
            System.out.println("\nHash table non-prime number with load factor .7 after hashing:");
            hashing(hashTableNonPrime7, collisionNonPrime7, K, words, words.length);

            //display hashtable nonprime .05
            System.out.println("\nHash table non-prime number with load factor .5 after hashing:");
            hashing(hashTableNonPrime5, collisionNonPrime5, O, words, words.length);

            // Search for the words in my list depending on how many of them are called for
            System.out.println("\nWords to search in hashtable:");
            for (String word : randomWords) 
            {
                System.out.println("Word: " + word);
                searchWord(hashTablePrime7, collisionPrime7, word);
            }

        } 
        catch (IOException e)//catches if the code can not put the data into the output file 
        {
            System.out.println("Error creating output file.");
        } 
        //ends the print to the txt file when all the data has been put in
        finally  
        {
            if (out != null) 
            {
                out.close();  // Close the PrintStream when done
            }
        }
    }
}
