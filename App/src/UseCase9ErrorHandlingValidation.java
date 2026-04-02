import java.util.HashMap;
import java.util.Map;

// Custom exception for invalid booking scenarios
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
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

    public Reservation(String reservationId, Guest guest, String roomType, double bookingAmount) {
        this.reservationId = reservationId;
        this.guest = guest;
        this.roomType = roomType;
        this.bookingAmount = bookingAmount;
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
}

// Validator class
class InvalidBookingValidator {

    public static void validateReservation(Reservation reservation, Map<String, Integer> roomInventory)
            throws InvalidBookingException {

        if (reservation == null) {
            throw new InvalidBookingException("Reservation cannot be null.");
        }

        if (reservation.getReservationId() == null || reservation.getReservationId().trim().isEmpty()) {
            throw new InvalidBookingException("Reservation ID cannot be empty.");
        }

        if (reservation.getGuest() == null) {
            throw new InvalidBookingException("Guest details are missing.");
        }

        if (reservation.getGuest().getGuestName() == null || reservation.getGuest().getGuestName().trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (reservation.getRoomType() == null || reservation.getRoomType().trim().isEmpty()) {
            throw new InvalidBookingException("Room type cannot be empty.");
        }

        if (!roomInventory.containsKey(reservation.getRoomType())) {
            throw new InvalidBookingException("Invalid room type: " + reservation.getRoomType());
        }

        if (reservation.getBookingAmount() <= 0) {
            throw new InvalidBookingException("Booking amount must be greater than zero.");
        }

        int availableRooms = roomInventory.get(reservation.getRoomType());

        if (availableRooms <= 0) {
            throw new InvalidBookingException("No rooms available for room type: " + reservation.getRoomType());
        }
    }
}

// Hotel booking system
class HotelBookingSystem {
    private Map<String, Integer> roomInventory;

    public HotelBookingSystem() {
        roomInventory = new HashMap<>();
        roomInventory.put("Standard", 2);
        roomInventory.put("Deluxe", 1);
        roomInventory.put("Suite", 0); // intentionally unavailable
    }

    public void processBooking(Reservation reservation) {
        try {
            InvalidBookingValidator.validateReservation(reservation, roomInventory);

            String roomType = reservation.getRoomType();
            int currentCount = roomInventory.get(roomType);

            if (currentCount - 1 < 0) {
                throw new InvalidBookingException("Inventory cannot become negative for room type: " + roomType);
            }

            roomInventory.put(roomType, currentCount - 1);

            System.out.println("Booking successful!");
            System.out.println("Reservation ID: " + reservation.getReservationId());
            System.out.println("Guest Name: " + reservation.getGuest().getGuestName());
            System.out.println("Room Type: " + reservation.getRoomType());
            System.out.println("Booking Amount: Rs." + reservation.getBookingAmount());
            System.out.println("Remaining " + roomType + " rooms: " + roomInventory.get(roomType));
        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        System.out.println("-----------------------------------");
    }

    public void displayInventory() {
        System.out.println("Current Room Inventory:");
        for (Map.Entry<String, Integer> entry : roomInventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        System.out.println("-----------------------------------");
    }
}

// Main class
public class UseCase9ErrorHandlingValidation {
    public static void main(String[] args) {
        HotelBookingSystem system = new HotelBookingSystem();

        system.displayInventory();

        Guest guest1 = new Guest("G101", "Sarika");
        Guest guest2 = new Guest("G102", "Aathmika");
        Guest guest3 = new Guest("G103", "Sumant");

        Reservation validBooking = new Reservation("R001", guest1, "Standard", 2500.0);
        Reservation invalidRoomBooking = new Reservation("R002", guest2, "Premium", 4000.0);
        Reservation invalidAmountBooking = new Reservation("R003", guest3, "Deluxe", -1500.0);
        Reservation unavailableRoomBooking = new Reservation("R004", guest1, "Suite", 6000.0);
        Reservation anotherValidBooking = new Reservation("R005", guest2, "Standard", 2600.0);
        Reservation noRoomLeftBooking = new Reservation("R006", guest3, "Standard", 2700.0);

        system.processBooking(validBooking);           // success
        system.processBooking(invalidRoomBooking);     // invalid room type
        system.processBooking(invalidAmountBooking);   // invalid booking amount
        system.processBooking(unavailableRoomBooking); // no rooms available
        system.processBooking(anotherValidBooking);    // success
        system.processBooking(noRoomLeftBooking);      // no rooms left now

        system.displayInventory();
    }
}