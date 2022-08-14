package controllers;

import models.Station;
import models.Reading;
import play.Logger;
import play.mvc.Controller;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends Controller {
    public static void index() {
        Logger.info("Rendering Dashboard");

        List<Station> stations = Station.findAll();


        render("dashboard.html", stations);
    }

    public static void addStation(String name) {
        Station station = new Station(name);
        Logger.info("Adding a new station called " + name);
        station.save();
        redirect("/dashboard");
    }

    public static void deleteStation(Long id) {
        Station station = Station.findById(id);
        Logger.info("Removing" + station.name);
        station.delete();
        redirect("/dashboard");
    }
}
