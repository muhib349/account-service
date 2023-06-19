package com.abc.bank.accountservice.service;

import com.abc.bank.accountservice.exceptions.ResourceNotFoundException;
import com.abc.bank.accountservice.model.Accounts;
import com.abc.bank.accountservice.model.Customer;
import com.abc.bank.accountservice.repository.AccountsRepository;
import jakarta.annotation.Resource;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class AccountsServiceImpl implements AccountsService{

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountsServiceImpl.class);
    @Value("${spring.kafka.topic.name}")
    private String saveAccountTopic;

    private final AccountsRepository repository;

    private final KafkaTemplate<String, Accounts> kafkaTemplate;

    @Autowired
    public AccountsServiceImpl(AccountsRepository repository, KafkaTemplate<String, Accounts> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public Accounts getAccountDetails(Customer customer) {
        return repository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found exception")
        );
    }

    @Override
    public void saveAccounts(Accounts accounts) {
        Message<Accounts> accountsMessage = MessageBuilder
                .withPayload(accounts)
                .setHeader(KafkaHeaders.TOPIC, saveAccountTopic)
                .build();

        kafkaTemplate.send(accountsMessage);

        LOGGER.info(String.format("Message sent : -> %s", accounts.getAccountNumber()));
    }
}
