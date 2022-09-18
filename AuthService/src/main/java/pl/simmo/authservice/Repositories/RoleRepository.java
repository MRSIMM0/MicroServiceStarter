package pl.simmo.authservice.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.simmo.authservice.Models.Role;
import pl.simmo.authservice.Models.RoleEnum;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(RoleEnum name);
}
