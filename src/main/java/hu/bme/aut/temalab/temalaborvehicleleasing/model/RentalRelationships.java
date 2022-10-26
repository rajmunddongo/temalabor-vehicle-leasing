package hu.bme.aut.temalab.temalaborvehicleleasing.model;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class RentalRelationships {
    Integer ID;
    Integer vehicleID;
    Integer customerID;
    Date startDate;
    Date endDate;
    Integer price;
    Integer useKm;

}
