package ru.netology.selenide;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static java.time.format.DateTimeFormatter.ofPattern;


public class CardDeliveryTest2 {
    @Test
    void shouldRegisterCardDelivery() {
        Configuration.holdBrowserOpen = true;

        open("http://localhost:9999");
        $("span[data-test-id='city'] input").setValue("Са");
        $$("div.popup__content div").find(exactText("Самара")).click();
        $("span[data-test-id='date'] button").click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        LocalDate currentDate = LocalDate.now();
        LocalDate planningDate = LocalDate.now().plusDays(7);
        if (currentDate.getMonthValue() != planningDate.getMonthValue()) {
            $(".calendar__arrow_direction_right[data-step='1']").click();
        }
        $$("td.calendar__day").find(exactText(String.valueOf(planningDate.getDayOfMonth()))).click();
        $("[data-test-id='name'] input").setValue("Петров Иван");
        $("[data-test-id='phone'] input").setValue("+79033354213");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        String Date = planningDate.format(ofPattern("dd.MM.yyyy"));
        $(".notification__content")
                .shouldHave(text("Встреча успешно забронирована на " + Date), Duration.ofSeconds(15));


    }
}

