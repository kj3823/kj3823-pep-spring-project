package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class AccountService {
   AccountRepository accountRepository;
   
   @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Account addAccount(Account account)
    {
        Account retrievedAccount = accountRepository.findAccountByUsername(account.getUsername());
        System.out.println(retrievedAccount);
        if(retrievedAccount != null)
        {
            return null;
        }
        return accountRepository.save(account);
    }

    public Account login(Account account)
    {
        return accountRepository.checkValidCredentials(account.getUsername(), account.getPassword());
    }

}