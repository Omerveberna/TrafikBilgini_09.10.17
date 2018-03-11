package com.example.omerveberna.trafikbilgini;

/**
 * Created by Omerveberna on 26.09.2017.
 */

public class Application extends android.app.Application {
    private Prefs prefs;
    private static Application app;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        prefs = new Prefs(this);
    }
    public static Application getApp() {

        return app;
    }
    public Prefs getPrefs() {

        return prefs;
    }
    public void setPrefs(Prefs prefs) {
        this.prefs = prefs;
    }

}
