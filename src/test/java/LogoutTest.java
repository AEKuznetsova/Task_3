import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import io.qameta.allure.Step;
import static org.junit.Assert.assertTrue;

public class LogoutTest extends CommonTest {
    @Test
    @DisplayName("Выход из аккаунта")
    public void logoutFromProfile() {
        loginToPersonal();
        profilePage.waitForProfilePageToLoad();
        profilePage.clickLogout();
        loginPage.waitForLoginPageToLoad();
        assertTrue(driver.getCurrentUrl().contains("/login"));
    }

    @Step("Авторизация и переход в личный кабинет")
    private void loginToPersonal() {
        mainPage.clickPersonalAccount();
        loginPage.waitForLoginPageToLoad();
        loginPage.enterEmail(user.getEmail());
        loginPage.enterPassword(user.getPassword());
        loginPage.clickLoginButton();
        mainPage.clickPersonalAccount();
    }
}
