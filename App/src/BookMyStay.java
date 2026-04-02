// File: UseCase5BookingRequestQueue.java

import java.util.LinkedList;
import java.util.Queue;

// Represents a guest's intent to book a room
class reservation {
    private String guestName;

    public reservation(String guestName) {
        this.guestName = guestName;
    }

    public String getGuestName() {
        return guestName;
    }

    @Override
    public String toString() {
        return "Reservation request from: " + guestName;
    }
}

// Main class to demonstrate booking request queue
public class BookMyStay {

    public static void main(String[] args) {
        // Queue to store booking requests in FIFO order
        Queue<Reservation> bookingRequestQueue = new LinkedList<>();

        // Guests submit booking requests
        Queue<Reservation> bookingRequestQueue = new LinkedList<>();

        bookingRequestQueue.add(
                new Reservation("R1", new Guest("G1", "Alice"), "Deluxe", 3000)
        );

        bookingRequestQueue.add(
                new Reservation("R2", new Guest("G2", "Bob"), "Standard", 2000)
        );

        bookingRequestQueue.add(
                new Reservation("R3", new Guest("G3", "Charlie"), "Suite", 5000)
        );

        bookingRequestQueue.add(
                new Reservation("R4", new Guest("G4", "Diana"), "Deluxe", 3500)
        );

        System.out.println("=== Booking Request Queue (FIFO) ===");
        System.out.println("Requests are stored in arrival order.");
        System.out.println("No inventory mutation occurs at this stage.\n");

        // Display queued requests
        for (Reservation request : bookingRequestQueue) {
            System.out.println(request);
        }

        System.out.println("\n=== Processing Requests ===");
        System.out.println("Requests will be processed fairly in order of arrival.\n");

        // Simulate processing requests
        while (!bookingRequestQueue.isEmpty()) {
            Reservation nextRequest = bookingRequestQueue.poll(); // FIFO
            System.out.println("Processing: " + nextRequest);
        }
    }
}}