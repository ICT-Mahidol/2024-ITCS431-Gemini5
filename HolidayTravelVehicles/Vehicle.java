public class Vehicle {
    private String vehicleSerialNumber;
    private String model;
    private String manufacturer;
    private String name;
    private int year;
    private double baseCost;

    public Vehicle(String vehicleSerialNumber, String model, String manufacturer, String name, int year, double baseCost) {
        this.vehicleSerialNumber = vehicleSerialNumber;
        this.model = model;
        this.manufacturer = manufacturer;
        this.name = name;
        this.year = year;
        this.baseCost = baseCost;
    }

    public String getVehicleSerialNumber() { return vehicleSerialNumber; }
    public String getModel() { return model; }
    public String getManufacturer() { return manufacturer; }
    public String getName() { return name; }
    public int getYear() { return year; }
    public double getBaseCost() { return baseCost; }

    public void createNewVehicleRecord() {
        System.out.println("New vehicle record created for: " + name);
    }
}