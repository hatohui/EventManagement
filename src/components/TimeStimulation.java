package components;

import java.time.LocalDate;

public class TimeStimulation {
    private static LocalDate today;

    public static void init() {
        today = LocalDate.now();
    }

    public static void init(LocalDate date) {
        today = date;
    }

    public static void fastForwardManyDays(long days) {
        today.plusDays(days);
    }

    public static void fastForwardOneWeek() {
        today.plusWeeks(1);
    }

    public static void minusManyDays(long days) {
        today.minusDays(days);
    }
}
