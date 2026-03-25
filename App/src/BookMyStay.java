
import java.util.*;

// Abstract Room Class
abstract class Room {
    private String roomType;
    private int numberOfBeds;
    private double size;
    private double price;

    public Room(String roomType, int numberOfBeds, double size, double price) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.size = size;
        this.price = price;
    }

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

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + size + " sq.ft");
        System.out.println("Price: ₹" + price);
    }
}

// Concrete Room Classes
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 150.0, 2000.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 250.0, 3500.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 500.0, 8000.0);
    }
}

// Inventory Class (State Holder)
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoom(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

// NEW CLASS: Search Service (Read-Only)
class RoomSearchService {

    public void searchAvailableRooms(List<Room> rooms, RoomInventory inventory) {

        System.out.println("=== Available Rooms ===\n");

        boolean found = false;

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            // Validation: show only available rooms
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available);
                System.out.println();
                found = true;
            }
        }

        // Defensive Programming
        if (!found) {
            System.out.println("No rooms available at the moment.");
        }
    }
}

// Main Class
public class BookMyStay
{

    public static void main(String[] args) {

        // Create Room Objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        List<Room> rooms = Arrays.asList(single, doubleRoom, suite);

        // Initialize Inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoom(single.getRoomType(), 5);
        inventory.addRoom(doubleRoom.getRoomType(), 0); // Not available
        inventory.addRoom(suite.getRoomType(), 2);

        // Search Service (Read-Only)
        RoomSearchService searchService = new RoomSearchService();

        // Guest initiates search
        searchService.searchAvailableRooms(rooms, inventory);

        System.out.println("Application Terminated.");
    }
}