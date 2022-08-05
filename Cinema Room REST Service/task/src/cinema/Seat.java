package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;
import java.util.UUID;

public class Seat {

    private int row;
    private int column;
    private int price;
    private boolean isAvailable;
    private UUID token;
    private boolean isPurchased;

    public Seat(){};

    public Seat(int row, int column){
        this.row = row;
        this.column = column;
        this.price = getPrice(row);
        this.isAvailable = true;
        this.token = UUID.randomUUID();
    }

    private int getPrice(int row){
        if(row <= 4){
            return 10;
        }
        return 8;
    }

    @JsonIgnore
    public UUID getToken() {
        return token;
    }

    @JsonIgnore
    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "row=" + row +
                ", column=" + column +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
