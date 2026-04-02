import java.io.*;
import java.util.HashMap;
import java.util.Map;

// Guest class
class Guest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String guestId;
    private String guestName;

    public Guest(String guestId, String guestName) {
        this.guestId = guestId;
        this.guestName = guestName;
    }

    public String getGuestId() {
        return guestId;
    }

    public String getGuestName() {
        return guestName;
    }

    @Override
    public String toString() {
        return guestId + " - " + guestName;
    }
}

// Reservation class
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String reservationId;
    private Guest guest;
    private String roomType;
    private double bookingAmount;
    private boolean cancelled;

    public Reservation(String reservationId, Guest guest, String roomType, double bookingAmount) {
        this.reservationId = reservationId;
        this.guest = guest;
        this.roomType = roomType;
        this.bookingAmount = bookingAmount;
        this.cancelled = false;
    }

    public String getReservationId() {
        return reservationId;
    }

    public Guest getGuest() {
        return guest;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getBookingAmount() {
        return bookingAmount;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancel() {
        this.cancelled = true;
    }

    @Override
    public String toString() {
        return reservationId + " | " + guest.getGuestName() + " | " + roomType
                + " | Rs." + bookingAmount + " | Status: "
                + (cancelled ? "Cancelled" : "Confirmed");
    }
}

// Wrapper class to persist complete system state
class HotelData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Integer> roomInventory;
    private Map<String, Reservation> bookingHistory;

    public HotelData(Map<String, Integer> roomInventory, Map<String, Reservation> bookingHistory) {
        this.roomInventory = roomInventory;
        this.bookingHistory = bookingHistory;
    }

    public Map<String, Integer> getRoomInventory() {
        return roomInventory;
    }

    public Map<String, Reservation> getBookingHistory() {
        return bookingHistory;
    }
}

// Persistence service
class PersistenceService {
    private final String fileName = "hotel_state.dat";

    public void saveState(HotelData data) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(data);
            System.out.println("System state saved successfully to file: " + fileName);
        } catch (IOException e) {
            System.out.println("Failed to save system state: " + e.getMessage());
        }
    }

    public HotelData loadState() {
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("No persistence file found. Starting with default system state.");
            return null;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            HotelData data = (HotelData) in.readObject();
            System.out.println("System state restored successfully from file: " + fileName);
            return data;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Persistence file is missing or corrupted. Starting safely with default state.");
            return null;
        }
    }
}

// Hotel system
class HotelBookingSystem {
    private Map<String, Integer> roomInventory;
    private Map<String, Reservation> bookingHistory;
    private PersistenceService persistenceService;

    public HotelBookingSystem() {
        persistenceService = new PersistenceService();

        HotelData recoveredData = persistenceService.loadState();

        if (recoveredData != null) {
            roomInventory = recoveredData.getRoomInventory();
            bookingHistory = recoveredData.getBookingHistory();
        } else {
            roomInventory = new HashMap<>();
            bookingHistory = new HashMap<>();

            roomInventory.put("Standard", 2);
            roomInventory.put("Deluxe", 1);
            roomInventory.put("Suite", 1);
        }
    }

    public void addBooking(Reservation reservation) {
        String roomType = reservation.getRoomType();

        if (!roomInventory.containsKey(roomType)) {
            System.out.println("Booking failed: Invalid room type - " + roomType);
            return;
        }

        int available = roomInventory.get(roomType);

        if (available > 0) {
            roomInventory.put(roomType, available - 1);
            bookingHistory.put(reservation.getReservationId(), reservation);
            System.out.println("Booking successful: " + reservation);
        } else {
            System.out.println("Booking failed: No rooms available for " + roomType);
        }
    }

    public void cancelBooking(String reservationId) {
        if (!bookingHistory.containsKey(reservationId)) {
            System.out.println("Cancellation failed: Reservation not found - " + reservationId);
            return;
        }

        Reservation reservation = bookingHistory.get(reservationId);

        if (reservation.isCancelled()) {
            System.out.println("Cancellation failed: Reservation already cancelled - " + reservationId);
            return;
        }

        reservation.cancel();
        String roomType = reservation.getRoomType();
        roomInventory.put(roomType, roomInventory.get(roomType) + 1);

        System.out.println("Cancellation successful: " + reservationId);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (Map.Entry<String, Integer> entry : roomInventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    public void displayBookingHistory() {
        System.out.println("\nBooking History:");
        if (bookingHistory.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation reservation : bookingHistory.values()) {
            System.out.println(reservation);
        }
    }

    public void saveSystemState() {
        HotelData data = new HotelData(roomInventory, bookingHistory);
        persistenceService.saveState(data);
    }
}

// Main class
public class UseCase12DataPersistenceRecovery {
    public static void main(String[] args) {
        System.out.println("=== SYSTEM STARTUP ===");
        HotelBookingSystem system = new HotelBookingSystem();

        system.displayInventory();
        system.displayBookingHistory();

        System.out.println("\n=== PERFORMING OPERATIONS ===");

        Guest guest1 = new Guest("G1", "Alice");
        Guest guest2 = new Guest("G2", "Bob");
        Guest guest3 = new Guest("G3", "Charlie");

        system.addBooking(new Reservation("R1", guest1, "Deluxe", 3000));
        system.addBooking(new Reservation("R2", guest2, "Standard", 2000));
        system.addBooking(new Reservation("R3", guest3, "Suite", 5000));

        system.cancelBooking("R2");

        system.displayInventory();
        system.displayBookingHistory();

        System.out.println("\n=== SAVING SYSTEM STATE BEFORE SHUTDOWN ===");
        system.saveSystemState();

        System.out.println("\n=== SIMULATED RESTART ===");
        HotelBookingSystem recoveredSystem = new HotelBookingSystem();

        recoveredSystem.displayInventory();
        recoveredSystem.displayBookingHistory();
    }
}