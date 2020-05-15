import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;





/**
 * This class reprsent the left part of the insomnia app GUI
 * This part hold and show the last request by they group
 * 
 * 
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.1.3
 */
public class LastRequestsGUI extends JPanel
{
            /*  Fields  */


    // Insomnia text label
    private JLabel insomniaLabel;

    // requests groups tabbed pane
    private JTabbedPane requestGroupsTabs;

    // groups name combo box
    private JComboBox<String> groupsComboBox;

    // hold the Groups panels
    private HashMap<String, JPanel> requestGroupPanels = new HashMap<>();

    // add group tab panel
    private JPanel addGroupTabPanel;

    // get new tab name Text field
    private JFormattedTextField getNewGroupNameField;

    // add new group button
    private JButton addGroupButton;

    // remove group tab panel
    private JPanel removeGroupTabPanel;

    // remove group button
    private JButton removeGroupButton;

    // last requests panel
    private JPanel lastRequestsPanel;

    // hold the object of this class
    private final LastRequestsGUI THIS;

    // Event handler of buttons
    private EventHandler handler;

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
        THIS = this;
        




        // set handler
        handler = new EventHandler();
        


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
        requestGroupPanels.put("+", addGroupTabPanel); // add to the hashMap
        


        // set new tab name text field
        getNewGroupNameField = new JFormattedTextField("new group name ... ");
        getNewGroupNameField.setForeground(new Color(71, 71, 69)); // set foreground color
        getNewGroupNameField.setPreferredSize(new Dimension(190, 33)); // set size
        getNewGroupNameField.setFont(insomniaLabel.getFont().deriveFont(15.0f)); // set text size
        addGroupTabPanel.add(getNewGroupNameField); // add text field



        // set add button
        addGroupButton = new JButton(" Add "); // creat new button
        addGroupButton.setPreferredSize(new Dimension(70, 35)); // set size
        addGroupButton.addActionListener(handler);
        addGroupTabPanel.add(addGroupButton); // add button



        // set the '-' tab
        removeGroupTabPanel = new JPanel(); // create new panel
        removeGroupTabPanel.setLayout(new FlowLayout()); // set layout manager
        removeGroupTabPanel.setBackground(backgroundColor); // set background color
        removeGroupTabPanel.setOpaque(true); // apply color changes
        requestGroupPanels.put("-", removeGroupTabPanel); // add to the hash map



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
        lastRequestsPanel = newEmptyPanel(); // create new panel
        requestGroupPanels.put("last requests", lastRequestsPanel); // add to the hashMap



        // set request grouping
        requestGroupsTabs = new JTabbedPane(); // create new tabbed pane
        requestGroupsTabs.setBackground(backgroundColor); // set color
        requestGroupsTabs.setOpaque(true); // apply color changes
        requestGroupsTabs.addTab("last requests", lastRequestsPanel); 
        requestGroupsTabs.addTab(" + ", addGroupTabPanel); 
        requestGroupsTabs.addTab(" - ", removeGroupTabPanel);
        this.add(requestGroupsTabs, BorderLayout.CENTER); // add to the main panel
    }









            /*  Methods  */


    // This return true if the client has been choose a name for new Group
    private boolean isNewGroupNameEmpty()
    {
        if (getNewGroupNameField.getText().length() == 0
            ||
            getNewGroupNameField.getText().equals("new group name ... "))
        {
            JOptionPane.showMessageDialog(this, "Please choose a name for new group !", "error", JOptionPane.ERROR_MESSAGE);
            return true;
        }


        return false;
    }


    // This method check that client new group name has already choosen or not
    private boolean isAlreadyAdded(String newGroupName)
    {
        if (requestGroupPanels.keySet().contains(newGroupName))
            return true;

        return false;
    } 


    // this method return a new empty panel
    private JPanel newEmptyPanel()
    {
        JPanel output = new JPanel(); // create new panel
        output.setLayout(new BoxLayout(output, BoxLayout.Y_AXIS)); // set the layout manager
        output.setBackground(backgroundColor); // set the background color
        output.setOpaque(true); // apply color changes

        return output;
    }




    

    private class EventHandler implements ActionListener
    {
        /**
         * This method handl the event of the add button and remove button
         * 
         * @param e : ActionEvent
         */
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            /* add button case */
            if (e.getSource().equals(addGroupButton))
            {
                // check that client has choose a name for new group or not
                if (isNewGroupNameEmpty())
                    return;
                


                String newGroupName = getNewGroupNameField.getText(); // get name of the new group

                // check the given name
                if (isAlreadyAdded(newGroupName))
                {
                    JOptionPane.showMessageDialog(THIS, "This name has already added !", "error", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                JPanel newGroupPanel = newEmptyPanel(); // create new panel
                groupsComboBox.addItem(newGroupName); // add new group name to combo box
                requestGroupPanels.put(newGroupName, newGroupPanel); // add to the hashMap
                requestGroupsTabs.addTab(newGroupName, newGroupPanel); // add new tab
            }
            
            // if (e.getSource().equals(removeGroupButton))
            // {}

            return;
        }
    }
}