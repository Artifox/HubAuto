package api.models.api.cv_controller.cv;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Manager{
    public String first_name;
    public int id;
    public String last_name;
}
