import java.util.*;

class Service {
    private String name;
    private double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return name + " (₹" + cost + ")";
    }
}

public class UseCaseAddOnServiceSelection {

    // Map reservation ID -> list of services
    private Map<String, List<Service>> reservationServices = new HashMap<>();

    // Attach services to a reservation
    public void addServicesToReservation(String reservationId, List<Service> services) {
        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).addAll(services);

        System.out.println("Services added to Reservation ID: " + reservationId);
        for (Service s : services) {
            System.out.println(" - " + s);
        }
    }

    // Calculate total additional cost for a reservation
    public double calculateAdditionalCost(String reservationId) {
        List<Service> services = reservationServices.getOrDefault(reservationId, Collections.emptyList());
        double total = 0;
        for (Service s : services) {
            total += s.getCost();
        }
        return total;
    }

    // Display services attached to reservations
    public void displayReservationServices() {
        System.out.println("\n--- Reservation Add-On Services ---");
        for (Map.Entry<String, List<Service>> entry : reservationServices.entrySet()) {
            String reservationId = entry.getKey();
            List<Service> services = entry.getValue();
            System.out.println("Reservation ID: " + reservationId);
            for (Service s : services) {
                System.out.println("   " + s);
            }
            System.out.println("   Total Add-On Cost: ₹" + calculateAdditionalCost(reservationId));
        }
    }

    // Main driver
    public static void main(String[] args) {
        UC7 manager = new UC7();

        // Example reservation IDs (these would normally come from UC6 allocations)
        String reservation1 = "RES-101";
        String reservation2 = "RES-102";

        // Guest selects services
        List<Service> servicesForRes1 = Arrays.asList(
                new Service("Breakfast", 500),
                new Service("Airport Pickup", 1200)
        );

        List<Service> servicesForRes2 = Arrays.asList(
                new Service("Spa Access", 1500),
                new Service("Dinner Buffet", 800),
                new Service("Late Checkout", 600)
        );

        // Attach services
        manager.addServicesToReservation(reservation1, servicesForRes1);
        manager.addServicesToReservation(reservation2, servicesForRes2);

        // Display final mapping
        manager.displayReservationServices();
    }
}