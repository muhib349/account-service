package com.abc.bank.accountservice.service;

import com.abc.bank.accountservice.model.Accounts;
import com.abc.bank.accountservice.repository.AccountsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SaveAccountConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SaveAccountConsumer.class);

    private final AccountsRepository repository;

    public SaveAccountConsumer(AccountsRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(Accounts accounts){
        repository.save(accounts);
        LOGGER.info(String.format("Message received : -> %s", accounts.getAccountNumber()));
    }
}
