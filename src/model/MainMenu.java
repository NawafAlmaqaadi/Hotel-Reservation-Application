package model;

import service.CustomerService;
import service.ReservationService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static model.RoomType.Single;

public class MainMenu {

    public static void main (String[] args){

        Scanner input = new Scanner(System.in);
        CustomerService customerService = new CustomerService();
        ReservationService reservationService = new ReservationService();

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

            switch(exitKey) {
                case 1:
                    System.out.println("Find and reserve a room");

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        System.out.println("Enter CheckIn Date dd/mm/yyyy");
                        String checkInDateString = input.next();
                        System.out.println("Enter CheckOut Date dd/mm/yyyy");
                        String checkOutDateString = input.next();

                        String[] checkInDate = checkInDateString.split("/");
                        String[] checkOutDate = checkOutDateString.split("/");

                        Date checkInDateValidated = returnDate(checkInDate[0], (checkInDate[1]), checkInDate[2]);
                        Date checkOutDateValidated = returnDate(checkOutDate[0], checkOutDate[1], checkOutDate[2]);
                        reservationService.findRooms(checkInDateValidated, checkOutDateValidated);

                        System.out.println("Would you like to book a room? y/n");
                        String qBook = input.next();

                        if (qBook.equalsIgnoreCase("y")) {
                            System.out.println("Do you have an account with us y/n?");
                            String qAccount = input.next();
                            if (qAccount.equalsIgnoreCase("y")) {
                                System.out.println("Enter Email format: name@domain.com");
                                String email = input.next();
                                if (customerService.customerExists(email)) {
                                    System.out.println("what room number would you like to reserve");
                                    String roomNumber = input.next();
                                    if (reservationService.roomExists(roomNumber)) {
                                        reservationService.reserveARooms(customerService.getCustomer(email), reservationService.getARoom(roomNumber), checkInDateValidated, checkOutDateValidated);
                                    } else {
                                        System.out.println("Room number is invalid.");
                                    }
                                } else {
                                    System.out.println("Account not registered.");
                                }
                            } else if (qAccount.equalsIgnoreCase("n")) {
                                System.out.println("Please create an account");
                                createAccount(input, customerService);
                            } else {
                                System.out.println("Invalid input for account question.");
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid Date format! Please use dd/mm/yyyy format.");
                    } catch (ParseException e) {
                        System.out.println("Invalid Date format! Please use dd/mm/yyyy format.");
                    }
                    break;
                case 2:
                    System.out.println("Please write your registered mail");
                    String getCustomerReservationEmail = input.next();
                    reservationService.getCustomerReservation(customerService.getCustomer(getCustomerReservationEmail));
                    break;

                case 3:
                    createAccount(input, customerService);
                    break;

                case 4:
                    AdminMenu.Menu();
                    break;

                default:
                    System.out.println("Invalid option!");
                    break;
            }

        } while (exitKey <= 4);

    }
    public static Date returnDate (String year, String month, String day) throws ParseException {


        return new SimpleDateFormat("dd/MM/yyyy").parse(year+"/"+month+"/"+day);
    }

    public static String validateEmail (String email, Scanner input){

        String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                        "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        Matcher matcher2 = pattern.matcher(email);
        while (!matcher.matches()) {
            System.out.println("Enter Email format: name@domain.com");
            email = input.nextLine();
            matcher = pattern.matcher(email);

        }
        return email;

    }

    public static void createAccount(Scanner input, CustomerService customerService){


        System.out.println("Enter Email format: name@domain.com");
        String email = input.next();
        email =validateEmail(email,input);
        System.out.println("First Name");
        String firstName = input.next();
        System.out.println("Last Name");
        String lastName = input.next();
        customerService.addCustomer(email,firstName,lastName);
        System.out.println("Added successfully");



    }
}
