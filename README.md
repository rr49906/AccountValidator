# AccountValidator
AccountValidator is Spring Boot Rest API application used to validate the account number.
It acts intermediate API and invokes the various other data providers asynchrounously and collects the results from all providers and send it to client.

Data providers can be passed as input to service. if provided then input providers will be consider other wise providers configured in property file will be used.


# Configuration - Profiling.
  Spring Boot provides the profiling features, it allow us to maintain the properties environment specific and there wont be any manual intervention like placing the properties at specific location for each environment.
  
  profile can be enabled by passing run time argument to application.
  **--spring.profiles.active=prod**
