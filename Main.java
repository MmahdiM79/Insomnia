import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;




public class Main 
{
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
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }

    
        MainFrame.MainFrameInit();
        MainFrame.showFrame();
    }
}