package ist.challenge.bobiahmadrival.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ABaseResponse {
    private int code;
    private String message;
}
