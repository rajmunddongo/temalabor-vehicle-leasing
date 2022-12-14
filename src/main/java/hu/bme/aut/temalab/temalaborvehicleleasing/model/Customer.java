package hu.bme.aut.temalab.temalaborvehicleleasing.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import hu.bme.aut.temalab.temalaborvehicleleasing.controller.util.CustomAuthorityDeserializerForCustomer;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@SuperBuilder
public final class Customer extends User {
    @Column
    private String drivingLicenceNumber;

    @Column
    private int bonusPoints;

    @OneToMany
    private Collection<Rental> rentals;

    @Override
    @JsonDeserialize(using = CustomAuthorityDeserializerForCustomer.class)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(UserRole.ROLE_CUSTOMER);
    }
}
