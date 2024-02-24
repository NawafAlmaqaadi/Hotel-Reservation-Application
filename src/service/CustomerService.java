package service;

import model.Customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {

    private static final Map<String, Customer> customers = new HashMap<>();
    public void addCustomer(String email, String firstName, String lastName) throws IllegalArgumentException {

        if(!customers.containsKey(email)){

            customers.put(email, new Customer(firstName,lastName,email));

        }


    }

    public Customer getCustomer(String customerEmail){
    return customers.get(customerEmail);
    }




    public Collection<Customer> getAllCustomers(){

        System.out.println(customers.values());
    return customers.values();
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