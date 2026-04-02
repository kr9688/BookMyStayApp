import java.util.*;

public class UC6 {

    // FIFO queue for booking requests
    private Queue<String> bookingQueue = new LinkedList<>();

    // Map: room type -> allocated room IDs
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    // Inventory: room type -> available count
    private Map<String, Integer> inventory = new HashMap<>();

    public UC6() {
        // Initialize inventory with sample data
        inventory.put("Deluxe", 3);
        inventory.put("Suite", 2);
        inventory.put("Standard", 5);
    }

    // Add booking request to queue
    public void addBookingRequest(String roomType) {
        bookingQueue.offer(roomType);
        System.out.println("Request queued for room type: " + roomType);
    }

    // Process requests in FIFO order
    public void processBookings() {
        while (!bookingQueue.isEmpty()) {
            String roomType = bookingQueue.poll();
            confirmReservation(roomType);
        }
    }

    // UC6: Reservation confirmation & safe allocation
    private void confirmReservation(String roomType) {
        // Check availability
        if (inventory.getOrDefault(roomType, 0) > 0) {
            // Generate unique room ID
            String roomId = UUID.randomUUID().toString();

            // Ensure uniqueness with Set
            allocatedRooms.putIfAbsent(roomType, new HashSet<>());
            Set<String> roomSet = allocatedRooms.get(roomType);

            if (!roomSet.contains(roomId)) {
                // Atomic operation: assign + update inventory
                roomSet.add(roomId);
                inventory.put(roomType, inventory.get(roomType) - 1);

                System.out.println("Reservation confirmed → Room Type: "
                        + roomType + ", Room ID: " + roomId);
            } else {
                System.out.println("Duplicate room ID detected. Allocation aborted.");
            }
        } else {
            System.out.println("No availability for room type: " + roomType);
        }
    }

    // Display system state
    public void displayStatus() {
        System.out.println("\n--- Inventory Status ---");
        inventory.forEach((type, count) ->
                System.out.println(type + ": " + count + " available"));

        System.out.println("\n--- Allocated Rooms ---");
        allocatedRooms.forEach((type, ids) ->
                System.out.println(type + ": " + ids));
    }

    // Main driver
    public static void main(String[] args) {
        UC6 service = new UC6();

        // Sample requests
        service.addBookingRequest("Deluxe");
        service.addBookingRequest("Suite");
        service.addBookingRequest("Standard");
        service.addBookingRequest("Deluxe");
        service.addBookingRequest("Suite");
        service.addBookingRequest("Deluxe"); // may fail if inventory exhausted

        // Process all requests
        service.processBookings();

        // Show final state
        service.displayStatus();
    }
}