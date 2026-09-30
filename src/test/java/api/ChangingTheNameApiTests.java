package api;

import api.models.*;
import api.requests.skeleton.requesters.CrudRequester;
import api.requests.skeleton.requesters.Endpoint;
import api.requests.skeleton.requesters.ValidatedCrudRequester;
import api.requests.steps.AuthenticationStep;
import api.requests.steps.CustomerProfileStep;
import api.requests.steps.UserCreationStep;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;
import api.utils.RandomData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ChangingTheNameApiTests extends BaseTest {
    private String authTokenUser;
    private final String validName = RandomData.getRandomValidName();

    @BeforeEach
    public void setUp() {
        AdminCreateUserRequest user = UserCreationStep.createUserRequest();
        authTokenUser = AuthenticationStep.getUserTokenStep(user);
    }

    @Test
    public void userCanRenameThemselves() {
        CustomerProfileUpdateRequest customerProfileUpdateRequest = CustomerProfileUpdateRequest
                .builder().name(validName).build();

        CustomerProfileUpdateResponse customerProfileUpdateResponse = new ValidatedCrudRequester<CustomerProfileUpdateResponse>(RequestSpecs.userAuthSpec(authTokenUser),
                Endpoint.CUSTOMER_PROFILE_UPDATE,
                ResponseSpecs.profileUpdatedSuccessfully())
                .put(customerProfileUpdateRequest);
        softly.assertThat(validName).isEqualTo(customerProfileUpdateResponse.getCustomer().getName());

//        CustomerProfileGetResponse customerProfileGetResponse =
//                CustomerProfileStep.getCustomerProfileResponse(authTokenUser);
//        softly.assertThat(validName).isEqualTo(customerProfileGetResponse.getName());
    }

    @ParameterizedTest
    @ValueSource(strings = {"JohnSmith", "a", "", " ", "John Smith2", "John Smith?"})
    public void userCannotRenameThemselvesUsingIncorrectName(String name) {
        CustomerProfileGetResponse customerProfileGetResponseOld =
                CustomerProfileStep.getCustomerProfileResponse(authTokenUser);

        CustomerProfileUpdateRequest customerProfileUpdateRequest = CustomerProfileUpdateRequest
                .builder().name(name).build();
        new CrudRequester(RequestSpecs.userAuthSpec(authTokenUser),
                Endpoint.CUSTOMER_PROFILE_UPDATE,
                ResponseSpecs.invalidNameError())
                .put(customerProfileUpdateRequest)
                .extract()
                .asString();

        CustomerProfileGetResponse customerProfileGetResponseNew =
                CustomerProfileStep.getCustomerProfileResponse(authTokenUser);
        softly.assertThat(customerProfileGetResponseOld.getName()).isEqualTo(customerProfileGetResponseNew.getName());
    }

    @Test
    public void userCannotRenameThemselvesUsingNullName() {
        CustomerProfileGetResponse customerProfileGetResponseOld =
                CustomerProfileStep.getCustomerProfileResponse(authTokenUser);

        CustomerProfileUpdateRequest customerProfileUpdateRequest = CustomerProfileUpdateRequest
                .builder().name(null).build();
        new CrudRequester(RequestSpecs.userAuthSpec(authTokenUser),
                Endpoint.CUSTOMER_PROFILE_UPDATE,
                ResponseSpecs.returnsInternalServerError())
                .put(customerProfileUpdateRequest);

        CustomerProfileGetResponse customerProfileGetResponseNew =
                CustomerProfileStep.getCustomerProfileResponse(authTokenUser);
        softly.assertThat(customerProfileGetResponseOld.getName()).isEqualTo(customerProfileGetResponseNew.getName());
    }
}
