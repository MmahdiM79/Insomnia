import java.io.*;




/**
 * This class set a file to request body
 * for use : first call {@link BinaryFileBody#build()} then call {@link BinaryFileBody#set(OutputStream)}. 
 * Use {@link BinaryFileBody#getContentType()} and {@link BinaryFileBody#getContentLength()} to set the Content headers
 * 
 * 
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.2.0
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
     */
    public BinaryFileBody(String filePath)
    {
        this.filePath = filePath;
    } 







            /*  Methods  */

    
    /**
     * This method open your given file and make it ready for send as request body
     */
    public void build()
    {
        // check that given path is available or not
        try { bodyFile = new FileInputStream(filePath); }
        catch (FileNotFoundException e) { error(); }
    }


    /**
     * This method set the file as request body
     */
    public void set(OutputStream connectionOutputStream) throws IOException
    {
        BufferedInputStream fileInputStream = new BufferedInputStream(bodyFile);

        connectionOutputStream.write(fileInputStream.readAllBytes());
        super.Content_Length = fileInputStream.readAllBytes().length;

        connectionOutputStream.flush();
        connectionOutputStream.close();
    }


    public String getContentType()
    {
        return "application/octet-stream";
    }
}