import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;





/**
 * This class reprsent the left part of the insomnia app GUI
 * This part hold and show the last request by they group
 * 
 * 
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.1.0
 */
public class LastRequestsGUI 
{
            /*  Fields  */

    // the main panel of the last requests part
    private JPanel lastRequestsMainPanel;

    // Insomnia text label
    private JLabel insomniaLabel;

    // requests groups tabbed pane
    private JTabbedPane requestGroups;

    // add tab panel
    private JPanel addTabPanel;

    // get new tab name Text field
    private JFormattedTextField getNewTabNameField;

    // add new tab button
    private JButton addButton;

    // last requests panel
    private JPanel lastRequestsPanel;

    // background color
    private Color backgroundColor;

    









          /* Constructor */

    /**
     * Create new panel for last requests
     * 
     * 
     * @param bgColor : the color of the background
     */
    public LastRequestsGUI(Color bgColor)
    {
        // set background color
        backgroundColor = bgColor;
       


        // set the main panel
        lastRequestsMainPanel = new JPanel(); // create new panel
        lastRequestsMainPanel.setLayout(new BorderLayout()); // set layout manager



        // set the insomnia label
        insomniaLabel = new JLabel("   Insomnia        "); // set text
        insomniaLabel.setHorizontalAlignment(SwingConstants.LEFT);
        insomniaLabel.setPreferredSize(new Dimension(170, 50)); // set size
        insomniaLabel.setFont (insomniaLabel.getFont().deriveFont(22.0f)); // set text size
        insomniaLabel.setForeground(Color.WHITE); // set text color
        insomniaLabel.setBackground(new Color(102, 96, 178)); // set back ground color
        insomniaLabel.setOpaque(true); // apply color changes
        lastRequestsMainPanel.add(insomniaLabel, BorderLayout.NORTH); // add to the main panel
        
        
        
        // set the '+' tab
        addTabPanel = new JPanel(); // create new panel
        addTabPanel.setLayout(new FlowLayout()); // set the layout manager
        


        // set new tab name text field
        getNewTabNameField = new JFormattedTextField("new group name ... ");
        getNewTabNameField.setForeground(new Color(71, 71, 69)); // set foreground color
        getNewTabNameField.setPreferredSize(new Dimension(190, 33)); // set size
        getNewTabNameField.setFont(insomniaLabel.getFont().deriveFont(15.0f)); // set text size
        addTabPanel.add(getNewTabNameField); // add text field



        // set add button
        addButton = new JButton(" Add "); // creat new button
        addButton.setPreferredSize(new Dimension(70, 35)); // set size
        addTabPanel.add(addButton); // add button


        
        // set last requests panel
        lastRequestsPanel = new JPanel(); // create new panel
        lastRequestsPanel.setLayout(new BoxLayout(lastRequestsPanel, BoxLayout.Y_AXIS)); // set the layout manager
        lastRequestsPanel.setBackground(backgroundColor); // set the background color
        lastRequestsPanel.setOpaque(true); // apply color changes



        // set request grouping
        requestGroups = new JTabbedPane(); // create new tabbed pane
        requestGroups.setBackground(backgroundColor); // set color
        requestGroups.setOpaque(true); // apply color changes
        requestGroups.addTab("last requests", lastRequestsPanel); 
        requestGroups.addTab(" + ", addTabPanel); 
        lastRequestsMainPanel.add(requestGroups, BorderLayout.CENTER); // add to the main panel
    }
}