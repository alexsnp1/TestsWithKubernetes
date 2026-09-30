package api.requests.steps;

import api.models.DepositFundsRequest;
import api.requests.skeleton.requesters.CrudRequester;
import api.requests.skeleton.requesters.Endpoint;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;

public class DepositFundsStep {
    public static void depositFunds(String authTokenUser, int userId, double balance) {
        DepositFundsRequest depositFundsRequest = DepositFundsRequest.builder()
                .id(userId).balance(balance).build();
        new CrudRequester(RequestSpecs.userAuthSpec(authTokenUser),
                Endpoint.ACCOUNTS_DEPOSIT,
                ResponseSpecs.returnsOK())
                .post(depositFundsRequest);
    }
}
