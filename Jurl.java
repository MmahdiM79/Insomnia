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
 * @version 0.2.0
 */
public class Jurl 
{
            /*  Fields  */

    // client inputs
    private static ArrayList<String> args = new ArrayList<>();

    // app difined args
    private static String[]  options =  {"-m", "--method",   // set kind of the request 
                                         "-h", "--headers",  // set headers of the request
                                         "-d", "--data",     // set datas for message body
                                         "-o", "--output",   // save response body to given file
                                         "-s", "--save",     // save request
                                         "-q", "--query",    // add a query to request
                                         "-u", "--upload",   // send a file as request body
                                         "-l", "--list",     // show the list of the request groups
                                         "rm", "--remove",   // remove a request or group
                                         "so", "--save-over",// override saving
                                               "send",     // send again the given requests
                                               "--help",     // open help text
                                         "-f",               // follow redirect
                                         "-i",               // show the response or not

                                         "--GUI--"           // set the outputs to GUI
                                        }; 


    //show outputs on terminal or not
    private static boolean terminal = true;

    // where to save response body
    private static String outputFileName = null;

    // address of file to upload
    private static String uploadFilePath = null;

    // check that user do anything or not
    private static boolean doSomeThing = false;



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
        // set user inputs in a ArrayList
        getArgs(inputs);

        // check number of the user inputs
        if (args.size() == 0)
        {
            System.out.println("Jurl:   no option given");
            System.out.print("Jurl:   try 'java Jurl --help' for more information\n");
            return;
        }


        // check that user is using GUI or not 
        if (args.contains("--GUI--"))
            terminal = false;
        else    
        {
            System.out.println("\033[H\033[2J");
            System.out.flush();
        }


        // initialize static classes
        init();




        // check that user wants to send a request or someting else
        if (args.contains("rm") || args.contains("--remove") || args.contains("--send"))
            doSomeThing = true;






        // do help option
        if (args.contains("--help"))
        {
            showHelp();
            return;
        }

        // do remove option
        if (args.contains("rm") || args.contains("--remove"))   
        {
            doSomeThing = true;
            removeOption();
        }

        // do list option
        if (args.contains("-l") || args.contains("--list"))
        {
            Out.list(DataBase.getRequests());
            return;
        }

    
        // check that user has given a url or not
        if (!doSomeThing && isArgDefined(args.get(0)))
            Out.printErrors("urlFirst");
        else if (!isArgDefined(args.get(0)) && doSomeThing)
            return;

        
        // set url
        url = args.get(0);


        // see and set user inputs
        checkInputsAndSetFields();


        // do save option
        if (args.contains("-s") || args.contains("--save"))
            saveOption();



        // creat request with user given details
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

        

        // save request if user want it 
        if (args.contains("-s") || args.contains("--save"))
            request.saveToFile();


        // send the request
        request.run();


        // do output option
        if (args.contains("-o") || args.contains("--output"))
            Out.saveOutput(outputFileName, request.getResponseBodyFormat());


        // do send option
        if (args.contains("send"))
            sendOption();
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

        query = checkArg("-q", "--query", true);


