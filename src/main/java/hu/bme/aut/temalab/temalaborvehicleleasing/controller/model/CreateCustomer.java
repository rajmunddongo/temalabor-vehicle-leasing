package hu.bme.aut.temalab.temalaborvehicleleasing.controller.model;

import lombok.Data;

@Data
public class CreateCustomer {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String drivingLicenceNumber;
}
