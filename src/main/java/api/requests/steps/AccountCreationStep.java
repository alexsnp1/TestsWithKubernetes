package api.requests.steps;

import api.models.UserCreateAccountResponse;
import api.requests.skeleton.requesters.Endpoint;
import api.requests.skeleton.requesters.ValidatedCrudRequester;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;

public class AccountCreationStep {
    public static UserCreateAccountResponse userCreateAccount(String authTokenUser) {
        return new ValidatedCrudRequester<UserCreateAccountResponse>(
                RequestSpecs.userAuthSpec(authTokenUser),
                Endpoint.ACCOUNTS,
                ResponseSpecs.returnsCreated())
                .post();
    }
}
