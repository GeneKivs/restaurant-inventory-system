package com.restaurant.inventory.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    // Indicates if the user is active (not deleted)
    @Column(name = "isActive")
    private boolean active = true;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String phoneNumber;

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToOne
    @JoinColumn(name = "roleID", nullable = false)
    private Roles role;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

}