        if (args.contains("-o") || args.contains("--output"))
        {
            checkToTime("-o", "--output");

            String usedArg = args.contains("-o") ? "-o" : "--output";


            if (args.indexOf(usedArg)+1 < args.size() && !isArgDefined(args.get(args.indexOf(usedArg)+1)))
                outputFileName = args.get(args.indexOf(usedArg)+1);
        }
    }


    // this method handle the remove option
    private static void removeOption()
    {
        String selectedArg = args.contains("rm") ? "rm" : "--remove";
        checkHasEntery(selectedArg);

        String rmCode = args.get(args.indexOf(selectedArg)+1);
        
        if (rmCode.length() == 2)
            DataBase.removeRequest(rmCode);
        else
            DataBase.removeGroup(rmCode);
    }


    // check the save option and handle it
    private static void saveOption()
    {
        String selectedArg = args.contains("-s") ? "-s" : "--save";
        checkHasEntery(selectedArg);

        if (args.contains("so") || args.contains("--save-over"))
            DataBase.setOverride(true);


        if (args.indexOf(selectedArg)+2 < args.size() && !isArgDefined(args.get(args.indexOf(selectedArg)+2)))
        {
            group = args.get(args.indexOf(selectedArg)+1);
            name = args.get(args.indexOf(selectedArg)+2);
        }
        else
            name = args.get(args.indexOf(selectedArg)+1);
    }


    private static void sendOption()
    {
        checkHasEntery("send");


        ArrayList<String> sendEntries = new ArrayList<>();
        for (int i = args.indexOf("send")+1; i < args.size(); i++)
        {
            if (!isArgDefined(args.get(i)))
            {
                if (args.get(i).length() != 2)
                    Out.printErrors("badEntery", "send");
                else
                    sendEntries.add(args.get(i));
            }
            else 
                break;
        }


        Request reqToResend = null;
        for (String reqCode : sendEntries)
        {
            try { reqToResend = DataBase.openRequest(reqCode.charAt(0)-'0', reqCode.charAt(1)-'0'); }
            catch (IOException e) { Out.printErrors("load"); }

            reqToResend.run();
        }        
    }
    

    // this method check that user given arg is defined or not
    private static boolean isArgDefined(String arg)
    {
        if (arg == null)
            return false;


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
    private static String checkArg(String argShort, String argLong, boolean setEntery)
    {
        if (args.contains(argShort) || args.contains(argLong))
        {
            checkToTime(argShort, argLong);

            String usedArg = args.contains(argShort) ? argShort : argLong;


            if (setEntery)
            {
                checkHasEntery(usedArg);
                return args.get(args.indexOf(usedArg)+1);
            }
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
    private static void checkToTime(String s1, String s2)
    {
        if (args.contains(s1) && args.contains(s2))
            Out.printErrors("twotime", s1 + " and " + s2);
    }


    private static void showHelp()
    {
        System.out.println(
        "\n \t\t\t\t ------------------------------------------------- \n\n" + 
        "Jurl is a tool to sent http request over terminal.\n" + 
        "this tool support GET, POST, PUT and DELETE methods for requests.\n\n" +
        "contact us:  https://github.com/MmahdiM79 \n\n" +

        "java Jurl < url - if you want send new request > < options >\n\n" +

        "optins:\n\n" + 

        "\t -m, --method    <method to set>\n\n" + 
        "\t\t use this option to set your request method.\n" +
        "\t\t if you don't use this option, request method will set as GET.\n" + 
        "\t\t supported methods: GET, POST, PUT, DELETE.\n\n\n" +

        "\t -q, --query    \"key=value&key1=value1& ...\" \n\n" +
        "\t\t use this option to set a query to your request. \n" +
        "\t\t some example for bad entry:  \"=value\", \"=\" \n\n\n" +

        "\t -h, --headers    \"key:value,key1:value1, ...\" \n\n" +
        "\t\t use this option to set the headers of the new request.\n" + 
        "\t\t your given key or value can't be empty.\n" + 
        "\t\t some examples for bad entry:  \":value\", \"key:\", \":\" \n\n\n" +

        "\t -i \n" +
        "\t\t use this option if you want to see the response details.\n\n\n" +

        "\t -f \n" +
        "\t\t if you use this option, Jurl will follow redirects. \n\n\n" +

        "\t -o, --output    <file neme, file absolute path, none>\n\n" +
        "\t\t use this method to save response body in a file.\n" +
        "\t\t outputs will save in outputs folder in app folder.\n\n\n" +

        "\t -s, --save    <group name, none> <request name, none> \n\n" + 
        "\t\t use this option to save your request details. \n" + 
        "\t\t if you don't give the group name, your request will save in last requests foder. \n" +
        "\t\t if you don't give the request name, your request will save with a default name. \n\n\n" +

        "\t so, --save-over \n" +
        "\t\t if you use this file option, your request will save with given name and group, \n" +
        "\t\t even a request with given name and group is available. \n" +
        "\t\t this option should use with -s or --save. \n\n\n" +

        "\t -d, --data    \"key=value&key1=value1& ...\" \n\n" +
        "\t\t use this method to give your datas for request body. \n" +
        "\t\t your given datas with this option, will send as form-data. \n" + 
        "\t\t with this option, your body request kind will set as Form Data. \n" +
        "\t\t if you want send a binary file with your request in Form Data kind,  \n" +
        "\t\t add '(FILE)' at the end of your key and set your file path as value of this key \n" +
        "\t\t some example for bad entry: \"key=\", \"=value\", \"=\", \"key\" \n\n\n" +

        "\t -j, --json    {key:value,key1:value1, ...} \n\n" + 
        "\t\t use this option to send a JSON with your request. \n" +
        "\t\t if you use this option your body kind will set as JSON. \n\n\n" +

        "\t -u, --upload    <file absolute path> \n\n" +
        "\t\t use this option to send a file with your request. \n" +
        "\t\t if you use this option your request body kind, will set as binary file. \n\n\n" + 

        "\t -l, --list \n" +
        "\t\t this metod will show you a list of saved requests. \n\n\n" +

        "\t rm, --remove    <group number><request number, none> \n\n" +
        "\t\t use this option to remove a saved request. \n" +
        "\t\t 'java Jurl rm 0' means that remove group number 0. \n" +
        "\t\t 'java Jurl rm 01' means that remove request number 1 from group number 0. \n" + 
        "\t\t this option only gives one code (remove a group or remove a request) at the same time. \n\n\n" +

        "\t send    <request code> \n\n" +
        "\t\t use this request to send a saved request. \n" +
        "\t\t 'java Jurl send 00' means that re-send request number 0 form group number 0" + 

        "\n\n \t\t\t\t ------------------------------------------------- \n\n"
        );
    }
}