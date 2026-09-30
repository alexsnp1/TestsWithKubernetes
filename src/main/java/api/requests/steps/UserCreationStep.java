package api.requests.steps;

import api.generators.RandomModelGenerator;
import api.models.AdminCreateUserRequest;
import api.models.UserRole;
import api.requests.skeleton.requesters.CrudRequester;
import api.requests.skeleton.requesters.Endpoint;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;

import java.util.UUID;

public class UserCreationStep {
    public static AdminCreateUserRequest createUserRequest() {
        AdminCreateUserRequest credentials = RandomModelGenerator.generate(AdminCreateUserRequest.class);

        AdminCreateUserRequest user = AdminCreateUserRequest.builder()
                .username(uniqueUsername())
                .password(credentials.getPassword())
                .role(UserRole.USER.toString())
                .build();
        new CrudRequester(
                RequestSpecs.adminAuthSpec(),
                Endpoint.ADMIN_USERS,
                ResponseSpecs.returnsCreated())
                .post(user);
        return user;
    }
    private static String uniqueUsername() {
        return "u" + UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 14);
    }
}
