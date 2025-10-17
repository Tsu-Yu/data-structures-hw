package ds;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RecordParserTest {

    private String fixedWidth(char op, String num, String lastName, String dept, String prog, String year) {
        // 固定欄寬：1 + 7 + 25 + 4 + 4 + 1 = 42
        StringBuilder sb = new StringBuilder();
        sb.append(op);
        sb.append(String.format("%-7s", num));
        sb.append(String.format("%-25s", lastName));
        sb.append(String.format("%-4s", dept));
        sb.append(String.format("%-4s", prog));
        sb.append(String.format("%-1s", year));
        return sb.toString();
    }

    @Test
    void parse_ok() {
        String line = fixedWidth('I', "1234567", "Smith", "CS", "SE", "3");
        RecordParser.Parsed p = RecordParser.parseLine(line);
        assertEquals('I', p.op);
        assertEquals("1234567", p.rec.getStudentNo());
        assertEquals("Smith", p.rec.getLastname());
        assertEquals("CS", p.rec.getDept());
        assertEquals("SE", p.rec.getProgram());
        assertEquals("3", p.rec.getYear());
    }

    @Test
    void parse_tooShort_throws() {
        assertThrows(IllegalArgumentException.class, () -> RecordParser.parseLine("I123"));
    }
}
