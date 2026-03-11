import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import constants.LoginSource;
import constants.Endpoints;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class LoginTest extends CommonTest {
    private final LoginSource source;

    public LoginTest(LoginSource source) {
        this.source = source;
    }

    @Parameterized.Parameters(name = "Точка входа: {0}")
    public static Object[][] data() {
        return new Object[][]{{LoginSource.HOME_PAGE}, {LoginSource.PERSONAL_ACCOUNT}, {LoginSource.REGISTER_FORM}, {LoginSource.RECOVERY_FORM}};
    }

    @Test
    @DisplayName("Успешная авторизация через разные кнопки")
    public void loginToProfile() {
        openLoginForm(source);
        loginPage.waitForLoginPageToLoad();
        loginPage.enterEmail(user.getEmail());
        loginPage.enterPassword(user.getPassword());
        loginPage.clickLoginButton();
        mainPage.waitForConstructorToLoad();
        assertEquals(Endpoints.BASE_URI + "/", driver.getCurrentUrl());
    }
}
