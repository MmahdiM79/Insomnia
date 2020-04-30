import javax.swing.JFrame;




/**
 * This class is the main frame of the app
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.0.0
 */
public class MainFrame
{
    // main frame
    private static JFrame frame;




    /**
     * Initialize the frame ( a kind of Constructor )
     */
    public static void MainFrameInit()
    {
        frame = new JFrame(" Insomnia ");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(300, 200);
    }


    /**
     * This method show the main frame
     * 
     * @see JFrame#setVisible(boolean)
     */
    public static void showFrame()
    {
        frame.setVisible(true);
    }
}