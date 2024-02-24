package service;

import model.*;

import java.util.*;

public class ReservationService {

    private static final Collection<Reservation> reservations = new LinkedList<>();
    private static final Collection<IRoom> rooms = new LinkedList<>();
    private static final Collection<IRoom> availableRooms = new LinkedList<>();
    Scanner input = new Scanner(System.in);


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

    public Reservation reserveARooms(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation newReservation = new Reservation(customer, room, checkInDate, checkOutDate);

        if(reservations.isEmpty()){
            reservations.add(newReservation);
            System.out.println("Reserved successfully !");

        }else{
            for (Reservation reserved : reservations) {
                if (((checkInDate.after(reserved.getCheckOutDate())) || (checkInDate.before(reserved.getCheckInDate()))) &&
                        ((checkOutDate.after(reserved.getCheckOutDate())) || checkOutDate.before(reserved.getCheckInDate()))) {


                    reservations.add(newReservation);
                    System.out.println("Reservation information");
                    System.out.println("Customer name: "+ newReservation.getCustomer().getFirstName() + " " + newReservation.getCustomer().getLastName());
                    System.out.println("Room number: "+ newReservation.getRoom().getRoomNumber());
                    System.out.println("Room type: "+ newReservation.getRoom().getRoomType());
                    System.out.println("Check In Date : "+ newReservation.getCheckInDate());
                    System.out.println("Check In Date : "+ newReservation.getCheckOutDate());
                    System.out.println("--------------------------------------------------------");
                    System.out.println("Total price: "+ newReservation.getRoom().getRoomPrice());
                    System.out.println("--------------------------------------------------------");

                    System.out.println("Reserved successfully !");



                }
            }
        }



        return newReservation;

    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        List<IRoom> availableRooms = new ArrayList<>();

        if (rooms.isEmpty()) {
            System.out.println("No rooms available");
        } else {
            boolean roomAvailable;
            for (IRoom room : rooms) {
                roomAvailable = true;
                for (Reservation reserved : reservations) {
                    if (room.equals(reserved.getRoom()) &&
                            !((checkOutDate.before(reserved.getCheckInDate())) ||
                                    (checkInDate.after(reserved.getCheckOutDate())))) {
                        roomAvailable = false;
                        break; // Room is reserved for the requested dates
                    }
                }
                if (roomAvailable) {
                    availableRooms.add(room);
                }
            }
        }

        System.out.println("Available rooms: ");
        for (IRoom freeRooms : availableRooms) {
            System.out.println(freeRooms);
        }

        return availableRooms;
    }


    public Collection<Reservation> getCustomerReservation(Customer customer) {
        for (Reservation reserved : reservations) {
            if (customer.getEmail().equals(reserved.getCustomer().getEmail())) {
                System.out.println(reserved);
                return reservations;
            }
            }
        return null;
    }




    public void printAllReservation(){
        for (Reservation reserved : reservations){
            System.out.println(reserved);
            }
        }


        public Collection<IRoom> getAllRooms(){


            for (IRoom room : rooms) {
                System.out.println("Room Number: "+room.getRoomNumber() + " "  + room.getRoomType() + " Room Price "+room.getRoomPrice());
                }
        return rooms;
        }

    public boolean roomExists(String roomNumber) {
        for (IRoom room : rooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                return true;
            }
        }
        return false;
    }

    }

