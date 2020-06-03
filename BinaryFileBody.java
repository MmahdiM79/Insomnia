import java.io.*;




/**
 * This class set a file to request body
 * for use : first call {@link BinaryFileBody#build()} then call {@link BinaryFileBody#set()}
 * 
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.1.1
 */
public class BinaryFileBody extends RequestBody
{
            /*  Fields  */
        
    // file address
    private String filePath;

    // file that we want to send it as request body
    private transient FileInputStream bodyFile;


    private static final long serialVersionUID = 775061443934725L;
    




    
         /* Constructor */

    /**
     * Create a new binary file body for request
     * 
     * 
     * @param filePath : address of the file that you want to send it as your request body
     * @param connectionOutputStream : your connection output stream
     */
    public BinaryFileBody(String filePath, OutputStream connectionOutputStream)
    {
        super(connectionOutputStream);

        this.filePath = filePath;
    } 







            /*  Methods  */

    
    /**
     * This method open your given file and make it ready for send as request body
     */
    public void build()
    {
        try { bodyFile = new FileInputStream(filePath); }
        catch (FileNotFoundException e) { error();}
    }


    /**
     * This method set the file as request body
     */
    public void set() throws IOException
    {
        BufferedInputStream fileInputStream = new BufferedInputStream(bodyFile);
        outputStream.write(fileInputStream.readAllBytes());
        outputStream.flush();
        outputStream.close();
    }
}