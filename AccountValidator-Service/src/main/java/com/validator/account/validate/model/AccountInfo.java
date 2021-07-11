package com.validator.account.validate.model;

import java.util.List;

public class AccountInfo {
    private String accountNumber;
    private List<Provider> providers;

    /**
     * @return Account Number
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * @param accountNumber Set the Account Number
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * @return List of Provider information
     */
    public List<Provider> getProviders() {
        return providers;
    }

    /**
     * @param providers Set the list of provider information
     */
    public void setProviders(List<Provider> providers) {
        this.providers = providers;
    }
}
