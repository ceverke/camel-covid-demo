package de.adesso.model;


import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator = ",", skipField = true, skipFirstLine = true)
public class CovidDailyReport {

    /*
        Heeader of JHU CSV-File::
        FIPS,Admin2,Province_State,Country_Region,Last_Update,Lat,Long_,Confirmed,Deaths,Recovered,Active,Combined_Key,Incident_Rate,Case_Fatality_Ratio
     */

    private String date;

    @DataField(pos = 3)
    private String province;

    @DataField(pos = 4)
    private String country;

    @DataField(pos = 5)
    private String lastUpdate;

    @DataField(pos = 8)
    private Integer confirmedCases;

    @DataField(pos = 9)
    private Integer confirmedDeaths;

    @DataField(pos = 10)
    private Integer recoveredPersons;

    @DataField(pos = 11)
    private Integer activeCases;

    @Override
    public String toString() {
        return "\n" + country +  (!province.equals("")?" (" + province + ")":"") + " - data from date: " + date +  "\n"
                + "confirmed cases: " + confirmedCases + "\n"
                + "deaths: " + confirmedDeaths + "\n"
                + "active infections: " + activeCases + "\n"
                + "recovered " + recoveredPersons + "\n"
                + "(last update: " + lastUpdate + ")\n\n";
    }

    public boolean matchesCountry(String country) {
        return this.country.equals(country);
    }

    public boolean matchesProvince(String province) {
        return this.province.equals(province);
    }

    public String getIdentifier() {
        return "JHU-report_" + country + "_" + province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setConfirmedCases(Integer confirmedCases) {
        this.confirmedCases = confirmedCases;
    }

    public void setConfirmedDeaths(Integer confirmedDeaths) {
        this.confirmedDeaths = confirmedDeaths;
    }

    public void setRecoveredPersons(Integer recoveredPersons) {
        this.recoveredPersons = recoveredPersons;
    }

    public void setActiveCases(Integer activeCases) {
        this.activeCases = activeCases;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
