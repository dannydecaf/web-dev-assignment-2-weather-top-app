package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Station extends Model
{
    public String name;
    @OneToMany(cascade = CascadeType.ALL)
    public List<Reading> readings = new ArrayList<Reading>();

    public Station(String name) {
        this.name = name;
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