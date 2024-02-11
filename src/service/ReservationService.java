package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;

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
                vaildate=false;
            }
            }
        if(vaildate){
            rooms.add(room);
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
        reservations.add(newReservation);
        System.out.println("Reserved successfully !");


        return newReservation;

    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {


        for (Reservation reserved : reservations) {
            System.out.println(reserved.getCheckInDate() + " reserved checkIn");
            System.out.println(reserved.getCheckOutDate() + " reserved checkout ");

            System.out.println(checkInDate + " looking for checkIn");
            System.out.println(checkOutDate + " looking for checkout");
            if (((checkInDate.after(reserved.getCheckOutDate())) || (checkInDate.before(reserved.getCheckInDate()))) &&
                    ((checkOutDate.after(reserved.getCheckOutDate())) || checkOutDate.before(reserved.getCheckInDate()))) {
                availableRooms.add(reserved.getRoom());
            }
        }

        for (IRoom freeRooms : availableRooms) {
            System.out.println(availableRooms);
        }
        return availableRooms;
    }


    public Collection<Reservation> getCustomerReservation(Customer customer) {
        for (Reservation reserved : reservations) {
            if (customer.getEmail().equals(reserved.getCustomer().getEmail())) {
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
    }

