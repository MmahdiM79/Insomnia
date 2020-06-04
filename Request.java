import java.io.*;
import java.net.*;
import java.util.*;



/**
 * This class reprsent a HTTP request with GET method
 * 
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.1.0
 */
public class Request implements Serializable
{
            /*  Fields  */

    private static final String USER_AGENT = " Jurl - Insomnia";

    private static final String ACCEPT = "*/*";




    // the request receiver
    private URL url = null;

    // connection of this request
    private transient HttpURLConnection connection = null;
    


    // a name for this request (use as file name that this request will save in it)
    private String requestName;

    // group name of this request
    private String groupName;



    // kind of the request
    private RequestKinds requestKind = RequestKinds.GET;

    // headers <header key, header value>
    private HashMap<String, String> requestHeaders = null; 

    // query of the request
    private HashMap<String, String> query = null;

    // follow redirect or not
    private boolean followRedirect = false;



    // response code
    private int responseCode;

    // response message
    private String responseMessage;

    // this.endTime - this.startTime
    private long responseTime;

    // size of the resposnse
    private long responseSize;

    // response headers
    private HashMap<String, String> responseHeaders = new HashMap<>();

    // body of the respose
    private byte[] responseBody;



    // show in terminal or not
    private boolean terminalShow = true;

    // serial version UID
    private static final long serialVersionUID = 2215266465311155162L;

  
    




         /* Constructor */

    
    /**
     * Create new Request GET with given details
     * 
     * 
     * @param name : a name for this request
     * @param group : name of the this request group
     * @param url : url for send this requst
     * @param headers : a {@code String} of request headers in this format: "header:value;header1:value1; ..."
     * @param query : a {@code String} of request query in this format: "name=value&name1=value1& ..."
     * @param followRedirect : set it {@code true} if you want follow redirect
     * @param terminalShow : Show the result on terminal or not
     * 
     * @throws IOException make sure that given url has protocol and you are cannected to the internet 
     */
    public Request(String name, String group, String url, String headers, String query, boolean followRedirect, boolean terminalShow) 
            throws IOException
    {
        requestName = name;
        groupName = group;

        url = checkURL(url);
        this.url = new URL(url);

        if (query != null)
            this.query = new HashMap<>();
        buildQuery(query);

        requestKind = RequestKinds.GET;

        if (headers != null)
            requestHeaders = new HashMap<>();
        buildHeaders(headers);

        this.followRedirect = followRedirect;
        this.terminalShow = terminalShow;
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
    public URL geturl() 
    {
        return url;
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
     * @return the value of given key
     */
    public String getResponseHeader(String key)
    {
        return responseHeaders.get(key);
    }
    



    /**
     * This method send request and read the response
     */
    public void run()
    {
        setQuery();

        try{ connection = (HttpURLConnection) url.openConnection(); }
        catch (IOException e) { error('I'); }
        
        try{ connection.setRequestMethod(RequestKinds.getKind(requestKind)); }
        catch (ProtocolException | SecurityException e) { return; }
        
        HttpURLConnection.setFollowRedirects(false);


        setHeaders();


        long startTime = System.nanoTime();
        try
        { 
            connection.connect();
            responseCode = connection.getResponseCode(); 
            responseMessage = connection.getResponseMessage();
            responseSize = connection.getContentLengthLong();
        }
        catch(IOException e) { error('I'); }
        long endTime = System.nanoTime();

        responseTime = endTime - startTime;

        System.out.println(" at host: " + url.getHost() + url.getPath() + " |  response code" + responseCode);

        if (followRedirect && (responseCode/100 == 3))
        {
            String newUrl = connection.getHeaderField("Location");
            try { this.url = new URL(newUrl); }
            catch (MalformedURLException e) {  }
            this.run(); 
        }
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
     */
    public void saveToFile()
    {
        try { DataBase.saveRequest(groupName, requestName, this); }
        catch (IOException e) { error('S'); }
    }






    // this method set the headers of the request
    private void setHeaders()
    {
        if (requestHeaders == null)
            return;


        connection.setRequestProperty("User-Agent", USER_AGENT);
        connection.setRequestProperty("Accept", ACCEPT);

        for (String key : requestHeaders.keySet())
            connection.setRequestProperty(key, requestHeaders.get(key));
    }


    // this method fill requsetHeaders hashMap by given string
    private void buildHeaders(String headers)
    {
        if (requestHeaders == null)
            return;


        String[] holdDatas = headers.split(";");
        

        String key = null, value = null;
        for (String data : holdDatas)
        {
            try
            {
                key = data.substring(0, data.indexOf(':'));
                value = data.substring(data.indexOf(':')+1);

                if (value.length() == 0 || key.length() == 0)
                    error('H');
            }
            catch (IndexOutOfBoundsException e) { error('H'); }


            requestHeaders.put(key, value);
        }
    }


    // this method read the response headers
    private void readResponseHeaders()
    {
        responseHeaders.put("", connection.getHeaderField("null"));

        for (String key : connection.getHeaderFields().keySet())
        {
            if (key.equals("null"))
                continue;

            responseHeaders.put(key, connection.getHeaderField(key));
        }
    }


    // set the Query of request
    private void setQuery()
    {
        if (query == null)
            return;



        String urlString = "http:/" + this.url.getPath();

        urlString = urlString.concat("?");

        for (String key : query.keySet())
            urlString = urlString.concat(key + "=" + query.get(key) + "&");

        urlString = urlString.substring(0, urlString.length()-1);
        
        
        
        try{ this.url = new URL(urlString); }
        catch(MalformedURLException e) { System.out.println(" at setQuery: " + urlString); error('Q');}
    }


    // this method build query from given string
    private void buildQuery(String query)
    {
        if (query == null)
            return;


        String[] holdDatas = query.split("&");
        

        String key = null, value = null;
        for (String data : holdDatas)
        {
            try
            {
                key = data.substring(0, data.indexOf('='));
                value = data.substring(data.indexOf('=')+1);

                if (key.length() == 0)
                    error('Q');
            }
            catch (IndexOutOfBoundsException e) { error('Q'); }


            this.query.put(key, value);
        }
    }


    // this method set the url protocol to http
    private String checkURL(String url)
    {
        if ((url.substring(0, 7)).equals("http://"))
            return url;


        return "http://" + url;
    }


    // for erros
    private void error(char whichCase)
    {
        switch (whichCase)
        {
            case 'H':
                System.out.println(" Your given headers are invalid - ( check Jurl --help )");
            break;


            case 'I':
                System.out.println(" Faild to open connection - ( check your internet connection or Jurl --help )");
            break;


            case 'Q':
                System.out.println(" Your given Query is invalid - ( check Jurl --help )");
            break;


            case 'S':
                System.out.println(" Faild to save this request - ( check Jurl --help )");
            break;



            default:
            break;
        }


        System.exit(0);
    }
}