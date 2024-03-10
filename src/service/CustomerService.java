package service;

import model.Customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {

    private static final Map<String, Customer> customers = new HashMap<>();
    private static CustomerService instance;

    public static void setInstance(CustomerService instance) {
        CustomerService.instance = instance;
    }

    public void addCustomer(String email, String firstName, String lastName) throws IllegalArgumentException {
        if (customers.containsKey(email)) {
            throw new IllegalArgumentException("Customer with email " + email + " already exists");
        }
        customers.put(email, new Customer(firstName, lastName, email));
    }

    public Customer getCustomer(String customerEmail){
        return customers.get(customerEmail);
    }




    public Collection<Customer> getAllCustomers() {
        System.out.println("List of customers:");
        int i = 1;
        for (Customer customer : customers.values()) {
            System.out.print(i + " :");
            i++;
            System.out.println("----------------------------------------");
            System.out.println(customer); // Using toString() here
            System.out.println(" ");
        }
        return customers.values();
    }


    public CustomerService() {
    }


    // Static method to get the singleton instance
    public static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }



    public boolean customerExists(String email) {
        // Iterate through the values of the customers map
        for (Customer customer : customers.values()) {
            if (customer.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }
}