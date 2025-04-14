package main.repository;

import main.models.Booking;
import main.models.Timeslot;

import java.util.ArrayList;
import java.util.List;

public class BookingRepository {
    // This class is responsible for managing bookings
    // It will contain methods to add, remove, and retrieve bookings

    private final List<Booking> userBookings = new ArrayList<>();


    public boolean addBooking(Booking booking){
        if(hasBooking(booking.getTimeslot(), booking.getBuildingName(), booking.getFloorName(), booking.getRoomId())){
            System.out.println("Booking already exists for the given slot and room.");
            return false;
        }
        userBookings.add(booking);
        return true;
    }

    public boolean hasBooking(Timeslot slot, String building, String floor, String id){
        return userBookings.stream().anyMatch(booking ->
                booking.getTimeslot().equals(slot) &&
                        booking.getBuildingName().equals(building) &&
                        booking.getFloorName().equals(floor) &&
                        booking.getRoomId().equals(id));
    }

    public List<Booking> getAllBookings(){
        return userBookings;
    }

    public void cancelBooking(Timeslot slot, String building, String floor, String id){
        userBookings.removeIf(booking ->
                booking.getTimeslot().equals(slot) &&
                        booking.getBuildingName().equals(building) &&
                        booking.getFloorName().equals(floor) &&
                        booking.getRoomId().equals(id));
    }
}
