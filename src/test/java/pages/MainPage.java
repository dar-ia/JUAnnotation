package pages;

import com.codeborne.selenide.*;

import org.openqa.selenium.interactions.Actions;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class MainPage {

    private SelenideElement hackButton = $("button.btn.btn-primary");
    private SelenideElement openBooking = $("button.btn.openBooking");
    private SelenideElement selectMonthToolbar = $(".rbc-toolbar");
    private SelenideElement nextMonth = selectMonthToolbar.$(byText("Next"));
    private SelenideElement bookButton = $(".btn-outline-primary.book-room");
    private SelenideElement calendarView = $(".rbc-month-view");
    private ElementsCollection setOfDays = $$(".rbc-button-link");

    private SelenideElement bookedRecord = calendarView.$(byText("Unavailable"));

    private SelenideElement bookingFirstName = $("input[placeholder='Firstname']");
    private SelenideElement bookingLastName = $("input[placeholder='Lastname']");
    private SelenideElement bookingEmail = $("input[placeholder='Email']");
    private SelenideElement bookingPhone = $("input[placeholder='Phone']");

    private SelenideElement closeWindow = $(".col-sm-12.text-center .btn-outline-primary");

    private SelenideElement outputResut = $(".col-sm-6.text-center");
    private SelenideElement alerts = $(".alert-danger");


    public MainPage openPage() {
        open("");
        return this;
    }

    public MainPage startBooking() {
        openBooking.click();
        return this;
    }


    public MainPage selectPeriodInTheFuture(String from, String to) {
        nextMonth.click();
        while (bookedRecord.exists()) {
            nextMonth.click();
        }


        Actions action = new Actions(webdriver().object());

        SelenideElement fromElement = setOfDays.find(Condition.text(from));
        SelenideElement toElement = setOfDays.find(Condition.text(to));

        Selenide.actions()
                .moveToElement(fromElement)
                .clickAndHold()
                .moveByOffset(-10, 0)
                .moveToElement(toElement)
                .release()
                .perform();

        return this;

    }

    public MainPage setBookingFirstName(String firstName) {
        bookingFirstName.setValue(firstName);
        return this;
    }

    public MainPage setBookingLastName(String lastNameName) {
        bookingLastName.setValue(lastNameName);
        return this;
    }

    public MainPage setBookingEmail(String email) {
        bookingEmail.setValue(email);
        return this;
    }

    public MainPage setBookingPhone(String phone) {
        bookingPhone.setValue(phone);
        return this;
    }

    public MainPage submitBooking() {
        bookButton.click();
        return this;
    }


    public MainPage assertResult(String result) {
        outputResut.$(byText(result)).shouldBe(Condition.visible);
        return this;
    }

    public MainPage closeDialogWindow() {
        closeWindow.click();
        return this;
    }

    public MainPage assertAlert(String assertion) {
        sleep(1000);

        //alerts.$(byText(assertion))
        if (alerts.$(byText(assertion)).exists()) {
            return this;
        } else {
            throw new AssertionError("Validation failed because of the bug");
        }

    }
}
