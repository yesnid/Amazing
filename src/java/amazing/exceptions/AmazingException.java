package amazing.exceptions;

/**
 * Created by nsimi on 6/2/15.
 */
public class AmazingException extends Exception {
    private int _httpStatus;
    private String _response;

    public AmazingException(int status, String response){
        super(response);
        _httpStatus = status;
    }

    public int getHttpStatus(){
        return _httpStatus;
    }
    public String getResponseText(){
        return _response;
    }
}
