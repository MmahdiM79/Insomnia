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

    // requests panel
    private static JPanel requestsPanel = new JPanel();

    // request details
    private static JPanel requestDetailsPanel = new JPanel();



   

    
    
    
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
            insomniaText.setFont (insomniaText.getFont().deriveFont(22.0f)); // set text size
            insomniaText.setForeground(Color.WHITE); // set text color
            insomniaText.setBackground(new Color(102, 96, 178)); // set back ground color
            insomniaText.setOpaque(true); // apply color changes
            
            
            
            // set the '+' tab
            JPanel addTab = new JPanel(); // create new panel
            addTab.setLayout(new FlowLayout()); // set the layout manager
            
            JTextField getNewTabNameField = new JTextField(" new group name ... ");
            getNewTabNameField.setPreferredSize(new Dimension(190, 33)); // set size
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


        // set the new requests details panel
        {
            requestDetailsPanel.setLayout(new BorderLayout()); // set the layout manager
            requestDetailsPanel.setBackground(new Color(46, 47, 44));
            requestDetailsPanel.setOpaque(true); 
            frame.add(requestDetailsPanel, BorderLayout.CENTER);


            // set the kind combo box
            JComboBox<String> kindComboBox = new JComboBox<>(); // create new combo box
            kindComboBox.setPreferredSize(new Dimension(100, 50)); // set size 
            kindComboBox.addItem(" GET ");
            kindComboBox.addItem(" POST ");
            kindComboBox.addItem(" PUT ");
            kindComboBox.addItem(" PATCH ");
            kindComboBox.addItem(" DELET ");
            kindComboBox.setFont(kindComboBox.getFont().deriveFont(15.0f)); // set the text size
            kindComboBox.setBackground(Color.WHITE); // set background color
            kindComboBox.setForeground(new Color(102, 96, 178)); // set foreground color
            kindComboBox.setOpaque(true); // apply color changes

            // set url text field
            JTextField getUrlTextField = new JTextField(" https:// ?! "); // create new text field
            getUrlTextField.setPreferredSize(new Dimension(300, 50)); // set size 
            getUrlTextField.setFont(getUrlTextField.getFont().deriveFont(15.0f)); // set text size
            getUrlTextField.setBackground(Color.WHITE); // set background color
            getUrlTextField.setForeground(Color.BLACK); // set foreground color
            getUrlTextField.setOpaque(true); // apply color changes

            // set send button
            JButton sendButton = new JButton(" Send "); // create new button
            sendButton.setPreferredSize(new Dimension(70, 50)); // set size
            sendButton.setBackground(Color.WHITE); // set background color
            sendButton.setForeground(Color.GREEN); // set foreground color
            sendButton.setOpaque(true); // apply color changes

            // set the north panel of the request detail
            JPanel northPanel = new JPanel(); // creat new panel
            northPanel.setBackground(Color.WHITE); // set the back ground color
            northPanel.setOpaque(true); // apply color changes
            northPanel.setLayout(new FlowLayout(0, 0, 0)); // set the layout manager
            northPanel.add(kindComboBox); // add kind combo box
            northPanel.add(getUrlTextField); // add get url text field
            northPanel.add(sendButton); // add send button

            requestDetailsPanel.add(northPanel, BorderLayout.NORTH);
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