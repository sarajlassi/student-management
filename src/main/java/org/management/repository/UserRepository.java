package org.management.repository;

import org.management.entity.UserApps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository  extends JpaRepository<UserApps, Long> {
    Optional<UserApps> findByUsername(String username);
}
