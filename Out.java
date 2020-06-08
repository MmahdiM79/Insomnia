import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;





/**
 * This class manage outputs of this app.
 * First of all call {@link Out#init(boolean)}. (it's a kind of constructor for this class).
 * 
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.1.9
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
            catch(FileNotFoundException e) { printErrors("guiFiles"); }
        }


        try { RESPONSE_BODY = new PrintStream(new File(DataBase.getResponseBodyGuiPath())); }
        catch(FileNotFoundException e) { printErrors("guiFiles"); }
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
     * This method make a copy of respnse body in given file
     * 
     * @param where to save resposne body
     */
    public static void saveOutput(String where, String format)
    {
        format = (format == null) ? ".txt" : format;



        String outputFilePath;

        if (where == null)
            outputFilePath = DataBase.getEmptyOutputFilePath(format);

        else if ((new File(where)).getParent() == null)
            outputFilePath = DataBase.getOuputsFolderPath() + where;

        else 
            outputFilePath = where;



        File output = new File(outputFilePath);

        try (BufferedInputStream responseBodyFile = new BufferedInputStream(new FileInputStream(DataBase.getResponseBodyGuiPath()));
             BufferedOutputStream outputFile = new BufferedOutputStream(new FileOutputStream(output)))
        {
            outputFile.write(responseBodyFile.readAllBytes());
        }
        catch(IOException e){ Out.printErrors("output"); }
    }


    /**
     * This method show a list of requests in terminal
     * 
     * 
     * @param requests : saved requests
     */
    public static void list(HashMap<String, ArrayList<String>> requests)
    {
        System.out.print("\n\n");

        System.out.println(". Request Groups");
        System.out.println("|\n|");



        int groupNumber = 0; 
        for (String group : requests.keySet())
        {
            System.out.println("|––––– " + groupNumber +": " + group + "\n|       |\n|       |");


            int requestNumber = 0;
            for (String request : requests.get(group))
            {
                Request holdForPrint = null;
                try { holdForPrint = DataBase.openRequest(groupNumber, requestNumber); }
                catch (IOException e) { System.out.println(e.getLocalizedMessage()); Out.printErrors("load"); }


                System.out.print("|       |––––– " + requestNumber + ": ");

                System.out.print("url: " + holdForPrint.getUrl() + " | ");
                System.out.print("method: " + RequestKinds.getKind(holdForPrint.getRequestKind()) + " | ");
                System.out.print("headers: { ");
                    if (holdForPrint.getrequestHeadersKeys() != null)
                        for (String key : holdForPrint.getrequestHeadersKeys())
                            System.out.print(key + ": " + holdForPrint.getRequestHeader(key) + ", ");
                    else
                        System.out.print(" ");
                System.out.print("\b\b } | ");
                

                if (holdForPrint instanceof RequestWithBody)
                {
                    RequestWithBody chengeType = (RequestWithBody) holdForPrint;

                    System.out.print("content type: " + chengeType.getContentType() + " | ");
                }

                System.out.print("\n");
                requestNumber++;
            }

            System.out.println("|\n|");
            groupNumber++;
        }
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
     *          'outputNotExist', 
     *          'output', 
     *          'load', 
     *          'guiFiles'
     * 
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


            case "save":
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


            case "outputNotExist":
                ERRORS_LOG.println(" your given path for option output not exist");
            break;


            case "output":
                ERRORS_LOG.println(" an error occurred when trying to write response body on given file for option --output");
            break;


            case "load":
                ERRORS_LOG.println(" an error occurued when trying to load saved files");
            break;


            case "guiFiles":
                ERRORS_LOG.println(" can not open GUI defalt files");
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
     *          'outputNotExist', 
     *          'output', 
     *          'load', 
     *          'guiFiles'
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