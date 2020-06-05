import java.io.*;
import java.net.*;
import java.util.*;



/**
 * This class reprsent a HTTP request with GET method
 * 
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.1.4
 */
public class Request implements Serializable
{
            /*  Fields  */

    protected static final String USER_AGENT = " Jurl - Insomnia";

    protected static final String ACCEPT = "*/*";




    // String of url
    protected final String urlString;

    // the request receiver
    protected URL url = null;

    // connection of this request
    protected transient HttpURLConnection connection = null;
    


    // a name for this request (use as file name that this request will save in it)
    protected String requestName;

    // group name of this request
    protected String groupName;



    // kind of the request
    protected RequestKinds requestKind = RequestKinds.GET;

    // headers <header key, header value>
    protected HashMap<String, String> requestHeaders = null; 

    // follow redirect or not
    protected boolean followRedirect = false;



    // response code
    protected int responseCode;

    // response message
    protected String responseMessage;

    // this.endTime - this.startTime
    protected long responseTime;

    // size of the resposnse
    protected long responseSize;

    // response headers
    protected HashMap<String, String> responseHeaders = new HashMap<>();

    // body of the respose
    protected byte[] responseBody;



    // check first send
    protected boolean check = true;

    // show in terminal or not
    protected boolean terminalShow = true;

    // serial version UID
    protected static final long serialVersionUID = 2215266465311155162L;

  
    




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
     * @param terminalShow : show the result on terminal or not
     * 
     * @throws IOException make sure that given url has protocol and you are cannected to the internet 
     */
    public Request(String name, String group, String url, String headers, String query, boolean followRedirect, boolean terminalShow) 
            throws IOException
    {
        requestName = name;
        groupName = group;


        url = checkURL(url);
        this.urlString = checkAndSetQuery(url, query);
        this.url = new URL(url);


        requestKind = RequestKinds.GET;

        if (headers != null)
            requestHeaders = new HashMap<>();
        buildHeaders(headers);

        this.followRedirect = followRedirect;
        this.terminalShow = terminalShow;
    }










            /*  Methods  */


    // * setter mothods *

    /**
     * @param requestKind : set the method of this request
     */
    public void setRequestKind(String requestKind)
    {
        this.requestKind = RequestKinds.getKind(requestKind);
    }
    


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
        HttpURLConnection.setFollowRedirects(false);


        if (check)
        {
            try{ url = new URL(urlString);}
            catch(MalformedURLException e) {error('I');}
            
            check = false;
        }
        


        try{ connection = (HttpURLConnection) url.openConnection(); }
        catch (IOException e) { error('I'); }
        
        try{ connection.setRequestMethod(RequestKinds.getKind(requestKind)); }
        catch (ProtocolException | SecurityException e) { return; }
        
        


        setHeaders();


        long startTime = System.nanoTime();

        try
        { 
            connection.connect();
            responseCode = connection.getResponseCode(); 
            responseMessage = connection.getResponseMessage();
            responseSize = connection.getContentLengthLong();
            readResponseHeaders();
        }
        catch(IOException e) { error('I'); }

        long endTime = System.nanoTime();

        responseTime = endTime - startTime;



        if (followRedirect && (responseCode/100 == 3))
        {
            String newUrl = connection.getHeaderField("Location");

            try { this.url = new URL(newUrl); }
            catch (MalformedURLException e) { error('I'); }

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
        catch (IOException e) { System.out.println(e.getMessage()); error('S'); }
    }






    /**
     * This method set the headers of the request
     */
    protected void setHeaders()
    {
        if (requestHeaders == null)
            return;


        connection.setRequestProperty("User-Agent", USER_AGENT);
        connection.setRequestProperty("Accept", ACCEPT);

        for (String key : requestHeaders.keySet())
            connection.setRequestProperty(key, requestHeaders.get(key));
    }


    /**
     * This method fill requsetHeaders hashMap by given string
     * 
     * @param headers : a {@code String} of headers
     */
    protected void buildHeaders(String headers)
    {
        if (requestHeaders == null || headers.equals(""))
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


    /**
     * This method read the response headers
     */
    protected void readResponseHeaders()
    {
        responseHeaders.put("details", connection.getHeaderField(null));

        for (String key : connection.getHeaderFields().keySet())
        {
            if (key == null)
                continue;

            responseHeaders.put(key, connection.getHeaderField(key));
        }
    }


    /**
     * This method check and set query
     * 
     * 
     * @param url : requset url
     * @param query : a {@code String} of query
     * 
     * @return new url with query
     */
    protected String checkAndSetQuery(String url, String query)
    {
        if (query == null)
            return url;



        String[] holdDatas = query.split("&");

        String key = null;
        for (String data : holdDatas)
        {
            try
            {
                key = data.substring(0, data.indexOf('='));

                if (key.length() == 0)
                    error('Q');
            }
            catch (IndexOutOfBoundsException e) { error('Q'); }
        }


        if (url.charAt(url.length()-1) != '/')
            url = url.concat("/");

        return url.concat("?").concat(query);
    }


    /**
     * This method set the url protocol to http
     * 
     * 
     * @param url : url of this request
     * @return return url with protocol
     */
    protected String checkURL(String url)
    {
        if ((url.substring(0, 7)).equals("http://"))
            return url;


        return "http://" + url;
    }


    /**
     * For erros
     * 
     * @param whichCase : which error?
     */
    protected void error(char whichCase)
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


            case 'B':
                System.out.println(" Faild to set the body of this request - ( check Jurl --help )");
            break;

            default:
            break;
        }


        System.exit(0);
    }
}