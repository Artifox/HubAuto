package api.models.api.cv_controller.cv;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Education{
    public String degree;
    public long finish_year;
    public String specialization;
    public long start_year;
    public String university_name;
}
