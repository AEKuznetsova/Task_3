import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import io.qameta.allure.Step;
import static org.junit.Assert.assertTrue;

public class PersonalAccountTest extends CommonTest {
    @Test
    @DisplayName("Успешный переход авторизованного пользователя в личный кабинет")
    public void moveToPersonalAccountAfterLogin() {
        login();
        mainPage.clickPersonalAccount();
        profilePage.waitForProfilePageToLoad();
        assertTrue(driver.getCurrentUrl().contains("/account"));
    }

    @Step("Авторизация пользователя")
    private void login() {
        mainPage.clickPersonalAccount();
        loginPage.waitForLoginPageToLoad();
        loginPage.enterEmail(user.getEmail());
        loginPage.enterPassword(user.getPassword());
        loginPage.clickLoginButton();
    }
}
