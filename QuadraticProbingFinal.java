import java.util.*;
import java.io.*; // For file handling

public class QuadraticProbingFinal {
    private int tableSize;
    private String[] hashTable;
    private int[] hashDistribution; // Tracks collisions for each index
    private int itemCount;
    private int totalCollisions;

    public QuadraticProbingFinal(int tableSize) {
        this.tableSize = tableSize;
        this.hashTable = new String[tableSize];
        this.hashDistribution = new int[tableSize];
        this.itemCount = 0;
        this.totalCollisions = 0;
    }

    private int hashFunction(String key) {
        return Math.abs(key.hashCode()) % tableSize;
    }

    public boolean insert(String key) {
        int hash = hashFunction(key);
        for (int i = 0; i < tableSize; i++) {
            int newIndex = (hash + i * i) % tableSize;
            if (hashTable[newIndex] == null) {
                hashTable[newIndex] = key;
                hashDistribution[newIndex] = i; // Record number of collisions before placement
                itemCount++;
                return true;
            }
            totalCollisions++;
        }
        return false; // Hash table is full or probing limit reached
    }

    public String searchWithDetails(String key) {
        int hash = hashFunction(key); // Calculate the hash value
        long startTime = System.nanoTime(); // Start timing
        for (int i = 0; i < tableSize; i++) {
            int newIndex = (hash + i * i) % tableSize; // Quadratic probing
            if (hashTable[newIndex] == null) {
                long endTime = System.nanoTime(); // End timing
                return String.format("Word: '%s', Probes: %d, Hash Value: %d, Search Time: %d ns, Collisions: %d",
                        key, i + 1, hash, (endTime - startTime), i); // Key not found
            }
            if (hashTable[newIndex].equals(key)) {
                long endTime = System.nanoTime(); // End timing
                return String.format("Word: '%s', Probes: %d, Hash Value: %d, Search Time: %d ns, Collisions: %d",
                        key, i + 1, hash, (endTime - startTime), i); // Key found
            }
        }
        long endTime = System.nanoTime(); // End timing
        return String.format("Word: '%s', Probes: -1, Hash Value: %d, Search Time: %d ns, Collisions: %d",
                key, hash, (endTime - startTime), tableSize); // Key not found after full traversal
    }
    

    public int getTotalCollisions() {
        return totalCollisions;
    }

    public int getMaximumCollisions() {
        int maxCollisions = 0;
        for (int collisions : hashDistribution) {
            if (collisions > maxCollisions) {
                maxCollisions = collisions;
            }
        }
        return maxCollisions;
    }

    public double getAverageCollisions() {
        int nonEmptySlots = 0;
        int totalCollisionsForAverage = 0;
        for (int collisions : hashDistribution) {
            if (collisions > 0) {
                totalCollisionsForAverage += collisions;
                nonEmptySlots++;
            }
        }
        return nonEmptySlots == 0 ? 0.0 : (double) totalCollisionsForAverage / nonEmptySlots;
    }

    public int getEmptyPositions() {
        int emptyCount = 0;
        for (String slot : hashTable) {
            if (slot == null) {
                emptyCount++;
            }
        }
        return emptyCount;
    }

    public static void runExperiment(String experimentName, List<String> fullWords, Map<Integer, List<String>> subsets,
                                     int tableSize, double loadFactor, PrintWriter writer) {
        QuadraticProbingFinal hashing = new QuadraticProbingFinal(tableSize);

        writer.printf("Experiment: %s\n", experimentName);
        writer.printf("Table Size: %d, Load Factor: %.2f\n\n", tableSize, loadFactor);

        // Insert full dataset into the hash table
        long startInsertTime = System.nanoTime();
        for (String word : fullWords) {
            hashing.insert(word);
        }
        long endInsertTime = System.nanoTime();
        writer.printf("Total Insertion Time: %.2f ms\n", (endInsertTime - startInsertTime) / 1e6);

        // Collision and Hash Table Statistics
        writer.printf("Total Collisions During Insertion: %d\n", hashing.getTotalCollisions());
        writer.printf("Maximum Collisions: %d\n", hashing.getMaximumCollisions());
        writer.printf("Average Collisions: %.2f\n", hashing.getAverageCollisions());
        writer.printf("Empty Positions: %d\n\n", hashing.getEmptyPositions());

        // Process each subset in order
        for (Map.Entry<Integer, List<String>> entry : subsets.entrySet()) {
            int subsetSize = entry.getKey();
            List<String> subset = entry.getValue();

            writer.printf("\nSubset: %d Words\n", subsetSize);
            for (String word : subset) {
                String result = hashing.searchWithDetails(word);
                writer.println(result);
            }
        }
        writer.println("==========================================\n");
    }

