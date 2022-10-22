package hu.bme.aut.temalab.temalaborvehicleleasing.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vehicle {
    Integer ID;
    String platenum;
    Integer seats;
    Integer mileageLimit;
    String gearbox;
    String description;
    final String TYPE = "no type";
}
