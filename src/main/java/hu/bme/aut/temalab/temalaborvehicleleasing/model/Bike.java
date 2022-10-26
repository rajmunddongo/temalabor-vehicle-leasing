package hu.bme.aut.temalab.temalaborvehicleleasing.model;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.BikeStrokeType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public final class Bike extends Vehicle {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    BikeStrokeType strokeType;
}
