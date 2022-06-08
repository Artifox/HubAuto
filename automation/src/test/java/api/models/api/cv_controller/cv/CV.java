package api.models.api.cv_controller.cv;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class CV {
    public int id;
    @SerializedName("is_approved")
    boolean isApproved;
    @SerializedName("approved_at")
    public long approvedAt;
    @SerializedName("generated_at")
    public long generatedAt;
    @SerializedName("employee_id")
    public int employeeId;
    @SerializedName("personal_info")
    public PersonalInfo personalInfo;
    @SerializedName("professional_summary")
    public List<String> professionalSummary;
    public List<Education> educations;
    @SerializedName("contact_info")
    public ContactInfo contactInfo;
    public List<Experience> experience;
    @SerializedName("skill_group")
    public List<SkillGroup> skillGroups;
    public List<Certificate> certificates;
}
