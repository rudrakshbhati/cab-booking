package booking.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Booking {
    private String bookingId;
    private String riderUserId;
    private String partnerId;
    private String vehicleId;
    private long startTime;
    private long endTime;
    private String status;
}
