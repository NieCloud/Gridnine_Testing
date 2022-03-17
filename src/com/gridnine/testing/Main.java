package com.gridnine.testing;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Flight> listOfFlights = FlightBuilder.createFlights();

        //1. Вылет до текущего момента времени
        System.out.println(FlightsFilter.filter(
                listOfFlights,
                FlightsFilter.DEPARTURE_TO_THE_CURRENT_TIME()
                )
        );

        //2. Имеются сегменты с датой прилёта раньше даты вылета
        System.out.println(FlightsFilter.filter(
                listOfFlights,
                FlightsFilter.SEGMENTS_WITH_ARRIVAL_DATE_EARLIER_THAN_DEPARTURE_DATE()
                )
        );

        //3. Общее время, проведённое на земле превышает два часа (время на земле — это интервал между прилётом одного сегмента и вылетом следующего за ним)
        System.out.println(FlightsFilter.filter(
                listOfFlights,
                FlightsFilter.MORE_THAN_TWO_HOURS_ON_THE_GROUND_BETWEEN_SEGMENTS()
                )
        );
    }
}
