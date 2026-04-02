import java.util.*;

// Represents a guest
class guest {
    private String guestId;
    private String guestName;

    public guest(String guestId, String guestName) {
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
        return guestName + " (" + guestId + ")";
    }
}

// Represents a reservation
class reservation {
    private String reservationId;
    private Guest guest;
    private String roomType;
    private double bookingAmount;

    public reservation(String reservationId, Guest guest, String roomType, double bookingAmount) {
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

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                "\nGuest: " + guest.getGuestName() +
                "\nRoom Type: " + roomType +
                "\nBooking Amount: Rs." + bookingAmount;
    }
}

// Maintains booking history in insertion order
class BookingHistory {
    private List<Reservation> confirmedBookings;

    public BookingHistory() {
        confirmedBookings = new ArrayList<>();
    }

    // Store confirmed reservation
    public void addConfirmedBooking(Reservation reservation) {
        confirmedBookings.add(reservation);
    }

    // Retrieve all stored reservations
    public List<Reservation> getConfirmedBookings() {
        return confirmedBookings;
    }

    // Display all bookings
    public void displayBookingHistory() {
        if (confirmedBookings.isEmpty()) {
            System.out.println("No booking history available.");
            return;
        }

        System.out.println("===== BOOKING HISTORY =====");
        for (Reservation reservation : confirmedBookings) {
            System.out.println(reservation);
            System.out.println("---------------------------");
        }
    }
}

// Generates reports from booking history
class BookingReportService {

    public void generateSummaryReport(List<Reservation> reservations) {
        if (reservations.isEmpty()) {
            System.out.println("No bookings available for reporting.");
            return;
        }

        int totalBookings = reservations.size();
        double totalRevenue = 0;

        Map<String, Integer> roomTypeCount = new HashMap<>();

        for (Reservation reservation : reservations) {
            totalRevenue += reservation.getBookingAmount();

            String roomType = reservation.getRoomType();
            roomTypeCount.put(roomType, roomTypeCount.getOrDefault(roomType, 0) + 1);
        }

        System.out.println("===== BOOKING SUMMARY REPORT =====");
        System.out.println("Total Confirmed Bookings: " + totalBookings);
        System.out.println("Total Revenue: Rs." + totalRevenue);
        System.out.println("Room Type Distribution:");

        for (Map.Entry<String, Integer> entry : roomTypeCount.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

