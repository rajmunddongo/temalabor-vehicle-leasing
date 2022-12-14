package hu.bme.aut.temalab.temalaborvehicleleasing.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.FuelType;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.GearboxType;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.VehicleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@SuperBuilder
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public abstract class Vehicle {
    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false)
    private UUID id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Column(unique = true, nullable = false)
    private String licensePlate;

    @Column(nullable = false)
    private int seats;

    @Column(nullable = false)
    private int mileage;

    @Column(nullable = false)
    private int mileageLimit;

    @Column(nullable = false)
    private int horsePower;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GearboxType gearBoxType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @Column(columnDefinition = "TEXT")
    private String description;

}
