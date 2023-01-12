package ist.challenge.bobiahmadrival.dto.response;

import lombok.Data;

@Data
public class ResponseWithData<T> extends ABaseResponse{
    private T data;

    public ResponseWithData(int code, String message, T data){
        super(code, message);
        this.data = data;
    }
}
