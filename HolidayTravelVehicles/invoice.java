import java.util.Date;

public class invoice {
    private int invoiceNumber;
    private double taxes;
    private String customerSignature;
    private double licenseFees;
    private Date date;
    private double tradeInAllowance;

    public invoice(int invoiceNumber, double taxes, String customerSignature, double licenseFees, Date date, double tradeInAllowance) {
        this.invoiceNumber = invoiceNumber;
        this.taxes = taxes;
        this.customerSignature = customerSignature;
        this.licenseFees = licenseFees;
        this.date = date;
        this.tradeInAllowance = tradeInAllowance;
    }

    public int getInvoiceNumber() { return invoiceNumber; }
    public double getTaxes() { return taxes; }
    public String getCustomerSignature() { return customerSignature; }
    public double getLicenseFees() { return licenseFees; }
    public Date getDate() { return date; }
    public double getTradeInAllowance() { return tradeInAllowance; }

    public void requestDealerInstallOptions() {
        System.out.println("Requesting dealer-installed options for Invoice: " + invoiceNumber);
    }

    public double sumFinalNegotiatedPrice(double basePrice, double dealerOptionsCost) {
        return basePrice + taxes + licenseFees - tradeInAllowance + dealerOptionsCost;
    }
}