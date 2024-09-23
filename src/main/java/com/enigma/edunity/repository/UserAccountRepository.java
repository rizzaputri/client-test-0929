package com.enigma.edunity.repository;

import com.enigma.edunity.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
    Optional<UserAccount> findByUsername(String username);
    void deleteById(String id);

    @Modifying
    @Query(value = "DELETE FROM m_user_account_roles WHERE user_account_id = :id", nativeQuery = true)
    void deleteConstraint(@Param("id") String id);
}
