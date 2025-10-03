package bank;

public class Account {
    public final int id;
    public String name;
    public String address;
    public String ssn;
    public double initialDeposit;

    public Account(int id, String name, String address, String ssn, double initialDeposit){
        this.id = id;
        this.name = name;
        this.address = address;
        this.ssn = ssn;
        this.initialDeposit = initialDeposit;
    }

    public String toString(){
        return "Account{id= " + id + ", name= " + name + ", address= " + address + ", ssn= " + ssn + ", initialDeposit= " + initialDeposit + "}";
    }
}
