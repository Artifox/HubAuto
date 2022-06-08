package api.models.api.cv_controller.cv;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkillGroup {
    @SerializedName("skill_type")
    String skillType;
    List<String> skills;
}
