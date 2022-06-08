package api.models.api.cv_controller.cv;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Credentials {
    String username;
    String password;
}
