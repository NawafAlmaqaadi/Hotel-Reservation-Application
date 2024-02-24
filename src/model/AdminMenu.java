package model;

import api.AdminResource;

import java.util.Scanner;

public class AdminMenu {

    public static void Menu(){

        Scanner input = new Scanner(System.in);

        AdminResource adminResource = new AdminResource();
        int exitKey = 0;

        do {
            System.out.println("1. See all Customers");
            System.out.println("2. See all Rooms");
            System.out.println("3. See all Reservations");
            System.out.println("4. Add a Room");
            System.out.println("5. Back to Main Menu");

            System.out.print("Please select a number for the menu option: ");
            exitKey = input.nextInt();

            if(exitKey == 1){
                System.out.println("All cutsomers");
                adminResource.getAllCustomers();

            } else if (exitKey == 2) {
                adminResource.getAllRooms();

            } else if (exitKey == 3) {
                adminResource.displayAllReservations();
            } else if (exitKey == 4) {


            try {


                String anotherRoom;

                do {
                    System.out.println("Enter room number");
                    String roomNumber = input.next();
                    System.out.println("Enter price");
                    double price = input.nextDouble();
                    System.out.println("Room type: \\n1. Single\\n2. Double");
                    int type = input.nextInt();
                    Room newRoom = new Room(roomNumber,price,type == 1 ? RoomType.Single : RoomType.Double);
                    adminResource.addRoom(newRoom);


                    System.out.println("Would like to add another room y/n");
                    anotherRoom = input.next();

                    while(!(anotherRoom.equalsIgnoreCase("y") || anotherRoom.equalsIgnoreCase("n"))){
                        System.out.print("Please enter Y (Yes) or N (No) : ");
                        anotherRoom = input.next();
                    }

                }while (anotherRoom.equals("y"));
            }catch (Exception e){
            System.out.println("Invalid data entered \n");
            Menu();
            }



            }

        }while(exitKey <= 4);


    }

}
