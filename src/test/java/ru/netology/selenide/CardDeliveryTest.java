package ru.netology.selenide;


import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {
    public String generateDate(int planningDate) {
        return LocalDate.now().plusDays(planningDate).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldSuccessfullySendARequestToTheCard() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Самара");
        $("[data-test-id='name'] input").setValue("Петров Иван");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(String.valueOf(planningDate));
        $("[data-test-id='phone'] input").setValue("+79033354213");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15));

    }

    @Test
    public void shouldWarnWhenCityFieldIsEmpty() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("");
        $("[data-test-id='name'] input").setValue("Петров Иван");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(String.valueOf(planningDate));
        $("[data-test-id='phone'] input").setValue("+79033354213");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='city'] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldWarnWhenDeliveryToTheSelectedLocalityIsNotAvailable() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Красная Поляна");
        $("[data-test-id='name'] input").setValue("Петров Иван");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(String.valueOf(planningDate));
        $("[data-test-id='phone'] input").setValue("+79033354213");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='city'] .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    public void shouldWarnWhenThereAreForeignLettersInTheNameOfTheDeliveryCity() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Camara");
        $("[data-test-id='name'] input").setValue("Петров Иван");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(String.valueOf(planningDate));
        $("[data-test-id='phone'] input").setValue("+79033354213");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='city'] .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    public void shouldWarnWhenThereAreInvalidCharactersInTheNameOfTheDeliveryCity() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Са-ма1а");
        $("[data-test-id='name'] input").setValue("Петров Иван");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(String.valueOf(planningDate));
        $("[data-test-id='phone'] input").setValue("+79033354213");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='city'] .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    public void shouldWarnIfDateIsNotSelected() {

        $("[data-test-id='city'] input").setValue("Самара");
        $("[data-test-id='name'] input").setValue("Петров Иван");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='phone'] input").setValue("+79033354213");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='date'] .input__sub").shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    public void shouldBeWarnedIfTheDateIsSelectedEarlierThanThreeDaysBeforeTheMeeting() {
        String planningDate = generateDate(2);

        $("[data-test-id='city'] input").setValue("Самара");
        $("[data-test-id='name'] input").setValue("Петров Иван");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(String.valueOf(planningDate));
        $("[data-test-id='phone'] input").setValue("+79033354213");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='date'] .input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    public void shouldBeWarnedIfTheLastNameAndFirstNameFieldsAreNotFilledIn() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Самара");
        $("[data-test-id='name'] input").setValue("");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(String.valueOf(planningDate));
        $("[data-test-id='phone'] input").setValue("+79033354213");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='name'] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldBeWarnedIfThereAreForeignLettersAndInvalidCharactersInTheLastNameAndFirstNameField() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Самара");
        $("[data-test-id='name'] input").setValue("Petrov Ivan");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(String.valueOf(planningDate));
        $("[data-test-id='phone'] input").setValue("+79033354213");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='name'] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldWarnedAboutTheInvalidFormatForEnteringAPhoneNumber() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Самара");
        $("[data-test-id='name'] input").setValue("Петров Иван");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(String.valueOf(planningDate));
        $("[data-test-id='phone'] input").setValue("79033354213");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='phone'] .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void should() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Самара");
        $("[data-test-id='name'] input").setValue("Петров Иван");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(String.valueOf(planningDate));
        $("[data-test-id='phone'] input").setValue("+79033354213");
        $(byText("Забронировать")).click();
        $("[data-test-id=agreement].input_invalid .checkbox__text").should(visible);
    }
}
