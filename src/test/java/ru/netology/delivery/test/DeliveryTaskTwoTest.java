package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import org.junit.jupiter.api.BeforeEach;

public class DeliveryTaskTwoTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    private String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldBeFormsCompleted() {
        String city = "Владивосток";
        int dayAdd = 7;
        $("[data-test-id='city'] input").setValue(city.substring(0, 2));
        $$(".menu-item__control").findBy(Condition.text(city)).click();
        String planningDate = generateDate(3,"dd.MM.yyyy");
        $("[data-test-id='date'] input").click();
        if (!generateDate(3, "MM").equals(generateDate(dayAdd, "MM"))){
            $("[data-step='1']").click();
        }
        $$(".calendar__day").findBy(Condition.text(generateDate(dayAdd, "dd"))).click();
        //$("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Иван Степанович");
        $("[data-test-id='phone'] input").setValue("+79215627565");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на " + generateDate(dayAdd,"dd.MM.yyyy")));
    }
}
