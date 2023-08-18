package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateDate(int days) {
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate inputText = LocalDate.now().plusDays(days);
        return outputFormat.format(inputText);
    }

    public static String generateCity(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.address().city();
    }

    public static String generateName(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.name().name();
    }

    public static String generatePhone(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.phoneNumber().phoneNumber();
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            return new UserInfo(generateCity(locale), generateName(locale), generatePhone(locale));
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}
