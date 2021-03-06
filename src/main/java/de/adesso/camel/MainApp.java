package de.adesso.camel;

import org.apache.camel.main.Main;

/**
 * A Camel Application
 */
public class MainApp {

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {
        Main main = new Main();
        main.setPropertyPlaceholderLocations("de/adesso/camel/covid_dev.properties");
        main.addRouteBuilder(new CovidDailyReportRoute());
        main.run(args);
    }

}

