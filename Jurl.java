import java.io.*;
import java.util.*;
import java.net.*;
import java.net.http.*;





/**
 * This class is for sending a request via Terminal
 * This class make a http request and send it to given host
 * 
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.1.3
 */
public class Jurl 
{
            /*  Fields  */

    // client inputs
    private static ArrayList<String> args = new ArrayList<>();

    // app difined args
    private static String[]  options =  {"-m", "--method",   // set kind of the request 
                                         "-b", "--body",     // set kind of the request body
                                         "-h", "--headers",  // set headers of the request
                                         "-d", "--data",     // set datas for message body
                                         "-i",               // show the response or not
                                               "--help",     // open help text
                                         "-f",               // follow redirect
                                         "-o", "--output",   // save response body to given file
                                         "-s", "--save",     // save request
                                         "-q", "--query",    // add a query to request
                                         "-u", "--upload",   // send a file as request body
                                         "-l", "--list",     // show the list of the request groups
                                               "--send",     // send again the given requests
                                         "-r", "--remove",   // remove a request or group
                                         "--GUI--"           // set the outputs to GUI
                                        }; 

    // kinds of the request body
    private static String[] bodyKinds = {"form-data", "json", "binary-file"};

    //show outputs on terminal or not
    private static boolean terminal = true;

    // where to save response body
    private static String outputFileName = null;

    // address of file to upload
    private static String uploadFilePath = null;



    // * new request detials *

    private static Request request;

    private static String name = null;
    private static String group = null;
    private static String url;
    private static String method = null;
    private static String headers = null;
    private static String query = null;
    private static String requestBodyKind = null;
    private static String bodyDatas = null;
    private static boolean followRedirect = false;
    private static boolean showResponseHeaders = false;










    public static void main(String[] inputs) throws IOException
    {
        getArgs(inputs);
        System.out.println(args.toString());
        if (args.contains("--GUI--"))
            terminal = false;


        init();

        
        url = args.get(0);
        checkInputsAndSetFields();


        if (!method.equals("GET"))
            request = new RequestWithBody(name, 
                                          group, 
                                          url, 
                                          method, 
                                          headers, 
                                          requestBodyKind, 
                                          bodyDatas, 
                                          query, 
                                          followRedirect, 
                                          showResponseHeaders);

        else
            request = new Request(name, group, url, headers, query, followRedirect, showResponseHeaders);

        

        if (args.contains("-s") || args.contains("--save"))
            request.saveToFile();


        request.run();
    }





            /*  Methods  */

    // This method add Args to a Arraylist
    private static void getArgs(String[] inputs)
    {
        for(String arg : inputs)
        {
            if (isArgDefined(arg))
            {
                Jurl.args.add(arg.toLowerCase());
                continue;
            }


            Jurl.args.add(arg);
        }
    }


    // this method prepare classes for start the app
    private static void init()
    {
        DataBase.init();
        Out.init(terminal);
    }


    // this method check the player inputs and set fields
    private static void checkInputsAndSetFields()
    {
        for (String arg : args)
            if (arg.charAt(0) == '-')
                if (!isArgDefined(arg))
                    Out.printErrors("invalidArg", arg);

        
        
        checkAndSetMethod();
        if (method.equals("get") && (args.contains("-d") || args.contains("--data")))
            Out.printErrors("GET");

        
        if (args.contains("-i"))
            showResponseHeaders = true;

        if (args.contains("-f"))
            followRedirect = true;

        
        headers = checkArg("-h", "--headers", true);

        bodyDatas = checkArg("-d", "--data", true);



        requestBodyKind = checkArg("-b", "--body", true);
        if (requestBodyKind != null)
        {
            boolean isGivenKindValid = false;
                for (String kind : bodyKinds)
                    if (requestBodyKind.equals(kind))
                        isGivenKindValid = true;
    

                if (!isGivenKindValid)
                    Out.printErrors("invalidBodyKind");
        }

        if (requestBodyKind == null && bodyDatas != null)
            Out.printErrors("noBodyKind");

        if (requestBodyKind != null && bodyDatas == null)
            Out.printErrors("noData");

        
        
        query = checkArg("-q", "--query", true);

        checkArg("-r", "--remove", false);
    }

    
    // this method check that user given arg is defined or not
    private static boolean isArgDefined(String arg)
    {
        arg = arg.toLowerCase();

        for (String difinedArg : options)
            if (difinedArg.equals(arg))
                return true;

        return false;
    }


    // this method check and set request method
    private static void checkAndSetMethod()
    {
    
        if (!(args.contains("-m") || args.contains("--method")))
        {
            method = "GET";
            return;
        }

        checkToTime("-m", "--method");

        String usedArg = args.contains("-m") ? "-m" : "--method";
        checkHasEntery(usedArg);


        if (RequestKinds.getKind(args.get(args.indexOf(usedArg)+1)) == null)
            Out.printErrors("invalidMethod", args.get(args.indexOf(usedArg)+1));


        method = args.get(args.indexOf(usedArg)+1).toLowerCase();
    }


    // this method check that the users used a option true or not and return the arg entery
    private static String checkArg(String argShort, String argLong, Boolean setEntery)
    {
        if (args.contains(argShort) || args.contains(argLong))
        {
            checkToTime(argShort, argLong);

            String usedArg = args.contains(argShort) ? argShort : argLong;
            checkHasEntery(usedArg);

            if (setEntery)
                return args.get(args.indexOf(usedArg)+1);
        }

        return null;
    }


    // this method check that given option has a entry or not
    private static void checkHasEntery(String option)
    {
        try
        {
            if (isArgDefined(args.get(args.indexOf(option)+1)))
                Out.printErrors("noEntery", option);

        } catch (IndexOutOfBoundsException e) { Out.printErrors("noEntery", option); }
    }


    // this method end app if an option given to times
    public static void checkToTime(String s1, String s2)
    {
        if (args.contains(s1) && args.contains(s2))
            Out.printErrors("twotime", s1 + " and " + s2);
    }
}