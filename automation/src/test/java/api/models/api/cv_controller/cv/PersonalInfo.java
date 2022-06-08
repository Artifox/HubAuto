package api.models.api.cv_controller.cv;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PersonalInfo{
    public long birth_date;
    public String first_name;
    public String last_name;
    public String position;
    /*public String activity_status;
    public int id;
    public Location location;
    public Manager manager;
    public String photo;
    public String professional_summary;
    public String role;
    public List<Visa> visas;*/
}
