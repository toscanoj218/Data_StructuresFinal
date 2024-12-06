import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WordSearch {

    private int size; // size of hash table
    private final ArrayList<String>[] hashtable; // hash table with separate chaining

    
    @SuppressWarnings("unchecked") // constructor to initialize hash table size
    public WordSearch(int size) {
        this.size = size; 
        this.hashtable = (ArrayList<String>[]) new ArrayList[size];  // create an array
        for (int i = 0; i < size; i++) {
            hashtable[i] = new ArrayList<>();
        }
    }

    
    public int hashFunction(String key) {
        return Math.abs(key.hashCode() % size); // hash function 
    }

    
    public int insertItem(String key) { // insert a word into the hash table 
        int index = hashFunction(key);
        int probes = 1; // initial probe count is 1 

        
        if (!hashtable[index].isEmpty()) { // check if index empty(collision)
            probes += 1; // Additional probe if collision occurs
        }

        hashtable[index].add(key); // insert at the calculated index
        return probes;
    }

    
    public int searchItem(String key) { // search for a word in the hash table, return probes
        int index = hashFunction(key);
        int probes = 1;

        
        for (String word : hashtable[index]) { // check if the word is present in the chain 
            if (word.equals(key)) {
                return probes; // return the number of probes
            }
            probes++;
        }
        
        return probes; // return probes
    }

    
    public static List<String> readWordsFromFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName));
    }

    // Driver
    public static void main(String[] args) {
        try {
      
            String fileName = "word.txt";  // word file 
            List<String> words = readWordsFromFile(fileName);

            //  table sizes
            int primeTableSize = 765221;
            int nonPrimeTableSize = 689900;

            // create instances for both prime and non-prime table sizes
            WordSearch primeTable = new WordSearch(primeTableSize);
            WordSearch nonPrimeTable = new WordSearch(nonPrimeTableSize);

            // initialize counters for total search time and probes 
            long primeTotalSearchTime = 0;
            long nonPrimeTotalSearchTime = 0;
            int primeTotalProbes = 0;
            int nonPrimeTotalProbes = 0;

            // words to search
            List<String> searchWords = List.of("mules"
            );  

            
            for (String searchWord : searchWords) { //search method 
                
                //Non-prime
                long nonPrimeStartTime = System.nanoTime();  // start time
                int nonPrimeProbes = nonPrimeTable.searchItem(searchWord);
                long nonPrimeEndTime = System.nanoTime();  // end time
                nonPrimeTotalSearchTime += (nonPrimeEndTime - nonPrimeStartTime); // total time
                nonPrimeTotalProbes += nonPrimeProbes; // Total probes for non-prime

                //Prime
                long primeStartTime = System.nanoTime(); // start time
                int primeProbes = primeTable.searchItem(searchWord);
                long primeEndTime = System.nanoTime(); // end time
                primeTotalSearchTime += (primeEndTime - primeStartTime); // rotal time
                primeTotalProbes += primeProbes; // rotal probes for prime
            }
            //display results 
            System.out.println("\nSearch time for " + searchWords.size() + " words in Non-Prime: " + nonPrimeTotalSearchTime + " ns.");
            System.out.println("Probes used for Non-Prime search: " + nonPrimeTotalProbes);

            System.out.println("\nSearch time for " + searchWords.size() + " words in Prime: " + primeTotalSearchTime + " ns.");
            System.out.println("Probes used for Prime search: " + primeTotalProbes);

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
