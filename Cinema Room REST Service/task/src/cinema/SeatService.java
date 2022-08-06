package cinema;

import java.util.List;
import java.util.stream.Collectors;

public class SeatService {

    private final Cinema cinema = new Cinema(9, 9);

    public Cinema getCinema() {
        return cinema;
    }

    public List<Seat> getSeats(){
        return cinema.getSeats();
    }

    public List<Seat> getAvailableSeats() {
        return cinema.getSeats().stream().filter(Seat::isAvailable).collect(Collectors.toList());
    }

    public List<Seat> getUnvailableSeats(){
        return cinema.getSeats().stream().filter(seat -> !seat.isAvailable()).collect(Collectors.toList());
    }
}
