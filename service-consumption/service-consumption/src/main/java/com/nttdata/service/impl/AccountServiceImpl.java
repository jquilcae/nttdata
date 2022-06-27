package com.nttdata.service.impl;

import com.nttdata.client.ClientResultClient;
import com.nttdata.client.dto.AccountRequest;
import com.nttdata.client.dto.ClientResult;
import com.nttdata.dao.Account;
import com.nttdata.repository.AccountRepository;
import com.nttdata.service.AccountSerevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AccountServiceImpl implements AccountSerevice {
    private final Logger LOGGER = LoggerFactory.getLogger("AccountServiceImpl");
    private AccountRepository accountRepository;
    private ClientResultClient client;

    public AccountServiceImpl(AccountRepository accountRepository, ClientResultClient client) {
        this.accountRepository = accountRepository;
        this.client = client;
    }


    /**
     * - verifier:
     * 1: consume
     * 2: deposits
     * 3: withdrawals
     */
    @Override
    public void create(Account account) {
        //search data for client by id
        ClientResult clientResult = client.retrievePersonResult(account.getClientId());

        if (clientResult.getClientId().equals(account.getClientId())) {
            switch (account.getDetail().getTransactionType()) {
                case 1:
//                 verifier if account exists
                    try {
                        AccountRequest result = client.retrieveProductResult(account.getDetail().getCardNumber());
                        LOGGER.info("client product " + result.getCardNumber());
                        LOGGER.info("amount " + result.getAmount());

                        if (result.getAmount().compareTo(account.getDetail().getAmount()) >= 0) {
                            //consumption subtraction
                            result.setAmount(account.getDetail().getAmount());
                            ResponseEntity<AccountRequest> response = client.updateAccount(result);
                            LOGGER.info("status code: " + response.getStatusCode());
                        } else {
                            LOGGER.error("cannot be debited from the card\n");
                        }
                    } catch (Exception e) {
                        LOGGER.error("# no se pudo obtener los datos del microservicio producto");
                        e.printStackTrace();
                    }

                    break;
            }
            account.setClientId(clientResult.getClientId());
            account.setClientName(clientResult.getName());
            account.setClientIdentification(clientResult.getIdentification());
            accountRepository.save(account);
        } else LOGGER.error("error getting client data");
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }
    @Override
    public List<Account> findById(String id) {
        return accountRepository.findAll()
                .stream()
                .filter(s -> s.getClientId().equals(id))
                .collect(Collectors.toList());

    }
}
