// File: UseCase5BookingRequestQueue.java

import java.util.LinkedList;
import java.util.Queue;

// Represents a guest's intent to book a room
class Reservation {
    private String guestName;

    public Reservation(String guestName) {
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
        bookingRequestQueue.add(new Reservation("Alice"));
        bookingRequestQueue.add(new Reservation("Bob"));
        bookingRequestQueue.add(new Reservation("Charlie"));
        bookingRequestQueue.add(new Reservation("Diana"));

        System.out.println("=== Booking Request Queue (FIFO) ===");
        System.out.println("Requests are stored in arrival order.");
        System.out.println("No inventory mutation occurs at this stage.\n");

        // Display queued requests
        for (Reservation request : bookingRequestQueue) {
            System.out.println(request);
        }

        System.out.println("\n=== Processing Requests ===");
        System.out.println("Requests will be processed fairly in order of arrival.\n");

        // Simulate processing requests (allocation system will handle later)
        while (!bookingRequestQueue.isEmpty()) {
            Reservation nextRequest = bookingRequestQueue.poll(); // FIFO
            System.out.println("Processing: " + nextRequest);
        }
    }
}