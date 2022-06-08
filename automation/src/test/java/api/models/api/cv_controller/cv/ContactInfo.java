package api.models.api.cv_controller.cv;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ContactInfo{
    public String email;
    public List<String> links;
    public String phone;
}
