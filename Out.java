import java.io.*;
import java.util.Date;





/**
 * This class manage outputs of this app.
 * First of all call {@link Out#init(boolean)}. (it's a kind of constructor for this class).
 * 
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.1.6
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
            TIME_LINE = RESPONSE_HEADERS = ERRORS_LOG = System.out;
        }
        else
        {
            try
            { 
                TIME_LINE = new PrintStream(new File(DataBase.getTimeLineGuiPath()));
                RESPONSE_HEADERS = new PrintStream(new File(DataBase.getResponseHeadersGuiPath())); 
                ERRORS_LOG = new PrintStream(new File(DataBase.getErrorsLogGuiPath()));
            }
            catch(FileNotFoundException e) { System.out.println(e.getLocalizedMessage()); System.exit(0);}
        }


        try { RESPONSE_BODY = new PrintStream(new File(DataBase.getResponseBodyGuiPath())); }
        catch(FileNotFoundException e) { System.out.println(e.getLocalizedMessage()); System.exit(0);}
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


        TIME_LINE.println(" " + RequestKinds.getKind(request.getRequestKind()) + " " +
                          request.getUrl().getFile() + " " +
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
     * @return size of the request body in kilo byte
     * 
     * @throws IOException if can't read from given stream
     */
    public static double printResponseBody(InputStream connectionInputStream) throws IOException
    {
        byte[] character = new byte[1];


        while (connectionInputStream.read(character) != -1)
        { 
            RESPONSE_BODY.print((char) (character[0]));

            if (terminalCheck)
                System.out.print((char) (character[0]));
        }


        File responseBodyFile = new File(DataBase.getResponseBodyGuiPath());
        return responseBodyFile.length() / 1024f; 
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
     *          'invalidArg', 
     *          'host', 
     *          'twotime', 
     *          'noEntery', 
     *          'invalidMethod', 
     *          'GET', 
     *          'noBodyKind', 
     *          'isDirectory'
     *      
     * 
     * @param whichCase : which error?
     * @param errorFactor : for some cases that need a addition message to show
     */
    public static void printErrors(String whichCase, String errorFactor)
    {
        if (terminalCheck)
            System.out.print("Jurl:  ");


        switch (whichCase)
        {
            case "header":
                ERRORS_LOG.println(" your given headers are invalid");
            break;


            case "internet":
                ERRORS_LOG.println(" failed to open connection");
            break;


            case "query":
                ERRORS_LOG.println(" your given Query is invalid");
            break;


            case "sava":
                ERRORS_LOG.println(" failed to save this request");
            break;


            case "reqBody":
                ERRORS_LOG.println(" failed to set the body of this request");
            break;


            case "invalidArg":
                ERRORS_LOG.println(" option " + errorFactor + ":  is unknown");
            break;


            case "host":
                ERRORS_LOG.println(" could not resolve host: " + errorFactor);
            break;


            case "twotime":
                ERRORS_LOG.println(" you can't use " + errorFactor + " together");
            break;


            case "noEntery":
                ERRORS_LOG.println(" option " + errorFactor + ": requires parameter");
            break;


            case "invalidMethod":
                ERRORS_LOG.println(" method " + errorFactor + ": is invalid");
            break;


            case "GET":
                ERRORS_LOG.println(" can not send a body with GET method");
            break;


            case "invalidBodyKind":
                ERRORS_LOG.println(" your given body kind is unknown");
            break;


            case "noBodyKind":
                ERRORS_LOG.println(" please set a kind for your given datas");
            break;


            case "noData":
                ERRORS_LOG.println(" no given data for this request body");
            break;


            case "isDirectory":
                ERRORS_LOG.println(" your given path for option output is directory");
            break;



            default:
            break;
        }


        if (terminalCheck)
        {
            ERRORS_LOG.print("Jurl:   try 'java Jurl --help' for more information\n");
            System.exit(0);
        }
        else
            ERRORS_LOG.print("\n");
    }


    /**
     * A simple mode of {@link Out#printErrors(String, String)}.
     * printErrors("example") is equal to printErrors("example", null).
     * 
     * <p> Error Cases: </p>
     *          'header', 
     *          'internet',
     *          'query',
     *          'save',
     *          'reqbody',
     *          'invalidArg',
     *          'GET', 
     *          'invalidBodyKind'
     *          'noBodyKind', 
     *          'noData', 
     *          'isDirectory'
     *          
     * 
     * 
     * @param whichCase : which error
     */
    public static void printErrors(String whichCase)
    {
        printErrors(whichCase, null);
    }
}