    public static List<String> readWordsFromFile(String fileName) throws IOException {
        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line.trim()); // Add each word from the file
            }
        }
        return words;
    }

    public static void main(String[] args) {
        String fileName = "words.txt"; // File containing the words to process
        int[] tableSizes = {765211, 998651, 689900, 998654}; // Prime and non-prime table sizes
        double[] loadFactors = {0.7, 0.5};

        // Predefined subsets for searches
        Map<Integer, List<String>> subsets = new TreeMap<>();
        subsets.put(10, Arrays.asList("hystazarin", "mules", "rebaled", "hereditaments", "apodioxis",
                "prefeudal", "galvanofaradization", "currachs", "gastrea", "Resuscitated"));
        subsets.put(20, Arrays.asList("custumal", "fibrochondrosteal", "fullwords", "evadible", "tuffets",
                "shirtiness", "hydroxyapatite", "Fabe", "theropodous", "IX", "dulias", "rubeoloid",
                "home-sickness", "superconsecrated", "Nedi", "myomantic", "actinologous", "gelotherapy",
                "untechnical", "Blagoveshchensk"));
        subsets.put(30, Arrays.asList("hopple", "well-manured", "ovaliform", "brontothere", "Tricholoma",
                "incircumspectly", "swoopers", "fumed", "tidecoach", "Sloane", "custumal", "fibrochondrosteal",
                "fullwords", "evadible", "tuffets", "shirtiness", "hydroxyapatite", "Fabe", "theropodous",
                "IX", "dulias", "rubeoloid", "home-sickness", "superconsecrated", "Nedi", "myomantic",
                "actinologous", "gelotherapy", "untechnical", "Blagoveshchensk"));
        subsets.put(40, Arrays.asList("near-ushering", "asteer", "bacteric", "abwatts", "rocktree", "faveolate",
                "ingratiatory", "unificator", "usurpment", "Steeks", "hopple", "well-manured", "ovaliform",
                "brontothere", "Tricholoma", "incircumspectly", "swoopers", "fumed", "tidecoach", "Sloane",
                "custumal", "fibrochondrosteal", "fullwords", "evadible", "tuffets", "shirtiness",
                "hydroxyapatite", "Fabe", "theropodous", "IX", "dulias", "rubeoloid", "home-sickness",
                "superconsecrated", "Nedi", "myomantic", "actinologous", "gelotherapy", "untechnical",
                "Blagoveshchensk"));
        subsets.put(50, Arrays.asList("hystazarin", "mules", "rebaled", "hereditaments", "apodioxis",
                "prefeudal", "galvanofaradization", "currachs", "gastrea", "Resuscitated", "near-ushering",
                "asteer", "bacteric", "abwatts", "rocktree", "faveolate", "ingratiatory", "unificator",
                "usurpment", "Steeks", "hopple", "well-manured", "ovaliform", "brontothere", "Tricholoma",
                "incircumspectly", "swoopers", "fumed", "tidecoach", "Sloane", "custumal", "fibrochondrosteal",
                "fullwords", "evadible", "tuffets", "shirtiness", "hydroxyapatite", "Fabe", "theropodous",
                "IX", "dulias", "rubeoloid", "home-sickness", "superconsecrated", "Nedi", "myomantic",
                "actinologous", "gelotherapy", "untechnical", "Blagoveshchensk"));

        try {
            List<String> words = readWordsFromFile(fileName);

            try (PrintWriter writer = new PrintWriter("QuadraticProbingOutput.txt")) {
                for (int tableSize : tableSizes) {
                    for (double loadFactor : loadFactors) {
                        runExperiment("Table Size: " + tableSize + ", Load Factor: " + loadFactor,
                                words, subsets, tableSize, loadFactor, writer);
                    }
                }
            }

            System.out.println("Results written to QuadraticProbingOutput.txt");

        } catch (IOException e) {
            System.err.println("Error reading file or writing output: " + e.getMessage());
        }
    }
}
