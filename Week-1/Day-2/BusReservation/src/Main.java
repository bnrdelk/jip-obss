public class Main {
    public static void main(String[] args) {
                Passenger passenger = new Passenger("Beyza", Destination.ANKARA);
                Passenger passenger1 = new Passenger("A", Destination.ADANA);
                Passenger passenger2 = new Passenger("B", Destination.ANKARA);
                Bus bus = new Bus(Destination.ANKARA, 1);

                bus.insertPassenger(passenger);
                bus.insertPassenger(passenger1);
                bus.insertPassenger(passenger2);
    }
}