package com.abc.bank.accountservice.controller;

import com.abc.bank.accountservice.client.CardsFeignClient;
import com.abc.bank.accountservice.client.LoansFeignClient;
import com.abc.bank.accountservice.config.AccountServiceConfig;
import com.abc.bank.accountservice.model.*;
import com.abc.bank.accountservice.service.AccountsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@AllArgsConstructor
@RefreshScope
public class AccountsController {
    private AccountServiceConfig serviceConfig;
    private CardsFeignClient cardsFeignClient;

    private LoansFeignClient loansFeignClient;
    private AccountsService service;

    @PostMapping("/myAccount")
    @Timed(value = "getAccountDetails.time", description = "Time taken to return account details")
    public ResponseEntity<Accounts> getAccountDetails(@RequestBody Customer customer){
        var account = service.getAccountDetails(customer);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping("/accounts/properties")
    public String getConfigProperties() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        ConfigProperties properties = new ConfigProperties(
                serviceConfig.getMsg(),
                serviceConfig.getBuildVersion(),
                serviceConfig.getMailDetails(),
                serviceConfig.getActiveBranches()
        );


        return ow.writeValueAsString(properties);
    }

    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultsLoans")
    @PostMapping("/customer-details")
    public CustomerDetails getCustomerDetails(@RequestBody Customer customer){
        Accounts accounts = service.getAccountDetails(customer);
        List<Cards> cards = cardsFeignClient.getCustomerCards(customer);
        List<Loans> loans = loansFeignClient.getCustomerLoans(customer);

        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setAccounts(accounts);
        customerDetails.setLoans(loans);
        customerDetails.setCards(cards);
        return customerDetails;
    }


    public CustomerDetails getDefaultsLoans(Customer customer, Exception exception){
        Accounts accounts = service.getAccountDetails(customer);
        List<Cards> cards = cardsFeignClient.getCustomerCards(customer);
        List<Loans> loans = Collections.emptyList();

        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setAccounts(accounts);
        customerDetails.setLoans(loans);
        customerDetails.setCards(cards);
        return customerDetails;
    }
}
