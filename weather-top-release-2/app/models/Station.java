package models;

import play.db.jpa.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Station extends Model {
    public String name;
    public double latitude;
    public double longitude;
    @OneToMany(cascade = CascadeType.ALL)
    public List<Reading> readings = new ArrayList<>();

    public Station(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Station addReading(int code, double temp, double windspeed, int windDirection, int pressure) {
        Reading reading = new Reading(code, temp, windspeed, windDirection, pressure).save();
        this.readings.add(reading);
        this.save();
        return this;
    }

    public Station addReading(Reading reading) {
        return this.addReading(reading.code, reading.temp, reading.windspeed, reading.windDirection, reading.pressure);
    }
}