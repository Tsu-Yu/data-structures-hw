package ds;

import java.util.Comparator;

public class StudentRecord {
    private final String studentNo;
    private final String lastName;
    private final String dept;
    private final String program;
    private final String year;

    public StudentRecord(String studentNo, String lastName, String dept, String program, String year){
        this.studentNo = studentNo.trim();
        this.lastName = lastName.trim();
        this.dept = dept.trim();
        this.program = program.trim();
        this.year = year;
    }

    public String getStudentNo() { return studentNo; }
    public String getLastname() { return lastName; }
    public String getDept() { return dept; }
    public String getProgram() { return program; }
    public String getYear() { return year; }

    public static final Comparator<StudentRecord> KEY_COMPARATOR = 
            Comparator.comparing((StudentRecord s) -> s.lastName.toLowerCase())
                        .thenComparing(s -> s.studentNo);

    public String toCvs(){
        return String.join(",", studentNo, lastName, dept, program, String.valueOf(year));
    }

    @Override
    public String toString(){ return toCvs(); }    
}
