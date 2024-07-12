package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Conditional;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
//import com.codeborne.selenide.Condition;
//import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
//import ru.netology.delivery.data.DataGenerator;

//import static com.codeborne.selenide.Selenide.$;
//import static com.codeborne.selenide.Selenide.open;

public class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    private String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }
    //LocalDate - формат для работы с датой
    //plusDays - работает с разными значениями ( -/+/0)

    @Test
    public void shouldBeSuccssfullForms() {
        $("[data-test-id='city'] input").setValue("Владикавказ");
        String planningDate = generateDate(3,"dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Иван Степанович");
        $("[data-test-id='phone'] input").setValue("+79215627565");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate));
    }
}