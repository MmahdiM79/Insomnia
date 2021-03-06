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
 * @version 0.3.0
 */
public final class Out 
{
            /*  Fields  */

    // a file for showing time line
    private static PrintStream TIME_LINE;

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
            TIME_LINE = ERRORS_LOG = System.out;
        }
        else
        {
            try
            { 
                TIME_LINE = new PrintStream(new File(DataBase.getTimeLineGuiPath()));
                ERRORS_LOG = new PrintStream(new File(DataBase.getErrorsLogGuiPath()));
            }
            catch(FileNotFoundException e) { printErrors("guiFiles"); }
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
        TIME_LINE.println("\n\n  ------ Response Details ------  \n");


        TIME_LINE.println(" " + request.getResponseHeader("details") + "  " + request.getResponseTime() + " ms");
        
        for (String key : request.getResponseHeaders())
        {
            if (key.equals("details"))
                continue;

            TIME_LINE.println(key + ":  " + request.getResponseHeader(key));
        }



        TIME_LINE.print("\n\n\n");
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
    public static long printResponseBody(InputStream connectionInputStream) throws IOException
    {
        InputStream in = connectionInputStream;
        byte[] byts = null;
        try(FileOutputStream out = new FileOutputStream(DataBase.getResponseBodyGuiPath())) 
        {
            byts = in.readAllBytes();
            out.write(byts);
        } 
        catch (IOException ex) { Out.printErrors("cantReadResponseBody"); }


        if (terminalCheck)
            for (byte b : byts)
                System.out.print((char) b);

        File responseBodyFile = new File(DataBase.getResponseBodyGuiPath());
        return responseBodyFile.length() / 1024; 
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
        File mkdirForFile = new File(output.getParent());
        mkdirForFile.mkdirs();

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
            System.out.println("|––––– " + groupNumber +": " + group + "\n|       |");


            int requestNumber = 0;
            for (String request : requests.get(group))
            {
                Request holdForPrint = null;
                try { holdForPrint = DataBase.openRequest(groupNumber, requestNumber); }
                catch (IOException e) { System.out.println(e.getLocalizedMessage()); Out.printErrors("load"); }


                System.out.print("|       |\n|       |––––– " + requestNumber + ": ");

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
     *          'guiFiles', 
     *          'cantRemove', 
     *          'cantRemoveD', 
     *          'urlFirst', 
     *          'badEntery', 
     *          'cantReadResponseBody', 
     *          'issaved', 
     *          'bodyBuild'
     *          'multiBody'
     * 
     *      
     * 
     * @param whichCase : which error?
     * @param errorFactor : for some cases that need a addition message to show
     */
    public static void printErrors(String whichCase, String errorFactor)
    {
        if (terminalCheck)
            System.out.print("\n\nJurl:  ");


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


            case "reqbody":
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


            case "cantRemove":
                ERRORS_LOG.println(" can not remove the choosen request file");
            break;


            case "cantRemoveD":
                ERRORS_LOG.println(" can not remove the choosen group");
            break;


            case "urlFirst":
                ERRORS_LOG.println(" your first input shuold be the url of new request");
            break;


            case "badEntery":
                ERRORS_LOG.println(" bad entery for option:  " + errorFactor);
            break;


            case "cantReadResponseBody":
                ERRORS_LOG.println(" can not read response body");
            break;


            case "issaved":
                ERRORS_LOG.println(" can't save this request. there is a same file with this name and group.");
            break;


            case "bodyBuild":
                ERRORS_LOG.println(" an error occurs while trying to set requset body");
            break;


            case "multiBody":
                ERRORS_LOG.println(" you can only use one of the -j, -u and -d options");
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
        {
            ERRORS_LOG.print(whichCase + "\n" + errorFactor + "\n");
            MainFrame.showError();
        }
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
     *          'GET', 
     *          'invalidBodyKind'
     *          'noBodyKind', 
     *          'noData', 
     *          'outputNotExist', 
     *          'output', 
     *          'load', 
     *          'guiFiles', 
     *          'cantRemove', 
     *          'cantRemoveD', 
     *          'urlFirst', 
     *          'cantReadResponseBody', 
     *          'issaved', 
     *          'bodyBuild', 
     *          'multiBody'
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