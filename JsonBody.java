import java.io.*;
import java.nio.charset.StandardCharsets;





/**
 * This class build and set a JSON as requset body.
 * for use : first call {@link JsonBody#build()} then call {@link JsonBody#set(OutputStream)}.
 * Use {@link JsonBody#getContentType()} and {@link JsonBody#getContentLength()} to set the Content headers
 * 
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.2.0
 */
public class JsonBody extends RequestBody
{
            /*  Fields  */

    // Json String (body of request)
    private String jsonBodyString = null;



    private static final long serialVersionUID = 2285484035553569407L;









         /* Constructors */


    /**
     * Create a new Json with given {@code String}.
     * Your given {@code String} should be in this format: {"key": "value", "kay1": "value1", ...}
     * 
     * 
     * @param jsonBodyString : a {@code String} of Json datas to send it as request body
     */
    public JsonBody(String jsonBodyString)
    {
        this.jsonBodyString = jsonBodyString;
    }









            /*  Methods  */


    /**
     * This method check the given Json and terminates the currently running JVM if the given Json has some problems
     */
    public void build()
    {
        if (jsonBodyString == null)
            error();
            
        if (!(jsonBodyString.contains("{") && jsonBodyString.contains("}")))
            error();


        jsonBodyString = jsonBodyString.replaceAll(" ", "");
        jsonBodyString = jsonBodyString.substring(1, jsonBodyString.length()-1);

        String[] holdKeyValues = jsonBodyString.split(",");


        String key = null, value = null;
        for (String pair : holdKeyValues)
        {
            try
            {
                key = pair.substring(0, pair.indexOf(':'));
                value = pair.substring(pair.indexOf(':')+1);

                if (value.length() == 0 || key.length() == 0)
                    error();

                if (key.charAt(0) != '\"' || key.charAt(key.length()-1) != '\"')
                    error();

                if (value.charAt(0) != '\"' || key.charAt(value.length()-1) != '\"')
                    error();
            }
            catch (IndexOutOfBoundsException e) { error(); }
        }
    }


    /**
     * This method set the given Json as requset body
     */
    public void set(OutputStream connectionOutputStream) throws IOException
    {
        byte[] input = jsonBodyString.getBytes(StandardCharsets.UTF_8);

        super.Content_Length = input.length;

        connectionOutputStream.write(input, 0, input.length);           
    }



    public String getContentType()
    {
        return "application/json";
    }
}