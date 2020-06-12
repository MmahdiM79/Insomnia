import java.net.*;
import java.util.*;
import java.io.*;





/**
 * This method represent a Request with body.
 * Body kinds: form-data, Json, binary-file.
 * You can't sent a 'GET' method with this class
 * 
 * 
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.2.0
 */
public class RequestWithBody extends Request
{
            /*  Feilds  */

    // kind of the request body
    private String bodyKind;

    // data of body
    private String bodyDatas;

    // body of the request
    private RequestBody requestBody;










          /* Constructor */

    /**
     * Create a new Request with body and given details
     * Method of this Request can't be "GET" (becuse it has body)
     * 
     * 
     * @param name : a name for this request
     * @param group : name of the this request group
     * @param url : url for send this requst
     * @param method : method of this request (POST, PUT, DELETE)
     * @param headers : a {@code String} of request headers in this format: "header:value;header1:value1; ..."
     * @param requestBodyKind : kind of the body requset
     * @param bodyDatas : datas to send as rquest body 
     * @param query : a {@code String} of request query in this format: "name=value&name1=value1& ..."
     * @param followRedirect : set it {@code true} if you want follow redirect
     * @param terminalShow : show the result on terminal or not
     * @throws IOException
     */
    public RequestWithBody(String name, 
                           String group, 
                           String url, 
                           String method,
                           String headers,
                           String requestBodyKind,
                           String bodyDatas, 
                           String query, 
                           boolean followRedirect, 
                           boolean terminalShow)  throws IOException
    {
        super(name, group, url, (headers == null ? "": headers), query, followRedirect, terminalShow);

        super.setRequestKind(method);

        this.bodyKind = requestBodyKind;
        this.bodyDatas = bodyDatas;
        setBodyObject();
    }







            /*  Methods  */


    // * getter methods *

    /**
     * @return the content type of this requset
     */
    public String getContentType()
    {
        if (requestBody != null)
            return this.requestBody.getContentType();

        return "no body content";
    }
    /**
     * @return the content length of this request in byte
     */
    public Long getContentLength()
    {
        return this.requestBody.getContentLength();
    }





    /**
     * This method send the request and read the body
     */
    @Override
    public void run()
    {
        HttpURLConnection.setFollowRedirects(false);


        if (check)
        {
            try{ url = new URL(urlString); }
            catch(MalformedURLException e) { Out.printErrors("internet"); }
        }
        
        try{ connection = (HttpURLConnection) url.openConnection(); }
        catch (IOException e) { Out.printErrors("internet"); }
        


        try{ connection.setRequestMethod(RequestKinds.getKind(requestKind)); }
        catch (ProtocolException | SecurityException e) { return; }
        

        connection.setDoOutput(true);

        setHeaders();
    




        long startTime = System.nanoTime(), endTime = 0;

        if (requestBody != null)
        {
            requestBody.build();

            try
            { 
                requestBody.set(connection.getOutputStream()); 
                endTime = System.nanoTime();
            }
            catch(IOException e) { Out.printErrors("reqbody"); }
        }


        try
        { 
            connection.connect();
            responseCode = connection.getResponseCode(); 
            if (endTime == 0)
                endTime = System.nanoTime();
            responseMessage = connection.getResponseMessage();
            responseSize = connection.getContentLengthLong();
            readResponseHeaders();
            setResponseBodyFormat();
        }
        catch(IOException e) { Out.printErrors("internet"); }

        responseTime = (endTime - startTime) / 1000000;



        if (check)
        {
            Out.printRequestDetails(this);
            check = false;
        }
        if (showResponseHeaders)
            Out.printResponseDetails(this);




        if (followRedirect && (responseCode/100 == 3))
        {
            String newUrl = connection.getHeaderField("Location");

            try { this.url = new URL(newUrl); }
            catch (MalformedURLException e) { Out.printErrors("internet"); }

            this.run(); 
        }


        if (connection.getErrorStream() != null)
        {
            try{ Out.printResponseBody(connection.getErrorStream()); }
            catch(IOException e){}
        }
        else
        {
            try{ Out.printResponseBody(connection.getInputStream()); }
            catch(IOException e){}
        }

        connection.disconnect();
    }




    /**
     * This method set the headers of the request 
     */
    @Override
    protected void setHeaders()
    {
        super.setHeaders();

        if (bodyKind != null)
        {
            connection.setRequestProperty("Content-Type", this.getContentType());
            super.addHeader("Content-Type", this.getContentType());
        }
    }





    // this method set the request body object
    private void setBodyObject()
    {
        if (bodyKind == null)
            return;


        switch(bodyKind.toLowerCase())
        {
            case "form-data":
                this.requestBody = new FormDataBody(this.bodyDatas);
            break;


            case "json":
                this.requestBody = new JsonBody(this.bodyDatas);
            break;


            case "binary-file":
                this.requestBody = new BinaryFileBody(this.bodyDatas);
            break;



            default:
                this.requestBody = null;
        }
    }
}