package com.example.repository;


import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query(value = "SELECT * FROM Account where username = ?1", nativeQuery = true)
    Account findAccountByUsername(String userName);

    @Query(value = "SELECT * FROM Account where username = ?1 AND password = ?2", nativeQuery = true)
    Account checkValidCredentials(String userName, String password);

}
