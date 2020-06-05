import java.io.*;
import java.util.Date;





/**
 * This class manage outputs of this app.
 * First of all call {@link Out#init(boolean)}. (it's a kind of constructor for this class).
 * 
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.1.0
 */
public final class Out 
{
            /*  Fields  */

    // a file for showing time line
    private static PrintStream TIME_LINE;

    // a file for showing response headers
    private static PrintStream RESPONSE_HEADERS;   

    // a file for showing response body
    private static PrintStream RESPONSE_BODY;

    // a file for saveing the errors log
    private static PrintStream ERRORS_LOG;

    // true if outputs should be print on std_out
    private static boolean terminalCheck;







            /*  Methods  */


    /**
     * This method is a kind of Constructor for this class. so, first of all call this method
     * 
     * 
     * @param terminal : set it {@code true}, if you wants to show te resaults in terminal
     */
    public static void init(boolean terminal)
    {
        terminalCheck = terminal;


        if (terminal)
        {
            TIME_LINE = RESPONSE_HEADERS = RESPONSE_BODY = ERRORS_LOG = System.out;
        }

        else
        {
            try
            { 
                TIME_LINE = new PrintStream(new File(DataBase.getTimeLineGuiPath()));
                RESPONSE_HEADERS = new PrintStream(new File(DataBase.getResponseHeadersGuiPath()));
                RESPONSE_BODY = new PrintStream(new File(DataBase.getResponseBodyGuiPath())); 
                ERRORS_LOG = new PrintStream(new File(DataBase.getErrorsLogGuiPath()));
            }
            catch(FileNotFoundException e) { System.out.println(e.getLocalizedMessage()); System.exit(0);}
        }
    }   


    /**
     * This method print the request detials on output file
     * 
     * 
     * @param request : your request
     */
    public static void printRequestDetails(Request request)
    {
        TIME_LINE.println("\n\n  ------ Request Details ------  \n");


        TIME_LINE.println(RequestKinds.getKind(request.getRequestKind()) + "  " +
                          request.getUrl().getFile() + "  " +
                          request.getUrl().getProtocol().toUpperCase() +
                          request.getResponseHeader("details").
                                            substring(request.getResponseHeader("details").indexOf('/'), 
                                                      request.getResponseHeader("details").indexOf(' ')));

        TIME_LINE.println("Host:  " + request.getUrl().getHost());

        TIME_LINE.println("Date:  " + (new Date()).toGMTString());

        if (request.getrequestHeadersKeys() != null)
            for (String key : request.getrequestHeadersKeys())
                TIME_LINE.println(key + ":  " + request.getRequestHeader(key));

        
        
        TIME_LINE.print("\n\n\n");
    }


    /**
     * This method print the response headers on output file
     * 
     * 
     * @param request : your request
     */
    public static void printResponseDetails(Request request)
    {
        RESPONSE_HEADERS.println("\n\n  ------ Response Details ------  \n");


        RESPONSE_HEADERS.println(" " + request.getResponseHeader("details"));
        
        for (String key : request.getResponseHeaders())
        {
            if (key.equals("details"))
                continue;

            RESPONSE_HEADERS.println(key + ":  " + request.getResponseHeader(key));
        }



        RESPONSE_HEADERS.print("\n\n\n");
    }


    /**
     * This method read and print response body
     * 
     * 
     * @param connectionInputStream : input stream of your connection
     * @throws IOException if can't read from given stream
     */
    public static void printResponseBody(InputStream connectionInputStream) throws IOException
    {
        byte[] character = new byte[1];

        while (connectionInputStream.read(character) != -1) 
            RESPONSE_BODY.print((char) (character[0]));
    }


    /**
     * This method prints the error messages.
     * 
     * <p>Error Cases: </p>
     *          'header', 
     *          'internet',
     *          'query',
     *          'save',
     *          'reqbody',
     * 
     * @param whichCase : which error e
     */
    public static void printErrors(String whichCase)
    {
        switch (whichCase)
        {
            case "header":
                ERRORS_LOG.print(" Your given headers are invalid");
            break;


            case "internet":
                ERRORS_LOG.print(" Faild to open connection");
            break;


            case "query":
                ERRORS_LOG.print(" Your given Query is invalid");
            break;


            case "sava":
                ERRORS_LOG.print(" Faild to save this request");
            break;


            case "reqBody":
                ERRORS_LOG.print(" Faild to set the body of this request");
            break;



            default:
            break;
        }


        if (terminalCheck)
        {
            ERRORS_LOG.print(" - ( check Jurl --help )\n");
            System.exit(0);
        }
        else
            ERRORS_LOG.print("\n");
    }
}