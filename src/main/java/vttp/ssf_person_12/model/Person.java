package vttp.ssf_person_12.model;

import java.util.Date;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class Person {
    private String id;

    @NotNull(message = "field cannot be empty")
    @Size(min=3, max=64, message = "First name must be between 3 to 64 characters long.")
    private String firstName;

    @NotNull(message = "field cannot be empty")
    @Size(min=3, max=64, message = "Last name must be between 3 to 64 characters long.")
    private String lastName;

    @NotNull(message = "field cannot be empty")
    @Past(message = "date of birth must be in the past")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @NotNull(message = "field cannot be empty")
    @Email(message = "must be valid email format")
    private String email;

    @NotNull(message = "field cannot be empty")
    @Size(min = 7, message = "phone number must be at least 7 digits long")
    private String phoneNumber;
    
    public Person() {
        this.id = UUID.randomUUID().toString().substring(0,8);;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id){
        // if (id==null){
        //     this.id = UUID.randomUUID().toString().substring(0,8);
        // }   
        // else {
        //     this.id = id;
        // }
        this.id = id;
    }
   
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
   
}
