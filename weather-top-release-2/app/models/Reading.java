package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import java.text.DecimalFormat;

@Entity
public class Reading extends Model {
    public int code;
    public double temp;
    public double tempFahr;
    public double windspeed;
    public int windDirection;
    public int pressure;
    public String weather;
    public int beufortSpeed;
    public double windChill;
    public String windDirectionText;
    public String weatherIcon;
    public double minTempReading;
    public double maxTempReading;
    public double minWindSpeedReading;
    public double maxWindSpeedReading;
    public double minPressureReading;
    public double maxPressureReading;

    public Reading(int code, double temp, double windspeed, int windDirection, int pressure) {
        this.code = code;
        this.temp = temp;
        this.windspeed = windspeed;
        this.windDirection = windDirection;
        this.pressure = pressure;
        this.tempFahr = setFahrenheit(temp);
        this.weather = setWeatherReading(code);
        this.beufortSpeed = setWindSpeedBeufort(windspeed);
        this.windChill = setWindChill();
        this.windDirectionText = setWindDirectionText();
        this.weatherIcon = setWeatherIcon(code);
    }

    public String setWindDirectionText() {
        String windDirectionText = "North";

        if (this.windDirection > 348.75 && this.windDirection <= 11.25) {
            windDirectionText = "North";
        } else if (this.windDirection > 11.25 && this.windDirection <= 33.75) {
            windDirectionText = "North North East";
        } else if (this.windDirection > 33.75 && this.windDirection <= 56.25) {
            windDirectionText = "North East";
        } else if (this.windDirection > 56.25 && this.windDirection <= 78.75) {
            windDirectionText = "East North East";
        } else if (this.windDirection > 78.75 && this.windDirection <= 101.25) {
            windDirectionText = "East";
        } else if (this.windDirection > 101.25 && this.windDirection <= 123.75) {
            windDirectionText = "East South East";
        } else if (this.windDirection > 123.75 && this.windDirection <= 146.25) {
            windDirectionText = "South East";
        } else if (this.windDirection > 146.25 && this.windDirection <= 168.75) {
            windDirectionText = "South South East";
        } else if (this.windDirection > 168.75 && this.windDirection <= 191.25) {
            windDirectionText = "South";
        } else if (this.windDirection > 191.25 && this.windDirection <= 213.75) {
            windDirectionText = "South South West";
        } else if (this.windDirection > 213.75 && this.windDirection >= 236.25) {
            windDirectionText = "South West";
        } else if (this.windDirection > 236.25 && this.windDirection <= 258.75) {
            windDirectionText = "West South West";
        } else if (this.windDirection > 258.75 && this.windDirection <= 281.25) {
            windDirectionText = "West";
        } else if (this.windDirection > 281.25 && this.windDirection <= 303.75) {
            windDirectionText = "West North West";
        } else if (this.windDirection > 303.75 && this.windDirection <= 326.25) {
            windDirectionText = "North West";
        } else if (this.windDirection > 326.25 && this.windDirection <= 348.75) {
            windDirectionText = "North North West";
        }

        this.windDirectionText = windDirectionText;
        return windDirectionText;
    }

    public double setWindChill() {
        double windChill = 13.12 + (0.6215 * this.temp) - 11.37 * (Math.pow(this.windspeed, 0.16)) + 0.3965 * this.temp * Math.pow(this.windspeed, 0.16);
        DecimalFormat df = new DecimalFormat("#.##");
        windChill = Double.valueOf(df.format(windChill));
        this.windChill = windChill;
        return windChill;
    }

    public double setFahrenheit(double cTemp) {
        double tempFahr = (cTemp * (9 / 5)) + 32;
        this.tempFahr = tempFahr;
        return tempFahr;
    }

    public String setWeatherReading(int code) {
        String weather = "";
        switch (code) {
            case 100:
                weather = "Clear";
                weatherIcon = "sun icon";
                break;
            case 200:
                weather = "Partial Clouds";
                weatherIcon = "cloud sun icon";
                break;
            case 300:
                weather = "Cloudy";
                weatherIcon = "cloud icon";
                break;
            case 400:
                weather = "Light Showers";
                weatherIcon = "cloud sun rain icon";
                break;
            case 500:
                weather = "Heavy Showers";
                weatherIcon = "cloud showers heavy icon";
                break;
            case 600:
                weather = "Rain";
                weatherIcon = "cloud rain icon";
                break;
            case 700:
                weather = "Snow";
                weatherIcon = "snowflake outline icon";
                break;
            case 800:
                weather = "Thunder";
                weatherIcon = "bolt icon";
                break;
            default:
                weather = "Unknown code";
                weatherIcon = "question icon";
                break;
        }
        this.weather = weather;
        return weather;
    }

    public String setWeatherIcon(int code) {
        String weatherIcon = "";
        switch (code) {
            case 100:
                weatherIcon = "sun icon";
                break;
            case 200:
                weatherIcon = "cloud sun icon";
                break;
            case 300:
                weatherIcon = "cloud icon";
                break;
            case 400:
                weatherIcon = "cloud sun rain icon";
                break;
            case 500:
                weatherIcon = "cloud showers heavy icon";
                break;
            case 600:
                weatherIcon = "cloud rain icon";
                break;
            case 700:
                weatherIcon = "snowflake outline icon";
                break;
            case 800:
                weatherIcon = "bolt icon";
                break;
            default:
                weatherIcon = "question icon";
                break;
        }
        this.weatherIcon = weatherIcon;
        return weatherIcon;
    }

    public int setWindSpeedBeufort(double speed) {
        int beufortSpeed = 0;
        if (speed >= 1.0 && speed <= 5.0) {
            beufortSpeed = 1;
        } else if (speed >= 6.0 && speed <= 11.0) {
            beufortSpeed = 2;
        } else if (speed >= 12.0 && speed <= 19.0) {
            beufortSpeed = 3;
        } else if (speed >= 20.0 && speed <= 28.0) {
            beufortSpeed = 4;
        } else if (speed >= 29.0 && speed <= 38.0) {
            beufortSpeed = 5;
        } else if (speed >= 39.0 && speed <= 49.0) {
            beufortSpeed = 6;
        } else if (speed >= 50.0 && speed <= 61.0) {
            beufortSpeed = 7;
        } else if (speed >= 62.0 && speed <= 74.0) {
            beufortSpeed = 8;
        } else if (speed >= 75.00 && speed <= 88.0) {
            beufortSpeed = 9;
        } else if (speed >= 89.0 && speed <= 102.0) {
            beufortSpeed = 10;
        } else if (speed >= 103.0 && speed <= 117.0) {
            beufortSpeed = 11;
        }
        this.beufortSpeed = beufortSpeed;
        return beufortSpeed;
    }

    public void setMinTempReading(double minTempReading) {
        this.minTempReading = minTempReading;
    }

    public void setMaxTempReading(double maxTempReading) {
        this.maxTempReading = maxTempReading;
    }

    public void setMinWindSpeedReading(double minWindSpeedReading) {
        this.minWindSpeedReading = minWindSpeedReading;
    }

    public void setMaxWindSpeedReading(double maxWindSpeedReading) {
        this.maxWindSpeedReading = maxWindSpeedReading;
    }

    public void setMinPressureReading(double minPressureReading) {
        this.minPressureReading = minPressureReading;
    }

    public void setMaxPressureReading(double maxPressureReading) {
        this.maxPressureReading = maxPressureReading;
    }
}