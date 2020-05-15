import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;





/**
 * This class represnt the center part of the Insomnia app GUI
 * This part is for make a new request
 * 
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.1.0
 */
public class NewRequestGUI extends JPanel
{
            /*  Fields  */


    // requset kind combo box
    private JComboBox<String> requestKindComboBox = new JComboBox<>();

    // get url text field
    private JTextField getUrlTextField = new JTextField(" https:// ?! ");

    // request send button
    private JButton sendButton = new JButton("Send");

    // save button
    private JButton saveButton = new JButton("Save");


    // request details tabs
    private JTabbedPane requestDetailsTabs = new JTabbedPane();

    // body tab panel
    private JPanel bodyTabMainPanel = new JPanel();

    // body kinds combo box
    private JComboBox<String> bodyKindsComboBox = new JComboBox<>();

    // body tab kinds panel (Card Layout)
    private JPanel bodyTabSubPanel = new JPanel();


    // form data kind card panel (used in bodyTabKindPanel)
    private JPanel formDataKindPanel = new JPanel();

    // form datas
    private ArrayList<NameValueData> formDatas = new ArrayList<>();

   


    // back text color
    private Color backTextColor = new Color(71, 71, 69);

    // background color
    private Color backgroundColor;







    public NewRequestGUI(Color bgColor)
    {
        // set the super class
        super();
        this.setLayout(new BorderLayout()); // set the layout manager
        this.setBackground(backgroundColor); // set background color
        this.setOpaque(true); // apply color changes





        // set background color
        backgroundColor = bgColor;



        // set url part
        urlPartINIT();
    


        // set the tabs
        requestDetailsTabs.setBackground(backgroundColor); // set color
        requestDetailsTabs.setOpaque(true); // apply color changes
        this.add(requestDetailsTabs, BorderLayout.CENTER);



        // set body tab panel
        bodyTabMainPanel.setBackground(backgroundColor); // set background color
        bodyTabMainPanel.setOpaque(true); // apply color changes
        bodyTabMainPanel.setLayout(new BoxLayout(bodyTabMainPanel, BoxLayout.Y_AXIS)); // set layout manager
        requestDetailsTabs.add("Body", bodyTabMainPanel); // add body panel


        
        // set body kinds combo box
        bodyKindsComboBox.setBackground(backgroundColor); // set background color
        bodyKindsComboBox.setOpaque(true); // apply color changes
        bodyKindsComboBox.addItem("Form data"); 
        bodyKindsComboBox.addItem("JSON");
        bodyKindsComboBox.addItem("Binary Data");
        bodyTabMainPanel.add(bodyKindsComboBox); // add to tab panel
       
        

        // set body kinds panel (CardLayout)
        bodyTabSubPanel.setLayout(new CardLayout()); // set layout manager
        bodyTabSubPanel.setBackground(backgroundColor); // set the background color
        bodyTabSubPanel.setOpaque(true); // apply color changes
        bodyTabMainPanel.add(bodyTabSubPanel); // add edit panel 
        


        // set the form data Panel
        formDataKindPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0)); // set layout manager
        formDataKindPanel.setBackground(backgroundColor); // set background color
        formDataKindPanel.setOpaque(true); // apply color changes


        JPanel addf = new JPanel();
        addf.setLayout(new FlowLayout(FlowLayout.LEFT));

    

        formDataKindPanel.add(addf);
        bodyTabSubPanel.add("form data", formDataKindPanel);

        
        // set Json panel
        JPanel jsonPanel = new JPanel(); // create new panel
        jsonPanel.setLayout(new GridLayout(1, 1, 7, 7)); //set layout manager
        jsonPanel.setBackground(backgroundColor); // set background color
        jsonPanel.setOpaque(true); // apply color changes

        JTextArea jsonTextArea = new JTextArea(); // creat new text area
        jsonTextArea.setFont(jsonTextArea.getFont().deriveFont(15.0f)); // set text size
        jsonPanel.add(jsonTextArea); // add text area

        bodyTabSubPanel.add("JSON", jsonPanel); // add body panel
    
        

        // set binary file panel
        JPanel binaryFilePanel = new JPanel(); // create new panel
        binaryFilePanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // set layout manager
        binaryFilePanel.setBackground(backgroundColor); // set background color
        binaryFilePanel.setOpaque(true); // apply color changes
        bodyTabSubPanel.add("binary file", binaryFilePanel); // add binary file panel

        JLabel choosenFileName = new JLabel("No file selected !"); // create new label
        choosenFileName.setPreferredSize(new Dimension(500, 35)); // set size
        choosenFileName.setFont(choosenFileName.getFont().deriveFont(14.0f)); // set text size
        choosenFileName.setBackground(Color.WHITE); // set back ground color
        choosenFileName.setOpaque(true); // apply color changes
        binaryFilePanel.add(choosenFileName); // add to the binary file panel
        
        JButton resetChoosenFileButton = new JButton(" Reset file"); // create new button
        resetChoosenFileButton.setPreferredSize(new Dimension(100, 35)); // set button size
        resetChoosenFileButton.setBackground(backgroundColor); resetChoosenFileButton.setForeground(Color.RED);
        resetChoosenFileButton.setOpaque(true); // apply color changes
        binaryFilePanel.add(resetChoosenFileButton); // add to the binary file panel

        JButton choosenFileButton = new JButton(" Choose file "); // create new button
        choosenFileButton.setPreferredSize(new Dimension(105, 35)); // set button size
        choosenFileButton.setBackground(backgroundColor); choosenFileButton.setForeground(Color.GREEN);
        choosenFileButton.setOpaque(true); // apply color changes
        binaryFilePanel.add(choosenFileButton); // add to the binary panel

        

       
        // set Auth tab
        {

        // set Auth panel
        JPanel authPanel = new JPanel(); // create new panel
        authPanel.setLayout(new GridBagLayout()); // set layout manager
        authPanel.setBackground(backgroundColor); // set background color
        authPanel.setOpaque(true); // apply changes
        GridBagConstraints gbc = new GridBagConstraints();
        requestDetailsTabs.addTab("Auth", authPanel); // add auth panel

        JLabel tokenLabel = new JLabel(" TOKEN "); // set text
        tokenLabel.setMinimumSize(new Dimension(100, 40)); // set size
        tokenLabel.setFont(tokenLabel.getFont().deriveFont(16.0f)); // set text size
        tokenLabel.setBackground(backgroundColor); tokenLabel.setForeground(Color.WHITE);
        tokenLabel.setOpaque(true); // apply color changes
        gbc.gridx = gbc.gridy = 0;
        authPanel.add(tokenLabel, gbc); // add to panel

        JTextField getTokenTextField = new JTextField(); // create new text field
        getTokenTextField.setFont(getTokenTextField.getFont().deriveFont(16.0f)); // set text size
        getTokenTextField.setPreferredSize(new Dimension(100, 35)); // set size
        gbc.weightx = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1; gbc.gridy = 0;
        authPanel.add(getTokenTextField, gbc); // add to the auth panel
        gbc.fill = 0; gbc.weightx = 0;

        JLabel prefixLabel = new JLabel(" PREFIX "); // set text
        prefixLabel.setMinimumSize(new Dimension(100, 40)); // set size
        prefixLabel.setFont(prefixLabel.getFont().deriveFont(16.0f)); // set text size
        prefixLabel.setBackground(backgroundColor); prefixLabel.setForeground(Color.WHITE);
        prefixLabel.setOpaque(true); // apply color changes
        gbc.gridx = 0; gbc.gridy = 1; 
        authPanel.add(prefixLabel, gbc); // add to panel

        JTextField getPrefixTextField = new JTextField(); // create new text field
        getPrefixTextField.setFont(getPrefixTextField.getFont().deriveFont(16.0f)); // set text size
        getPrefixTextField.setPreferredSize(new Dimension(100, 35)); // set size
        gbc.weightx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = gbc.gridy = 1;
        authPanel.add(getPrefixTextField, gbc); // add to the auth panel
        gbc.fill = 0; gbc.weightx = 0;

        JLabel enabledLabel = new JLabel(" ENABLED "); // set text
        enabledLabel.setMinimumSize(new Dimension(100, 40)); // set size
        enabledLabel.setFont(enabledLabel.getFont().deriveFont(16.0f)); // set text size
        enabledLabel.setBackground(backgroundColor); enabledLabel.setForeground(Color.WHITE);
        enabledLabel.setOpaque(true); // apply color changes
        gbc.gridx = 0; gbc.gridy = 2; 
        authPanel.add(enabledLabel, gbc); // add to the auth panel

        JRadioButton enabledCheckButton = new JRadioButton(); // creat new radio button
        gbc.gridx = 1; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        authPanel.add(enabledCheckButton, gbc); // add to the auth panel


        JLabel space = new JLabel(); // create new label
        space.setBackground(backgroundColor); // set background color
        space.setOpaque(true); // apply color changes
        gbc.weightx = gbc.weighty = 1; gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0; gbc.gridy = 3;
        authPanel.add(space, gbc); // add to the auth panel
        }



        // set Query tab
        {
        
        // set the Query Panel
        JPanel queryPanel = new JPanel(); // create new panel
        queryPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0)); // set layout manager
        queryPanel.setBackground(backgroundColor); // set background color
        queryPanel.setOpaque(true); // apply color changes
        requestDetailsTabs.add("Query", queryPanel); // add Query tab

        JTextField getDataName = new JTextField("name...");
        getDataName.setPreferredSize(new Dimension(224, 40));
        JTextField getDataValue = new JTextField("value...");
        getDataValue.setPreferredSize(new Dimension(224, 40));
        JRadioButton selectButton = new JRadioButton();
        selectButton.putClientProperty("JComponent.sizeVariant", "large");
        SwingUtilities.updateComponentTreeUI(this);
        JButton deletButton = new JButton("✘");
        deletButton.setFont(deletButton.getFont().deriveFont(17.0f));
        deletButton.setBackground(backgroundColor);
        deletButton.setForeground(Color.RED);
        deletButton.setOpaque(true);
        deletButton.setPreferredSize(new Dimension(40, 40));

        queryPanel.add(getDataName); queryPanel.add(getDataValue); 
        queryPanel.add(selectButton); queryPanel.add(deletButton);

        }



        // set Header tab
        {

        // set the Header Panel
        JPanel headerPanel = new JPanel(); // create new panel
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0)); // set layout manager
        headerPanel.setBackground(backgroundColor); // set background color
        headerPanel.setOpaque(true); // apply color changes
        requestDetailsTabs.add("Header", headerPanel); // add Query tab

        // JTextField getDataName = new JTextField("header...");
        // getDataName.setPreferredSize(new Dimension(224, 40));
        // JTextField getDataValue = new JTextField("value...");
        // getDataValue.setPreferredSize(new Dimension(224, 40));
        // JRadioButton selectButton = new JRadioButton();
        // JButton deletButton = new JButton("✘");
        // deletButton.setFont(deletButton.getFont().deriveFont(17.0f));
        // deletButton.setBackground(backgroundColor);
        // deletButton.setForeground(Color.RED);
        // deletButton.setOpaque(true);
        // deletButton.setPreferredSize(new Dimension(40, 40));

        // headerPanel.add(getDataName); headerPanel.add(getDataValue); 
        // headerPanel.add(selectButton); headerPanel.add(deletButton);

        }
    
    }






            /*  Methods  */

    // This method set the north part of the new request details
    private void urlPartINIT()
    {
        // set the kind combo box
        requestKindComboBox.setPreferredSize(new Dimension(92, 50)); // set size 
        requestKindComboBox.addItem("<html><p style=\"color:rgb(121, 108, 197);\"> GET </p></html>");
        requestKindComboBox.addItem("<html><p style=\"color:rgb(121, 168, 70);\"> POST </p></html>");
        requestKindComboBox.addItem("<html><p style=\"color:rgb(197, 122, 46);\"> PUT </p></html>");
        requestKindComboBox.addItem("<html><p style=\"color:rgb(171, 150, 49);\"> PATCH </p></html>");
        requestKindComboBox.addItem("<html><p style=\"color:rgb(193, 78, 73);\"> DELET </p></html>");
        requestKindComboBox.setFont(requestKindComboBox.getFont().deriveFont(15.0f)); // set the text size
        requestKindComboBox.setBackground(Color.WHITE); // set background color
        requestKindComboBox.setForeground(new Color(102, 96, 178)); // set foreground color
        requestKindComboBox.setOpaque(true); // apply color changes



        // set url text field
        getUrlTextField.setPreferredSize(new Dimension(300, 50)); // set size 
        getUrlTextField.setFont(getUrlTextField.getFont().deriveFont(17.0f)); // set text size
        getUrlTextField.setBackground(Color.WHITE); // set background color
        getUrlTextField.setForeground(backTextColor); // set foreground color
        getUrlTextField.setOpaque(true); // apply color changes



        // set send button
        sendButton.setPreferredSize(new Dimension(57, 50)); // set size
        sendButton.setBackground(Color.WHITE); // set background color
        sendButton.setForeground(new Color(76, 159, 103)); // set foreground color
        sendButton.setOpaque(true); // apply color changes



        // set the save button
        saveButton.setPreferredSize(new Dimension(57, 50)); // set size
        saveButton.setBackground(Color.WHITE); // set background color
        saveButton.setForeground(new Color(76, 159, 103)); // set foreground color
        saveButton.setOpaque(true); // apply color changes



        // set the north panel of the request detail
        JPanel northPanel = new JPanel(); // creat new panel
        northPanel.setBackground(Color.WHITE); // set the back ground color
        northPanel.setOpaque(true); // apply color changes
        northPanel.setLayout(new FlowLayout(0, 0, 0)); // set the layout manager
        northPanel.add(requestKindComboBox); // add kind combo box
        northPanel.add(getUrlTextField); // add get url text field
        northPanel.add(sendButton); // add send button
        northPanel.add(saveButton); // add save button



        // add to panel
        this.add(northPanel, BorderLayout.NORTH);
    }






    // This class represnt to text fields and a select button and a delet button
    // The object of this class used to hold the headers, Quary datas and etc
    private class NameValueData extends JPanel
    {
                /*  Fields  */

        // get data name text field
        private JTextField dataNameTextField;

        // get data value text field
        private JTextField dataValueTextField;

        // select button 
        private JRadioButton selectButton;

        // delet button
        private JButton deletButton;

        



              /* Constructor */

        /**
         * Create new data holder with given texts for fields
         * 
         * @param bgNameText : name text
         * @param bgValueText : value text
         */
        public NameValueData(String bgNameText, String bgValueText)
        {
            // set super class
            super();
            super.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5)); // set layout manager
            super.setBackground(backgroundColor); // set background color
            super.setOpaque(true); // apply color changes


            // set name text field
            dataNameTextField = new JTextField(bgNameText); // create new text field
            dataNameTextField.setForeground(backTextColor); // set text color
            dataNameTextField.setOpaque(true); // apply color changes
            dataNameTextField.setPreferredSize(new Dimension(224, 40)); // set size
            super.add(dataNameTextField); // add to the panel
        

            // set value text field
            dataValueTextField = new JTextField(bgValueText); // create new text field
            dataValueTextField.setForeground(backTextColor); // set text color
            dataValueTextField.setOpaque(true); // apply color changes
            dataValueTextField.setPreferredSize(new Dimension(224, 40)); // set size
            super.add(dataValueTextField); // add to the panel


            // set select button
            selectButton = new JRadioButton(); // create new button
            selectButton.putClientProperty("JComponent.sizeVariant", "large"); // set size
            SwingUtilities.updateComponentTreeUI(this);
            super.add(selectButton); // add to the panel


            // set delet button
            deletButton = new JButton("✘"); // create new button
            deletButton.setPreferredSize(new Dimension(40, 40)); // set button size
            deletButton.setFont(deletButton.getFont().deriveFont(17.0f)); // set text size
            deletButton.setBackground(backgroundColor); // set background color
            deletButton.setForeground(Color.RED); // set text color
            deletButton.setOpaque(true); // apply color changes
            super.add(deletButton); // add to the panel
        }
    }
}