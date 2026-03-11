package api;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import constants.Endpoints;
import models.LoginRequest;
import models.UserData;
import static io.restassured.RestAssured.given;

public class UserClient {
    @Step("Создание пользователя")
    public ValidatableResponse create(UserData user) {
        return given()
                .baseUri(Endpoints.BASE_URI)
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(Endpoints.REGISTER)
                .then();
    }

    @Step("Авторизация")
    public ValidatableResponse login(UserData user) {
        LoginRequest req = new LoginRequest(user.getEmail(), user.getPassword());
        return given()
                .baseUri(Endpoints.BASE_URI)
                .contentType(ContentType.JSON)
                .body(req)
                .when()
                .post(Endpoints.LOGIN)
                .then();
    }

    @Step("Удаление пользователя")
    public ValidatableResponse delete(String accessToken) {
        return given()
                .baseUri(Endpoints.BASE_URI)
                .header("Authorization", accessToken)
                .when()
                .delete(Endpoints.DELETE_USER)
                .then();
    }
}

