package ru.netology.form.carddelivery;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;



public class CardDeliveryTest {
    private String generateDate(int addDays, String pattern){
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldBeSuccessCityCompleted(){
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Во");
        $(byText("Волгоград")).click();
        String currentDate = generateDate(4,"dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(currentDate);
        $("[data-test-id='name'] input").setValue("Петров Петр Петрович");
        $("[data-test-id='phone'] input").setValue("+78567324855");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));

    }
    @Test
    public void shouldBeSuccessTimeCompleted(){
        open("http://localhost:9999");
            String planningDate = generateDate(7, "dd.MM.yyyy");

            $("[data-test-id=city] input").setValue("Ка");
            $(".popup__inner").shouldBe(exist, Duration.ofSeconds(5));
            $$(".menu-item").find(text("Казань")).click();
            $("button span.icon_name_calendar").click();
            $("div.calendar-input__calendar-wrapper").shouldBe(visible, Duration.ofSeconds(40));
            if (!generateDate(3, "MM").equals(generateDate(7, "MM"))) {
                $(".popup [data-step='1']").click();
            }
            $$(".calendar__day").find(text(generateDate(7, "d"))).click();
            $("[data-test-id=name] input").setValue("Петров Петр Петрович");
            $("[data-test-id=phone] input").setValue("+79136698749");
            $(".checkbox__box").click();
            $(".button").click();
            $("[data-test-id=notification] .notification__title").shouldHave(exactText("Успешно!"), Duration.ofSeconds(20));
            $("[data-test-id=notification] .notification__content").shouldHave(exactText("Встреча успешно забронирована на " + planningDate));
        }

}