import java.net.*;
import java.util.HashMap;



/**
 * This class reprsent a HTTP request
 * 
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.0.0
 */
public class Request
{
            /*  Fields  */

    // the request receiver
    private final URL serverUrl;

    // kind of the request
    private RequestKinds requestKind;

    // headers <header key, header value>
    private HashMap<String, String> headers;

  
    




         /* Constructors */

    
    /**
     * Create new Request with given details
     * 
     * 
     * @param url : url for send this requst
     * @param kind : kind of this requset
     * 
     * @throws MalformedURLException as specific by {@link URL#URL(String)}
     */
    public Request(String url, String kind) throws MalformedURLException
    {
        serverUrl = new URL(url);
        requestKind = RequestKinds.getKind(kind);
    }
}