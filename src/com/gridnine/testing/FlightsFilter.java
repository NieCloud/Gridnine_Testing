package com.gridnine.testing;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FlightsFilter {

    /*  Фильтр 1 - вылет до текущего момента времени. В перелете из всего массива сегментов берется начальный сегмент, обозначающий начало перелета (0 элемент в массиве) и проверяется,
        раньше он текущего локального времени или нет. Если раньше - тогда фильтр данный перелет оставляет. */
    public static Predicate<Flight> DEPARTURE_TO_THE_CURRENT_TIME() {
                return x -> x
                            .getSegments()
                            .get(0)
                            .getDepartureDate()
                            .isBefore(LocalDateTime.now());
    }

    /*  Фильтр 2 - имеются сегменты с датой прилёта раньше даты вылета. Для каждого сегмента перелета происходит проверка - является ли дата прилета сегмента более ранней чем дата вылета.
    Если да - фильтр оставляет такой перелет */
    public static Predicate<Flight> SEGMENTS_WITH_ARRIVAL_DATE_EARLIER_THAN_DEPARTURE_DATE() {
        return x -> x
                .getSegments()
                .stream()
                .anyMatch(z -> z.getArrivalDate().isBefore(z.getDepartureDate()));
    }


    /*  Фильтр 3 - общее время, проведённое на земле превышает два часа. Суммируется все время перелета и вычитается время проведенное в воздухе (разница между взлетом и посадкой каждого сегмента)
        Если сумма часов больше двух - фильтр оставляет такой перелет*/
    public static Predicate<Flight> MORE_THAN_TWO_HOURS_ON_THE_GROUND_BETWEEN_SEGMENTS() {

        return x -> {

            final long hoursOnTheGround = 2;

            // полное время перелета
            Duration totalTime = Duration.between(
                    x.getSegments().get(0).getDepartureDate(),
                    x.getSegments().get(x.getSegments().size() - 1).getArrivalDate());

            // время в воздухе - сумма разниц времени между взлетом и падением каждого сегмента
            Duration timeInTheAir = x
                    .getSegments()
                    .stream()
                    .map(z -> Duration.between(z.getDepartureDate(), z.getArrivalDate()))
                    .reduce(Duration::plus)
                    .orElse(Duration.ofHours(0));


            // проверка - если разница полного времени перелета и времени в воздухе больше 2 часов - возвращает TRUE
            return totalTime.minus(timeInTheAir).compareTo(Duration.ofHours(hoursOnTheGround)) > 0;
        };
    }

        // метод использует все предикаты (фильтры) на предоставленном листе полетов и возвращает отфильтрованный лист
        public static <Flight> List<Flight> filter (final List<Flight> list, Predicate<Flight>... predicates) {

            return list.stream()
                    .filter(Arrays.stream(predicates).reduce(z -> true, Predicate::and))
                    .collect(Collectors.toList());
        }
}













