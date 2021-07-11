package com.validator.account.validate.service;

import com.validator.account.validate.configuration.AccountValidationProvider;
import com.validator.account.validate.model.AccountInfo;
import com.validator.account.validate.model.AccountValidationResults;
import com.validator.account.validate.model.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AccountValidatorService {
    @Autowired
    private AccountValidationProvider provider;
    @Autowired
    private RestTemplate restTemplate;
    /**
     * ExecutorService used as thread pool for completable future.
     */
    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    /**
     * Service method invokes the providers asynchronously and collects results from each providers
     * and returns back to calling service method.
     * if Providers from input object(account Info) are null/empty then use all provided configured in the
     * property file to call asynchronously.
     * @param accountInfo Model consists of Account number and provider details
     * @return List contains provider and its results.
     * @throws ExecutionException when exception occurs.
     * @throws InterruptedException when there is interruption happens during the thread execution
     */
    public List<AccountValidationResults> validateAccount(AccountInfo accountInfo) throws ExecutionException, InterruptedException {
        final String account = accountInfo.getAccountNumber();
        List<String> providersConfig = !CollectionUtils.isEmpty(accountInfo.getProviders())  ?
                accountInfo.getProviders().stream().map(k -> k.getName().toUpperCase()).collect(Collectors.toList()): Collections.EMPTY_LIST;
        List<Provider> providers = !CollectionUtils.isEmpty(accountInfo.getProviders())
                ? provider.getProviders().stream().filter(m->providersConfig.contains(m.getName().toUpperCase())).collect(Collectors.toList())
                : provider.getProviders();
        List<CompletableFuture<Void>> futures = providers.stream().map(p -> {
            return CompletableFuture.runAsync(() -> restTemplate.getForEntity(p.getUrl() + "/" + account, String.class), executorService);
        }).collect(Collectors.toList());

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).get();
        List<AccountValidationResults> results = IntStream.range(0, providers.size()).mapToObj(i-> {
            AccountValidationResults result = new AccountValidationResults();
            result.setProvider(providers.get(i).getName());
            try {
                result.setValid(Boolean.valueOf(String.valueOf(futures.get(i).get())));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return result;
        }).collect(Collectors.toList());
        return  results;
    }
}
