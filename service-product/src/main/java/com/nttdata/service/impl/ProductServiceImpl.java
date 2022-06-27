package com.nttdata.service.impl;

import com.nttdata.client.ClientResultClient;
import com.nttdata.client.dto.ClientResult;
import com.nttdata.model.dao.Account;
import com.nttdata.model.dao.Customer;
import com.nttdata.model.dao.Product;
import com.nttdata.model.request.ProductRequest;
import com.nttdata.model.response.AccountResponse;
import com.nttdata.model.response.CustomerResponse;
import com.nttdata.model.response.ProductResponse;
import com.nttdata.repository.ProductRepository;
import com.nttdata.service.AccountService;
import com.nttdata.service.CustomerService;
import com.nttdata.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final Logger LOGGER = LoggerFactory.getLogger("ProductServiceImpl");
    private final ProductRepository productRepository;
    private final CustomerService customerService;
    private final ClientResultClient clientResultClient;
    private final AccountService accountService;

    public ProductServiceImpl(ProductRepository productRepository, ClientResultClient clientResultClient,
                              CustomerService customerService,
                              AccountService accountService) {
        this.productRepository = productRepository;
        this.clientResultClient = clientResultClient;
        this.customerService = customerService;
        this.accountService = accountService;
    }

    /**
     * 1(Personal):  you can only have a maximum of one savings account,
     * one checking account or fixed terms accounts
     * <p>
     * <p>
     * 2(Business): you cannot have a savings or time deposit account
     * but yes  multiple checking accounts
     */
    @Override
    public void create(ProductRequest productRequest) {
        switch (productRequest.getCustomerType()) {
            case 1:
                //find client by ID
                try {
                    LOGGER.info("find client by ID....");

                    ClientResult result = clientResultClient.retrievePersonResult(productRequest.getCustomerId());
                    //verifier products
                    Optional<Customer> customer = customerService.findAll()
                            .stream()
                            .filter(s -> s.getClientId().equals(result.getClientId())).findFirst();

                    if (customer.isPresent()) {
                        LOGGER.info("verifier products for client");
                        Optional<Product> productOptional = productRepository.findById(customer.get().getProductId());

                        if (productOptional.isPresent()) {
                            List<Account> accounts = accountService.findAll()
                                    .stream()
                                    .filter(s -> s.getProductId().equals(productOptional.get().getId()))
                                    .collect(Collectors.toList());
                            boolean count = accounts
                                    .stream()
                                    .noneMatch(s -> s.getAccountType() == productRequest.getAccountType());
                            if (count) {
                                // obtenemos el id del producto
                                LOGGER.info("creating new account");
                                String productId = productOptional.get().getId();
                                Account account = new Account();
                                account.setProductId(productId);
                                account.setAccountType(productRequest.getAccountType());
                                account.setCardNumber(productRequest.getCardNumber());
                                account.setAmount(productRequest.getAmount());
                                accountService.create(account);
                            }
                        }
                    } else {

                        LOGGER.info("dont' found accounts for this client");

                        Product product = new Product();
                        product.setProductType(productRequest.getProductType());
                        product.setCustomerType(productRequest.getCustomerType());
                        product.setClientId(productRequest.getCustomerId());
                        productRepository.save(product);
                        LOGGER.info("create success");
                        Optional<Product> p = productRepository.findAll()
                                .stream()
                                .filter(s -> s.getClientId().equals(productRequest.getCustomerId())).findFirst();

                        Account account = new Account();
                        account.setAmount(productRequest.getAmount());
                        account.setCardNumber(productRequest.getCardNumber());
                        account.setAccountType(productRequest.getAccountType());
                        account.setProductId(p.get().getId());
                        accountService.create(account);

                        Customer cust = new Customer();
                        cust.setClientId(productRequest.getCustomerId());
                        cust.setProductId(p.get().getId());
                        customerService.create(cust);
                    }


                } catch (Exception e) {
                    LOGGER.error("error getting cliente data: " + e.getMessage());
                }
                break;
            //cuentas para empresas
            case 2:
                try {
                    LOGGER.error("Verifier business");

                    ClientResult resultClient = clientResultClient.retrieveCompanyResult(productRequest.getCustomerId());

                    if (productRequest.getAccountType() != 1 &&
                            productRequest.getAccountType() != 3 &&
                            productRequest.getAccountType() != 4) {

                        Optional<Customer> customer = customerService.findAll()
                                .stream()
                                .filter(s -> s.getClientId().equals(productRequest.getCustomerId()))
                                .findFirst();

                        if (customer.isPresent()) {
                            LOGGER.info("data:  " + customer.get().getClientId());
                            Optional<Product> product = productRepository.findById(customer.get().getProductId());
                            // validate multiple checking account
                            if (product.isPresent()) {
                                switch (productRequest.getAccountType()) {
                                    case 2:
                                        LOGGER.error("type account 2");
                                        Account account = new Account();
                                        account.setProductId(product.get().getId());
                                        account.setAccountType(productRequest.getAccountType());
                                        account.setAmount(productRequest.getAmount());
                                        account.setCardNumber(productRequest.getCardNumber());
                                        accountService.create(account);
                                        break;
                                    case 5:
                                    case 6:
                                        LOGGER.error("# type account  5 y 6");

                                        boolean data = accountService
                                                .findAll()
                                                .stream()
                                                .noneMatch(s -> s.getAccountType() == 5 || s.getAccountType() == 6);
                                        if (data) {
                                            Account accounts = new Account();
                                            accounts.setProductId(product.get().getId());
                                            accounts.setAccountType(productRequest.getAccountType());
                                            accounts.setAmount(productRequest.getAmount());
                                            accounts.setCardNumber(productRequest.getCardNumber());
                                            accountService.create(accounts);
                                        }
                                        break;
                                }
                            }

                        } else {
                            LOGGER.info("Business don't have products. Creating product");

                            Product product = new Product();
                            product.setProductType(productRequest.getProductType());
                            product.setCustomerType(productRequest.getCustomerType());
                            product.setClientId(productRequest.getCustomerId());
                            productRepository.save(product);
                            LOGGER.info("success");
                            Optional<Product> p = productRepository.findAll()
                                    .stream()
                                    .filter(s -> s.getClientId().equals(productRequest.getCustomerId())).findFirst();

                            Account account = new Account();
                            account.setAmount(productRequest.getAmount());
                            account.setCardNumber(productRequest.getCardNumber());
                            account.setAccountType(productRequest.getAccountType());
                            account.setProductId(p.get().getId());
                            accountService.create(account);

                            Customer cust = new Customer();
                            cust.setClientId(productRequest.getCustomerId());
                            cust.setProductId(p.get().getId());
                            customerService.create(cust);

                        }
                    }


                } catch (Exception e) {
                    LOGGER.error("error getting business data: " + e.getMessage());
                }

                break;
        }

    }


    @Override
    public List<ProductResponse> getProductFindById(String id) {
        Optional<Product> product = productRepository
                .findAll()
                .stream().filter(s -> s.getClientId().equals(id)).findFirst();
        ProductResponse productResponse = new ProductResponse();


        if (product.isPresent()) {
            String productType = product.get().getProductType() == 1 ? "Passive" : "Active";
            String customerType = product.get().getProductType() == 1 ? "Persona" : "Business";
            productResponse.setProductType(productType);
            productResponse.setCustomerType(customerType);

            accountService.findAll()
                    .stream()
                    .filter(s -> s.getProductId().equals(product.get().getId()))
                    .forEach(data -> {
                        String accountType = null;
                        switch (data.getAccountType()) {
                            case 1:
                                accountType = "saving";
                                break;
                            case 2:
                                accountType = "Checking account";
                                break;
                            case 3:
                                accountType = "Fixed term";
                                break;
                            case 4:
                                accountType = "Personal";
                                break;
                            case 5:
                                accountType = "Business";
                                break;
                            case 6:
                                accountType = "Credit target";
                                break;
                        }
                        productResponse
                                .getAccountResponses()
                                .add(new AccountResponse(accountType, data.getCardNumber(), data.getAmount()));
                    });
            customerService.findAll().stream()
                    .filter(s -> s.getProductId().equals(product.get().getId()))
                    .forEach(data -> {
                        productResponse
                                .getCustomerResponses()
                                .add(new CustomerResponse(data.getClientId()));
                    });
        }

        return new ArrayList<>(Arrays.asList(productResponse));
    }


}
