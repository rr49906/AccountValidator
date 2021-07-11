package com.validator.account.validate.model;

import java.util.Objects;

public class AccountValidationResults {
    private String provider;
    private Boolean isValid;

    /**
     * @return Provider Name
     */
    public String getProvider() {
        return provider;
    }

    /**
     * @param provider Sets the provider
     */
    public void setProvider(String provider) {
        this.provider = provider;
    }

    /**
     * @return true if valid account number or else false.
     */
    public Boolean getValid() {
        return isValid;
    }

    /**
     * @param valid sets true if valid account number or else false.
     */
    public void setValid(Boolean valid) {
        isValid = valid;
    }

    @Override
    public boolean equals(Object obj){
        AccountValidationResults avr = (AccountValidationResults) obj;
        return this.provider.equalsIgnoreCase(avr.getProvider())
                && this.isValid == avr.getValid();
    }

    @Override
    public int hashCode() {
        return Objects.hash(provider, isValid);
    }
}
