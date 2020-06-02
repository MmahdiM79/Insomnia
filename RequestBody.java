import java.io.*;




/**
 * This is an abstract class that repersent a body for request.
 * It has to abstract method {@code build()} and {@code set()} that should implements in sub classes.
 * It has one field that hold the connection output stream
 * 
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.1.2
 */
public abstract class RequestBody 
{
            /*  Field  */

    // hold connection output stream
    protected BufferedOutputStream outputStream;






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
     * this method close the app 
     * use this method on errors
     */
    protected void error()
    {
        System.out.println(" can't write on connectoin output stream - ( check Jurl --help )");
        System.exit(0);
    }
}