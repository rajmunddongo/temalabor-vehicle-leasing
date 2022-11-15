package hu.bme.aut.temalab.temalaborvehicleleasing.model;

import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Collection;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public final class Administrator extends User {
    @Column
    private String employeeNumber;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(UserRole.ROLE_ADMIN);
    }
}
