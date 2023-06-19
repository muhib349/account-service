package com.abc.bank.accountservice.model;

import java.time.LocalDate;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
public class Accounts {

    @Column(name = "customer_id")
    private int customerId;
    @Column(name="account_number")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountNumber;
    @Column(name="account_type")
    private String accountType;
    @Column(name = "branch_address")
    private String branchAddress;
    @Column(name = "create_at")
    private LocalDate createAt;

}
