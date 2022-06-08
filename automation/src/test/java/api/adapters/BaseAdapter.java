package api.adapters;


import api.models.api.cv_controller.cv.CV;
import api.models.api.cv_controller.cv.Credentials;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.codehaus.groovy.ast.GenericsType;
import org.testng.Assert;
import utils.PropertyManager;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BaseAdapter {
    PropertyManager propertyManager;
    private String URL;
    public static final String TOKEN = BaseAdapter.getBearerToken();
    Gson gson = new Gson();

    public BaseAdapter() {
        propertyManager = new PropertyManager();
        URL = propertyManager.getProperty("url");
    }

    /**
     * @param parameters
     * @param uri
     * @param statusCode
     * @return
     */
    public Response get(Map parameters, String uri, int statusCode) {
        return given().
                header("Authorization", "Bearer " + TOKEN).
                header("Content-type", "application/json").
                params(parameters).
                log().all().
                when().
                get(URL + uri).
                then().
                log().all().
                statusCode(statusCode).
                contentType(ContentType.JSON).
                extract().response();
    }

    public <T> void get(T body, String uri, int statusCode) {
        return given().
                header("Authorization", "Bearer " + TOKEN).
                header("Content-type", "application/json").
                pathParam(body)
                log().all().
                when().
                get(URL + uri).
                then().
                log().all().
                statusCode(statusCode).
                contentType(ContentType.JSON).
                extract().response();
    }

    public Response get(String payload, String uri, int statusCode) {
        return given().
                header("Authorization", "Bearer " + TOKEN).
                header("Content-type", "application/json").
                body(payload).
                log().all().
                when().
                get(URL + uri).
                then().
                log().all().
                //statusCode(statusCode).
                        contentType(ContentType.JSON).
                        extract().response();
    }

    public Response post(String body, String uri, int statusCode) {
        return given().
                header("Authorization", "Bearer " + TOKEN).
                header("Content-type", "application/json").
                body(body).
                when().
                post(URL + uri).
                then().
                log().all().
                statusCode(statusCode).
                extract().response();
    }

    public Response put(Map parameters, String uri, int statusCode) {
        return given().
                queryParams(parameters).
                header("Authorization", "Bearer " + TOKEN).
                header("Content-type", "application/json").
                log().all().
                when().
                put(URL + uri).
                then().
                log().all().
                statusCode(statusCode).
                extract().response();
    }

    public void checkJsonSchema(Response response, String pathToSchema) {
        response.then().assertThat().body(matchesJsonSchemaInClasspath(pathToSchema));
    }

    public void checkResponseTime(Response response, long expectedResponseTime) {
        response.then().time(lessThan(expectedResponseTime));
    }

    public void checkIds(int actualId, int expectedId) {
        assertThat(actualId, equalTo(expectedId));
    }

    public void checkIsCvApproved(Response response, boolean isApproved) {
        CV dataResponse = gson.fromJson(response.asString(), CV.class);
        Assert.assertEquals(dataResponse.isApproved(), isApproved, "Approved status is not correct");
    }

    public void checkContentLengthGreaterThen(Response response, int greaterThen) {
        int contentLength = Integer.parseInt(response.getHeader("content-length"));
        assertThat(contentLength, greaterThan(greaterThen));
    }

    public static String getBearerToken() {
        Gson gson = new Gson();
        Credentials credentials = Credentials.builder()
                .password(new PropertyManager().getProperty("password"))
                .username(new PropertyManager().getProperty("username"))
                .build();
        String token = given().
                header("Content-Type", "application/json").
                body(gson.toJson(credentials)).
                when().
                post("http://10.86.1.74:8081/hr/api/v1/authentication/login").
                then().
                statusCode(200)
                .extract()
                .path("token");
        return token;
    }
}
