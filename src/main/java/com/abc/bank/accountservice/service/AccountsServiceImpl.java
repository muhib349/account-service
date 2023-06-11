package com.abc.bank.accountservice.service;

import com.abc.bank.accountservice.exceptions.ResourceNotFoundException;
import com.abc.bank.accountservice.model.Accounts;
import com.abc.bank.accountservice.model.Customer;
import com.abc.bank.accountservice.repository.AccountsRepository;
import jakarta.annotation.Resource;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountsServiceImpl implements AccountsService{

    private final AccountsRepository repository;

    @Autowired
    public AccountsServiceImpl(AccountsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Accounts getAccountDetails(Customer customer) {
        return repository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found exception")
        );
    }
}
