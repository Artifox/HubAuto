package api.models.api.cv_controller.payloads;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class GetZIPArchivePayload {
    @SerializedName("employee_id_list")
    public List<Integer> employeeIds;
}
