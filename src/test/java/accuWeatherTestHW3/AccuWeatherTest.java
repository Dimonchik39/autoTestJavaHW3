package accuWeatherTestHW3;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.example.location.Location;

import java.util.List;

public class AccuWeatherTest extends AccuweatherAbstractTest {

    @Test
    void tTopCitiesListStatucCodeTest() {
        given()
                .when()
                .get(getBaseUrl()+"/locations/v1/topcities/50?" +
                        "apikey=" +getApiKey())
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l))
                .body("[0].LocalizedName", equalTo("Dhaka"));
    }

    @Test
    void t1HourOfHourlyForecastsTest() {
        given()
                .when()
                .get(getBaseUrl()+"/forecasts/v1/hourly/1hour/155?" +
                        "apikey=" +getApiKey())
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l))
                .body("[0].Link", equalTo("http://www.accuweather.com/en/fr/barfleur/155/hourly-weather-forecast/155?day=1&hbhhour=15&lang=en-us"));
    }

//    Этот тест не идет. Ошибка: "Message": "Api Authorization failed"
//    @Test
//    void t72HoursOfHourlyForecastsTest() {
//        given()
//                .when()
//                .get(getBaseUrl()+"/forecasts/v1/hourly/72hour/155?" +
//                        "apikey=" +getApiKey())
//                .then()
//                .statusCode(200)
//                .time(Matchers.lessThan(2000l))
//                .body("WeatherIcon", equalTo(7));
//    }

    @Test
    void t1DayOfDailyForecastsTest() {
        given()
                .when()
                .get(getBaseUrl()+"/forecasts/v1/daily/1day/155?" +
                        "apikey=" +getApiKey())
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l))
                .body("Headline.Link", equalTo("http://www.accuweather.com/en/fr/barfleur/155/daily-weather-forecast/155?lang=en-us"));
    }

    @Test
    void t10DaysOfDailyForecastsTest() {
        given()
                .when()
                .get(getBaseUrl()+"/forecasts/v1/daily/10day/155?" +
                        "apikey=" +getApiKey())
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l));
    }

    @Test
    void t15DaysOfDailyForecastsTest() {
        given()
                .when()
                .get(getBaseUrl()+"/forecasts/v1/daily/15day/155?" +
                        "apikey=" +getApiKey())
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l));
    }

    @Test
    void tCitySearchKaliningradTest() {
        List<Location> response = given()
                .queryParam("apikey", getApiKey())
                .queryParam("q", "Kaliningrad")
                .when()
                .get(getBaseUrl()+"/locations/v1/cities/search")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(4000l))
                .extract()
                .body().jsonPath().getList(".", Location.class);

        assertEquals(1,response.size());
        assertEquals("Kaliningrad", response.get(0).getEnglishName());
    }

    @Test
    void tRegionListTest() {
        given()
                .when()
                .get(getBaseUrl() + "/locations/v1/regions?" +
                        "apikey=" + getApiKey())
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l))
                .body("[0].ID", equalTo("AFR"));
    }

    @Test
    void tAutocompleteSearchTest() {
        List<Location> response = given()
                .queryParam("apikey", getApiKey())
                .queryParam("q", "Kalinin")
                .when()
                .get(getBaseUrl()+"/locations/v1/cities/autocomplete")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(4000l))
                .extract()
                .body().jsonPath().getList(".", Location.class);

        assertEquals("Kaliningrad", response.get(0).getLocalizedName());
    }

    @Test
    void tPostalCodeSearchTest() {
        List<Location> response = given()
                .queryParam("apikey", getApiKey())
                .queryParam("q", 236005)
                .when()
                .get(getBaseUrl()+"/locations/v1/postalcodes/search")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(4000l))
                .extract()
                .body().jsonPath().getList(".", Location.class);

        assertEquals("Martos", response.get(0).getLocalizedName());
        // первый раз написал Matros и долго не мог понять, почему тест не идет :)
    }
}
