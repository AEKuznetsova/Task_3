package poms;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PasswordRecoveryPage {
    private final WebDriver driver;

    private final By loginLink = By.linkText("Войти");

    public PasswordRecoveryPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Вход со страницы восстановления пароля")
    public void clickLoginLink() {
        driver.findElement(loginLink).click();
    }
}
