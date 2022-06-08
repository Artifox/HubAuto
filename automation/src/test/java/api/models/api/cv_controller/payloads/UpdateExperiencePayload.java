package api.models.api.cv_controller.payloads;

import api.models.api.cv_controller.cv.Experience;
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
public class UpdateExperiencePayload {
    @SerializedName("experience")
    List<Experience> experiences;
}
