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
 * @version 0.1.5
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


    // form data kind card panel (used in bodyTabSubPanel)
    private JPanel formDataKindPanel = new JPanel();

    // form datas
    private ArrayList<NameValueData> formDatas = new ArrayList<>();


    // JSON kind card panel (used in bodyTabSubPanel)
    private JPanel jsonKindPanel = new JPanel();

    // JSON text area 
    private JTextArea jsonTextArea = new JTextArea();


    // binary file kind card panel (used in bodyTabSubPanel)
    private JPanel binaryFileKindPanel = new JPanel();

    // choosen file status label
    private JLabel choosenBinaryFileName = new JLabel("No file selected !");

    // reset choosen file button
    private JButton resetChoosenFileButton = new JButton(" Reset file");

    // choose file button
    private JButton chooseFileButton = new JButton(" Choose file ");
   

    // Auth tab panel
    private JPanel authTabPanel = new JPanel();

    // get token text field
    private JTextField getTokenTextField = new JTextField(" TOKEN . . .");

    // get preifix text field
    private JTextField getPrefixTextField = new JTextField(" PREFIX . . ."); 

    // enable check box
    private JCheckBox enabledCheckButton = new JCheckBox(" ENABLE "); // creat new radio button


    // Query tab panel
    private JPanel queryTabPanel = new JPanel();

    // Query datas
    private ArrayList<NameValueData> QueryDatas = new ArrayList<>();


    // Header tab panel
    private JPanel headerTabPanel = new JPanel();

    // Header datas
    private ArrayList<NameValueData> headerDatas = new ArrayList<>();



    // Event handler of buttons
    private EventHandler handler = new EventHandler();

    // back text color
    private Color backTextColor = new Color(104, 106, 112);

    // background color
    private Color backgroundColor;






         /* Constructor */

    /**
     * Create new panel for new request part
     * 
     * 
     * @param bgColor : the color of the background
     */
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
        urlPartGuiInit();
    

        // set the tabs
        requestDetailsTabs.setBackground(backgroundColor); // set color
        requestDetailsTabs.setOpaque(true); // apply color changes
        this.add(requestDetailsTabs, BorderLayout.CENTER);

        // set body tab
        bodyTabGuiInit();
        
        // set auth tab
        authTabGuiInit();

        // set query tab
        queryTabGuiInit();

        // set header tab
        headerTabGuiInit();
    }






    




            /*  Methods  */


    // This method set the north part of the new request details
    private void urlPartGuiInit()
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


    // This method set the body tab of the new request 
    private void bodyTabGuiInit()
    {
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
        bodyKindsComboBox.addItem("Binary file");
        bodyKindsComboBox.addActionListener(handler);
        bodyTabMainPanel.add(bodyKindsComboBox); // add to tab panel
       
        

        // set body kinds panel (CardLayout)
        bodyTabSubPanel.setLayout(new CardLayout()); // set layout manager
        bodyTabSubPanel.setBackground(backgroundColor); // set the background color
        bodyTabSubPanel.setOpaque(true); // apply color changes
        bodyTabMainPanel.add(bodyTabSubPanel); // add edit panel 
        


        // set the form data Panel
        formDataKindPanel.setLayout(new BoxLayout(formDataKindPanel, BoxLayout.Y_AXIS)); // set layout manager
        formDataKindPanel.setBackground(backgroundColor); // set background color
        formDataKindPanel.setOpaque(true); // apply color changes
        bodyTabSubPanel.add("f", formDataKindPanel); // add to edit panel


        // set form data
        formDatas.add(new NameValueData("new name ...", "new value ..."));
        formDataKindPanel.add(formDatas.get(0));
        



        // set Json panel
        jsonKindPanel.setLayout(new GridLayout(1, 1, 12, 12)); //set layout manager
        jsonKindPanel.setBackground(backgroundColor); // set background color
        jsonKindPanel.setOpaque(true); // apply color changes
        bodyTabSubPanel.add("j", jsonKindPanel); // add to edit panel

        // set Json text area
        jsonTextArea.setFont(jsonTextArea.getFont().deriveFont(15.0f)); // set text size
        jsonTextArea.setBorder(BorderFactory.createLineBorder(backgroundColor, 30));
        jsonKindPanel.add(jsonTextArea); // add text area

        
    
        
        // set binary file panel
        binaryFileKindPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // set layout manager
        binaryFileKindPanel.setBackground(backgroundColor); // set background color
        binaryFileKindPanel.setOpaque(true); // apply color changes
        bodyTabSubPanel.add("b", binaryFileKindPanel); // add binary file panel

        
        // set choosen file label
        choosenBinaryFileName.setPreferredSize(new Dimension(500, 35)); // set size
        choosenBinaryFileName.setFont(choosenBinaryFileName.getFont().deriveFont(14.0f)); // set text size
        choosenBinaryFileName.setBackground(Color.WHITE); // set back ground color
        choosenBinaryFileName.setOpaque(true); // apply color changes
        binaryFileKindPanel.add(choosenBinaryFileName); // add to the binary file panel
        
        
        // set reset button
        resetChoosenFileButton.setPreferredSize(new Dimension(100, 35)); // set button size
        resetChoosenFileButton.setBackground(backgroundColor); resetChoosenFileButton.setForeground(Color.RED);
        resetChoosenFileButton.setOpaque(true); // apply color changes
        binaryFileKindPanel.add(resetChoosenFileButton); // add to the binary file panel

        
        // set choose button
        chooseFileButton.setPreferredSize(new Dimension(105, 35)); // set button size
        chooseFileButton.setBackground(backgroundColor); chooseFileButton.setForeground(Color.GREEN);
        chooseFileButton.setOpaque(true); // apply color changes
        binaryFileKindPanel.add(chooseFileButton); // add to the binary panel
    }


    // This method set the auth tab of the new requset
    private void authTabGuiInit()
    {
        // set Auth panel
        authTabPanel.setLayout(new BoxLayout(authTabPanel, BoxLayout.Y_AXIS)); // set layout manager
        authTabPanel.setBackground(backgroundColor); // set background color
        authTabPanel.setOpaque(true); // apply changes
        requestDetailsTabs.addTab("Auth", authTabPanel); // add auth panel


        // set token text field
        getTokenTextField.setFont(getTokenTextField.getFont().deriveFont(16.0f)); // set text size
        getTokenTextField.setForeground(backTextColor); // set text color
        getTokenTextField.setOpaque(true); // apply color changes
        getTokenTextField.setMaximumSize(new Dimension(920, 35)); // set size
        authTabPanel.add(getTokenTextField); // add to the auth panel


        // set prefix text field
        getPrefixTextField.setFont(getPrefixTextField.getFont().deriveFont(16.0f)); // set text size
        getPrefixTextField.setForeground(backTextColor); // set text color
        getPrefixTextField.setOpaque(true); // apply color changes
        getPrefixTextField.setMaximumSize(new Dimension(920, 35)); // set size
        authTabPanel.add(getPrefixTextField); // add to the auth panel
       

        // set enabled check box
        enabledCheckButton.putClientProperty("JComponent.sizeVariant", "large"); // set size
        enabledCheckButton.setBackground(backgroundColor); // set background color
        enabledCheckButton.setForeground(Color.GREEN); // set text color
        enabledCheckButton.setOpaque(true); // apply color changes
        authTabPanel.add(enabledCheckButton); // add to the auth panel
    }


    // This method set the query tab of the new request
    private void queryTabGuiInit()
    {
        // set the Query Panel
        queryTabPanel.setLayout(new BoxLayout(queryTabPanel, BoxLayout.Y_AXIS)); // set layout manager
        queryTabPanel.setBackground(backgroundColor); // set background color
        queryTabPanel.setOpaque(true); // apply color changes
        requestDetailsTabs.add("Query", queryTabPanel); // add Query tab


        // set Query datas
        QueryDatas.add(new NameValueData("new name ...", "new value ..."));
        queryTabPanel.add(QueryDatas.get(0));
    }


    // This method set the header tab of the new requset
    private void headerTabGuiInit()
    {
        // set the Header Panel
        headerTabPanel.setLayout(new BoxLayout(headerTabPanel, BoxLayout.Y_AXIS)); // set layout manager
        headerTabPanel.setBackground(backgroundColor); // set background color
        headerTabPanel.setOpaque(true); // apply color changes
        requestDetailsTabs.add("Header", headerTabPanel); // add Query tab

        // set Header datas
        headerDatas.add(new NameValueData("new header ...", "new value ..."));
        headerTabPanel.add(headerDatas.get(0));
    }









            /* inner Classes */


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




    private class EventHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {
            /*  body kinds combo box event handler */
            if (e.getSource().equals(bodyKindsComboBox))
            {
                if (bodyKindsComboBox.getSelectedItem().equals("Form data"))
                    ((CardLayout) bodyTabSubPanel.getLayout()).show(bodyTabSubPanel, "f");

                else if (bodyKindsComboBox.getSelectedItem().equals("JSON"))
                    ((CardLayout) bodyTabSubPanel.getLayout()).show(bodyTabSubPanel, "j");

                else if (bodyKindsComboBox.getSelectedItem().equals("Binary file"))
                    ((CardLayout) bodyTabSubPanel.getLayout()).show(bodyTabSubPanel, "b");
            }

            return;
        }
    }
}