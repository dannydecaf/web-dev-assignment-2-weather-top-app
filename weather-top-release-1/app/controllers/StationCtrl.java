package controllers;

import models.Reading;
import models.Station;
import play.Logger;
import play.mvc.Controller;

import java.util.List;

public class StationCtrl extends Controller {
    public static void index(Long id) {
        Station station = Station.findById(id);
        Reading reading = getReading(station);
        Logger.info("Station ID = " + id);
        render("station.html", station, reading);
    }

    public static void deletereading(Long id, Long readingid) {
        Station station = Station.findById(id);
        Reading oldReading = Reading.findById(readingid);
        Logger.info("Removing " + oldReading.code);
        station.readings.remove(oldReading);
        station.save();
        oldReading.delete();

        Reading reading = getReading(station);
        render("station.html", station, reading);
    }

    public static void addReading(Long id, int code, double temp, double windspeed, int windDirection, int pressure) {
        Reading reading = new Reading(code, temp, windspeed, windDirection, pressure);
        Station station = Station.findById(id);
        station.readings.add(reading);
        station.save();
        redirect("/stations/" + id);
    }

    public static Reading getReading(Station station) {
        List<Reading> readings = station.readings;
        Reading reading = null;
        if (readings.size() != 0) {
            reading = readings.get(readings.size() - 1);
            if (reading.tempFahr == 0.0 && reading.weather == null && reading.beufortSpeed == 0) {
                reading.setWeatherReading(reading.code);
                reading.setFahrenheit(reading.temp);
                reading.setWindSpeedBeufort(reading.windspeed);
                reading.setWindChill();
                reading.setWindDirectionText();
            }
        }
        return reading;
    }
}