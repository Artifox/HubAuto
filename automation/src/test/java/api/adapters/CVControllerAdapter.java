package api.adapters;

import api.models.api.cv_controller.cv.CV;
import api.models.api.cv_controller.payloads.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.PropertyManager;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static io.restassured.config.LogConfig.logConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CVControllerAdapter extends BaseAdapter {

    private RequestSpecification requestSpecification;
    PropertyManager propertyManager = new PropertyManager();
    public final String URL = propertyManager.getProperty("url");
    public static final String URI = "/cv";

    public CVControllerAdapter() {
        requestSpecification = new RequestSpecBuilder().
                addHeader("Authorization", "Bearer " + TOKEN).
                setContentType(ContentType.JSON).
                addFilter(new AllureRestAssured()).
                setConfig(config().logConfig(logConfig().blacklistHeader("Authorization"))).
                build();
    }

    //TODO: Method should return a value
    public Response getLastApprovedCVByEmployeeId(Map<String, String> parameters) {
        Response response = get(parameters, URI, 200);
        CV dataResponse = gson.fromJson(response.asString(), CV.class);
        checkIds(Integer.parseInt(parameters.get("employee_id")), dataResponse.getEmployeeId());
        checkIsCvApproved(response, true);
        return response;
    }

    public Response getLastDraftCVByEmployeeId(Map<String, String> parameters) {
        Response response = get(parameters, URI, 200);
        CV dataResponse = gson.fromJson(response.asString(), CV.class);
        checkIds(Integer.parseInt(parameters.get("employee_id")), dataResponse.getEmployeeId());
        checkIsCvApproved(response, false);
        return response;
    }


    public Response getCvById(Map<String,String> parameters) {
        Response response = get(parameters, URI, 200);
        return response;
        /*Response response = given().
                spec(requestSpecification).
                pathParam("id", cvId).
                when().
                get(URL + "/{id}").
                then().
                statusCode(200).
                contentType(ContentType.JSON).
                extract().response();
        CV dataResponse = gson.fromJson(response.asString(), CV.class);
        Assert.assertEquals(dataResponse.getId(), cvId);
        return response;*/
    }

    public Response getAllApprovedCV() {
        Response response = given().
                spec(requestSpecification).
                when().
                get(URL + "/approved").
                then().
                statusCode(200).
                contentType(ContentType.JSON).
                extract().response();
        List<CV> responses = Stream.of(gson.fromJson(response.asString(), CV[].class)).collect(Collectors.toList());
        assertThat(responses, not(empty()));
        return response;
    }


    public Response getApprovedCVInDocxFormat(int employeeId) {
        Response response = given().
                spec(requestSpecification).
                queryParam("employee_id", employeeId).
                when().
                get(URL + "/docx").
                then().
                statusCode(200).
                contentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document").
                extract().response();
        return response;
    }


    public Response getLastCVByEmployeeId(Map<String,String> parameters) {
        Response response = get(parameters, URI, 200);
        return response;
        /*Response response = given().
                spec(requestSpecification).
                pathParam("employee_id", employeeId).
                when().
                get(URL + "/employee/{employee_id}").
                then().
                statusCode(200).
                contentType(ContentType.JSON).
                extract().response();
        return response;*/
    }

    public Response saveCVAsDraft(CV cv) {
        Response response = post(gson.toJson(cv), URI, 200);
        CV actualCV = gson.fromJson(response.asString(), CV.class);
        checkJsonSchema(response, "json_schemas/post_cv_schema.json");
        checkResponseTime(response, 5000L);
        checkIds(actualCV.getEmployeeId(), cv.getEmployeeId());
        return response;
    }

    public Response approveCV(Map parameters) {
        Response response = put(parameters, URI + "/approve", 200);
        return response;
    }

    public Response getZipArchiveOfDocxFiles(GetZIPArchivePayload employeeIds) {
        Response response = post(gson.toJson(employeeIds), URI + "/zip", 200);
        checkContentLengthGreaterThen(response, 1);
        return response;
    }


    public Response updateProfessionalSummaryByEmployeeId(CV cvBeforeUpdate, UpdateProfessionalSummaryPayload payload) {
        Response response = given().
                spec(requestSpecification).
                pathParam("employee", "employee").
                pathParam("employeeId", cvBeforeUpdate.getEmployeeId()).
                pathParam("summary", "summary").
                body(gson.toJson(payload)).
                when().
                patch(URL + "/{employee}/{employeeId}/{summary}").
                then().
                statusCode(200).
                contentType(ContentType.JSON).
                extract().response();
        CV actualCV = gson.fromJson(response.asString(), CV.class);
        assertThat("Returned employee id is not the same as in request parameter", cvBeforeUpdate.getEmployeeId(), equalTo(actualCV.getEmployeeId()));
        //TODO: Change professional summary to array style in cv generator
        assertThat(cvBeforeUpdate.getProfessionalSummary(), equalTo(actualCV.getProfessionalSummary()));
        return response;
    }

    public Response updateSkillsByEmployeeId(CV cvBeforeUpdate, UpdateSkillsPayload payload) {
        Response response = given().
                spec(requestSpecification).
                pathParam("employee", "employee").
                pathParam("employeeId", cvBeforeUpdate.getEmployeeId()).
                pathParam("skills", "skills").
                body(gson.toJson(payload)).
                when().
                patch(URL + "/{employee}/{employeeId}/{skills}").
                then().
                statusCode(200).
                contentType(ContentType.JSON).
                extract().response();
        CV actualCV = gson.fromJson(response.asString(), CV.class);
        assertThat("Returned employee id is not the same as in request parameter", cvBeforeUpdate.getEmployeeId(), equalTo(actualCV.getEmployeeId()));
        assertThat("Group of skills is not the same as in request payload", cvBeforeUpdate.getSkillGroups(), equalTo(actualCV.getSkillGroups()));
        return response;
    }

    public Response updateExperienceByEmployeeId(CV cvBeforeUpdate, UpdateExperiencePayload experience) {
        Response response = given().
                spec(requestSpecification).
                pathParam("employee", "employee").
                pathParam("employeeId", cvBeforeUpdate.getEmployeeId()).
                pathParam("experience", "experience").
                body(gson.toJson(experience)).
                when().
                patch(URL + "/{employee}/{employeeId}/{experience}").
                then().
                statusCode(200).
                contentType(ContentType.JSON).
                extract().response();
        CV actualCV = gson.fromJson(response.asString(), CV.class);
        assertThat("Returned employee id is not the same as in request parameter", cvBeforeUpdate.getEmployeeId(), equalTo(actualCV.getEmployeeId()));
        assertThat(cvBeforeUpdate.getExperience(), equalTo(actualCV.getExperience()));
        return response;
    }

    public Response updateEducationsByEmployeeId(CV cvBeforeUpdate, UpdateEducationsPayload educations) {
        Response response = given().
                spec(requestSpecification).
                pathParam("employee", "employee").
                pathParam("employeeId", cvBeforeUpdate.getEmployeeId()).
                pathParam("educations", "educations").
                body(gson.toJson(educations)).
                when().
                patch(URL + "/{employee}/{employeeId}/{educations}").
                then().
                statusCode(200).
                contentType(ContentType.JSON).
                extract().response();
        CV actualCV = gson.fromJson(response.asString(), CV.class);
        assertThat("Returned employee id is not the same as in request parameter", cvBeforeUpdate.getEmployeeId(), equalTo(actualCV.getEmployeeId()));
        assertThat("Education is not the same as in request payload", cvBeforeUpdate.getEducations(), equalTo(actualCV.getEducations()));
        return response;
    }

    public Response updateCertificatesByEmployeeId(CV cvBeforeUpdate, UpdateCertificatesPayload payload) {
        Response response = given().
                spec(requestSpecification).
                pathParam("employee", "employee").
                pathParam("employeeId", cvBeforeUpdate.getEmployeeId()).
                pathParam("certificates", "certificates").
                body(gson.toJson(payload)).
                when().
                patch(URL + "/{employee}/{employeeId}/{certificates}").
                then().
                statusCode(200).
                contentType(ContentType.JSON).
                extract().response();
        CV actualCV = gson.fromJson(response.asString(), CV.class);
        assertThat("Returned employee id is not the same as in request parameter", cvBeforeUpdate.getEmployeeId(), equalTo(actualCV.getEmployeeId()));
        assertThat(cvBeforeUpdate.getCertificates(), equalTo(actualCV.getCertificates()));
        return response;
    }//@formatter:on
}
