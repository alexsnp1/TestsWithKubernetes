package api.requests.skeleton.requesters;


import api.models.AdminCreateUserRequest;
import api.models.AdminCreateUserResponse;
import api.models.BaseModel;
import api.models.CustomerAccountsGetResponse;
import api.models.CustomerProfileGetResponse;
import api.models.CustomerProfileUpdateRequest;
import api.models.CustomerProfileUpdateResponse;
import api.models.DepositFundsRequest;
import api.models.DepositFundsResponse;
import api.models.TransferFundsRequest;
import api.models.TransferFundsResponse;
import api.models.UserCreateAccountResponse;
import api.models.UserLoginRequest;
import api.models.UserLoginResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Endpoint {
    ADMIN_USERS(
            "/admin/users",
            AdminCreateUserRequest.class,
            AdminCreateUserResponse.class
    ),
    CUSTOMER_ACCOUNTS(
            "/customer/accounts",
            BaseModel.class,
            CustomerAccountsGetResponse[].class
    ),
    CUSTOMER_PROFILE_GET(
            "/customer/profile",
            BaseModel.class,
            CustomerProfileGetResponse.class
    ),
    CUSTOMER_PROFILE_UPDATE(
            "/customer/profile",
            CustomerProfileUpdateRequest.class,
            CustomerProfileUpdateResponse.class
    ),
    ACCOUNTS_DEPOSIT(
            "/accounts/deposit",
            DepositFundsRequest.class,
            DepositFundsResponse.class
    ),
    ACCOUNTS_TRANSFER(
            "/accounts/transfer",
            TransferFundsRequest.class,
            TransferFundsResponse.class
    ),
    ACCOUNTS(
            "/accounts",
            BaseModel.class,
            UserCreateAccountResponse.class
    ),

    LOGIN(
            "/auth/login",
            UserLoginRequest.class,
            UserLoginResponse.class
    );
    private final String url;
    private final Class<?> requestModel;
    private final Class<?> responseModel;
}
