package me.souprpk.forumbackend.api.repository;

import me.souprpk.forumbackend.api.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);

}
