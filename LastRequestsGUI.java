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
 * @version 0.1.1
 */
public class LastRequestsGUI extends JPanel
{
            /*  Fields  */


    // Insomnia text label
    private JLabel insomniaLabel;

    // requests groups tabbed pane
    private JTabbedPane requestGroups;

    // groups name combo box
    private JComboBox<String> groupsComboBox;

    // add group tab panel
    private JPanel addGroupTabPanel;

    // get new tab name Text field
    private JFormattedTextField getNewTabNameField;

    // add new group button
    private JButton addGroupButton;

    // remove group tab panel
    private JPanel removeGroupTabPanel;

    // remove group button
    private JButton removeGroupButton;

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
        // set the super class
        super();
        this.setLayout(new BorderLayout()); // set layout manager
        


        // set background color
        backgroundColor = bgColor;



        // set the insomnia label
        insomniaLabel = new JLabel("   Insomnia        "); // set text
        insomniaLabel.setHorizontalAlignment(SwingConstants.LEFT);
        insomniaLabel.setPreferredSize(new Dimension(170, 50)); // set size
        insomniaLabel.setFont (insomniaLabel.getFont().deriveFont(22.0f)); // set text size
        insomniaLabel.setForeground(Color.WHITE); // set text color
        insomniaLabel.setBackground(new Color(102, 96, 178)); // set back ground color
        insomniaLabel.setOpaque(true); // apply color changes
        this.add(insomniaLabel, BorderLayout.NORTH); // add to the main panel
        
        
        
        // set the '+' tab
        addGroupTabPanel = new JPanel(); // create new panel
        addGroupTabPanel.setLayout(new FlowLayout()); // set the layout manager
        addGroupTabPanel.setBackground(backgroundColor); // set background color
        addGroupTabPanel.setOpaque(true); // apply color changes
        


        // set new tab name text field
        getNewTabNameField = new JFormattedTextField("new group name ... ");
        getNewTabNameField.setForeground(new Color(71, 71, 69)); // set foreground color
        getNewTabNameField.setPreferredSize(new Dimension(190, 33)); // set size
        getNewTabNameField.setFont(insomniaLabel.getFont().deriveFont(15.0f)); // set text size
        addGroupTabPanel.add(getNewTabNameField); // add text field



        // set add button
        addGroupButton = new JButton(" Add "); // creat new button
        addGroupButton.setPreferredSize(new Dimension(70, 35)); // set size
        addGroupTabPanel.add(addGroupButton); // add button



        // set the '-' tab
        removeGroupTabPanel = new JPanel(); // create new panel
        removeGroupTabPanel.setLayout(new FlowLayout()); // set layout manager
        removeGroupTabPanel.setBackground(backgroundColor); // set background color
        removeGroupTabPanel.setOpaque(true); // apply color changes



        // set groups combo box
        groupsComboBox = new JComboBox<>(); // create new combo box
        groupsComboBox.setPreferredSize(new Dimension(193, 33)); // set size
        groupsComboBox.setFont(groupsComboBox.getFont().deriveFont(15.0f)); // set text size
        removeGroupTabPanel.add(groupsComboBox); // add groups combo box



        // set remove button
        removeGroupButton = new JButton(" Remove "); // creat new button
        removeGroupButton.setPreferredSize(new Dimension(85, 35)); // set size
        removeGroupTabPanel.add(removeGroupButton); // add button

        
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
        requestGroups.addTab(" + ", addGroupTabPanel); 
        requestGroups.addTab(" - ", removeGroupTabPanel);
        this.add(requestGroups, BorderLayout.CENTER); // add to the main panel
    }
}