package api.tests;

import api.adapters.CVControllerAdapter;
import api.models.api.cv_controller.cv.CV;
import api.models.api.cv_controller.payloads.*;
import api.utils.CVGenerator;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//@Listeners(ApiListener.class)
public class CVControllerTest extends BaseTest {


    @Test(description = "PUT | Approve cv")
    @Description("Expect code: 200, Approved cv id is = previous draft cv id, Content-type: JSON")
    public void approveCVTest() {
        CVControllerAdapter cvControllerAdapter = new CVControllerAdapter();
        Response saveCVAsDraftResponse = cvControllerAdapter.saveCVAsDraft(new CVGenerator().generateCV());
        CV draftCV = gson.fromJson(saveCVAsDraftResponse.asString(), CV.class);
        Map<String,String> parameters = new HashMap<>();
        parameters.put("employee_id", String.valueOf(draftCV.getEmployeeId()));

        cvControllerAdapter.approveCV(parameters);
    }

    @Test(description = "GET | Get last approved cv by employee ID")
    @Description("Expect isApproved: true, employeeId = source employee id, code: 200, Content-type: JSON")
    public void getLastApprovedCVByEmployeeIdTest() {
        Map<String,String> parameters = new HashMap<>();
        parameters.put("is_approved", "true");
        parameters.put("employee_id", String.valueOf(2));
        new CVControllerAdapter().getLastApprovedCVByEmployeeId(parameters);
    }

    @Test(description = "GET | Get last draft cv by employee ID")
    @Description("Expect isApproved: false, employeeId = source employee id, code: 200, Content-type: JSON")
    public void getLastDraftCVByEmployeeIdTest() {
        Map<String,String> parameters = new HashMap<>();
        parameters.put("is_approved", "false");
        parameters.put("employee_id", String.valueOf(1));

        new CVControllerAdapter().getLastDraftCVByEmployeeId(parameters);
    }

    @Test(description = "POST | Save cv as draft")
    @Description("Expect code: 200, JSONSchema is equal to predefined template, response time < 5 sec, employeeId = source employee id, Content-type: JSON")
    public void saveCVAsDraftVersionTest() {
        CV cv = new CVGenerator().generateCV();
        new CVControllerAdapter().saveCVAsDraft(cv);
    }


    @Test(description = "POST | Get zip of approved .docx")
    @Description("Expect code: 200, Content-type: ZIP, Content-length > 0")
    public void getZipArchiveOfDocxFilesTest() {
        List<Integer> employeeIds = Stream.of(1).collect(Collectors.toList());
        GetZIPArchivePayload employeeList = GetZIPArchivePayload.builder()
                .employeeIds(employeeIds)
                .build();
        new CVControllerAdapter().getZipArchiveOfDocxFiles(employeeList);
    }

    @Test(description = "PATCH | Update professional summary by employee id")
    @Description("Expect code: 200, Content-type: JSON")
    public void updateProfessionalSummaryByEmployeeIdTest() {
        CV cv = new CVGenerator().generateCV();
        UpdateProfessionalSummaryPayload professionalSummaryPayload = UpdateProfessionalSummaryPayload.builder()
                .professionalSummaries(cv.getProfessionalSummary())
                .build();
        new CVControllerAdapter().updateProfessionalSummaryByEmployeeId(cv, professionalSummaryPayload);
    }

    @Test(description = "PATCH | Update skills by employee id")
    @Description("Expect code: 200, Content-type: JSON")
    public void updateSkillsByEmployeeIdTest() {
        CVGenerator cvGenerator = new CVGenerator();
        CV cvBeforeUpdate = cvGenerator.generateCV();
        UpdateSkillsPayload skillGroupPayload = UpdateSkillsPayload.builder()
                .skillGroups(cvBeforeUpdate.getSkillGroups())
                .build();

        new CVControllerAdapter().updateSkillsByEmployeeId(cvBeforeUpdate, skillGroupPayload);
    }

    @Test(description = "PATCH | Update experience by employee id")
    @Description("Expect code: 200, Content-type: JSON")
    public void updateExperienceByEmployeeIdTest() {
        CVGenerator cvGenerator = new CVGenerator();
        CV cv = cvGenerator.generateCV();
        UpdateExperiencePayload updateExperiencePayload = UpdateExperiencePayload.builder()
                .experiences(cv.getExperience())
                .build();

        new CVControllerAdapter().updateExperienceByEmployeeId(cv, updateExperiencePayload);
    }

    @Test(description = "PATCH | Update educations by employee id")
    @Description("Expect code: 200, Content-type: JSON")
    public void updateEducationsByEmployeeIdTest() {
        CVGenerator cvGenerator = new CVGenerator();
        CV cv = cvGenerator.generateCV();
        UpdateEducationsPayload updateEducationsPayload = UpdateEducationsPayload.builder()
                .educations(cv.getEducations())
                .build();

        new CVControllerAdapter().updateEducationsByEmployeeId(cv, updateEducationsPayload);
    }

    @Test(description = "PATCH | Update certificates by employee id")
    @Description("Expect code: 200, Content-type: JSON")
    public void updateCertificatesByEmployeeIdTest() {
        CVGenerator cvGenerator = new CVGenerator();
        CV cv = cvGenerator.generateCV();
        UpdateCertificatesPayload updateCertificatesPayload = UpdateCertificatesPayload.builder()
                .certificates(cv.getCertificates())
                .build();

        new CVControllerAdapter().updateCertificatesByEmployeeId(cv, updateCertificatesPayload);
    }

    @Test(description = "GET | Get cv by id")
    @Description("Expect code: 200, employeeId = source employee id, Content-type: JSON")
    public void getCvByIdTest() {
        Map<String,String> parameters = new HashMap<>();
        parameters.put("employee_id", String.valueOf(1));
        new CVControllerAdapter().getCvById(parameters);
    }

    @Test(description = "GET | Get last cv by employee id")
    @Description("Expect code: 200, Content-type: JSON")
    void getLastCVByEmployeeIdTest() {
        Map<String,String> parameters = new HashMap<>();
        parameters.put("employee_id", "1");
        new CVControllerAdapter().getLastCVByEmployeeId(parameters);
    }

    @Test(description = "GET | Get approved cv in .docx format")
    @Description("Expect code: 200, Content-type: application/vnd.openxmlformats-officedocument.wordprocessingml.document")
    public void getApprovedCvInDocxFormatTest() {
        new CVControllerAdapter().getApprovedCVInDocxFormat(1);
    }

    @Test(description = "GET | Get all approved cvs")
    @Description("Expect code: 200, List of CVs is not empty, Content-type: JSON")
    public void getAllApprovedCVTest() {
        new CVControllerAdapter().getAllApprovedCV();
    }

}
