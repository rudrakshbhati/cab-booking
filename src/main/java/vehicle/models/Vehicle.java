package vehicle.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    private String id;
    private String carNumber;
    private Double lat;
    private Double lon;
    private String type;
    private boolean isAvailable;
    private String partnerId;
    private String aggregationId;
}
