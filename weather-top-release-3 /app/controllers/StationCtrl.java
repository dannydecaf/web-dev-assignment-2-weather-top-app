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
            if (reading.minTempReading == 0.0 || reading.minPressureReading == 0.0 || reading.minWindSpeedReading == 0.0) {
                reading.setMinTempReading(getMinTemp(readings));
                reading.setMaxTempReading(getMaxTemp(readings));
                reading.setMinPressureReading(getMinPressure(readings));
                reading.setMaxPressureReading(getMaxPressure(readings));
                reading.setMinWindSpeedReading(getMinWindSpeed(readings));
                reading.setMaxWindSpeedReading(getMaxWindSpeed(readings));
            }
            if (reading.trendingWindSpeed == null || reading.trendingPressure == null || reading.trendingTemp == null) {
                reading.setTrendingTemp(getTrendingTemp(readings));
                reading.setTrendingPressure(getTrendingPressure(readings));
                reading.setTrendingWindSpeed(getTrendingWindSpeed(readings));
            }
        }
        return reading;
    }

    public static String getTrendingWindSpeed(List<Reading> readings) {
        if (readings.size() < 3) {
            return "arrows alternate horizontal icon";
        }
        double windSpeed1 = readings.get(0).windspeed;
        double windSpeed2 = readings.get(1).windspeed;
        double windSpeed3 = readings.get(2).windspeed;

        if (windSpeed1 < windSpeed2 && windSpeed2 < windSpeed3) {
            return "caret up icon";
        } else if (windSpeed1 > windSpeed2 && windSpeed2 > windSpeed3) {
            return "caret down icon";
        } else {
            return "arrows alternate horizontal icon";
        }
    }

    public static String getTrendingPressure(List<Reading> readings) {
        if (readings.size() < 3) {
            return "arrows alternate horizontal icon";
        }
        double pressure1 = readings.get(0).pressure;
        double pressure2 = readings.get(1).pressure;
        double pressure3 = readings.get(2).pressure;

        if (pressure1 < pressure2 && pressure2 < pressure3) {
            return "caret up icon";
        } else if (pressure1 > pressure2 && pressure2 > pressure3) {
            return "caret down icon";
        } else {
            return "arrows alternate horizontal icon";
        }
    }

    public static String getTrendingTemp(List<Reading> readings) {
        if (readings.size() < 3) {
            return "arrows alternate horizontal icon";
        }
        double temp1 = readings.get(0).temp;
        double temp2 = readings.get(1).temp;
        double temp3 = readings.get(2).temp;

        if (temp1 < temp2 && temp2 < temp3) {
            return "caret up icon";
        } else if (temp1 > temp2 && temp2 > temp3) {
            return "caret down icon";
        } else {
            return "arrows alternate horizontal icon";
        }
    }

    public static double getMinTemp(List<Reading> readings) {
        Reading minTempReading = null;
        if (readings.size() > 0) {
            minTempReading = readings.get(0);
            for (Reading reading : readings) {
                if (reading.temp < minTempReading.temp) {
                    minTempReading = reading;
                    System.out.println("Smallest temperature element present in given array: " + minTempReading.temp);
                }
            }
            return minTempReading.temp;
        }
        return 0.0;
    }

    public static double getMinWindSpeed(List<Reading> readings) {
        Reading minWindSpeedReading = null;
        if (readings.size() > 0) {
            minWindSpeedReading = readings.get(0);
            for (Reading reading : readings) {
                if (reading.windspeed < minWindSpeedReading.windspeed) {
                    minWindSpeedReading = reading;
                    System.out.println("Smallest wind speed element present in given array: " + minWindSpeedReading.windspeed);
                }
            }
            return minWindSpeedReading.windspeed;
        }
        return 0.0;
    }

    public static double getMinPressure(List<Reading> readings) {
        Reading minPressureReading = null;
        if (readings.size() > 0) {
            minPressureReading = readings.get(0);
            for (Reading reading : readings) {
                if (reading.pressure < minPressureReading.pressure) {
                    minPressureReading = reading;
                    System.out.println("Smallest pressure element present in given array: " + minPressureReading.pressure);
                }
            }
            return minPressureReading.pressure;
        }
        return 0.0;
    }

    public static double getMaxTemp(List<Reading> readings) {
        Reading maxTempReading = null;
        if (readings.size() > 0) {
            maxTempReading = readings.get(0);
            for (Reading reading : readings) {
                if (reading.temp > maxTempReading.temp) {
                    maxTempReading = reading;
                    System.out.println("Highest temperature element present in given array: " + maxTempReading.temp);
                }
            }
            return maxTempReading.temp;
        }
        return 0.0;
    }

    public static double getMaxWindSpeed(List<Reading> readings) {
        Reading maxWindSpeedReading = null;
        if (readings.size() > 0) {
            maxWindSpeedReading = readings.get(0);
            for (Reading reading : readings) {
                if (reading.windspeed > maxWindSpeedReading.windspeed) {
                    maxWindSpeedReading = reading;
                    System.out.println("Highest wind speed element present in given array: " + maxWindSpeedReading.windspeed);
                }
            }
            return maxWindSpeedReading.windspeed;
        }
        return 0.0;
    }

    public static double getMaxPressure(List<Reading> readings) {
        Reading maxPressureReading = null;
        if (readings.size() > 0) {
            maxPressureReading = readings.get(0);
            for (Reading reading : readings) {
                if (reading.pressure > maxPressureReading.pressure) {
                    maxPressureReading = reading;
                    System.out.println("Highest pressure element present in given array: " + maxPressureReading.pressure);
                }
            }
            return maxPressureReading.pressure;
        }
        return 0.0;
    }
}