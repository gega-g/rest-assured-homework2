package ge.tbc.tbcitacademy.steps;

import ge.tbc.tbcitacademy.constants.MARSHALLINGCONSTANTS;
import ge.tbc.tbcitacademy.util.Marshall;
import ge.tbc.tbcitacademy.util.SoapServiceSender;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.tempuri.ArrayOfFavoriteColorsItemString;
import org.tempuri.FindPerson;
import org.tempuri.FindPersonResponse;
import org.tempuri.ObjectFactory;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SomeServiceSteps {
    Response response;
    String body = null;
    ObjectFactory objectFactory = new ObjectFactory();

    @Step("Create request body of `FindPerson`")
    public SomeServiceSteps findPerson(){
        FindPerson findPerson = objectFactory.createFindPerson();
        findPerson.setId("10");

        body = Marshall.marshallSoapRequest(findPerson);
        response = SoapServiceSender.send(MARSHALLINGCONSTANTS.SOMESERVICEURL,
                "http://tempuri.org/SOAP.Demo.FindPerson",
                body);
        assertThat(response.statusCode(), equalTo(200));
        return this;
    }

    @Step("Validate the presence (not null) of the FindPersonResult `element`")
    public SomeServiceSteps validatePresenceOfPerson(){
        FindPersonResponse findPersonResponse = Marshall.unmarshallResponse(response.asString(), FindPersonResponse.class);
        assertThat(findPersonResponse.getFindPersonResult(), notNullValue());
        System.out.println(findPersonResponse);
        return this;
    }

    @Step("Validate the person's `name`")
    public SomeServiceSteps validateName(){
        FindPersonResponse findPersonResponse = Marshall.unmarshallResponse(response.asString(), FindPersonResponse.class);
        assertThat(findPersonResponse.getFindPersonResult().getName(), equalTo(MARSHALLINGCONSTANTS.XAVIER));
        return this;
    }

    @Step("Validate the `SSN` value")
    public SomeServiceSteps validateSSN(){
        FindPersonResponse findPersonResponse = Marshall.unmarshallResponse(response.asString(), FindPersonResponse.class);
        assertThat(findPersonResponse.getFindPersonResult().getSSN(), equalTo(MARSHALLINGCONSTANTS.SSN));
        return this;
    }

    @Step("Validate the number of characters in the `SSN`")
    public SomeServiceSteps validateSSNLength(){
        FindPersonResponse findPersonResponse = Marshall.unmarshallResponse(response.asString(), FindPersonResponse.class);
        assertThat(findPersonResponse.getFindPersonResult().getSSN().getBytes().length, equalTo(11));
        return this;
    }

    @Step("Check if the SSN follows the pattern `NNN-NN-NNNN` (with digits only)")
    public SomeServiceSteps validateSSNPattern(){
        FindPersonResponse findPersonResponse = Marshall.unmarshallResponse(response.asString(), FindPersonResponse.class);
        assertThat(findPersonResponse.getFindPersonResult().getSSN(), matchesRegex("^\\d{3}-\\d{2}-\\d{4}$"));
        return this;
    }

//    ასერშენს აფეილებს არასწორად არუნდა აფეილებდეს ;დ
    @Step("Validate the `date of birth (DOB)`")
    public SomeServiceSteps validateDOB(){
        FindPersonResponse findPersonResponse = Marshall.unmarshallResponse(response.asString(), FindPersonResponse.class);
        assertThat(findPersonResponse.getFindPersonResult().getDOB(), equalTo(2013-12-12));
        return this;
    }

    @Step("Validate the presence of the `favorite colors`")
    public SomeServiceSteps validatePresenceOfColors(){
        FindPersonResponse findPersonResponse = Marshall.unmarshallResponse(response.asString(), FindPersonResponse.class);
        assertThat(findPersonResponse.getFindPersonResult().getFavoriteColors(), notNullValue());
        return this;
    }

    @Step("Validate the person's `last name`")
    public SomeServiceSteps validateLastname(){
        FindPersonResponse findPersonResponse = Marshall.unmarshallResponse(response.asString(), FindPersonResponse.class);
        assertThat(findPersonResponse.getFindPersonResult().getName(), containsString(MARSHALLINGCONSTANTS.JOE));
        return this;
    }

//    რაღაც პრობლემააქ
    @Step("Check if the person has `Red` as one of favorite colors and its index is 2")
    public SomeServiceSteps checkIfRedIsPresent(){
        ArrayOfFavoriteColorsItemString arrayOfFavoriteColorsItemString = Marshall.unmarshallResponse(response.asString(), ArrayOfFavoriteColorsItemString.class);
        List<String> favoriteColors = arrayOfFavoriteColorsItemString.getFavoriteColorsItem();
        boolean isRedIndex2 = favoriteColors.stream().skip(2).findFirst().map(color -> color.equals("Red")).orElse(false);
        assertThat(isRedIndex2, is(true));
        return this;
    }

    @Step("Validate that the `office` zip code is not the same as the `home` zip code")
    public SomeServiceSteps validateIndividualZipcodes(){
        FindPersonResponse findPersonResponse = Marshall.unmarshallResponse(response.asString(), FindPersonResponse.class);
        List<String> zipCodes = new ArrayList<>();
        zipCodes.add(findPersonResponse.getFindPersonResult().getHome().getZip());
        zipCodes.add(findPersonResponse.getFindPersonResult().getOffice().getZip());
        boolean zipCodesDifferent = zipCodes.stream().distinct().count() == zipCodes.size();
        assertThat(zipCodesDifferent, is(true));
        return this;
    }
}
