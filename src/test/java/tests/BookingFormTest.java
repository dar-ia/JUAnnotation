package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import pages.MainPage;

import java.util.stream.Stream;


public class BookingFormTest extends TestBase {

    MainPage page = new MainPage();


    @DisplayName("Room can be booked successfully")
    @Tag("POSITIVE")
    @MethodSource
    @ParameterizedTest(name = "Booking is ok for names in {6}")
    void roomCanBeSuccessfullyBookedInBothLanguages(String firstName,
                                                    String lastName,
                                                    String from,
                                                    String to,
                                                    String email,
                                                    String phone,
                                                    String language) {
        page.openPage()
                .startBooking()
                .selectPeriodInTheFuture(from, to)
                .setBookingFirstName(firstName)
                .setBookingLastName(lastName)
                .setBookingEmail(email)
                .setBookingPhone(phone)
                .submitBooking()
                .assertResult("Booking Successful!")
                .closeDialogWindow()
        ;

    }

    static Stream<Arguments> roomCanBeSuccessfullyBookedInBothLanguages() {
        return Stream.of(
                Arguments.of(
                        "Dora",
                        "Kukura",
                        "10",
                        "15",
                        "ert@ui.yu",
                        "9875557773454",
                        "english"
                ),
                Arguments.of("Дора",
                        "Кукура",
                        "10",
                        "15",
                        "ert@ui.yu",
                        "9875557773454",
                        "russian")
        );
    }


    @ParameterizedTest(name = "Check that validation failed if parameters name is {6} than required")
    @DisplayName("Negative case for out of boundaries")
    @Tag("NEGATIVE")
    @CsvSource(value = {
            "Po, Ya, 10,12,qwerty@mailic.pow,12345,less",
            "Firstnameismorethateighteen, Lastusernameismorethanthirtychars, 10,12,qwerty@mailic.pow,1234567890987654321012,more",

    })
    void bookingFormLengthMoreOrLessValidation(String firstName,
                                               String lastName,
                                               String from,
                                               String to,
                                               String email,
                                               String phone,
                                               String criteria) {
        page.openPage().
                startBooking()
                .selectPeriodInTheFuture(from, to)
                .setBookingFirstName(firstName)
                .setBookingLastName(lastName)
                .setBookingEmail(email)
                .setBookingPhone(phone)
                .submitBooking()
                .assertAlert("размер должен находиться в диапазоне от 3 до 18")
                .assertAlert("размер должен находиться в диапазоне от 3 до 30")
                .assertAlert("размер должен находиться в диапазоне от 11 до 21");

    }

    @ValueSource(strings = {
            "abcdefg",
            "abc.d@g"
    })
    @DisplayName("Email format validation")
    @ParameterizedTest(name = "Email format validation (value = {0})")
    void bookingOfOccupiedSlotsShouldBeUnavailable(String email) {
        page.openPage()
                .startBooking()
                .setBookingEmail(email)
                .submitBooking()
                .assertAlert("должно иметь формат адреса электронной почты");

    }
}
