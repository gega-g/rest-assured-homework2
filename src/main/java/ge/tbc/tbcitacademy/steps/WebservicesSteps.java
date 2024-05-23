package ge.tbc.tbcitacademy.steps;

import ge.tbc.tbcitacademy.constants.WEBSERVICECONSTANTS;
import ge.tbc.tbcitacademy.util.XmlPathFromWebservices;
import io.qameta.allure.Step;
import io.restassured.path.xml.XmlPath;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class WebservicesSteps {
    XmlPathFromWebservices xmlPath = new XmlPathFromWebservices();
    public XmlPath xmlpath;

    @Step("Validate count of all `sName` node ")
    public WebservicesSteps countValidation() {
        xmlpath = xmlPath.getXmlPathFromService().setRoot("ArrayOftContinent.tContinent");
        assertThat(xmlpath.getList("sName").size(), equalTo(6));
        return this;
    }

    @Step("Validate list of all `sName` node's value")
    public WebservicesSteps nodesValueValidation() {
        xmlpath = xmlPath.getXmlPathFromService().setRoot("ArrayOftContinent.tContinent");
        assertThat(xmlpath.getList("sName"),
                contains(WEBSERVICECONSTANTS.AFRICA,
                        WEBSERVICECONSTANTS.ANTARCTICA,
                        WEBSERVICECONSTANTS.ASIA,
                        WEBSERVICECONSTANTS.EUROPE,
                        WEBSERVICECONSTANTS.OCEANANIA,
                        WEBSERVICECONSTANTS.THEAMERICAS));
        return this;
    }

    @Step("Validate `sName` node result with value of `sCode` equals to `AN`")
    public WebservicesSteps equalsToAN() {
        xmlpath = xmlPath.getXmlPathFromService().setRoot("ArrayOftContinent.tContinent");
        String sNameValue = xmlpath.getString("find { it.sCode == 'AN' }.sName");
        assertThat(WEBSERVICECONSTANTS.ANTARCTICA, equalTo(sNameValue));
        return this;
    }

    @Step("Validate the last `tContinent` node's `sName` value")
    public WebservicesSteps lastContinentValue() {
        xmlpath = xmlPath.getXmlPathFromService().setRoot("ArrayOftContinent.tContinent");
        assertThat(xmlpath.getString("sName.last()"), equalTo(WEBSERVICECONSTANTS.THEAMERICAS));
        return this;
    }

    @Step("Verify that each `sName` is unique.")
    public WebservicesSteps validateUnique() {
        xmlpath = xmlPath.getXmlPathFromService().setRoot("ArrayOftContinent.tContinent");
        Set<String> sNames = new HashSet<>(xmlpath.getList("sName"));
        assertThat(sNames.size(), equalTo(xmlpath.getList("sName").size()));
        return this;
    }

    @Step("Validate the presence and correctness of both `sCode` and `sName` for each `tContinent`.")
    public WebservicesSteps validatePresence() {
        xmlpath = xmlPath.getXmlPathFromService().setRoot("ArrayOftContinent.tContinent");
        for (int i = 0; i < xmlpath.getList("sName").size(); i++) {
            String sCode = xmlpath.getString("sCode[" + i + "]");
            String sName = xmlpath.getString("sName[" + i + "]");
            assertThat(sCode, is(notNullValue()));
            assertThat(sName, is(notNullValue()));
        }
        return this;
    }

    @Step("Check that `sCode` values follow a specific pattern (two uppercase letters).")
    public WebservicesSteps validatePattern() {
        xmlpath = xmlPath.getXmlPathFromService().setRoot("ArrayOftContinent.tContinent");
        for (int i = 0; i < xmlpath.getList("sCode").size(); i++) {
            String sName = xmlpath.getString("sCode[" + i + "]");
            assertThat(sName, matchesRegex("^[A-Z]{2}$"));
        }
        return this;
    }

    @Step("Ensure that the order of `tContinent` nodes corresponds to a specific sequence (alphabetical order by `sName`).")
    public WebservicesSteps validateOrder() {
        xmlpath = xmlPath.getXmlPathFromService().setRoot("ArrayOftContinent.tContinent");
        List<String> sNames = xmlpath.getList("sName");
        List<String> sortedNames = new ArrayList<>(sNames);
        sortedNames.sort(String::compareTo);
        assertThat(sNames, equalTo(sortedNames));
        return this;
    }

    @Step("Validate the presence of all six continents.")
    public WebservicesSteps validatePresenceOfAllContinents() {
        xmlpath = xmlPath.getXmlPathFromService().setRoot("ArrayOftContinent");
        List<String> continents = xmlpath.getList("tContinent");
        assertThat(continents.size(), equalTo(6));
        for (int i = 0; i < continents.size(); i++) {
            assertThat(xmlpath.getString("tContinent[" + i + "]"), notNullValue());
        }
        return this;
    }

    @Step("Validate that no `sName` node contains numeric characters")
    public WebservicesSteps validateNoNumbersInsCode() {
        xmlpath = xmlPath.getXmlPathFromService().setRoot("ArrayOftContinent.tContinent");
        for (int i = 0; i < xmlpath.getList("sCode").size(); i++) {
            String sName = xmlpath.getString("sCode[" + i + "]");
            assertThat(sName, matchesRegex("^[^0-9]*$"));
        }
        return this;
    }

    @Step("find `sCode` that starting with `O` and ensure that is `Ocenania`")
    public WebservicesSteps findContainingO() {
        xmlpath = xmlPath.getXmlPathFromService().setRoot("ArrayOftContinent.tContinent");
        List<String> sCodes = xmlpath.getList("sCode");
        String oc = null;
        for (String code : sCodes) {
            if (code.startsWith("O")) {
                oc = code;
                break;
            }
        }
        List<String> sNames = xmlpath.getList("sName");
        for (String name : sNames) {
            if (name.contains(oc)) {
                assertThat(name, equalTo(WEBSERVICECONSTANTS.OCEANANIA));
                break;
            }
        }
        return this;
    }

    @Step("findAll `sName` that stars with `A` and ends with `ca`")
    public WebservicesSteps startsWithAendsWithCA() {
        xmlpath = xmlPath.getXmlPathFromService().setRoot("ArrayOftContinent.tContinent");
        List<String> sNames = xmlpath.getList("sName");
        List<String> list = new ArrayList<>();
        for (String name : sNames) {
            if (name.startsWith("A") && name.endsWith("ca")) {
                list.add(name);
            }
        }
        System.out.println(list);
        return this;
    }

    @Step("No Numeric Characters in `sName`")
    public WebservicesSteps validateNoNumbersInsName() {
        xmlpath = xmlPath.getXmlPathFromService().setRoot("ArrayOftContinent.tContinent");
        for (int i = 0; i < xmlpath.getList("sName").size(); i++) {
            String sName = xmlpath.getString("sName["+i+"]");
            assertThat(sName, matchesRegex("^[^0-9]*$"));
        }
        return this;
    }
}
