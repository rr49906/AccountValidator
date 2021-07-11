package com.validator.account.validate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.validator.account.validate.model.AccountInfo;
import com.validator.account.validate.model.AccountValidationResults;
import com.validator.account.validate.service.AccountValidatorService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AccountValidateController.class)
class AccountValidateControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccountValidatorService accountValidatorService;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    public void whenValidInput_thenReturns200() throws Exception {
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountNumber("12345");
        Mockito.when(accountValidatorService.validateAccount(Mockito.any())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/account/validator")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(accountInfo)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void whenValidInput_thenVerifyOutPut() throws Exception {
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountNumber("12345");

        Map<String, List<AccountValidationResults>> output = new HashMap<>();
        AccountValidationResults avr1 = new AccountValidationResults();
        avr1.setValid(true);
        avr1.setProvider("PROVIDER1");

        AccountValidationResults avr2 = new AccountValidationResults();
        avr2.setValid(true);
        avr2.setProvider("PROVIDER2");
        output.put("results", Arrays.asList(avr1,avr2));
        String expectedResponseBody = objectMapper.writeValueAsString(output);

        Mockito.when(accountValidatorService.validateAccount(Mockito.any())).thenReturn(Arrays.asList(avr1,avr2));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/account/validator")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(accountInfo)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);
    }
}