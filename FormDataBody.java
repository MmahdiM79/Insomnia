import java.util.*;
import java.util.Map.Entry;
import java.io.*;





/**
 * This class represent a form data body.
 * It holds the given key values and convert them to a form data body.
 * for use : first call {@link FormDataBody#build()} then call {@link FormDataBody#set()}
 * 
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.1.1
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
    private String boundary;


    private static final long serialVersionUID = 3231995008582149988L;









         /* Constructor */

    /**
     * Create a new FormData Body by given datas.
     * The created FormData body will sent to given output stream
     * 
     * 
     * @param datas : a {@code String} of datas that you want to sent them in form data.
     *                 the given {@code String} should be in this form: "key=valu&key1=value1& ..."
     * @param connectionOutputStream : your connection output stream   
     * @param boundary : a boundary for this FormData body                                           
     */
    public FormDataBody(String datas, OutputStream connectionOutputStream, String boundary)
    {
        super(connectionOutputStream);

        
        this.datasString = datas;
        this.keyValues = new HashMap<>();
        this.boundary = "--" + boundary + "\r\n";
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
    public void set() throws IOException
    {
        for (String key : keyValues.keySet())
        {
           try{ outputStream.write(boundary.getBytes()); }
           catch (IOException e) {System.out.println(" an IOException:" + e.getMessage()); error();}

           if (key.contains("(FILE)")) 
           {
                outputStream.write(("Content-Disposition: form-data; filename=\"" + 
                                   (new File(keyValues.get(key))).getName() + "\"\r\nContent-Type: Auto\r\n\r\n").getBytes());


                try (BufferedInputStream tempBIS = new BufferedInputStream(new FileInputStream(new File(keyValues.get(key)))))
                {
                    byte[] filesBytes = tempBIS.readAllBytes();
                    outputStream.write(filesBytes);
                    outputStream.write("\r\n".getBytes());
                } 
                catch (IOException e) { error(); }
            } 
            else 
            {
                outputStream.write(("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n").getBytes());
                outputStream.write((keyValues.get(key) + "\r\n").getBytes());
            }
        }

        outputStream.write(("--" + boundary + "--\r\n").getBytes());
        outputStream.flush();
        outputStream.close();
    }
}