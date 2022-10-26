package hu.bme.aut.temalab.temalaborvehicleleasing.model;

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
public abstract class Vehicle {
    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false)
    UUID id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    VehicleType vehicleType;

    @Column(unique = true, nullable = false)
    String licensePlate;

    @Column(nullable = false)
    int seats;

    @Column(nullable = false)
    int mileage;

    @Column(nullable = false)
    int mileageLimit;

    @Column(nullable = false)
    int horsePower;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    GearboxType gearBoxType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    FuelType fuelType;

    @Column(columnDefinition = "TEXT")
    String description;

}
