import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;






public class LastRequestsGUI
{
            /*  Feilds  */
    private JFrame frame;
    private ArrayList<JButton> requests;





            /*  Methods  */
    
    /**
     * This method initialize this class
     * (a kind of constructor)
     */
    public LastRequestsGUI()
    {
        frame = new JFrame();
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel insomniaText = new JLabel("   Insomnia");
        insomniaText.setBackground(new Color(102, 96, 178));
        insomniaText.setForeground(Color.WHITE);
        insomniaText.setFont (insomniaText.getFont ().deriveFont(2.0f));
        insomniaText.setOpaque(true);
        insomniaText.setHorizontalAlignment(SwingConstants.LEFT);
        
        
        frame.add(insomniaText);
        FlowLayout topLayout = new FlowLayout(FlowLayout.LEFT);

        
        BorderLayout mainLayout = new BorderLayout(5, 5);
       
        frame.setVisible(true);
    }
}