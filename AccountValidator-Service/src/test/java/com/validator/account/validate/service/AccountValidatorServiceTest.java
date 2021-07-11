package com.validator.account.validate.service;

import com.validator.account.validate.configuration.AccountValidationProvider;
import com.validator.account.validate.model.AccountInfo;
import com.validator.account.validate.model.AccountValidationResults;
import com.validator.account.validate.model.Provider;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RunWith(PowerMockRunner.class)
@ExtendWith(SpringExtension.class)
class AccountValidatorServiceTest {
    @Mock
    private AccountValidationProvider provider= Mockito.mock(AccountValidationProvider.class);
    @Mock
    private RestTemplate restTemplate= Mockito.mock(RestTemplate.class);
    @InjectMocks
    AccountValidatorService accountValidatorService = new AccountValidatorService();

    @Test
    public void whenProviderNull_thenUseConfiguration() throws ExecutionException, InterruptedException {
        Provider provider1 = new Provider();
        provider1.setName("Provider1");
        provider1.setUrl("https://provider1.com/v1/api/account/validate");
        Provider provider2 = new Provider();
        provider2.setName("Provider2");
        provider2.setUrl("https://provider2.com/v1/api/account/validate");

        Mockito.when(provider.getProviders()).thenReturn(Arrays.asList(provider1, provider2));

        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.eq(String.class)))
                .thenReturn(new ResponseEntity<>("true", HttpStatus.OK));

        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountNumber("1234");
        List<AccountValidationResults> accountValidationResults = accountValidatorService.validateAccount(accountInfo);
        System.out.println(accountValidationResults);
        List<AccountValidationResults> expectedValidationResults = new ArrayList<>();
        AccountValidationResults avr1 = new AccountValidationResults();
        avr1.setProvider("Provider1");
        avr1.setValid(false);
        AccountValidationResults avr2 = new AccountValidationResults();
        avr2.setProvider("Provider2");
        avr2.setValid(false);
        expectedValidationResults.add(avr1);
        expectedValidationResults.add(avr2);
        Assert.assertArrayEquals(accountValidationResults.toArray(),expectedValidationResults.toArray());
    }

    @Test
    public void whenProviderValidValue() throws ExecutionException, InterruptedException {
        Provider provider1 = new Provider();
        provider1.setName("Provider1");
        provider1.setUrl("https://provider1.com/v1/api/account/validate");
        Provider provider2 = new Provider();
        provider2.setName("Provider2");
        provider2.setUrl("https://provider2.com/v1/api/account/validate");

        Mockito.when(provider.getProviders()).thenReturn(Arrays.asList(provider1, provider2));
        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.eq(String.class)))
                .thenReturn(new ResponseEntity<>("true", HttpStatus.OK));

        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountNumber("1234");
        Provider provider3 = new Provider();
        provider3.setName("Provider1");
        provider3.setUrl("https://provider1.com/v1/api/account/validate");
        accountInfo.setProviders(Arrays.asList(provider3));
        List<AccountValidationResults> accountValidationResults = accountValidatorService.validateAccount(accountInfo);
        System.out.println(accountValidationResults);
        List<AccountValidationResults> expectedValidationResults = new ArrayList<>();
        AccountValidationResults avr1 = new AccountValidationResults();
        avr1.setProvider("Provider1");
        avr1.setValid(false);
        expectedValidationResults.add(avr1);
        Assert.assertArrayEquals(accountValidationResults.toArray(),expectedValidationResults.toArray());
    }

}