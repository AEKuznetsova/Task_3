package poms;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import constants.ConstructorButton;
import java.time.Duration;

public class ProfilePage {
    private final WebDriver driver;

    private final By profileTab = By.xpath(".//a[@href='/account']");
    private final By logoutButton = By.xpath(".//button[text()='Выход']");
    private final By constructorLink = By.linkText("Конструктор");
    private final By logoLink = By.className("AppHeader_header__logo__2D0X2");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Ожидание загрузки страницы профиля")
    public void waitForProfilePageToLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(profileTab));
    }

    @Step("Клик на кнопку Выйти")
    public void clickLogout() {
        driver.findElement(logoutButton).click();
    }

    @Step("Переход по линку Конструктор")
    public void clickConstructor() {
        driver.findElement(constructorLink).click();
    }

    @Step("Клик по лого Stellar Burgers")
    public void clickLogo() {
        driver.findElement(logoLink).click();
    }

    @Step("Клик по кнопке: {buttonName}")
    public void changeButton(ConstructorButton buttonName) {
        switch (buttonName) {
            case CONSTRUCTOR:
                clickConstructor();
                break;
            case LOGO_STELLAR_BURGER:
                clickLogo();
                break;
            default:
                throw new IllegalArgumentException("Неизвестная кнопка: " + buttonName);
        }
    }
}
