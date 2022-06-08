package api.models.api.cv_controller.cv;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Experience{
    public String customer_domain;
    public String customer_name;
    public long ended_at;
    public String project_description;
    public String project_name;
    public List<String> responsibilities;
    public String role;
    public long started_at;
    public List<String> technologies;
}
