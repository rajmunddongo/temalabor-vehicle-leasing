package hu.bme.aut.temalab.temalaborvehicleleasing.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cascade;

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
    @Cascade(org.hibernate.annotations.CascadeType.LOCK)
    Vehicle vehicle;

    @ManyToOne(optional = false)
    @Cascade(org.hibernate.annotations.CascadeType.LOCK)
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
