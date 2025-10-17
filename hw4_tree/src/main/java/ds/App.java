package ds;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

public class App {

    private static void writeLines(List<StudentRecord> list, Path out) throws IOException {
        Path dir = out.getParent();
        if (dir != null) Files.createDirectories(dir);
        try (BufferedWriter bw = Files.newBufferedWriter(out, StandardCharsets.UTF_8)) {
            for (StudentRecord r : list) {
                // 目前 StudentRecord.toString() 輸出 CSV
                bw.write(r.toString());
                bw.newLine();
            }
        }
    }

    public static void main(String[] args) {
        if (args.length < 1 || args.length > 3) {
            System.err.println("Usage: ds.App <input.txt> [inorder.txt] [levelorder.txt]");
            System.exit(1);
        }

        Path input = Paths.get(args[0]);

        // 決定輸出目錄：與 input 同一資料夾（若沒有父資料夾，則為當前工作目錄）
        Path outDir = input.getParent() != null ? input.getParent() : Paths.get(".");

        // 決定輸出檔名：若沒給，預設 inorder.txt / levelorder.txt；都放在 outDir
        Path outInorder = outDir.resolve(args.length >= 2 ? args[1] : "inorder.txt");
        Path outLevel   = outDir.resolve(args.length >= 3 ? args[2] : "levelorder.txt");

        // 建樹
        BST tree = new BST();

        // 讀檔 & 依 op 建樹
        try (BufferedReader br = Files.newBufferedReader(input, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                var parsed = RecordParser.parseLine(line);
                char op = Character.toUpperCase(parsed.op);
                switch (op) {
                    case 'I' -> tree.insert(parsed.rec);
                    case 'D' -> tree.delete(parsed.rec.getLastname(), parsed.rec.getStudentNo());
                    default  -> System.err.println("Warning: unknown op '" + parsed.op + "' in line: " + line);
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error reading/parsing input: " + e.getMessage());
            System.exit(2);
        }

        // 寫檔（Task-2 / Task-3）
        try {
            writeLines(tree.inOrderTraversal(),    outInorder);
            writeLines(tree.levelOrderTraversal(), outLevel);
            System.out.println("Wrote inorder to   : " + outInorder.toAbsolutePath());
            System.out.println("Wrote level-order to: " + outLevel.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error writing outputs: " + e.getMessage());
            System.exit(3);
        }
    }
}
