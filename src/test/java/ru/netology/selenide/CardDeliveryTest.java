package ru.netology.selenide;


import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;


import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {


    @Test
    public void shouldSuccessfullySendARequestToTheCard() {
        Configuration.holdBrowserOpen = true;

        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Самара");
        $("[data-test-id='date'] input").setValue("15.06.2022");
        $("[data-test-id='name'] input").setValue("Петров Иван");
        $("[data-test-id='phone'] input").setValue("+79033354213");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $(withText("Успешно!")).should(visible, Duration.ofSeconds(15));

    }

}
