package rider.models;

import lombok.Data;

import java.util.List;

@Data
public class Rider extends PersonalInfo {

    private Double lat;
    private Double lon;
    private List<String> bookingHistory;

    public List<String> getBookingHistory() {
        return bookingHistory;
    }

    public void setBookingHistory(List<String> bookingHistory) {
        this.bookingHistory = bookingHistory;
    }
}
