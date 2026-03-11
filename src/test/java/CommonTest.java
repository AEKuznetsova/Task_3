import io.qameta.allure.Step;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import constants.Endpoints;
import api.UserClient;
import constants.LoginSource;
import generator.UserDataGenerator;
import models.UserData;
import poms.MainPage;
import poms.LoginPage;
import poms.RegistrationPage;
import poms.PasswordRecoveryPage;
import poms.ProfilePage;
import java.io.File;
import java.time.Duration;

public abstract class CommonTest {
    protected WebDriver driver;
    protected MainPage mainPage;
    protected LoginPage loginPage;
    protected RegistrationPage registrationPage;
    protected PasswordRecoveryPage passwordRecoveryPage;
    protected ProfilePage profilePage;
    protected UserClient userClient;
    protected UserData user;
    protected String accessToken;

    @Before
    public void setUp() {
        String browser = System.getProperty("browser", "chrome");
        ChromeOptions options = new ChromeOptions();

        if ("yandex".equalsIgnoreCase(browser)) {
            String[] paths = {
                    "/Applications/Yandex.app/Contents/MacOS/Yandex",
                    "C:\\Users\\%USERNAME%\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe",
                    "/usr/bin/yandex-browser"
            };
            boolean found = false;
            for (String path : paths) {
                File bin = new File(path);
                if (bin.exists()) {
                    options.setBinary(bin); found = true;
                    break;
                }
            }
            if (!found) throw new RuntimeException("Не найден Yandex Browser");
        } else {
            WebDriverManager.chromedriver().setup();
        }

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.get(Endpoints.BASE_URI);

        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registrationPage = new RegistrationPage(driver);
        passwordRecoveryPage = new PasswordRecoveryPage(driver);
        profilePage = new ProfilePage(driver);
        userClient = new UserClient();

        if (!(this instanceof RegisterTest)) {
            user = UserDataGenerator.generateValidUser();
            accessToken = userClient.create(user).extract().path("accessToken");
        }
    }

    @Step("Открытие формы авторизации через: {source}")
    public void openLoginForm(LoginSource source) {
        switch (source) {
            case HOME_PAGE:
                mainPage.clickLoginButton();
                break;
            case PERSONAL_ACCOUNT:
                mainPage.clickPersonalAccount();
                break;
            case REGISTER_FORM:
                mainPage.clickPersonalAccount();
                loginPage.waitForLoginPageToLoad();
                loginPage.clickRegisterLink();
                registrationPage.clickLoginLink();
                break;
            case RECOVERY_FORM:
                mainPage.clickPersonalAccount();
                loginPage.waitForLoginPageToLoad();
                loginPage.clickForgotPasswordLink();
                passwordRecoveryPage.clickLoginLink();
                break;
        }
    }

    @After
    public void tearDown() {
        if (accessToken != null) userClient.delete(accessToken);
        if (driver != null) driver.quit();
    }
}

