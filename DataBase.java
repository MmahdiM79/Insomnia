import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;




/**
 * This class handle storing requests
 * 
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.0.3
 */
public class DataBase 
{
            /*  Fields  */

    // main folder of store age
    private static final String MAIN_FOLDER = "./DataBase/";

    // save the request that dosen't belong to any group
    private static final String LAST_REQUESTS_FOLDER = MAIN_FOLDER + "lastRequestsFolder/";

    // default name for files
    private static final String DEFAULT_FILE_NAME = "outputـ";

    // files format
    private static final String FORMAT = ".rqstdata";

    // hold requests of the groups
    private static final HashMap<String, ArrayList<String>> GROUPS_REQUESTS = new HashMap<>();











            /*  Methods  */

    /**
     * This method save the given request in it's group
     * 
     * 
     * @param groupName : name of the request group
     * @param requestName : a name for this request
     * @param request : request to save
     * 
     * @return {@code false} a same file with given name is available
     * 
     * @throws IOException if can't make file
     */
    public static boolean saveRequest(String groupName, String requestName, Request request) throws IOException
    {
        if (groupName == null)
            groupName = LAST_REQUESTS_FOLDER;

        else if (GROUPS_REQUESTS.keySet().contains(groupName.toLowerCase()))
        {
            if (isFileAvailable(groupName, requestName))
                return false;
        }   

        else
        {
            GROUPS_REQUESTS.put(groupName.toLowerCase(), new ArrayList<>());
            GROUPS_REQUESTS.get(groupName).add(requestName);
        }       
        
        

        ObjectOutputStream requestFile = new ObjectOutputStream(new FileOutputStream(new File(getPath(groupName, requestName))));
        requestFile.writeObject(request);
        requestFile.close();
        
        

        return true;    
    }








    // this method check that a file is available or not
    private static boolean isFileAvailable(String groupName, String fileName)
    {
        File groupFolder = new File(MAIN_FOLDER + "/" + groupName);

        for (File file : groupFolder.listFiles())
        {
            if (file.toString().replaceAll(FORMAT, "").equalsIgnoreCase(fileName))
                return false;
        }

        return false;
    }


    // this method return the path for file to save
    private static String getPath(String groupName, String requestName)
    {
        return MAIN_FOLDER + "/" + groupName + "/" + requestName + FORMAT;
    }
}