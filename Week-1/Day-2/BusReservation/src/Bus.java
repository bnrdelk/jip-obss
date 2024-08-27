public class Bus {
    private Destination destination;
    private Passenger[] passengers;
    private int size;
    private int ctr = 0;

    public Bus(Destination d, int size) {
        this.destination = d;
        this.size = size;
        passengers = new Passenger[size];
    }

    public void insertPassenger(Passenger p) {
        if(this.destination != p.getDestination()) {
            System.out.println("Destinations do not match.");
        } else {
            if(ctr>=size) {
                System.out.println("Bus is full.");
            } else {
                passengers[ctr] = p;
                ctr++;
                System.out.println("Passenger inserted.");
            }
        }

    }

    public Destination getDestination() {
        return destination;
    }

    public Passenger[] getPassengers() {
        return passengers;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public void setPassengers(Passenger[] passengers) {
        this.passengers = passengers;
    }
}
