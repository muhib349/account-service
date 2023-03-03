package com.abc.bank.accountservice.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerDetails {
    private Accounts accounts;
    private List<Cards> cards;
    private List<Loans> loans;
}
