import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

// Custom exception for cancellation-related errors
class CancellationException extends Exception {
    public CancellationException(String message) {
        super(message);
    }
}

// Guest class
class Guest {
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
}

// Reservation class
class Reservation {
    private String reservationId;
    private Guest guest;
    private String roomType;
    private double bookingAmount;
    private String allocatedRoomId;
    private boolean cancelled;

    public Reservation(String reservationId, Guest guest, String roomType, double bookingAmount, String allocatedRoomId) {
        this.reservationId = reservationId;
        this.guest = guest;
        this.roomType = roomType;
        this.bookingAmount = bookingAmount;
        this.allocatedRoomId = allocatedRoomId;
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

    public String getAllocatedRoomId() {
        return allocatedRoomId;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancel() {
        this.cancelled = true;
    }
}

// Cancellation service class
class CancellationService {
    private Map<String, Integer> roomInventory;
    private Map<String, Reservation> confirmedBookings;
    private Stack<String> rollbackStack;

    public CancellationService() {
        roomInventory = new HashMap<>();
        confirmedBookings = new HashMap<>();
        rollbackStack = new Stack<>();

        roomInventory.put("Standard", 1);
        roomInventory.put("Deluxe", 1);
        roomInventory.put("Suite", 0);
    }

    public void addConfirmedBooking(Reservation reservation) {
        confirmedBookings.put(reservation.getReservationId(), reservation);
    }

    public void displayInventory() {
        System.out.println("Current Room Inventory:");
        for (Map.Entry<String, Integer> entry : roomInventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        System.out.println("-----------------------------------");
    }

    public void displayBookingHistory() {
        System.out.println("Booking History:");
        for (Reservation reservation : confirmedBookings.values()) {
            System.out.println("Reservation ID: " + reservation.getReservationId()
                    + ", Guest: " + reservation.getGuest().getGuestName()
                    + ", Room Type: " + reservation.getRoomType()
                    + ", Room ID: " + reservation.getAllocatedRoomId()
                    + ", Status: " + (reservation.isCancelled() ? "Cancelled" : "Confirmed"));
        }
        System.out.println("-----------------------------------");
    }

    public void cancelBooking(String reservationId) {
        try {
            if (!confirmedBookings.containsKey(reservationId)) {
                throw new CancellationException("Reservation does not exist: " + reservationId);
            }

            Reservation reservation = confirmedBookings.get(reservationId);

            if (reservation.isCancelled()) {
                throw new CancellationException("Reservation is already cancelled: " + reservationId);
            }

            // Record allocated room for rollback tracking
            rollbackStack.push(reservation.getAllocatedRoomId());

            // Restore inventory
            String roomType = reservation.getRoomType();
            roomInventory.put(roomType, roomInventory.get(roomType) + 1);

            // Update booking status
            reservation.cancel();

            System.out.println("Cancellation successful!");
            System.out.println("Reservation ID: " + reservation.getReservationId());
            System.out.println("Guest Name: " + reservation.getGuest().getGuestName());
            System.out.println("Room Type: " + reservation.getRoomType());
            System.out.println("Released Room ID: " + reservation.getAllocatedRoomId());
            System.out.println("Rollback Stack Top: " + rollbackStack.peek());
            System.out.println("Updated " + roomType + " inventory: " + roomInventory.get(roomType));
        } catch (CancellationException e) {
            System.out.println("Cancellation failed: " + e.getMessage());
        }

        System.out.println("-----------------------------------");
    }
}

// Main class
public class UseCase10BookingCancellation {
    public static void main(String[] args) {
        CancellationService service = new CancellationService();

        Guest guest1 = new Guest("G101", "Sarika");
        Guest guest2 = new Guest("G102", "Aathmika");
        Guest guest3 = new Guest("G103", "Sumant");

        Reservation booking1 = new Reservation("R001", guest1, "Standard", 2500.0, "S101");
        Reservation booking2 = new Reservation("R002", guest2, "Deluxe", 4000.0, "D201");
        Reservation booking3 = new Reservation("R003", guest3, "Standard", 2600.0, "S102");

        service.addConfirmedBooking(booking1);
        service.addConfirmedBooking(booking2);
        service.addConfirmedBooking(booking3);

        service.displayInventory();
        service.displayBookingHistory();

        service.cancelBooking("R001"); // valid cancellation
        service.cancelBooking("R001"); // already cancelled
        service.cancelBooking("R004"); // non-existent reservation
        service.cancelBooking("R002"); // valid cancellation

        service.displayInventory();
        service.displayBookingHistory();
    }
}