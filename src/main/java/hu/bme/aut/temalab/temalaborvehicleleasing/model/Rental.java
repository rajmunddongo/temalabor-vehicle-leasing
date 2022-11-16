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
    private UUID id;

    @ManyToOne(optional = false)
    @Cascade(org.hibernate.annotations.CascadeType.LOCK)
    private Vehicle vehicle;

    @ManyToOne(optional = false)
    @Cascade(org.hibernate.annotations.CascadeType.LOCK)
    private Customer customer;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int useKm;
}
