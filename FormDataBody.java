import java.util.*;
import java.io.*;





/**
 * This class represent a form data body.
 * It holds the given key values and convert them to a form data body.
 * for use : first call {@link FormDataBody#build()} then call {@link FormDataBody#set()}. 
 * Use {@link FormDataBody#getContentType()} and {@link FormDataBody#getContentLength()} to set the Content headers
 * 
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.2.5
 */
public class FormDataBody extends RequestBody
{
            /*  Fields */

    // client given datas String
    private String datasString;

    // this hash map holds the key and values of the body
    // if some of the values are file, the key of them will contains "(FILE)"
    private HashMap<String, String> keyValues;

    // a boundary for this form data body
    private final String boundaryKey;

    // final boundray for write on output stream
    private final String boundary;


    private static final long serialVersionUID = 3231995008582149988L;









         /* Constructor */

    /**
     * Create a new FormData Body by given datas.
     * The created FormData body will sent to given output stream
     * 
     * 
     * @param datas : a {@code String} of datas that you want to sent them in form data.
     *                 the given {@code String} should be in this form: "key=valu&key1=value1& ..."                                              
     */
    public FormDataBody(String datas)
    {
        this.datasString = datas;
        this.keyValues = new HashMap<>();

        boundaryKey = "Jurl-MmMmM-79--" + (((new Date()).getTime() + System.nanoTime())/3006009);
        this.boundary = "--".concat(boundaryKey).concat("\r\n");
    }







            /*  Methods  */


    /**
     * This method build a {@code HashMap<String, String>} of given datas {@code String}.
     * If the given {@code String} isn't in "key=value&key1=value1& ..." format, the current JVM will terminate
     */
    public void build()
    {
        String[] holdDatas = datasString.split("&");
        

        String key = null, value = null;
        for (String data : holdDatas)
        {
            try
            {
                key = data.substring(0, data.indexOf('='));
                value = data.substring(data.indexOf('=')+1);

                if (value.length() == 0 || key.length() == 0)
                    error();
            }
            catch (IndexOutOfBoundsException e) { error(); }


            keyValues.put(key, value);
        }
    }


    /**
     * This method set the form date of given key-values as connection body
     */
    public void set(OutputStream connectionOutputStream) throws IOException
    {
        for (String key : keyValues.keySet())
        {
            // write boundry
            connectionOutputStream.write(boundary.getBytes()); 
            super.Content_Length += boundary.getBytes().length;
           

            // for file cases
            if (key.contains("(FILE)")) 
            {
                connectionOutputStream.write(("Content-Disposition: form-data; filename=\"" + 
                                        (new File(keyValues.get(key))).getName() + 
                                            "\"\r\nContent-Type: Auto\r\n\r\n").getBytes());

                super.Content_Length += ("Content-Disposition: form-data; filename=\"" + 
                                            (new File(keyValues.get(key))).getName() + 
                                                "\"\r\nContent-Type: Auto\r\n\r\n").getBytes().length;


                try (BufferedInputStream tempBIS = new BufferedInputStream(new FileInputStream(new File(keyValues.get(key)))))
                {
                    byte[] filesBytes = tempBIS.readAllBytes();
                    super.Content_Length += filesBytes.length;
                    connectionOutputStream.write(filesBytes);

                    super.Content_Length += "\r\n".getBytes().length;
                    connectionOutputStream.write("\r\n".getBytes());
                } 
                catch (IOException e) { error(); }
            } 

            // for text cases
            else 
            {
                super.Content_Length += ("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n").getBytes().length;
                connectionOutputStream.write(("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n").getBytes());

                super.Content_Length += (keyValues.get(key) + "\r\n").getBytes().length;
                connectionOutputStream.write((keyValues.get(key) + "\r\n").getBytes());
            }
        }

    
        super.Content_Length += boundary.getBytes().length;
        connectionOutputStream.write(("--" + boundaryKey + "--\r\n").getBytes());

        connectionOutputStream.flush();
        connectionOutputStream.close();
    }


    /**
     * This method return the boundary of this form data body. 
     * set a header to your request with key: "Content-Type",  and  value: 'output of this method'
     */
    public String getContentType()
    {
        return "multipart/form-data; charset=utf-8; boundary=" + boundaryKey;
    }
}