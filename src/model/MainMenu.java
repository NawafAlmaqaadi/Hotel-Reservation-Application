package model;

import service.CustomerService;
import service.ReservationService;

import java.util.Calendar;
import java.util.Date;

import static model.RoomType.Single;
import static model.RoomType.Double;

public class Main {

    public static void main (String[] args){


        CustomerService customerService = new CustomerService();
        Customer newCustomer = new Customer("Nawaf","Yaseen","nawaf@gmail.com");
        Customer newCustomer2 = new Customer("Omar","Yaseen","Omar@gmail.com");
        ReservationService reservationService = new ReservationService();
        customerService.addCustomer("nawaf@gmail.com","Nawaf","Yaseen");


        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");



        reservationService.printAllReservation(); //CORRECT

        System.out.println("----------------------");

        System.out.println("----------------------");


        System.out.println("Add a room");

        Room newRoom = new Room("1",100.0,Single);
        reservationService.addRoom(newRoom); //CORRECT
        System.out.println("----------------------");

        System.out.println("Reserve a room");

        Date checkInDate = returnDate(2024, 2-1, 5);
        Date checkOutDate = returnDate(2024, 2-1, 7);

        reservationService.reserveARooms(newCustomer,newRoom,checkInDate, checkOutDate);
        System.out.println(newRoom);
        System.out.println("----------------------");

        System.out.println("Get a room");
        System.out.println(  reservationService.getARoom("1"));
        System.out.println("----------------------");

        System.out.println("Find a room");
        Date searchForCheckInDate = returnDate(2024, 2-1, 1);
        Date searchForCheckOutDate = returnDate(2024, 2-1, 7);

        reservationService.findRooms(searchForCheckInDate,searchForCheckOutDate);


        System.out.println("Get customers reservation");
        System.out.println(reservationService.getCustomerReservation(newCustomer));
        System.out.println("Print all reservation ");
        reservationService.printAllReservation();
    }

    public static Date returnDate (int year, int month, int day){
        Calendar DateCalendar = Calendar.getInstance();
        DateCalendar.set(year, month, day);

        return DateCalendar.getTime();
    }
}
