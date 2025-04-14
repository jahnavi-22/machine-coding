package main.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Timeslot {
    private int start;
    private int end;

    public boolean overlaps(Timeslot other){
        return (this.start < other.end && this.end > other.start);
    }

    @Override
    public String toString(){
        return start + ":" + end;
    }
}
