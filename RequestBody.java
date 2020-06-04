import java.io.*;




/**
 * This is an abstract class that repersent a body for request.
 * It has to abstract method {@code build()} and {@code set()} that should implements in sub classes.
 * It has one field that hold the connection output stream
 * 
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.1.6
 */
public abstract class RequestBody  implements Serializable
{
            /*  Fields  */
      
    // hold connection output stream
    protected transient BufferedOutputStream outputStream;

    // length of the body (byte)
    private long Content_Length;


    private static final long serialVersionUID = 8483614136995L;






          /* Constructor */

    /**
     * Create a new request body that will sent to given stream
     * 
     * @param connectionOutputStream : your connnection output stream
     */      
    public RequestBody(OutputStream connectionOutputStream)
    {
        this.outputStream = new BufferedOutputStream(connectionOutputStream);
    }







            /*  Methods  */

    
    /**
     * This method build the request body
     */
    public abstract void build();


    /**
     * Thsi method set the request body
     * 
     * @throws IOException if can't write on output stream
     */
    public abstract void set() throws IOException;


    /**
     * This method return the value of the 'Content-Type' header
     * set a header to your request with key: "Content-Type",  and  value: 'output of this method'
     * 
     * @return a {@code String} that refers to Content-Type header
     */
    public abstract String getContentType();


    /**
     * This method return the size of the request body in byte
     * set a header to your request with key: "Content-Length",  and  value: 'output of this method'
     * 
     * @return a {@code long} that refers the body size in bytes
     */
    public long getContentLength()
    {
        return Content_Length;
    }



    
    /**
     * this method close the app 
     * use this method on errors
     */
    protected void error()
    {
        System.out.println(" An error occurs while trying to set requset body - ( check Jurl --help )");
        System.exit(0);
    }
}