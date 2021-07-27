package ru.netology;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardDeliveryTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }

    @Test
    void shouldWorkingT1() {
        $("[data-test-id=city] input").setValue("Уфа");

        String date = LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick();
        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);

        $("[data-test-id=name] input").setValue("Иванов");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement] .checkbox__box").click();
        $("[role=button] .button__content").click();

        $("[data-test-id=notification] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на " + date));
    }

    @Test
    void shouldWorkingT2() {
        $("[data-test-id=city] input").setValue("мо");
        $$("[class=popup__content] .menu-item__control").get(2).click();
        assertEquals("Москва", $("[data-test-id=city] input").getValue());

        LocalDate today = LocalDate.now();
        LocalDate needDate = today.plusDays(7);
        int month = needDate.getMonthValue() - today.getMonthValue();

        $("[data-test-id=date] input").click();
        if (month == 1) {
            $$(".calendar__arrow_direction_right").get(1).click();
        }
        $(byText(String.valueOf(needDate.getDayOfMonth()))).shouldHave(cssClass("calendar__day")).click();

        $("[data-test-id=name] input").setValue("Иванов");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement] .checkbox__box").click();
        $("[role=button] .button__content").click();

        $("[data-test-id=notification] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на "
                        + needDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));

    }
}
