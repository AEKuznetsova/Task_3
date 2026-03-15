package poms;

import constants.SectionNames;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MainPage {
    private static final String ACTIVE_TAB_CLASS = "tab_tab_type_current";

    private final WebDriver driver;
    private final By personalAccountButton = By.xpath("//a[@href='/account']");
    private final By loginButton = By.xpath(".//button[text()='Войти в аккаунт']");
    private final By constructorHeader = By.xpath(".//h1[text()='Соберите бургер']");
    private final By bunsTab = By.xpath("//span[text()='Булки']/parent::div");
    private final By saucesTab = By.xpath("//span[text()='Соусы']/parent::div");
    private final By fillingsTab = By.xpath("//span[text()='Начинки']/parent::div");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Нажатие на кнопку Войти в аккаунт")
    public void clickLoginButton() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(loginButton))
                .click();
    }

    @Step("Нажатие на кнопку Личный кабинет")
    public void clickPersonalAccount() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(personalAccountButton))
                .click();
    }

    @Step("Ожидание видимости Конструктора")
    public void waitForConstructorToLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(constructorHeader));
    }

    @Step("Клик по секции Конструктора: {section}")
    public void clickSection(SectionNames section) {
        By tabLocator = getSectionLocator(section);
        WebElement tabElement = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(tabLocator));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tabElement);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tabElement);

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.attributeContains(tabLocator, "class", ACTIVE_TAB_CLASS));
    }

    @Step("Проверка, что активна секция Конструктора: {section}")
    public boolean isSectionActive(SectionNames section) {
        return driver.findElement(getSectionLocator(section))
                .getAttribute("class")
                .contains(ACTIVE_TAB_CLASS);
    }

    private By getSectionLocator(SectionNames section) {
        switch (section) {
            case BUN: return bunsTab;
            case SAUCE: return saucesTab;
            case FILLING: return fillingsTab;
            default:
                throw new IllegalArgumentException("Неизвестный раздел конструктора: " + section);
        }
    }
}
