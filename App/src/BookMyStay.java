import java.util.LinkedList;
import java.util.Queue;

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

    public Reservation(String reservationId, Guest guest, String roomType,
                       double bookingAmount, String allocatedRoomId) {
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

    @Override
    public String toString() {
        return reservationId + " | " + guest.getGuestName() + " | " + roomType +
                " | RoomID: " + allocatedRoomId + " | Rs." + bookingAmount;
    }
}

// Main class
public class BookMyStay {
    public static void main(String[] args) {

        // Queue to store booking requests in FIFO order
        Queue<Reservation> bookingRequestQueue = new LinkedList<>();

        // Guests submit booking requests
        bookingRequestQueue.add(
                new Reservation("R1", new Guest("G1", "Alice"), "Deluxe", 3000, "D101")
        );

        bookingRequestQueue.add(
                new Reservation("R2", new Guest("G2", "Bob"), "Standard", 2000, "S201")
        );

        bookingRequestQueue.add(
                new Reservation("R3", new Guest("G3", "Charlie"), "Suite", 5000, "SU301")
        );

        bookingRequestQueue.add(
                new Reservation("R4", new Guest("G4", "Diana"), "Deluxe", 3500, "D102")
        );

        System.out.println("=== Booking Request Queue (FIFO) ===");
        System.out.println("Requests are stored in arrival order.");
        System.out.println("No inventory mutation occurs at this stage.\n");

        for (Reservation request : bookingRequestQueue) {
            System.out.println(request);
        }

        System.out.println("\n=== Processing Requests ===");
        System.out.println("Requests will be processed fairly in order of arrival.\n");

        while (!bookingRequestQueue.isEmpty()) {
            Reservation nextRequest = bookingRequestQueue.poll();
            System.out.println("Processing: " + nextRequest);
        }
    }
}