package calcApiTests;

import static io.restassured.RestAssured.*;
import static io.restassured.parsing.Parser.*;
import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

@RunWith(DataProviderRunner.class)
public class AppTest {

    @DataProvider
    public static Object[][] resultsOperatorsOperands() {
        String errorText = "unsupported operation";
        return new Object[][] {
            // @formatter:off
            // Results are passed first

            // Positive
            { "1", "+1" },
            { "4", "+2", "+2" },
            { "1", "+0", "+1" },
            { "-1", "-1", "+0" },
            { "0", "+0", "+0" },
            { "0", "-0", "+0" },
            { "0", "+0", "-0" },
            { "0", "-0", "-0" },
            { "2147483646", "+2000000000", "+147000000", "+483646" },
            { "2147483647", "+483647", "+2000000000", "+147000000" },
            { "2147483647", "+483647", "+2000000000", "+147000000" }, // the same prev call
            { "-3", "-3" },
            { "-8", "-4", "-4" },
            { "-2147483647", "-2000000000", "-483647", "-147000000" },
            { "-2147483648", "-147000000", "-483648", "-2000000000" },
            { "-2147483648", "-147000000", "-483648", "-2000000000" }, // the same prev call
            // { "*5", "*5", }, // [BUG] Result is 0
            // { "*-5", "*-5", }, // [BUG] Result is 0
            { "36", "+6", "*6" },
            { "0", "+3", "*0" },
            { "0", "-7", "*0" },
            { "2147483628", "+6", "*6", "*59652323" },
            { "-2147483628", "-6", "*59652323", "*6" },
            { "2147483628", "-59652323", "*-6", "*6" },
            { "9", "+17", "-8" },
            { "-9998", "-9999", "+1" },
            { "0", "+9999", "*-0" },
            { "65", "-13", "*-5" },
            { "-160", "-8", "*20" },
            { "-400", "+4", "*-100" },
            // { "-56", "+4", "-3", "*20" }, // [BUG] Result is 20 - (and similar) not in BODMAS

            //Negative
            { errorText, null, "+1" },
            { errorText, "-2", null },
            { errorText, "-3", null, "*4" },
            { errorText, null, "-5", null, "*6" },
            { errorText, "7", null, "*-8", null },
            // { errorText, "+2147483647", "+1" }, // [BUG] Result is -2147483648 - buffer overflow
            // { errorText, "+9999999999", "+1" }, // [BUG] Result is 1410065408
            // { errorText, "-2147483648", "-1" }, // [BUG] Result is 2147483647 - buffer underflow
            // { errorText, "-9999999999", "-1" }, // [BUG] Result is -1410065408
            // { errorText, "-1.0", "*47" }, // [BUG] HTTP Status 404 – Not Found instead of errorText
            // { errorText, "-0.01", "+49" }, // [BUG] HTTP Status 404 – Not Found instead of errorText
            { errorText, "$1", "+2" },
            { errorText, "-3", "&4" },
            { errorText, "*5", ",6" },
            { errorText, "-7", "/+8" },
            { errorText, "*9", ":*10" },
            { errorText, "=-*11", "+12" },
            { errorText, "-12", ";+11" },
            // { errorText, "+13", "?14" }, // [BUG] "?" Result is 13
            { errorText, "-15", "@+16" },
            // { errorText, "-26", "#-27" }, // [BUG] Result is -26
            { errorText, "~+17", "*18" },
            { errorText, "/19", "+20" },
            // { errorText, "-21", "*%22" }, // [BUG] HTTP Status 400 – Bad Request instead of errorText
        };
    }

    @BeforeClass
    public static void setup() {
        baseURI = "http://localhost:8080/";
        basePath = "calc/";
        registerParser("text/plain", HTML);
    }

    @Test
    @UseDataProvider("resultsOperatorsOperands")
    public void appTestMain(String expectedResult, String... args) {

        String completeOperatorsOperandsPath = "";
        for (String arg : args) {
            completeOperatorsOperandsPath += arg + "/";
        }

        String actualResult = given().urlEncodingEnabled(false)
        .when()
        .get(completeOperatorsOperandsPath)
        .then()
        .extract().response().asString();

        assertEquals(expectedResult, actualResult);

    }

}
