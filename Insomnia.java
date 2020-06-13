import javax.swing.*;
import java.awt.*;




/**
 * This class is the Main class of GUI of app
 * This class makes a connection between GUI and Jurl class
 * 
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.0.0
 */
public class Insomnia extends SwingWorker
{
            /*  Fields  */

    // follow redirects or not
    private static boolean followRedirects = true;






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
     * @param followRedirects : give {@code true} if you wants to follow redirects
     */
    public static void setFollowRedirects(boolean followRedirects) { Insomnia.followRedirects = followRedirects; }

    /**
     * @return {@code false} if this request dosen't follow redirects
     */
    public static boolean getFollowRedirects() { return followRedirects; }



    @Override
    protected Object doInBackground() throws Exception 
    {
	    // TODO Auto-generated method stub
	    return null;
    }

}