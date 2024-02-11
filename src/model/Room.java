package model;

import java.util.*;

public class Room implements IRoom{

     String roomNumber;
     Double price;
     RoomType enumeration;


    public Room(String roomNumber, double price, RoomType enumeration) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    @Override
    public String getRoomNumber() {

        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {

        return price;
    }

    @Override
    public RoomType getRoomType() {

        return enumeration;
    }

    @Override
    public boolean isFree() {

        return this.price == 0;
    }


    @Override
    public String toString() {
        return "Room{" +
                "roomNumber='" + roomNumber + '\'' +
                ", price=" + price +
                ", enumeration=" + enumeration +
                '}';
    }
}
