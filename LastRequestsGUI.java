import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.IOException;





/**
 * This class reprsent the left part of the insomnia app GUI
 * This part hold and show the last request by they group
 * 
 * 
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.2.5
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
    private JTextField getNewGroupNameField;

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


    // back text color
    private Color backTextColor = new Color(71, 71, 69);

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
        insomniaLabel.setFont(insomniaLabel.getFont().deriveFont(22.0f)); // set text size
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
        getNewGroupNameField = new JTextField("new group name ... ");
        getNewGroupNameField.setForeground(backTextColor); // set foreground color
        getNewGroupNameField.setPreferredSize(new Dimension(190, 33)); // set size
        getNewGroupNameField.setFont(insomniaLabel.getFont().deriveFont(15.0f)); // set text size
        getNewGroupNameField.addFocusListener(handler); // set handler
        addGroupTabPanel.add(getNewGroupNameField); // add text field



        // set add button
        addGroupButton = new JButton(" Add "); // creat new button
        addGroupButton.setPreferredSize(new Dimension(70, 35)); // set size
        addGroupButton.addActionListener(handler); // set handler
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
        removeGroupButton.addActionListener(handler); // set handler
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

    /**
     * This method show the saved request in GUI
     * 
     * 
     * @param reqKind : method of request
     * @param reqName : name of the request
     * @param gpName : name of the request group
     * @param reqDetails : a {@code String} that could set as {@link Jurl#main(String[])}  input
     */
    public void saveRequest(String reqKind, String reqName, String gpName, String reqDetails)
    {
        requestGroupPanels.get(gpName).add(new ReqeustButton(reqKind, reqName, gpName, reqDetails));
    }


    /**
     * This method load saved request to GUI
     * 
     * @param requests : saved requests in DataBase
     */
    public void loadRequests(HashMap<String, ArrayList<String>> requests)
    {
        int gpCntr = 0, reqCntr = 0; String gpLast;
        for (String gpName : requests.keySet())
        {
            gpLast = gpName;
            if (!".lastRequestsFolder".equals(gpName))
                addGroup(gpName);
            else
                gpLast = "last requests";


            reqCntr = 0;
            for (String reqName : requests.get(gpName))
            {
                try
                {
                    Request hold = DataBase.openRequest(gpCntr, reqCntr);
                    addRequest(gpLast, new ReqeustButton(RequestKinds.getKind(hold.getRequestKind()), reqName, gpLast, hold.getReqDetails()));
                }
                catch (IOException e) {}

                reqCntr++;
            }

            gpCntr++;
        }
    }


    /**
     * @return a combo box of groups
     */
    public JComboBox<String> getGroupsComboBox()
    {
        JComboBox<String> output = new JComboBox<>();
        output.setPreferredSize(new Dimension(193, 33)); // set size
        output.setFont(output.getFont().deriveFont(15.0f)); // set text size

        for (String group : requestGroupPanels.keySet())
            if (group.equals("+") || group.equals("-"))
                continue;
            else
                output.addItem(group);
            

        return output;
    }






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


    // this method add new group by its name
    private void addGroup(String gpName)
    {
        JPanel newGroupPanel = newEmptyPanel(); // create new panel
        groupsComboBox.addItem(gpName); // add new group name to combo box
        requestGroupPanels.put(gpName, newGroupPanel); // add to the hashMap
        requestGroupsTabs.insertTab(gpName, null, newGroupPanel, null, 1); // add new tab
    }


    // this method add a request to its group panel
    private void addRequest(String gpName, ReqeustButton req)
    {
        requestGroupPanels.get(gpName).add(req);
    }


    










    // this class represent a button object for reqeusts
    private class ReqeustButton extends JButton
    {
                /*  Fields  */

        // request method
        private String requestKind = null;

        // request name
        private String requestName = null;

        // request group name
        private String groupName = null;

        // request details (for Jurl main)
        private String requestDetailsString = null; 






             /* Constructor */

        /**
         * Create a new button for a request
         * 
         * 
         * @param reqKind : method of the request
         * @param reqName : a name for this request
         * @param gpName : name of the this request group
         * @param reqDetails : a string of this request detials
         */
        public ReqeustButton(String reqKind, String reqName, String gpName, String reqDetails)
        {
            super();

            this.requestKind = reqKind.toUpperCase();
            this.requestName = reqName;
            this.groupName = gpName;
            this.requestDetailsString = reqDetails;


            super.setMinimumSize(new Dimension(170, 35)); // set maximum size of the button
            super.setFont (super.getFont().deriveFont(12.0f)); // set text size
            super.setForeground(Color.BLACK); // set text color
            super.setBackground(new Color(153, 153, 153)); // set background color
            super.setOpaque(true);

            super.setText(buildButtonText());

            super.addActionListener(handler);
        }






                /*  Methods  */

        /**
         * @return details of this request
         */
        public String getDetails() { return requestDetailsString; }





        // return button text
        private String buildButtonText()
        {
            String colorCode = null;
            switch (this.requestKind.toUpperCase())
            {
                case "GET": colorCode = "rgb(121, 108, 197)"; break;
                case "POST": colorCode = "rgb(121, 168, 70)"; break;
                case "PUT": colorCode = "rgb(197, 122, 46)"; break;
                case "DELETE" : colorCode = "rgb(193, 78, 73)"; break;

                default: break;
            }

            return "<html>" + "<b style=\"color:" + colorCode + ";\">" +
                    ("DELETE".equals(requestKind) ? "DEL": requestKind) +
                    "&emsp;&emsp;&emsp;&emsp;</b>" + requestName + "</html>";
        }
    }










     // This class do the event handling of LastRequestsGUI class
    private class EventHandler implements ActionListener, FocusListener
    {
        /**
         * This method handl the event of the add button and remove button
         * 
         * @param e : ActionEvent
         */
        public void actionPerformed(ActionEvent e) 
        {
            /* add button case */
            if (e.getSource().equals(getNewGroupNameField) || e.getSource().equals(addGroupButton))
            {
                // check that client has choose a name for new group or not
                if (isNewGroupNameEmpty())
                    return;
                


                String newGroupName = getNewGroupNameField.getText(); // get name of the new group

                // check the given name
                if (isAlreadyAdded(newGroupName))
                {
                    JOptionPane.showMessageDialog(THIS, "This group has already added !", "error", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                addGroup(newGroupName);
            }
            


            /* remove button case */
            if (e.getSource().equals(removeGroupButton))
            {
                // get the client choose
                String groupNameToRemove = (String)groupsComboBox.getSelectedItem();



                requestGroupsTabs.remove(requestGroupPanels.get(groupNameToRemove)); // remove from tabs
                requestGroupPanels.remove(groupNameToRemove); // remove from hashMap
                groupsComboBox.removeItem(groupNameToRemove); // remove from combo box


                Insomnia.removeGroup(groupNameToRemove);
            }


            /* request button case */
            if (e.getSource() instanceof ReqeustButton)
            {
                MainFrame.openRequest(((ReqeustButton) e.getSource()).getDetails());
            }
        }



        /**
         * This mehtod handle the 
         */
        public void focusGained(FocusEvent e) 
        {
            /* get group name case */
            if (e.getSource().equals(getNewGroupNameField))
            {
                getNewGroupNameField.setForeground(Color.BLACK);
                getNewGroupNameField.setOpaque(true);
                getNewGroupNameField.setText("");
            }
        }
        
        
        public void focusLost(FocusEvent e) 
        {
            /* get group name case */
            if (e.getSource().equals(getNewGroupNameField))
            {
                if (getNewGroupNameField.getText().length() == 0)
                {
                    getNewGroupNameField.setForeground(backTextColor);
                    getNewGroupNameField.setOpaque(true);
                    getNewGroupNameField.setText("new group name ... ");
                }
            }
        }
    }
}