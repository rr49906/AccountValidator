package com.validator.account.validate.controller;

import com.validator.account.validate.model.AccountInfo;
import com.validator.account.validate.model.AccountValidationResults;
import com.validator.account.validate.service.AccountValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/account")
public class AccountValidateController {
    @Autowired
    private AccountValidatorService accountValidatorService;

    /**
     * Request endpoint to validate the account number by calling other rest endpoint using
     * providers passed in request/ configured providers.
     * @param accountInfo Request Body
     * @return Results from each providers.
     */
    @PostMapping("/validator")
    public @ResponseBody
    Map<String, List<AccountValidationResults>> validateAccount(@RequestBody AccountInfo accountInfo) {
        Map<String, List<AccountValidationResults>> response = new HashMap<>();
        try {
           response.put("results",accountValidatorService.validateAccount(accountInfo));
           return response;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_MAP;
    }
}
