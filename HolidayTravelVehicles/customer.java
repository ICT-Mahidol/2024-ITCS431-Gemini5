public class customer extends person {
    private int customerID;
    private String membershipLevel;

    public customer(String name, String address, String phoneNumber, int customerID, String membershipLevel) {
        super(name, address, phoneNumber);
        this.customerID = customerID;
        this.membershipLevel = membershipLevel;
    }

    public int getCustomerID() { return customerID; }
    public String getMembershipLevel() { return membershipLevel; }

    public void setMembershipLevel(String membershipLevel) { this.membershipLevel = membershipLevel; }

    public void getInvoice() {
        System.out.println("Fetching invoice for Customer ID: " + customerID);
    }

    public void negotiateVehicle() {
        System.out.println(getName() + " is negotiating the vehicle price.");
    }

    public void requestTradeIn() {
        System.out.println(getName() + " is requesting a trade-in.");
    }
}