public class DealerInstalledOptions {
    private double price;
    private String optionCode;
    private String description;

    public DealerInstalledOptions(double price, String optionCode, String description) {
        this.price = price;
        this.optionCode = optionCode;
        this.description = description;
    }

    public double getPrice() { return price; }
    public String getOptionCode() { return optionCode; }
    public String getDescription() { return description; }

    public void setPrice(double price) { this.price = price; }
    public void setOptionCode(String optionCode) { this.optionCode = optionCode; }
    public void setDescription(String description) { this.description = description; }
}