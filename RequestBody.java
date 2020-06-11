import java.io.*;




/**
 * This is an abstract class that repersent a body for request.
 * It has to abstract method {@code build()} and {@code set()} that should implements in sub classes.
 * It has one field that hold the connection output stream
 * 
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.2.0
 */
public abstract class RequestBody  implements Serializable
{
            /*  Fields  */

    // length of the body (byte)
    protected long Content_Length = 0;


    private static final long serialVersionUID = 8483614136995L;








            /*  Methods  */

    
    /**
     * This method build the request body
     */
    public abstract void build();


    /**
     * Thsi method set the request body
     * 
     * @param connectionOutputStream : output stream of your connectoin
     * @throws IOException if can't write on output stream
     */
    public abstract void set(OutputStream connectionOutputStream) throws IOException;


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
        Out.printErrors("bodyBuild");
    }
}