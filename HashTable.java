import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HashTable {

    private int tableSize; // size of hash table
    private int elementCount; // number of elements inserted
    private ArrayList<String>[] hashTable; // hash table with separate chaining

    @SuppressWarnings("unchecked")  //   initialize hash table with specified size
    public HashTable(int tableSize) {
        this.tableSize = tableSize;
        this.elementCount = 0;
        this.hashTable = (ArrayList<String>[]) new ArrayList[tableSize];  // initialize each index with an empty ArrayList
        for (int i = 0; i < tableSize; i++) {
            hashTable[i] = new ArrayList<>();
        }
    }

    public int computeHash(String key) { // hash function to map strings to hash table index
        return Math.abs(key.hashCode() % tableSize);
    }

    public void insertKey(String key) { // insert a word into the table
        int index = computeHash(key);  // find the index for the word
        hashTable[index].add(key);      // add the word at the index
        elementCount++;
        // resize the table if the load factor exceeds 0.7
        if (calculateLoadFactor() > 0.7) {
            resizeHashTable();
        }
    }

    public double calculateLoadFactor() { // calculate the load factor (number of elements / size of the table)
        return (double) elementCount / tableSize;
    }

    private void resizeHashTable() { // resize the hash table and rehash all elements
        int newTableSize = tableSize * 2;  // double the size of the table (simple resizing strategy)
        System.out.println("Resizing hash table to size: " + newTableSize);
        ArrayList<String>[] newTable = (ArrayList<String>[]) new ArrayList[newTableSize];

        // re-initialize the new table with empty lists
        for (int i = 0; i < newTableSize; i++) {
            newTable[i] = new ArrayList<>();
        }

        // rehash all existing elements into the new table
        for (int i = 0; i < tableSize; i++) {
            for (String item : hashTable[i]) {
                int newIndex = Math.abs(item.hashCode() % newTableSize);
                newTable[newIndex].add(item);
            }
        }

        // update the table reference and size
        this.hashTable = newTable;
        this.tableSize = newTableSize;
    }

    public void writeTableStatisticsToFile(String fileName) throws IOException { // write the hash table counts to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < tableSize; i++) {
                int itemCount = hashTable[i].size(); // count the number of words at each index
                if (itemCount > 0) {
                    writer.write("index " + i + ": " + itemCount);
                    writer.newLine();
                }
            }
        }
    }

    public static List<String> readWordsFromFile(String fileName) throws IOException { // Load words from a file
        return Files.readAllLines(Paths.get(fileName));  // read all lines from the file
    }

    public void calculateCollisions() { // calculate collisions, max collisions, average collisions, and empty positions
        int totalCollisions = 0;
        int maxCollisions = 0;
        int nonEmptyIndexes = 0;
        int emptySlots = 0;

        for (int i = 0; i < tableSize; i++) {  // traverse the hash table
            int itemCount = hashTable[i].size();  // number of words at each index
            if (itemCount > 0) {
                nonEmptyIndexes++;  // increment for each non-empty index
                totalCollisions += (itemCount - 1);  // collisions 
                maxCollisions = Math.max(maxCollisions, itemCount - 1);  // track max collisions
            } else {
                emptySlots++;  // count the number of empty positions
            }
        }

        double avgCollisions = nonEmptyIndexes == 0 ? 0 : (double) totalCollisions / nonEmptyIndexes; // avg collisions

        // print the statistics
        System.out.println("Total Collisions: " + totalCollisions);
        System.out.println("Max Collisions: " + maxCollisions);
        System.out.println("Average Collisions: " + String.format("%.2f", avgCollisions));
        System.out.println("Empty Slots: " + emptySlots);
    }

    // Driver
    public static void main(String[] args) {
        try {

            String inputFile = "word.txt";  // file containing words
            List<String> words = readWordsFromFile(inputFile);  // read the words from the file

            int initialCapacity = 765211;  // initial table size (restored to previous value)

            HashTable customHashTable = new HashTable(initialCapacity);  // create the hash table with the specified size

            for (String word : words) { // insert each word into the hash table
                customHashTable.insertKey(word);
            }

            String outputFile = "hash_table_statistics.txt";  // save the hash table counts at each index into a new text file
            customHashTable.writeTableStatisticsToFile(outputFile);
            System.out.println("Hash table statistics written to: " + outputFile);

            customHashTable.calculateCollisions(); // display calculations

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
