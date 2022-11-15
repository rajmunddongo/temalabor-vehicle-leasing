package hu.bme.aut.temalab.temalaborvehicleleasing.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
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

    @OneToOne(optional = false)
    @Cascade(org.hibernate.annotations.CascadeType.LOCK)
    Vehicle vehicle;

    @OneToOne(optional = false)
    @Cascade(org.hibernate.annotations.CascadeType.LOCK)
    Customer customer;

    @Column(nullable = false)
    LocalDate startDate;

    @Column(nullable = false)
    LocalDate endDate;

    @Column(nullable = false)
    int price;

    @Column(nullable = false)
    int useKm;
}
