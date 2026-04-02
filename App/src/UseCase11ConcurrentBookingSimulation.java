import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
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

    @Override
    public String toString() {
        return reservationId + " | " + guest.getGuestName() + " | " + roomType + " | Rs." + bookingAmount;
    }
}

// Shared booking system
class ConcurrentBookingSystem {
    private Queue<Reservation> bookingQueue = new LinkedList<>();
    private Map<String, Integer> roomInventory = new HashMap<>();

    public ConcurrentBookingSystem() {
        roomInventory.put("Standard", 2);
        roomInventory.put("Deluxe", 1);
        roomInventory.put("Suite", 1);
    }

    // synchronized method to safely add requests
    public synchronized void addBookingRequest(Reservation reservation) {
        bookingQueue.add(reservation);
        System.out.println(Thread.currentThread().getName() + " added request: " + reservation);
    }

    // synchronized method to safely retrieve request
    public synchronized Reservation getNextBookingRequest() {
        return bookingQueue.poll();
    }

    // synchronized method for critical section
    public synchronized void processBooking(Reservation reservation) {
        String roomType = reservation.getRoomType();

        if (!roomInventory.containsKey(roomType)) {
            System.out.println(Thread.currentThread().getName()
                    + " failed: Invalid room type for " + reservation.getReservationId());
            return;
        }

        int available = roomInventory.get(roomType);

        if (available > 0) {
            roomInventory.put(roomType, available - 1);
            System.out.println(Thread.currentThread().getName()
                    + " booked successfully -> " + reservation
                    + " | Remaining " + roomType + " rooms: " + roomInventory.get(roomType));
        } else {
            System.out.println(Thread.currentThread().getName()
                    + " failed: No " + roomType + " rooms available for " + reservation.getReservationId());
        }
    }

    public synchronized boolean hasPendingRequests() {
        return !bookingQueue.isEmpty();
    }

    public synchronized void displayInventory() {
        System.out.println("\nFinal Room Inventory:");
        for (Map.Entry<String, Integer> entry : roomInventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Worker thread
class BookingProcessor extends Thread {
    private ConcurrentBookingSystem system;

    public BookingProcessor(ConcurrentBookingSystem system, String threadName) {
        super(threadName);
        this.system = system;
    }

    @Override
    public void run() {
        while (true) {
            Reservation reservation = system.getNextBookingRequest();

            if (reservation == null) {
                break;
            }

            system.processBooking(reservation);

            try {
                Thread.sleep(100); // to simulate concurrent delay
            } catch (InterruptedException e) {
                System.out.println(getName() + " interrupted.");
            }
        }
    }
}

// Main class
public class UseCase11ConcurrentBookingSimulation {
    public static void main(String[] args) {
        ConcurrentBookingSystem system = new ConcurrentBookingSystem();

        // Multiple guests create requests
        system.addBookingRequest(new Reservation("R1", new Guest("G1", "Alice"), "Deluxe", 3000));
        system.addBookingRequest(new Reservation("R2", new Guest("G2", "Bob"), "Standard", 2000));
        system.addBookingRequest(new Reservation("R3", new Guest("G3", "Charlie"), "Suite", 5000));
        system.addBookingRequest(new Reservation("R4", new Guest("G4", "Diana"), "Deluxe", 3500));
        system.addBookingRequest(new Reservation("R5", new Guest("G5", "Eve"), "Standard", 2200));
        system.addBookingRequest(new Reservation("R6", new Guest("G6", "Frank"), "Standard", 2100));

        // Multiple processor threads
        BookingProcessor processor1 = new BookingProcessor(system, "Processor-1");
        BookingProcessor processor2 = new BookingProcessor(system, "Processor-2");
        BookingProcessor processor3 = new BookingProcessor(system, "Processor-3");

        processor1.start();
        processor2.start();
        processor3.start();

        try {
            processor1.join();
            processor2.join();
            processor3.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
        }

        system.displayInventory();
    }
}