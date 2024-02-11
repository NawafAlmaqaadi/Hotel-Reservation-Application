package model;

import service.CustomerService;
import service.ReservationService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import static model.RoomType.Single;

public class MainMenu {

    public static void main (String[] args){

        Scanner input = new Scanner(System.in);
        CustomerService customerService = new CustomerService();
        ReservationService reservationService = new ReservationService();
        customerService.addCustomer("nawaf@gmail.com","Nawaf","Yaseen");
        Customer newCustomer = new Customer("nawaf","Yaseen","nawaf@gmail.com");


        int exitKey = 0;


        System.out.println("Welcome to the Hotel Reservation Application");


        do {
    System.out.println("___________________________________________");
    System.out.println("1. Find and reserve a room");
    System.out.println("2. See my reservations");
    System.out.println("3. Create an account");
    System.out.println("4. Admin");
    System.out.println("5. Exit \n");
    System.out.println("___________________________________________");

    System.out.print("Please select a number for the menu option: ");

    exitKey = input.nextInt();

    if(exitKey == 1){

        System.out.println("Find a room");

        String searchForCheckInDate2 = input.next();
        String searchForCheckOutDate2 = input.next();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date CheckInDate = sdf.parse(searchForCheckInDate2);
            Date CheckOutDate = sdf.parse(searchForCheckOutDate2);
            reservationService.findRooms(CheckInDate,CheckOutDate);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    } else if (exitKey == 2) {

    }else if (exitKey == 3) {


        System.out.println("Enter Email format: name@domain.com");
        String firstName = input.next();
        String lastName = input.next();
        String email = input.next();


    }else if (exitKey == 4) {
        AdminMenu.Menu();

    }

        }while(exitKey <= 4);


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
