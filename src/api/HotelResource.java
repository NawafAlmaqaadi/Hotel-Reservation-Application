package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotelResource {

    private static final CustomerService customerService = new CustomerService();
    private static final ReservationService reservationService = new ReservationService();


    public Customer getCustomer(String email){

        return customerService.getCustomer(email);
    }

    public void addRoom(IRoom rooms){

        reservationService.addRoom(rooms);
    }

    public Collection<IRoom> getAllRooms(){
        return reservationService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    public void displayAllReservations(){

        System.out.println("");
    }
}
