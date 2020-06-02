import java.io.*;




/**
 * This is an abstract class that repersent a body for request.
 * It has to abstract method {@code build()} and {@code set()} that should implements in sub classes.
 * It has one field that hold the connection output stream
 * 
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.1.1
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
     */
    public abstract void set();




    // this method close the app 
    // use this method on errors
    protected void error()
    {
        System.out.println(" your given datas are invalid - ( check Jurl --help )");
        System.exit(0);
    }
}