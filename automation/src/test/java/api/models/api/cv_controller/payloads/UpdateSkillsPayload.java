package api.models.api.cv_controller.payloads;

import api.models.api.cv_controller.cv.SkillGroup;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class UpdateSkillsPayload {
    @SerializedName("skill_group")
    List<SkillGroup> skillGroups;
}
