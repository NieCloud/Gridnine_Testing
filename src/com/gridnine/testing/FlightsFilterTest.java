package com.gridnine.testing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class FlightsFilterTest {

    List<Flight> testingListOfFlights;
    List<Flight> expectedFlights;

    @BeforeEach
    void setUp() {
        testingListOfFlights = FlightBuilder.createFlights();
        expectedFlights = new ArrayList<>();
    }


    @org.junit.jupiter.api.Test
    void whenUsedFilterDepartureToTheCurrentTimesOnlyFlightsInThePastMustRemain() {
        //ожидаем что останется сформированным только полет с датой вылета ранее текущей локальной даты (собранный с помощью фабрики)
        expectedFlights.add(testingListOfFlights.get(2));

        Assertions.assertEquals(
                expectedFlights,
                testingListOfFlights
                        .stream()
                        .filter(FlightsFilter.DEPARTURE_TO_THE_CURRENT_TIME())
                        .collect(Collectors.toList())
        );
    }

    @org.junit.jupiter.api.Test
    void whenUsedFilterSegmentsWithArrivalDateEarlierThanDepartureDateOnlyFlightsWithSuchConditionMustRemain() {
        //ожидаем что останется сформированным только полет с датой прибытия ранее даты вылета (собранный с помощью фабрики)
        expectedFlights.add(testingListOfFlights.get(3));

        Assertions.assertEquals(
                expectedFlights,
                testingListOfFlights
                        .stream()
                        .filter(FlightsFilter.SEGMENTS_WITH_ARRIVAL_DATE_EARLIER_THAN_DEPARTURE_DATE())
                        .collect(Collectors.toList())
        );
    }

    @org.junit.jupiter.api.Test
    void whenUsedFilterMoreThanTwoHoursOnTheGroundBetweenSegemtnsOnlyFlightWithSuchCondinitonMustRemain() {
        //ожидаем что останутся сформированными только полеты с более чем двумя часами на земле (собранные с помощью фабрики)
        expectedFlights.add(testingListOfFlights.get(4));
        expectedFlights.add(testingListOfFlights.get(5));

        Assertions.assertEquals(
                expectedFlights,
                testingListOfFlights
                        .stream()
                        .filter(FlightsFilter.MORE_THAN_TWO_HOURS_ON_THE_GROUND_BETWEEN_SEGMENTS())
                        .collect(Collectors.toList()));
    }

    @org.junit.jupiter.api.Test
    void whenUsedFilterMethodsItAcceptsPredicateDepartureToTheCurrentTimeAndReturnOnlyFlightWithSuchCondition() {
        //проверка на то, что метод фильтр может принимать в качестве параметров предикаты и лист с полетами и возвращать корректную фильтрацию
        expectedFlights.add(testingListOfFlights.get(2));

        Assertions.assertEquals(
                expectedFlights,
                FlightsFilter.filter(testingListOfFlights, FlightsFilter.DEPARTURE_TO_THE_CURRENT_TIME())
        );
    }
}