public class Salesperson extends person {
    private int salespersonID;
    private double salary;
    private String position;

    public Salesperson(String name, String address, String phoneNumber, int salespersonID, double salary, String position) {
        super(name, address, phoneNumber);
        this.salespersonID = salespersonID;
        this.salary = salary;
        this.position = position;
    }

    public int getSalespersonID() { return salespersonID; }
    public double getSalary() { return salary; }
    public String getPosition() { return position; }

    public void createInvoice() {
        System.out.println(getName() + " is creating an invoice.");
    }

    public void negotiateVehicle() {
        System.out.println(getName() + " is negotiating a vehicle sale.");
    }
}