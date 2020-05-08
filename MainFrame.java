import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;





/**
 * This class is the main frame of the app
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.0.0
 */
public class MainFrame
{
            /*  Feilds  */

    // main frame
    private static JFrame frame = new JFrame(" Insomnia ");

    // requesrs panel
    private static JPanel requestsPanel = new JPanel();




   

    
    
    
          /* Constructor */
    
    /**
     * Create the app GUI frame
     */
    public static void MainFrameInit()
    {
        // set the frame
        frame.setLayout(new BorderLayout()); // set frame layout
        frame.setMinimumSize(new Dimension(700, 300)); // set minimum size
        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);






        // set the requests panel
        {
            // set the insomnia label
            JLabel insomniaText = new JLabel("   Insomnia        "); // set text
            insomniaText.setHorizontalAlignment(SwingConstants.LEFT);
            insomniaText.setPreferredSize(new Dimension(170, 50)); // set size
            insomniaText.setFont (insomniaText.getFont().deriveFont(25.0f)); // set text size
            insomniaText.setForeground(Color.WHITE); // set text color
            insomniaText.setBackground(new Color(102, 96, 178)); // set back ground color
            insomniaText.setOpaque(true); // apply color changes
            
            
            
            // set the '+' tab
            JPanel addTab = new JPanel(); // create new panel
            addTab.setLayout(new FlowLayout()); // set the layout manager
            
            JTextField getNewTabNameField = new JTextField(" new group name ... ");
            getNewTabNameField.setPreferredSize(new Dimension(170, 33)); // set size
            getNewTabNameField.setFont(insomniaText.getFont().deriveFont(15.0f)); // set text size

            JButton addButton = new JButton(" Add "); // creat new button
            addButton.setPreferredSize(new Dimension(70, 35)); // set size

            addTab.add(getNewTabNameField); // add text field
            addTab.add(addButton); // add button

            addTab.setBackground(new Color(46, 47, 44)); // set color
            addTab.setOpaque(true); // apply color changes



            // set request grouping
            JTabbedPane requestGroups = new JTabbedPane(); // create new tabbed pane
            requestGroups.setBackground(new Color(46, 47, 44)); // set color
            requestGroups.setOpaque(true); // apply color changes
            requestGroups.addTab("last requests", new JPanel()); 
            requestGroups.addTab(" + ", addTab);      


            requestsPanel.setLayout(new BorderLayout());
            requestsPanel.add(insomniaText, BorderLayout.NORTH);
            requestsPanel.add(requestGroups, BorderLayout.CENTER);

            
            frame.add(requestsPanel, BorderLayout.WEST);
        }
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