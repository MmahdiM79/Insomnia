import java.io.*;





/**
 * This class build and set a JSON as requset body.
 * for use : first call {@link JsonBody#build()} then call {@link JsonBody#set()}.
 * 
 * 
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.1.2
 */
public class JsonBody extends RequestBody
{
            /*  Fields  */

    // Json String (body of request)
    private String jsonBodyString = null;



    private static final long serialVersionUID = 2285484035553569407L;









         /* Constructors */


    /**
     * Create a new Json with given {@code String} and send it as request body to given connection output stream.
     * Your given {@code String} should be in this format: {"key": "value", "kay1": "value1", ...}
     * 
     * 
     * @param jsonBodyString : a {@code String} of Json datas to send it as request body
     * @param connectionOutputStream : your connectoin output stream
     */
    public JsonBody(String jsonBodyString, OutputStream connectionOutputStream)
    {
        super(connectionOutputStream);

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


        jsonBodyString = jsonBodyString.replaceAll("\"", "");
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
            }
            catch (IndexOutOfBoundsException e) { error(); }
        }
    }


    /**
     * This method set the given Json as requset body
     */
    public void set() throws IOException
    {
        byte[] input = jsonBodyString.getBytes("utf-8");

        super.Content_Length = input.length;

        outputStream.write(input, 0, input.length);           
    }


    public String getContentType()
    {
        return "application/json";
    }
}