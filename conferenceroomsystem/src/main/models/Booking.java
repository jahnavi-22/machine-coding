package main.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Booking {
    private String buildingName;
    private String floorName;
    private String roomId;
    private Timeslot timeslot;
}
