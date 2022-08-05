package cinema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Cinema {

    private int rows;
    private int columns;
    private List<Seat> seats;

    public Cinema(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public List<Seat> getSeats() {
        if(seats == null){
            List<Seat> seatsList = new ArrayList<>();
            for(int i = 1; i <=  this.rows; i++){
                for (int j = 1; j <= this.columns; j++){
                    seatsList.add(new Seat(i , j));
                }
            }
            seats = seatsList;
        }
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public List<Seat> getAvailableSeats() {
        return getSeats().stream().filter(Seat::isAvailable).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "rows=" + rows +
                ", columns=" + columns +
                ", seats=" + seats +
                '}';
    }
}
