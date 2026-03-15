import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import constants.Endpoints;
import generator.UserDataGenerator;
import models.UserData;
import io.qameta.allure.Step;
import static org.junit.Assert.assertEquals;

public class RegisterTest extends CommonTest {
    private static final String EXPECTED_LOGIN_URL = Endpoints.BASE_URI + "/login";
    private static final String EXPECTED_ERROR_TEXT = "Некорректный пароль";
    private String tempToken;

    @Before
    public void initUser() {
        user = UserDataGenerator.generateValidUser();
    }

    @After
    public void deleteUser() {
        if (tempToken == null) {
            tempToken = userClient.login(user).extract().path("accessToken");
        }
        if (tempToken != null) userClient.delete(tempToken);
    }

    @Test
    @DisplayName("Успешная регистрация при вводе валидных данных")
    public void getRegisteredWithCorrectData() {
        moveToRegistrationPage();
        registrationPage.setName(user.getName())
                .setEmail(user.getEmail())
                .setPassword(user.getPassword())
                .clickRegisterButton();
        loginPage.waitForLoginPageToLoad();
        assertEquals(EXPECTED_LOGIN_URL, driver.getCurrentUrl());
    }

    @Test
    @DisplayName("Сообщение об ошибке при длине пароля менее 6 символов")
    public void getErrorMessageWithShortPassword() {
        moveToRegistrationPage();
        registrationPage.setName(user.getName())
                .setEmail(user.getEmail())
                .setPassword("123")
                .clickRegisterButton();
        String err = registrationPage.getTextException();
        assertEquals(EXPECTED_ERROR_TEXT, err);
    }

    @Step("Открытие формы регистрации через личный кабинет")
    private void moveToRegistrationPage() {
        mainPage.clickPersonalAccount();
        loginPage.waitForLoginPageToLoad();
        loginPage.clickRegisterLink();
    }
}