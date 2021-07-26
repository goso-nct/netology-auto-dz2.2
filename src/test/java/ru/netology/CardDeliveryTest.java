package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.Keys;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class CardDeliveryTest {

    @Test
    void shouldWorking() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 5);
        String date = new SimpleDateFormat("dd.MM.yyyy").format(calendar.getTime());

        open("http://localhost:9999");

        $("[data-test-id=city] input").setValue("Уфа");
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

}
