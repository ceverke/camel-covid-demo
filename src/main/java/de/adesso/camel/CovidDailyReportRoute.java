package de.adesso.camel;

import de.adesso.model.CovidDailyReport;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangeProperty;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http4.HttpMethods;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.http.common.HttpOperationFailedException;

public class CovidDailyReportRoute extends RouteBuilder {

    private BindyCsvDataFormat bindyCsv = new BindyCsvDataFormat(CovidDailyReport.class);

    @Override
    public void configure()  {

        onException(HttpOperationFailedException.class)
                .handled(true)
                .maximumRedeliveries(3)
                .redeliveryDelay("{{covid.jhu.redelivery.delay}}")
                .log(LoggingLevel.WARN, "Http Request failed permanently");

        from("quartz2://covidTracker?cron={{covid.jhu.cron}}")
                .log("Catch data from John Hopkins University")
                .setProperty("Date", simple("${date:now-24h:MM-dd-yyyy}"))
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.GET))
                .toD("https4://{{covid.jhu.url}}${exchangeProperty.Date}.csv")
                .unmarshal(bindyCsv)
                .split(body())
                    .filter().method(CovidDailyReportRoute.class, "isRecordForGermany")
                    .bean(CovidDailyReportRoute.class, "setDate")
                    .recipientList(simple("direct:writeFile,direct:logBody,direct:pushTelegramMessage"))
                .end()
                .log(LoggingLevel.INFO, "Operation succeeded");

        from("direct:writeFile").routeId("writeFileRoute")
                .setHeader("fileName", simple("${body.getIdentifier()}"))
                .convertBodyTo(String.class)
                .log(LoggingLevel.INFO, "Writing file ${header.fileName}.txt")
                .toD("file:work/covid-19-reports/${date:now-24h:yyyy-MM-dd}?fileName=${header.fileName}.txt");

        from("direct:logBody").routeId("logBodyRoute")
                .log(LoggingLevel.INFO, "${body}");

        from("direct:pushTelegramMessage").routeId("pushTelegramMessageRoute")
                .log(LoggingLevel.INFO, "Sending telegram message")
                .convertBodyTo(String.class)
                .to("telegram:bots/{{telegram.authid}}?chatId={{telegram.chatid}}");
    }

    public boolean isRecordForGermany(@Body CovidDailyReport record) {
        return record.matchesCountry("Germany") && !record.matchesProvince("Unknown");
    }

    public void setDate(@ExchangeProperty("Date") String date, @Body CovidDailyReport record) throws Exception {
        record.setDate(date);
    }
}
