import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;






/**
 * This class represnt the center part of the Insomnia app GUI
 * This part is for make a new request
 * 
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.2.1
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
   



    // Query tab panel
    private JPanel queryTabPanel = new JPanel();

    // Query datas
    private ArrayList<NameValueData> queryDatas = new ArrayList<>();



    // Header tab panel
    private JPanel headerTabPanel = new JPanel();

    // Header datas
    private ArrayList<NameValueData> headerDatas = new ArrayList<>();




    // a pointer to this object
    private NewRequestGUI THIS;

    // Event handler of buttons
    private EventHandler mainHandler = new EventHandler();

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


        THIS = this;


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

        // set query tab
        queryTabGuiInit();

        // set header tab
        headerTabGuiInit();
    }






    




            /*  Methods  */


    /**
     * @return the user choosen url
     */
    public String getUrl()
    {
        if (getUrlTextField.getText().equals(" https:// ?! "))
        {
            JOptionPane.showMessageDialog(THIS, "Please choose a url to send reqeust.", "error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return getUrlTextField.getText();
    }


    /**
     * @return user choosen method
     */
    public String getMethod()
    {
        return " -m " + requestKindComboBox.getSelectedObjects();
    }


    /**
     * @return user choosen headers for new request
     */
    public String getHeaders()
    {
        String output = "";
        for (NameValueData data : headerDatas)
        {
            if (!data.isSelected())
                continue;

            if (data.getDataName().length() == 0 || data.getDataValue().length() == 0)
            {
                JOptionPane.showMessageDialog(THIS, "Some of your choosen headers are incorrect !", "error", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            output = output + data.getDataName() + ":" + data.getDataValue() + ",";
        }

        if (output.length() != 0)
            output = output.substring(0, output.length()-1);
        return output;
    }


    /**
     * @return user choosen query
     */
    public String getQuery()
    {
        String output = "";
        for (NameValueData data : queryDatas)
        {
            if (!data.isSelected())
                continue;

            if (data.getDataValue().length() == 0)
            {
                JOptionPane.showMessageDialog(THIS, "Your Query is incorrect !", "error", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            output = output + data.getDataName() + "=" + data.getDataValue() + "&";
        }

        if (output.length() != 0)
            output = output.substring(0, output.length()-1);
        return output;
    }


    /**
     * @return a {@code String} to set the request body
     */
    public String getBody()
    {
        switch ((String) bodyKindsComboBox.getSelectedItem())
        {
            case "Form data":
                return getFormData();

            case "JSON":
                return getJson();

            case "Binary file":
                return getBinaryFile();

            default:
                return null;
        }
    }


    /**
     * This method set fields of this panel with given details
     * 
     * @param detatils : request details
     */
    public void setPanel(String detatils)
    {
        String[] args = detatils.split("");
        ArrayList<String> inputs = new ArrayList<>();
        for (String arg : args)
            inputs.add(arg);


        getUrlTextField.setText(args[0]);

        requestKindComboBox.setSelectedItem(inputs.get(inputs.indexOf("-m")+1));


        if (inputs.contains("-h"))
        {
            String headers = inputs.get(inputs.indexOf("-h")+1);

            NameValueData hold = null;
            for (String pair : headers.split(","))
            {
                hold = new NameValueData(pair.substring(0, pair.indexOf(":")), 
                                         pair.substring(pair.indexOf(":")+1),
                                         headerTabPanel,
                                         headerDatas);

                headerTabPanel.add(hold);
                headerDatas.add(hold);
            }
        }


        if (inputs.contains("-q"))
        {
            String headers = inputs.get(inputs.indexOf("-q")+1);

            NameValueData hold = null;
            for (String pair : headers.split("&"))
            {
                hold = new NameValueData(pair.substring(0, pair.indexOf("=")), 
                                         pair.substring(pair.indexOf("=")+1),
                                         queryTabPanel,
                                         queryDatas);

                queryTabPanel.add(hold);
                queryDatas.add(hold);
            }
        }


        if (inputs.contains("-d"))
        {
            String headers = inputs.get(inputs.indexOf("-d")+1);

            NameValueData hold = null;
            for (String pair : headers.split("&"))
            {
                hold = new NameValueData(pair.substring(0, pair.indexOf("=")), 
                                         pair.substring(pair.indexOf("=")+1),
                                         formDataKindPanel,
                                         formDatas);

                formDataKindPanel.add(hold);
                formDatas.add(hold);
            }
        }
    }









    // This method set the north part of the new request details
    private void urlPartGuiInit()
    {
        // set the north panel of the request detail
        JPanel northPanel = new JPanel(); // creat new panel
        northPanel.setBackground(Color.WHITE); // set the back ground color
        northPanel.setOpaque(true); // apply color changes
        northPanel.setLayout(new FlowLayout(0, 0, 0)); // set the layout manager
        this.add(northPanel, BorderLayout.NORTH); // add to panel


        // set the kind combo box
        requestKindComboBox.setPreferredSize(new Dimension(92, 50)); // set size 
        requestKindComboBox.addItem("<html><p style=\"color:rgb(121, 108, 197);\"> GET </p></html>");
        requestKindComboBox.addItem("<html><p style=\"color:rgb(121, 168, 70);\"> POST </p></html>");
        requestKindComboBox.addItem("<html><p style=\"color:rgb(197, 122, 46);\"> PUT </p></html>");
        requestKindComboBox.addItem("<html><p style=\"color:rgb(193, 78, 73);\"> DELET </p></html>");
        requestKindComboBox.setFont(requestKindComboBox.getFont().deriveFont(15.0f)); // set the text size
        requestKindComboBox.setBackground(Color.WHITE); // set background color
        requestKindComboBox.setForeground(new Color(102, 96, 178)); // set foreground color
        requestKindComboBox.setOpaque(true); // apply color changes
        requestDetailsTabs.addFocusListener(mainHandler);
        northPanel.add(requestKindComboBox); // add kind combo box


        // set url text field
        getUrlTextField.setPreferredSize(new Dimension(300, 50)); // set size 
        getUrlTextField.setFont(getUrlTextField.getFont().deriveFont(17.0f)); // set text size
        getUrlTextField.setBackground(Color.WHITE); // set background color
        getUrlTextField.setForeground(backTextColor); // set foreground color
        getUrlTextField.setOpaque(true); // apply color changes
        getUrlTextField.addActionListener(mainHandler);
        getUrlTextField.addFocusListener(mainHandler);
        northPanel.add(getUrlTextField); // add get url text field


        // set send button
        sendButton.setPreferredSize(new Dimension(57, 50)); // set size
        sendButton.setBackground(Color.WHITE); // set background color
        sendButton.setForeground(new Color(76, 159, 103)); // set foreground color
        sendButton.setOpaque(true); // apply color changes
        sendButton.addActionListener(mainHandler);
        sendButton.addFocusListener(mainHandler);
        northPanel.add(sendButton); // add send button


        // set the save button
        saveButton.setPreferredSize(new Dimension(57, 50)); // set size
        saveButton.setBackground(Color.WHITE); // set background color
        saveButton.setForeground(new Color(76, 159, 103)); // set foreground color
        saveButton.setOpaque(true); // apply color changes
        saveButton.addActionListener(mainHandler);
        saveButton.addFocusListener(mainHandler);
        northPanel.add(saveButton); // add save button
    }


    // This method set the body tab of the new request 
    private void bodyTabGuiInit()
    {
        // set body tab panel
        bodyTabMainPanel.setBackground(backgroundColor); // set background color
        bodyTabMainPanel.setOpaque(true); // apply color changes
        bodyTabMainPanel.setLayout(new BorderLayout()); // set layout manager
        requestDetailsTabs.add("Body", bodyTabMainPanel); // add body panel


        
        // set body kinds combo box
        bodyKindsComboBox.setMaximumSize(new Dimension(925, 30));
        bodyKindsComboBox.setBackground(backgroundColor); // set background color
        bodyKindsComboBox.setOpaque(true); // apply color changes
        bodyKindsComboBox.addItem("Form data"); 
        bodyKindsComboBox.addItem("JSON");
        bodyKindsComboBox.addItem("Binary file");
        bodyKindsComboBox.addActionListener(mainHandler);
        bodyKindsComboBox.addFocusListener(mainHandler);
        bodyTabMainPanel.add(bodyKindsComboBox, BorderLayout.NORTH); // add to tab panel
       
        

        // set body kinds panel (CardLayout)
        bodyTabSubPanel.setLayout(new CardLayout()); // set layout manager
        bodyTabSubPanel.setBackground(backgroundColor); // set the background color
        bodyTabSubPanel.setOpaque(true); // apply color changes

        // set body kinds panel scroll pane
        JScrollPane bodyTabSubPanelScrollPane = new JScrollPane(bodyTabSubPanel);
        bodyTabSubPanelScrollPane.setBackground(backgroundColor);
        bodyTabSubPanelScrollPane.setOpaque(true);
        bodyTabMainPanel.add(bodyTabSubPanelScrollPane, BorderLayout.CENTER); // add edit panel 
        


        // set the form data Panel
        formDataKindPanel.setLayout(new BoxLayout(formDataKindPanel, BoxLayout.Y_AXIS)); // set layout manager
        formDataKindPanel.setBackground(backgroundColor); // set background color
        formDataKindPanel.setOpaque(true); // apply color changes
        bodyTabSubPanel.add("f", formDataKindPanel); // add to edit panel


        // set form data
        formDatas.add(new NameValueData("new name ...", "new value ...", formDataKindPanel, formDatas));
        formDataKindPanel.add(formDatas.get(0));        



        // set Json panel
        jsonKindPanel.setLayout(new GridLayout(1, 1, 12, 12)); //set layout manager
        jsonKindPanel.setBackground(backgroundColor); // set background color
        jsonKindPanel.setOpaque(true); // apply color changes
        bodyTabSubPanel.add("j", jsonKindPanel); // add to edit panel

        // set Json text area
        jsonTextArea.setFont(jsonTextArea.getFont().deriveFont(15.0f)); // set text size
        jsonTextArea.setBorder(BorderFactory.createLineBorder(backgroundColor, 30));
        jsonTextArea.addFocusListener(mainHandler);
        jsonKindPanel.add(jsonTextArea); // add text area

        
    
        
        // set binary file panel
        binaryFileKindPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // set layout manager
        binaryFileKindPanel.setBackground(backgroundColor); // set background color
        binaryFileKindPanel.setOpaque(true); // apply color changes
        bodyTabSubPanel.add("b", binaryFileKindPanel); // add binary file panel

        
        // set choosen file label
        choosenBinaryFileName.setPreferredSize(new Dimension(300, 35)); // set size
        choosenBinaryFileName.setFont(choosenBinaryFileName.getFont().deriveFont(14.0f)); // set text size
        choosenBinaryFileName.setBackground(Color.WHITE); // set back ground color
        choosenBinaryFileName.setOpaque(true); // apply color changes
        choosenBinaryFileName.addFocusListener(mainHandler);
        binaryFileKindPanel.add(choosenBinaryFileName); // add to the binary file panel
        
        
        // set reset button
        resetChoosenFileButton.setPreferredSize(new Dimension(100, 35)); // set button size
        resetChoosenFileButton.setBackground(backgroundColor); resetChoosenFileButton.setForeground(Color.RED);
        resetChoosenFileButton.setOpaque(true); // apply color changes
        resetChoosenFileButton.addActionListener(mainHandler);
        resetChoosenFileButton.addFocusListener(mainHandler);
        binaryFileKindPanel.add(resetChoosenFileButton); // add to the binary file panel

        
        // set choose button
        chooseFileButton.setPreferredSize(new Dimension(105, 35)); // set button size
        chooseFileButton.setBackground(backgroundColor); chooseFileButton.setForeground(Color.GREEN);
        chooseFileButton.setOpaque(true); // apply color changes
        chooseFileButton.addActionListener(mainHandler);
        chooseFileButton.addFocusListener(mainHandler);
        binaryFileKindPanel.add(chooseFileButton); // add to the binary panel
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
        queryDatas.add(new NameValueData("new name ...", "new value ...", queryTabPanel, queryDatas));
        queryTabPanel.add(queryDatas.get(0));
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
        headerDatas.add(new NameValueData("new header ...", "new value ...", headerTabPanel, headerDatas));
        headerTabPanel.add(headerDatas.get(0));
    }


    // This method generate a String for request body in form data case
    private String getFormData()
    {
        String output = "-d ";
        for (NameValueData data : formDatas)
        {
            if (!data.isSelected())
                continue;

            if (data.getDataName().length() == 0 || data.getDataValue().length() == 0)
            {
                JOptionPane.showMessageDialog(THIS, "Some of your given datas for form data are incorrect !", "error", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            output = output + data.getDataName() + "=" + data.getDataValue() + "&";
        }

        
        if (output.equals("-d ")) return "";

        output = output.substring(0, output.length()-1);
        return output;
    }


    // This method generate a String for request body in JSON case
    private String getJson()
    {
        String output = "-j ";

        try{ output = output + jsonTextArea.getText(); }
        catch (NullPointerException e) { return ""; }

        return output;
    }


    // This method generate a String for request body in binary file case
    public String getBinaryFile()
    {
        if (choosenBinaryFileName.getText().equals("No file selected !"))
            return "";

        return "-u " + choosenBinaryFileName.getText();
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

        // where this object will added
        private JPanel targetPanel;

        // where this object will be saved
        private ArrayList<NameValueData> holdPlace;


        // a pointer to this object
        private NameValueData THIS = this;

        // buttons and fields mainHandler
        private DataEventHandler dataHandler = new DataEventHandler();

        // name string
        private String bgNameString;

        // value string
        private String bgValueString;

        



              /* Constructor */

        /**
         * Create new data holder with given texts for fields
         * 
         * @param bgNameText : name text
         * @param bgValueText : value text
         * @param targetPanel : where this object will added
         * @param holdPlace : where this object will be saved
         */
        public NameValueData(String bgNameText, String bgValueText, JPanel targetPanel, ArrayList<NameValueData> holdPlace)
        {
            // set super class
            super();
            super.setMaximumSize(new Dimension(925, 50)); // set size
            super.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5)); // set layout manager
            super.setBackground(backgroundColor); // set background color
            super.setOpaque(true); // apply color changes



            bgNameString = bgNameText;
            bgValueString = bgValueText;
            this.targetPanel = targetPanel;
            this.holdPlace = holdPlace;


            // set name text field
            dataNameTextField = new JTextField(bgNameText); // create new text field
            dataNameTextField.setForeground(backTextColor); // set text color
            dataNameTextField.setOpaque(true); // apply color changes
            dataNameTextField.setPreferredSize(new Dimension(224, 40)); // set size
            dataNameTextField.addActionListener(dataHandler);
            dataNameTextField.addFocusListener(dataHandler);
            super.add(dataNameTextField); // add to the panel
        

            // set value text field
            dataValueTextField = new JTextField(bgValueText); // create new text field
            dataValueTextField.setForeground(backTextColor); // set text color
            dataValueTextField.setOpaque(true); // apply color changes
            dataValueTextField.setPreferredSize(new Dimension(224, 40)); // set size
            dataValueTextField.addActionListener(dataHandler);
            dataValueTextField.addFocusListener(dataHandler);
            super.add(dataValueTextField); // add to the panel


            // set select button
            selectButton = new JRadioButton(); // create new button
            selectButton.putClientProperty("JComponent.sizeVariant", "large"); // set size
            SwingUtilities.updateComponentTreeUI(this);
            selectButton.addActionListener(dataHandler);
            selectButton.addFocusListener(dataHandler);
            super.add(selectButton); // add to the panel


            // set delet button
            deletButton = new JButton("✘"); // create new button
            deletButton.setPreferredSize(new Dimension(40, 40)); // set button size
            deletButton.setFont(deletButton.getFont().deriveFont(17.0f)); // set text size
            deletButton.setBackground(backgroundColor); // set background color
            deletButton.setForeground(Color.RED); // set text color
            deletButton.setOpaque(true); // apply color changes
            deletButton.addActionListener(dataHandler);
            deletButton.addFocusListener(dataHandler);
            super.add(deletButton); // add to the panel
        }




                /*  Methods  */

        /**
         * @return name of this pair
         */
        public String getDataName() { return dataNameTextField.getText(); }

        /**
         * @return value of this pair
         */
        public String getDataValue() { return dataValueTextField.getText(); }

        /**
         * @return {@code true} if this pair selecet to send
         */
        public boolean isSelected() { return selectButton.isSelected(); }


        private class DataEventHandler implements ActionListener, FocusListener
        {
            public void actionPerformed(ActionEvent e)
            {
                /*  delet button */
                if (e.getSource().equals(THIS.deletButton))
                {
                    if (holdPlace.size() > 1)
                    {
                        holdPlace.get(0).requestFocus();
                        targetPanel.remove(THIS);
                        targetPanel.revalidate();
                        targetPanel.repaint();
                        holdPlace.remove(THIS);
                    }
                }
            }


            public void focusGained(FocusEvent e) 
            {
                if (e.getSource().equals(THIS.dataNameTextField)
                    ||
                    e.getSource().equals(THIS.dataValueTextField))
                {
                    JTextField focusedTextField = (JTextField)e.getSource();

                    if (focusedTextField.getText().equals(bgNameString) || focusedTextField.getText().equals(bgValueString))
                    {
                        focusedTextField.setText("");
                        focusedTextField.setForeground(Color.BLACK);
                        focusedTextField.setOpaque(true);
                    }


                    if (holdPlace.indexOf(THIS) == holdPlace.size()-1)
                    {
                        holdPlace.add(new NameValueData(bgNameString, bgValueString, targetPanel, holdPlace));
                        targetPanel.add(holdPlace.get(holdPlace.size()-1));
                        targetPanel.revalidate();
                        targetPanel.repaint();
                    }
                }
            }
        
        
            public void focusLost(FocusEvent e) 
            {
                if (e.getSource().equals(THIS.dataNameTextField) 
                    || 
                    e.getSource().equals(THIS.dataValueTextField))
                {
                    JTextField focusLostedTextField = (JTextField)e.getSource();
                    if (focusLostedTextField.getText().length() != 0)
                        return;


                    String toPutString;
                    if (e.getSource().equals(THIS.dataNameTextField))
                        toPutString = bgNameString;
                    else
                        toPutString = bgValueString;
                        
                    focusLostedTextField.setText(toPutString);
                    focusLostedTextField.setForeground(backTextColor);
                    focusLostedTextField.setOpaque(true);
                }
            }
        }
    }






    // This class do the even handling of NewRequestGUI class
    private class EventHandler implements ActionListener, FocusListener
    {
        public void actionPerformed(ActionEvent e) 
        {
            /*  body kinds combo box event mainHandler  */
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



        public void focusGained(FocusEvent e) 
        {
            if (e.getSource() instanceof JTextField)
            {
                JTextField focusedTextField = (JTextField)e.getSource();

                focusedTextField.setText("");
                focusedTextField.setForeground(Color.BLACK);
                focusedTextField.setOpaque(true);
            }
        }
        
        
        public void focusLost(FocusEvent e) 
        {
            JTextField focusLostedTextField = (JTextField)e.getSource();
            if (focusLostedTextField.getText().length() != 0)
                return;


            String toPutString;
            if (e.getSource().equals(getUrlTextField))
                toPutString = " https:// ?! ";
            else if (e.getSource().equals(getTokenTextField))
                toPutString = " TOKEN . . .";
            else 
                toPutString = " PREFIX . . .";
                
            focusLostedTextField.setText(toPutString);
            focusLostedTextField.setForeground(backTextColor);
            focusLostedTextField.setOpaque(true);
		}
    }
}