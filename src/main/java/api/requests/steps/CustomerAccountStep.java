package api.requests.steps;

import api.models.CustomerAccountsGetResponse;
import api.requests.skeleton.requesters.Endpoint;
import api.requests.skeleton.requesters.ValidatedCrudRequester;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;

public class CustomerAccountStep {
    public static CustomerAccountsGetResponse[] getCustomerAccountResponse(String authTokenUser) {
        return new ValidatedCrudRequester<CustomerAccountsGetResponse[]>(
                RequestSpecs.userAuthSpec(authTokenUser),
                Endpoint.CUSTOMER_ACCOUNTS,
                ResponseSpecs.returnsOK())
                .get();
    }
}
