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

            switch (exitKey) {
                case 1:
                    adminResource.getAllCustomers();
                    break;
                case 2:
                    adminResource.getAllRooms();
                    break;
                case 3:
                    adminResource.displayAllReservations();
                    break;
                case 4:
                    try {
                        String anotherRoom = "n";

                        do {
                            System.out.println("Enter room number");
                            String roomNumber = input.next();

                            // Check if roomNumber is a valid number
                            if (!roomNumber.matches("\\d+")) {
                                System.out.println("Room number must be a number. Please enter a valid room number.");
                                continue;
                            }

                            System.out.println("Enter price");
                            double price = input.nextDouble();
                            System.out.println("Room type: \n1. Single\n2. Double");
                            int type = input.nextInt();
                            Room newRoom = new Room(roomNumber, price, type == 1 ? RoomType.Single : RoomType.Double);
                            if (adminResource.roomExists(newRoom)) {
                                System.out.println("Room number already exists. Please enter a different room number.");
                                continue;
                            }
                            adminResource.addRoom(newRoom);

                            System.out.println("Would you like to add another room? (y/n)");
                            anotherRoom = input.next();

                            while (!(anotherRoom.equalsIgnoreCase("y") || anotherRoom.equalsIgnoreCase("n"))) {
                                System.out.print("Please enter Y (Yes) or N (No): ");
                                anotherRoom = input.next();
                            }

                        } while (anotherRoom.equalsIgnoreCase("y"));
                    } catch (Exception e) {
                        System.out.println("Invalid data entered\n");
                        Menu();
                    }
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Invalid option! Please select a valid option.");
                    break;
            }

        } while (exitKey != 5);
    }
}
