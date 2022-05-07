package ru.netology.selenide;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest2 {
    String[] monthNames = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
    String date;
    String month;
    String day;

    @BeforeEach
    void setUp() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 100);
        date = new SimpleDateFormat("DD.MM.YYYY").format(calendar.getTime());
        month = monthNames[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.YEAR);
        day = Integer.toString(calendar.get(Calendar.DATE));
        open("http://localhost:9999");
    }

    @Test
    void shouldRegisterCardDelivery() {
        Configuration.holdBrowserOpen = true;
        $("span[data-test-id='city'] input").setValue("Са");
        $$("div.popup__content div").find(exactText("Самара")).click();
        $("span[data-test-id='date'] button").click();

        while (!$("div.calendar__name").getText().equals(month)) {
            $$("div.calendar__arrow.calendar__arrow_direction_right").get(1).click();
        }

        $$("table.calendar__layout td").find(text(day)).click();
        $("[data-test-id='name'] input").setValue("Петров Иван");
        $("[data-test-id='phone'] input").setValue("+79033354213");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Успешно!")).should(visible, Duration.ofSeconds(15));


    }
}

