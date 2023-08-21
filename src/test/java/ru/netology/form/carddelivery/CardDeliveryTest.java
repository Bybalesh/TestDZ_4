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
import static org.apache.commons.lang3.StringUtils.stripStart;


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
        $("[data-test-id=city] input").setValue("Волгоград");

        int period = 7;
        String date = generateDate(period, "dd.MM.yyyy");
        String day = generateDate(period, "dd");
        String month = generateDate(period, "MM");
        String currentMonth = generateDate(0, "MM");
        $("[data-test-id='date']").click();
        if (!generateDate(3, "MM").equals(generateDate(7, "MM"))) {
            $$(".calendar__arrow_direction_right").findBy(attribute("data-step", "1")).click();
            $$(".calendar__day").find(exactText(stripStart(day, "0"))).click();
        } else {
            $$(".calendar__day").find(exactText(stripStart(day, "0"))).click();
        }
        $("[data-test-id='name'] input").setValue("Петров Петр Петрович");
        $("[data-test-id='phone'] input").setValue("+78567324855");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + date));

    }

}