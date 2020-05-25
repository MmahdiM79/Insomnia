import java.io.*;
import java.net.*;
import java.util.*;



/**
 * This class reprsent a HTTP request
 * 
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.0.7
 */
public class Request implements Serializable
{
            /*  Fields  */

    // a name for this request (use as file name that this request will save in it)
    private String requestName;

    // the request receiver
    private URL serverUrl;

    // kind of the request
    private RequestKinds requestKind;

    // headers <header key, header value>
    private HashMap<String, String> requestHeaders;



    // response code
    private int responseCode;

    // response message
    private String responseMessage;

    // response headers
    private HashMap<String, String> responseHeaders;



    // serial version UID
    private static final long serialVersionUID = 2215266465311155162L;

  
    




         /* Constructors */

    
    /**
     * Create new Request with given details
     * 
     * 
     * @param name: a name for this request
     * @param url : url for send this requst
     * @param kind : kind of this requset
     * 
     * @throws MalformedURLException as specific by {@link URL#URL(String)}
     */
    public Request(String name, String url, String kind)
    {
        requestName = name;
        
        try
        {
            serverUrl = new URL(url);
        }
        catch (MalformedURLException e){}


        requestKind = RequestKinds.getKind(kind);
        requestHeaders = new HashMap<>();
    }


    /**
     * Create a new Request from a *.rqstdata file
     * 
     * 
     * @param requestGroup : the folder of this request
     * @param fileName : name of the data file (without '.rqstdata' at the end)
     * 
     * @throws IOException make sure that given file name is available
     * @throws ClassNotFoundException don't care about this. it's handled in method
     */
    public Request(String requestGroup, String fileName) throws IOException, ClassNotFoundException
    {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("./" + requestGroup + "/" + fileName + ".rqstdata"));
        Request file = (Request) in.readObject();

        this.requestName = fileName;
        this.requestKind = file.getRequestKind();
        this.serverUrl = file.getServerUrl();

        for(String key : file.getHeadersKeys())
            this.addHeader(key, file.getHeader(key));
        
        
        in.close();
    }








            /*  Methods  */

    
    // * getter methods *

    /**
     * @return name of this request
     */
    public String getRequestName() 
    {
        return requestName;
    }
    /**
     * @return the url of this request
     */
    public URL getServerUrl() 
    {
        return serverUrl;
    }
    /**
     * @return kind of this request
     */
    public RequestKinds getRequestKind() 
    {
        return requestKind;
    }
    /**
     * @return a {@code Set} of headers keys
     */
    public Set<String> getrequestHeadersKeys()
    {
        return requestHeaders.keySet();
    }
    /** 
     * @return the value of given key
     */
    public String getRequestHeader(String key)
    {
        return requestHeaders.get(key);
    }
    /**
     * @return response code of this request
     */
    public int getResponseCode() 
    {
        return responseCode;
    }
    /**
     * @return response message of this request
     */
    public String getResponseMessage() 
    {
        return responseMessage;
    }
    /**
     * @return a {@code Set} of response headers keys
     */
    public Set<String> getResponseHeaders() 
    {
        return responseHeaders.keySet();
    }
    /** 
     * @return the value of given keyg
     */
    public String getResponseHeader(String key)
    {
        return responseHeaders.get(key);
    }
    


    /**
     * This method add a new header to the request
     * 
     * 
     * @param key : key of header
     * @param value : value of header
     */
    public void addHeader(String key, String value)
    {
        requestHeaders.put(key, value);
    }


    /**
     * This method save this object to given group
     * This file will save to a file with name of this request
     * 
     * @param groupName : the group witch this request belong to
     * @throws IOException make sure that given group name is available
     */
    public void saveToFile(String groupName) throws IOException
    {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./" + groupName + requestName + ".rqstdata"));
        out.writeObject(this);
        out.close();
    }
}