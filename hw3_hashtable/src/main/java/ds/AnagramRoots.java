package ds;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class AnagramRoots {
    public static int countUniqueRoots(File file, int tableCapacity) throws IOException {
        if (file == null) throw new IllegalArgumentException("file is null");
        if (!file.exists()) throw new IllegalArgumentException("file not found: " + file.getPath());
        if (tableCapacity <= 0) throw new IllegalArgumentException("capacity must be > 0");

        HashTable ht = new HashTable(tableCapacity);

        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = br.readLine()) != null){
                String[] toks = line.toLowerCase().split("[^a-z0-9]+");
                for (String w : toks) {
                    if (w.isEmpty()) continue;

                    char[] chars = w.toCharArray();
                    Arrays.sort(chars);
                    String root = new String(chars);

                    ht.insert(root);
                }
            }
        }
        return ht.size();
    }
}
