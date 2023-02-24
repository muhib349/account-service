package com.abc.bank.accountservice.controller;

import com.abc.bank.accountservice.model.Accounts;
import com.abc.bank.accountservice.model.Customer;
import com.abc.bank.accountservice.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountsController {

    @Autowired
    private AccountsRepository accountsRepository;

    @PostMapping("/myAccount")
    public Accounts getAccountDetails(@RequestBody Customer customer){
        return accountsRepository.findByCustomerId(customer.getCustomerId());
    }
}
