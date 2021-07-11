package com.validator.account.validate.configuration;

import com.validator.account.validate.model.Provider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@ConfigurationProperties(prefix = "account")
public class AccountValidationProvider {
    /**
     * providers to store the list of provides from application properties
     */
    private List<Provider> providers;

    /**
     * @return
     */
    public List<Provider> getProviders() {
        return providers;
    }
    /**
     * @param providers set the list of providers
     */
    public void setProviders(List<Provider> providers) {
        this.providers = providers;
    }
}
