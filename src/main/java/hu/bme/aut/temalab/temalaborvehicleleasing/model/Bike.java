package hu.bme.aut.temalab.temalaborvehicleleasing.model;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.BikeStrokeType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public final class Bike extends Vehicle {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BikeStrokeType strokeType;
}
