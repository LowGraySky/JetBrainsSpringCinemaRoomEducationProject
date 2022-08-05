package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class CinemaController {

    private Cinema cinema = new Cinema(9, 9);

    @GetMapping("/seats")
    public ResponseEntity<HashMap<String, Object>> getAvailableSeats(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("total_rows", cinema.getRows());
        result.put("total_columns", cinema.getColumns());
        result.put("available_seats", cinema.getAvailableSeats());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/return")
    public ResponseEntity<Map<String, Object>> refundTicket(@RequestBody Map<String, String> token){
        Optional<Seat> seat = cinema.getSeats()
                .stream()
                .filter( s -> s.getToken().equals(UUID.fromString(token.get("token"))))
                .findAny();

        if(seat.isEmpty()){
            return new ResponseEntity<>(Map.of("error","Wrong token!"), HttpStatus.BAD_REQUEST);
        }
        seat.get().setAvailable(true);
        return new ResponseEntity<>(Map.of("returned_ticket", seat.get()), HttpStatus.OK);
    }

    @PostMapping("/purchase")
    public ResponseEntity<Map<String, Object>> purchaseTicket(@RequestBody Seat body){
        Optional<Seat> seat = cinema.getSeats()
                .stream()
                .filter( s -> s.getRow() == body.getRow() && s.getColumn() == body.getColumn())
                .findAny();

        if(seat.isEmpty()){
            return new ResponseEntity<>(
                    Map.of("error", "The number of a row or a column is out of bounds!"),
                    HttpStatus.BAD_REQUEST
            );
        }
        if (!seat.get().isAvailable()){
            return new ResponseEntity<>(
                    Map.of("error","The ticket has been already purchased!"),
                    HttpStatus.BAD_REQUEST
            );
        }
        seat.get().setAvailable(false);
        HashMap<String, Object> result = new HashMap<>();
        result.put("token", seat.get().getToken());
        result.put("ticket", seat.get());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStatistic(@RequestParam(name = "password", required = false) String password){
        if(password == null || !password.equals("super_secret")){
            return new ResponseEntity<>(Map.of("error", "The password is wrong!"), HttpStatus.UNAUTHORIZED);
        }
        HashMap<String, Object> result = new HashMap<>();
        List<Seat> notAvailableSeats = cinema.getSeats().stream().filter(seat -> !seat.isAvailable()).collect(Collectors.toList());
        result.put("current_income", notAvailableSeats.stream().mapToInt(Seat::getPrice).sum());
        result.put("number_of_available_seats", cinema.getAvailableSeats().size());
        result.put("number_of_purchased_tickets", notAvailableSeats.size());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
