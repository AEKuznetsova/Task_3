import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import constants.Endpoints;
import constants.SectionNames;
import poms.MainPage;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class ConstructorTest {
    private WebDriver driver;
    private MainPage mainPage;
    private final SectionNames sectionName;

    public ConstructorTest(SectionNames sectionName) {
        this.sectionName = sectionName;
    }

    @Parameterized.Parameters(name = "Секция: {0}")
    public static Object[][] data() {
        return new Object[][]{{SectionNames.BUN}, {SectionNames.SAUCE}, {SectionNames.FILLING}};
    }

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        mainPage = new MainPage(driver);
        driver.get(Endpoints.BASE_URI);
        mainPage.waitForConstructorToLoad();
    }

    @Test
    @DisplayName("Переходы по разделам Конструктора")
    public void switchConstructorSection() {
        if (sectionName == SectionNames.BUN) mainPage.clickSection(SectionNames.SAUCE);
        else mainPage.clickSection(SectionNames.BUN);

        mainPage.clickSection(sectionName);
        String cls = mainPage.getClassName(sectionName);
        assertTrue("Не удается перейти на секцию " + sectionName, cls.contains("tab_tab_type_current__2BEPc"));
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}


