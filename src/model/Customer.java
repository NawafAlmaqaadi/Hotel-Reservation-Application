package model;

import java.util.regex.Pattern;

public class Customer {

    private  String firstName;
    private String lastName;
    private String email;

    public Customer(String firstName, String lastName, String email) {

        if(!emailValidation(email)){
            throw new IllegalArgumentException("Error, Invaild email - ");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private boolean emailValidation (String email){
        final String emailRegex = "[A-Za-z0-9._%+-]+@[A-Za-z0-9]+\\.com$";
        return email.matches(emailRegex);

    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


}
