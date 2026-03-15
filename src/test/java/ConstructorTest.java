import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import constants.SectionNames;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class ConstructorTest extends CommonTest {
    private final SectionNames sectionName;

    public ConstructorTest(SectionNames sectionName) {
        this.sectionName = sectionName;
    }

    @Override
    protected boolean requiresAuthorizedUser() {
        return false;
    }

    @Parameterized.Parameters(name = "Секция: {0}")
    public static Object[][] data() {
        return new Object[][]{{SectionNames.BUN}, {SectionNames.SAUCE}, {SectionNames.FILLING}};
    }

    @Test
    @DisplayName("Переходы по разделам Конструктора")
    public void switchConstructorSection() {
        mainPage.waitForConstructorToLoad();

        if (sectionName == SectionNames.BUN) {
            mainPage.clickSection(SectionNames.SAUCE);
        } else {
            mainPage.clickSection(SectionNames.BUN);
        }

        mainPage.clickSection(sectionName);
        assertTrue("Не удается перейти на секцию " + sectionName, mainPage.isSectionActive(sectionName));
    }
}
