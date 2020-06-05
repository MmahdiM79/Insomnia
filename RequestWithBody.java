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
 * @version 0.1.0
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
    }







            /*  Methods  */


    // * getter methods *

    /**
     * @return the content type of this requset
     */
    public String getContentType()
    {
        return this.requestBody.getContentType();
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
            try{ url = new URL(urlString);}
            catch(MalformedURLException e) {error('I');}
            

            check = false;
        }
        


        try{ connection = (HttpURLConnection) url.openConnection(); }
        catch (IOException e) { error('I'); }
        
        try{ connection.setRequestMethod(RequestKinds.getKind(requestKind)); }
        catch (ProtocolException | SecurityException e) { return; }
        

        connection.setDoOutput(true);

        
        setBodyObject(); 
        setHeaders();
    
    
        requestBody.build();
        try{ requestBody.set(connection.getOutputStream()); }
        catch(IOException e) { System.out.println(e.getMessage()); error('B'); }
        

        for (String key : requestHeaders.keySet())
            System.out.println(key + ":  " + requestHeaders.get(key));


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
     * This method set the headers of the request 
     */
    @Override
    protected void setHeaders()
    {
        super.setHeaders();

        super.addHeader("Content-Type", this.getContentType());
    }





    // this method set the request body object
    private void setBodyObject()
    {
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