public class UsaseCase8BookingHistoryReport {
    public static void main(String[] args) {

        // Create guests
        Guest guest1 = new Guest("G101", "Sarika");
        Guest guest2 = new Guest("G102", "Aathmika");
        Guest guest3 = new Guest("G103", "Sumant");

        // Create confirmed reservations
        Reservation r1 = new Reservation("R201", guest1, "Deluxe Room", 3500.0);
        Reservation r2 = new Reservation("R202", guest2, "Suite Room", 5000.0);
        Reservation r3 = new Reservation("R203", guest3, "Standard Room", 2500.0);

        // Add confirmed bookings to booking history
        BookingHistory bookingHistory = new BookingHistory();
        bookingHistory.addConfirmedBooking(r1);
        bookingHistory.addConfirmedBooking(r2);
        bookingHistory.addConfirmedBooking(r3);

        // Admin reviews booking history
        bookingHistory.displayBookingHistory();

        // Admin requests summary report
        BookingReportService reportService = new BookingReportService();
        reportService.generateSummaryReport(bookingHistory.getConfirmedBookings());
    }
}
