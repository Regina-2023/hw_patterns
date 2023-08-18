package ru.netology.delivery.test;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;


import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {
    public static String deleteString = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        SelenideElement form = $("form");
        form.$("input[placeholder='Город']").setValue(validUser.getCity());
        $$("[class='menu-item__control']").find(exactText(validUser.getCity())).click();
        form.$("input[placeholder='Дата встречи']").sendKeys(deleteString);
        form.$("input[placeholder='Дата встречи']").setValue(firstMeetingDate);
        form.$("input[name='name']").setValue(validUser.getName());
        form.$("input[name='phone']").setValue(validUser.getPhone());
        form.$("span[class='checkbox__box']").click();
        form.$("span[class='button__text']").click();
        $("div[class='notification__content']").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Встреча успешно запланирована на " + firstMeetingDate));

        form.$("input[placeholder='Дата встречи']").sendKeys(deleteString);
        form.$("input[placeholder='Дата встречи']").setValue(secondMeetingDate);
        form.$("span[class='button__text']").click();
        $(".notification_status_error>.notification__content .button__content").click();
        $("div[class='notification__content']").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Встреча успешно запланирована на " + secondMeetingDate));
    }

}

