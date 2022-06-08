package api.models.api.cv_controller.cv;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Certificate{
    public long expiration_date;
    public String name;
}