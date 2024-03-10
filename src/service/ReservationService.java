package service;

import model.*;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReservationService {

    private static final Collection<Reservation> reservations = new LinkedList<>();
    private static final Set<IRoom> rooms = new HashSet<>();
    private static final Collection<IRoom> availableRooms = new LinkedList<>();
    private static ReservationService instance;
    CustomerService customerService = new CustomerService();

    public static void setInstance(ReservationService instance) {
        ReservationService.instance = instance;
    }

    public void setInput(Scanner input) {
        this.input = input;
    }

    public Scanner getInput() {
        return input;
    }

    Scanner input = new Scanner(System.in);


    public ReservationService() {
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }

    public void addRoom(IRoom room) {

        boolean vaildate = true;

        for (IRoom roomValidation : rooms) {
            if (roomValidation.getRoomNumber().equals(room.getRoomNumber())) {
                System.out.println("Room number already added, please enter another room number");
                vaildate = false;
            }
        }
        if (vaildate) {
            rooms.add(room);
            availableRooms.add(room);
            System.out.println("Added Successfully ");

        }


    }

    public IRoom getARoom(String roomId) {

        for (IRoom room : rooms) {
            if (room.getRoomNumber().equals(roomId)) {
                return room;
            }

        }
        return null;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        for (Reservation reserved : reservations) {
            if (reserved.getCustomer().equals(customer) && reserved.getRoom().equals(room) &&
                    !(checkOutDate.before(reserved.getCheckInDate()) || checkInDate.after(reserved.getCheckOutDate()))) {
                System.out.println("You have already booked this room for the specified date range.");
                return null;
            }
        }

        boolean isAvailable = true;
        for (Reservation reserved : reservations) {
            if (room.equals(reserved.getRoom()) && !(checkOutDate.before(reserved.getCheckInDate()) || checkInDate.after(reserved.getCheckOutDate()))) {
                isAvailable = false;
                break;
            }
        }

        if (isAvailable) {
            Reservation newReservation = new Reservation(customer, room, checkInDate, checkOutDate);

            reservations.add(newReservation);

            // Calculate total price
            long numberOfDays = getNumberOfDays(checkInDate, checkOutDate);
            double totalPrice = numberOfDays * room.getRoomPrice();

            // Print reservation information
            System.out.println("Reservation information");
            System.out.println("Customer name: " + newReservation.getCustomer().getFirstName() + " " + newReservation.getCustomer().getLastName());
            System.out.println("Room number: " + newReservation.getRoom().getRoomNumber());
            System.out.println("Room type: " + newReservation.getRoom().getRoomType());
            System.out.println("Check In Date : " + newReservation.getCheckInDate());
            System.out.println("Check Out Date : " + newReservation.getCheckOutDate());
            System.out.println("Total price: " + totalPrice);
            System.out.println("Reserved successfully !");

            return newReservation;
        } else {
            System.out.println("The room is not available for the selected dates.");
            return null;
        }
    }
    private long getNumberOfDays(Date checkInDate, Date checkOutDate) {
        long diffInMillies = Math.abs(checkOutDate.getTime() - checkInDate.getTime());
        long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return diffInDays;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        List<IRoom> availableRooms = new ArrayList<>();

        if (rooms.isEmpty()) {
            System.out.println("No rooms available");
            return availableRooms; // Return empty list if no rooms available
        }

        // Check if there are available rooms for the given date range
        boolean roomAvailable;
        for (IRoom room : rooms) {
            roomAvailable = true;
            for (Reservation reserved : reservations) {
                if (room.equals(reserved.getRoom()) &&
                        !((checkOutDate.before(reserved.getCheckInDate())) ||
                                (checkInDate.after(reserved.getCheckOutDate())))) {
                    roomAvailable = false;
                    break;
                }
            }
            if (roomAvailable) {
                availableRooms.add(room);
            }
        }

        if (availableRooms.isEmpty()) {
            // No available rooms for the given date range
            // Check if there are available rooms for the recommended date range (7 days later)
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(checkInDate);
            calendar.add(Calendar.DAY_OF_YEAR, 7);
            Date recommendedCheckInDate = calendar.getTime();
            calendar.setTime(checkOutDate);
            calendar.add(Calendar.DAY_OF_YEAR, 7);
            Date recommendedCheckOutDate = calendar.getTime();

            boolean recommendedRoomAvailable = false;
            for (IRoom room : rooms) {
                recommendedRoomAvailable = true;
                for (Reservation reserved : reservations) {
                    if (room.equals(reserved.getRoom()) &&
                            !((recommendedCheckOutDate.before(reserved.getCheckInDate())) ||
                                    (recommendedCheckInDate.after(reserved.getCheckOutDate())))) {
                        recommendedRoomAvailable = false;
                        break;
                    }
                }
                if (recommendedRoomAvailable) {
                    availableRooms.add(room);
                    break; // Once a recommended room is found, no need to continue the loop
                }
            }

            if (!recommendedRoomAvailable) {
                // If no recommended rooms are available either, show any available room
                System.out.println("No rooms available for the selected dates or the recommended dates. Showing any available room.");
                return availableRooms;
            } else {
                System.out.println("No rooms available for the selected dates. Recommended dates: ");
                System.out.println("Check-in: " + recommendedCheckInDate);
                System.out.println("Check-out: " + recommendedCheckOutDate);

                // Recursively find available rooms for the recommended dates
                return findRooms(recommendedCheckInDate, recommendedCheckOutDate);
            }
        }

        // Check if the user already has a reservation for any of the available rooms
        List<IRoom> filteredRooms = new ArrayList<>();
        for (IRoom availableRoom : availableRooms) {
            boolean alreadyBooked = false;
            for (Reservation reserved : reservations) {
                if (reserved.getRoom().equals(availableRoom) &&
                        !((checkOutDate.before(reserved.getCheckInDate())) ||
                                (checkInDate.after(reserved.getCheckOutDate())))) {
                    alreadyBooked = true;
                    break;
                }
            }
            if (!alreadyBooked) {
                filteredRooms.add(availableRoom);
            }
        }

        System.out.println("Available rooms: ");
        for (IRoom freeRooms : filteredRooms) {
            System.out.println(freeRooms);
        }

        // Prompt the user for room booking
        while (true) {
            System.out.println("Would you like to book a room? (y/n)");
            String qBook = input.next();
            if (qBook.equalsIgnoreCase("y")) {
                System.out.println("Do you have an account with us? (y/n)");
                String qAccount = input.next();
                if (qAccount.equalsIgnoreCase("y")) {
                    System.out.println("Enter your email address: ");
                    String email = input.next();
                    if (customerService.customerExists(email)) {
                        System.out.println("Enter the room number you would like to reserve: ");
                        String roomNumber = input.next();
                        if (roomOn(roomNumber)) {
                            reserveARoom(customerService.getCustomer(email), getARoom(roomNumber), checkInDate, checkOutDate);
                            break; // Exit the loop if room reservation is successful
                        } else {
                            System.out.println("Invalid room number.");
                        }
                    } else {
                        System.out.println("Account not found.");
                    }
                } else if (qAccount.equalsIgnoreCase("n")) {
                    System.out.println("Please create an account.");
                    createAccount(input, customerService);
                } else {
                    System.out.println("Invalid input. Please enter 'y' or 'n'.");
                }
            } else if (qBook.equalsIgnoreCase("n")) {
                break; // Exit the loop if the user doesn't want to book a room
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        }

        return filteredRooms;
    }





    public Collection<Reservation> getCustomerReservation(Customer customer) {
        Collection<Reservation> customerReservations = new ArrayList<>();

        for (Reservation reserved : reservations) {
            if (customer.getEmail().equals(reserved.getCustomer().getEmail())) {
                System.out.println(reserved);
                customerReservations.add(reserved);
            }
        }

        return customerReservations;
    }


    public void printAllReservation() {
        for (Reservation reserved : reservations) {
            System.out.println(reserved);
        }
    }


    public Collection<IRoom> getAllRooms() {


        for (IRoom room : rooms) {
            System.out.println("Room Number: " + room.getRoomNumber() + " " + room.getRoomType() + " Room Price " + room.getRoomPrice());
        }
        return rooms;
    }

    public boolean roomExists(IRoom room) {
        if (rooms.contains(room)) {
            // Room is already stored
            return true;
        } else {
            // Room is not stored
            return false;

        }
    }

    public boolean roomOn(String roomNumber) {
        for (IRoom room : rooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                System.out.println("Room '" + roomNumber + "' validated successfully .");
                System.out.println("---------------------------------------------------");
                return true;
            }
        }
        System.out.println("Room '" + roomNumber + "' is not stored yet.");
        return false;
    }
    public static void createAccount(Scanner input, CustomerService customerService) {
        System.out.println("Enter Email format: name@domain.com");
        String email = input.next();
        email = validateEmail(email, input);
        System.out.println("First Name");
        String firstName = input.next();
        System.out.println("Last Name");
        String lastName = input.next();

        try {
            customerService.addCustomer(email, firstName, lastName);
            System.out.println("Added successfully");
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to add customer: " + e.getMessage());
        }
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

}

