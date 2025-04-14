package main.models;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Room {
    private String id;
    private List<Timeslot> bookedSlots = new ArrayList<>();

    public void addSlot(Timeslot slot){
        bookedSlots.add(slot);
    }

    public boolean isAvailable(Timeslot slot){
        for(Timeslot bookedSlot: bookedSlots){
            if(bookedSlot.overlaps(slot))
                return false;
        }
        return true;
    }

    public void cancelSlot(Timeslot slot){
        bookedSlots.removeIf(bookedSlot -> bookedSlot.equals(slot));
    }
}
