import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import constants.ConstructorButton;
import poms.ProfilePage;
import constants.Endpoints;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class PrivateToConstructorTest extends CommonTest {
    private final ConstructorButton buttonName;

    public PrivateToConstructorTest(ConstructorButton buttonName) {
        this.buttonName = buttonName;
    }

    @Parameterized.Parameters(name = "Кнопка перехода: {0}")
    public static Object[][] data() {
        return new Object[][]{{ConstructorButton.CONSTRUCTOR}, {ConstructorButton.LOGO_STELLAR_BURGER}};
    }

    @Test
    @DisplayName("Переход из личного кабинета в Конструктор")
    public void moveToConstructorFromPersonalAccount() {
        loginAndMoveToPersonalAccount();
        ProfilePage p = new ProfilePage(driver);
        p.waitForProfilePageToLoad();
        p.changeButton(buttonName);
        assertEquals(Endpoints.BASE_URI + "/", driver.getCurrentUrl());
    }

    @Step("Авторизация с валидными данными и переход в личный кабинет")
    private void loginAndMoveToPersonalAccount() {
        mainPage.clickPersonalAccount();
        loginPage.waitForLoginPageToLoad();
        loginPage.enterEmail(user.getEmail());
        loginPage.enterPassword(user.getPassword());
        loginPage.clickLoginButton();
        mainPage.clickPersonalAccount();
    }
}
