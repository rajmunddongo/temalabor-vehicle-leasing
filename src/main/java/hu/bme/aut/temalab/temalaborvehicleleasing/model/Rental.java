package hu.bme.aut.temalab.temalaborvehicleleasing.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@SuperBuilder
public final class Rental {
    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false)
    UUID id;

    @ManyToOne(optional = false)
    Vehicle vehicle;

    @ManyToOne(optional = false)
    Customer customer;

    @Column(nullable = false)
    Date startDate;

    @Column(nullable = false)
    Date endDate;

    @Column(nullable = false)
    int price;

    @Column(nullable = false)
    int useKm;
}
