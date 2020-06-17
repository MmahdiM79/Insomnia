import javax.swing.*;




/**
 * This class is the Main class of GUI of app
 * This class makes a connection between GUI and Jurl class
 * 
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.1.2
 */
public class Insomnia
{
            /*  Fields  */

    // for send req in swing worker
    private static BackGroundJurl jurl;

    // a string for Jurl class
    private static String reqDetailsString = null;
            
    // follow redirects or not
    private static boolean followRedirects = true;

    // what to do?
    private static String whatToDo = null;







            /*  Methods  */

    public static void main(String[] args)
    {
        //look and feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {}
    
    
        MainFrame.MainFrameInit();
        MainFrame.showFrame();
    }


    

    /**
     * This class starts the swing worker tread
     */
    public static void start()
    {
        jurl = new BackGroundJurl(reqDetailsString, followRedirects, whatToDo);
        jurl.execute();
    }


    /**
     * This method remove a group
     * 
     * @param groupName : name of the group that you want to remove it 
     */
    public static void removeGroup(String groupName)
    {
       DataBase.deleteGroup(groupName);
    }



    /**
     * @param followRedirects : give {@code true} if you wants to follow redirects
     */
    public static void setFollowRedirects(boolean followRedirects) { Insomnia.followRedirects = followRedirects; }

    /**
     * @return {@code false} if this request dosen't follow redirects
     */
    public static boolean isFollowRedirects() { return Insomnia.followRedirects; }

    /**
     * @param reqDetails a {@code String} of new request details
     */
    public static void setReqString(String reqDetails) { Insomnia.reqDetailsString = reqDetails; }

    /**
     * @param whatToDo : send or save
     */
    public static void setWhatToDo(String whatToDo) { Insomnia.whatToDo = whatToDo; }
}