package com.abc.bank.accountservice.service;

import com.abc.bank.accountservice.model.Accounts;
import com.abc.bank.accountservice.model.Customer;
import org.springframework.stereotype.Service;

public interface AccountsService {
    Accounts getAccountDetails(Customer customer);
}
