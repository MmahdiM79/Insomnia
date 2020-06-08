import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;




/**
 * This class handle storing requests and files 
 * 
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.1.8
 */
public class DataBase 
{
            /*  Fields  */


    // main folder of store age
    private static final String MAIN_FOLDER = "./.DataBase/";

    // save the request that dosen't belong to any group
    private static final String LAST_REQUESTS_FOLDER = ".lastRequestsFolder/";



    // Gui files folder
    private static final String GUI_FOLDER = "./.GUI/";
    // time line file for GUI
    private static final String TIME_LINE_GUI = ".timeline.data";

    // time line file for GUI
    private static final String RESPONSE_HEADERS = ".responseHeaders.data";
    
    // time line file for GUI
    private static final String RESPONSE_BODY = ".responseBody.data";

    // time line file for GUI
    private static final String ERRORS_LOG = ".errorslog.data";



    // outputs default folder
    private static final String OUTPUTS_FOLDER = "./outputs/";

    // default name for files
    private static final String DEFAULT_OUTPUT_FILE_NAME = "outputـ";



    // files format
    private static final String FORMAT = ".rqstdata";

    // hold requests of the groups
    private static final HashMap<String, ArrayList<String>> GROUPS_REQUESTS = new HashMap<>();












            /*  Methods  */


    /**
     * This method set the static fields of this class
     * call this method at the first of the program (befor starting to use other methods of this class)
     * (this method is like a constructor for this class)
     */
    public static void init()
    {
        File setDefaults = new File(MAIN_FOLDER + LAST_REQUESTS_FOLDER);
        setDefaults.mkdirs();


        File groups = new File(MAIN_FOLDER);

        for (File group : groups.listFiles())
        {
            if (group.getName().equals(".DS_Store"))
                continue;


            GROUPS_REQUESTS.put(group.getName(), new ArrayList<>());

            for (File request : group.listFiles())
                GROUPS_REQUESTS.get(group.getName()).add(request.getName().replaceAll(FORMAT, "")); 
        } 
        

        File outputsFolder = new File(OUTPUTS_FOLDER);
        outputsFolder.mkdirs();
        

        File guiFolder = new File(GUI_FOLDER);
        guiFolder.mkdirs();

        FileOutputStream setFile;
        try
        {
            setFile = new FileOutputStream(new File(GUI_FOLDER + TIME_LINE_GUI));
            setFile.close();

            setFile = new FileOutputStream(new File(GUI_FOLDER + RESPONSE_HEADERS));
            setFile.close();

            setFile = new FileOutputStream(new File(GUI_FOLDER + RESPONSE_BODY));
            setFile.close();

            setFile = new FileOutputStream(new File(GUI_FOLDER + ERRORS_LOG));
            setFile.close();
        }
        catch(IOException e){System.out.println(e.getStackTrace());} 
    }


    /**
     * This method save the given request in it's group
     * 
     * 
     * @param groupName : name of the request group
     * @param requestName : a name for this request
     * @param request : request to save
     * @param override : save file if there is a file with same name
     * 
     * @return {@code false} a same file with given name is available
     * 
     * @throws IOException if can't make file
     */
    public static boolean saveRequest(String groupName, String requestName, Request request, boolean override) throws IOException
    {
        if (groupName == null)
            groupName = LAST_REQUESTS_FOLDER;

        else if (GROUPS_REQUESTS.keySet().contains(groupName.toLowerCase()))
        {
            if (isFileAvailable(groupName, requestName) && !override)
                return false;
        }   

        else
        {
            GROUPS_REQUESTS.put(groupName.toLowerCase(), new ArrayList<>());
            mkdir(groupName);
        }
        

        ObjectOutputStream requestFile = new ObjectOutputStream(new FileOutputStream(new File(getPath(groupName, requestName))));
        requestFile.writeObject(request);
        requestFile.close();
        

        GROUPS_REQUESTS.get(groupName).add(requestName);

        return true;    
    }


    /**
     * This method read a {@code Request} object from file
     * 
     * 
     * @param groupName : number of the request group
     * @param requestIndex : number of the request in group
     * 
     * @return your choosen {@code Request}
     * 
     * @throws IOException if can't open your choosen file
     * @throws IndexOutOfBoundsException if you given an invalid request number
     */
    public static Request openRequest(int groupIndex, int requestIndex) 
    throws IOException, IndexOutOfBoundsException
    {
        String groupName = null; int cntr = 0;
        for (String holdName : GROUPS_REQUESTS.keySet())
        {
            groupName = holdName;

            if (cntr == groupIndex)
                break;

            cntr++;
        }
        if (cntr != groupIndex)
            throw new IndexOutOfBoundsException();


        String requestName = GROUPS_REQUESTS.get(groupName).get(requestIndex);
        ObjectInputStream requestFile = new ObjectInputStream(new FileInputStream(new File(getPath(groupName, requestName))));  
        
        try { return (Request) requestFile.readObject(); } 
        catch (ClassNotFoundException e) { return null; }
        finally { requestFile.close(); }
    }


    /**
     * @return the path of time line gui file
     */
    public static String getTimeLineGuiPath()
    {
        return getPath(GUI_FOLDER, TIME_LINE_GUI);
    }


    /**
     * @return the path of response Headers gui file
     */
    public static String getResponseHeadersGuiPath()
    {
        return getPath(GUI_FOLDER, RESPONSE_HEADERS);
    }


    /**
     * @return the path of response body gui file
     */
    public static String getResponseBodyGuiPath()
    {
        return getPath(GUI_FOLDER, RESPONSE_BODY);
    }


    /**
     * @return the path of errors log gui file
     */
    public static String getErrorsLogGuiPath()
    {
        return getPath(GUI_FOLDER, ERRORS_LOG);
    }


    /**
     * @return path of default outputs folder
     */
    public static String getOuputsFolderPath()
    {
        return OUTPUTS_FOLDER;
    }


    /**
     * @param format : format of the output file
     * @return path of a empty file for save an output of a request
     */
    public static String getEmptyOutputFilePath(String format)
    {
        return OUTPUTS_FOLDER + DEFAULT_OUTPUT_FILE_NAME + 
                (new Date()).toLocaleString().replaceAll(" ", "_").replaceAll(",", "") + format;
    }










    // this method check that a file is available or not
    private static boolean isFileAvailable(String groupName, String fileName)
    {
        File groupFolder = new File(MAIN_FOLDER + groupName);

        for (File file : groupFolder.listFiles())
        {
            if (file.toString().replaceAll(FORMAT, "").equalsIgnoreCase(fileName))
                return true;
        }

        return false;
    }


    // this method add the new group
    private static void mkdir(String groupName)
    {
        File mkdir = new File(MAIN_FOLDER + groupName);
        mkdir.mkdirs();
    }


    // this method return the path for file to save
    private static String getPath(String groupName, String requestName)
    {
        return MAIN_FOLDER + "/" + groupName + "/" + requestName + FORMAT;
    }
}
