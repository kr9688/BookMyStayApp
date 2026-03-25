
// Abstract Class
abstract class Room {
    private String roomType;
    private int numberOfBeds;
    private double size;
    private double price;

    // Constructor
    public Room(String roomType, int numberOfBeds, double size, double price) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.size = size;
        this.price = price;
    }

    // Getters (Encapsulation)
    public String getRoomType() {
        return roomType;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public double getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    // Common method
    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + size + " sq.ft");
        System.out.println("Price: ₹" + price);
    }
}

// Single Room Class
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 150.0, 2000.0);
    }
}

// Double Room Class
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 250.0, 3500.0);
    }
}

// Suite Room Class
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 500.0, 8000.0);
    }
}

// Main Application Class (Version 2.1)
public class BookMyStay {

    public static void main(String[] args) {

        // Polymorphism: Using Room reference
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability (simple variables)
        int singleAvailability = 5;
        int doubleAvailability = 3;
        int suiteAvailability = 2;

        // Display Details
        System.out.println("=== Hotel Room Details ===\n");

        System.out.println("---- Single Room ----");
        single.displayDetails();
        System.out.println("Available: " + singleAvailability);
        System.out.println();

        System.out.println("---- Double Room ----");
        doubleRoom.displayDetails();
        System.out.println("Available: " + doubleAvailability);
        System.out.println();

        System.out.println("---- Suite Room ----");
        suite.displayDetails();
        System.out.println("Available: " + suiteAvailability);
        System.out.println();

        System.out.println("Application Terminated.");
    }
}