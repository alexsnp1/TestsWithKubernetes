package ui;

import api.requests.steps.CustomerProfileStep;
import api.utils.RandomData;
import com.codeborne.selenide.Selenide;
import common.annotations.UserSession;
import common.data.UserSessionData;
import common.data.UserSessions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.pages.BankAlert;
import ui.pages.DashboardPage;
import ui.pages.EditProfilePage;

@UserSession()
public class ChangingTheNameTests extends BaseUiTest {
    private UserSessionData user;
    private final String validName = RandomData.getRandomValidName();
    private final String invalidName = RandomData.getRandomInvalidName();
    private DashboardPage dashboardPage = new DashboardPage();
    private EditProfilePage editProfilePage = new EditProfilePage();

    @BeforeEach
    public void prepareTestData(UserSessions users) {
        user = users.get(0);
    }

    @Test
    public void userCanRenameThemselves() {
        dashboardPage.open();
        dashboardPage.pressProfileHeader();
        editProfilePage
                .shouldHaveEditProfileHeader()
                .enterNewName(validName).pressSaveChangesButton().checkAlertMessageAndAccept(BankAlert.NAME_UPDATED_SUCCESSFULLY.getMessage()).shouldHaveEditProfileHeader();
        Selenide.refresh();
        editProfilePage.nameShouldBeVisible(validName);
        softly.assertThat(CustomerProfileStep.getCustomerProfileResponse(user.getAuthToken()).getName()).isEqualTo(validName);
    }

    @Test
    public void userCannotRenameThemselvesUsingIncorrectName() {
        dashboardPage.open();
        dashboardPage.pressProfileHeader();
        String name = editProfilePage.getNameOfUser();
        editProfilePage.shouldHaveEditProfileHeader().enterNewName(invalidName).pressSaveChangesButton()
                .checkAlertMessageAndAccept(BankAlert.NAME_MUST_CONTAIN_TWO_WORDS_WITH_LETTERS_ONLY.getMessage(), BankAlert.PLEASE_ENTER_A_VALID_NAME.getMessage())
                .shouldHaveEditProfileHeader();
        Selenide.refresh();
        editProfilePage.nameShouldBeVisible(name);
        softly.assertThat(CustomerProfileStep.getCustomerProfileResponse(user.getAuthToken()).getName()).isNull();
    }
}
