package api.models.api.cv_controller.payloads;

import api.models.api.cv_controller.cv.Certificate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCertificatesPayload {
    List<Certificate> certificates;
}
