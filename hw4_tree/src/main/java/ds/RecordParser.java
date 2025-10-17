package ds;

import java.util.Objects;

public class RecordParser {
    public static class Parsed {
        public final char op;   // I or D
        public final StudentRecord rec;  // remainning data except for I/D
        public Parsed(char op, StudentRecord rec) { this.op = op; this.rec = rec; }
    }

    public static Parsed parseLine(String line){
        Objects.requireNonNull(line);
        if(line.length() < 42) 
            throw new IllegalArgumentException("Line too short: expected > 42 chars, got" + line.length());

        char op = line.charAt(0);
        String num = line.substring(1, 8); // substring() is end exclusive
        String name = line.substring(8, 33);
        String dept = line.substring(33, 37);
        String prog = line.substring(37, 41);
        String year = line.substring(41);
        StudentRecord rec = new StudentRecord(num, name, dept, prog, year);

        return new Parsed(op, rec);
    }
}
