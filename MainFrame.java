import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;





/**
 * This class is the main frame of the app
 * 
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

    // request details panel
    private static JPanel requestDetailsPanel = new JPanel();

    // response panel
    private static JPanel responsePanel = new JPanel();

    // background color of panels
    private static Color backgroundColor = new Color(46, 47, 44);



   

    
    
    
          /* Constructor */
    
    /**
     * Create the app GUI frame
     */
    public static void MainFrameInit()
    {
        // set the frame
        frame.setLayout(new BorderLayout()); // set frame layout
        frame.setMinimumSize(new Dimension(850, 400)); // set minimum size
        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);



        /* set the requests panel */
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
            
            JFormattedTextField getNewTabNameField = new JFormattedTextField(" new group name ... ");
            getNewTabNameField.setPreferredSize(new Dimension(190, 33)); // set size
            getNewTabNameField.setFont(insomniaText.getFont().deriveFont(15.0f)); // set text size

            JButton addButton = new JButton(" Add "); // creat new button
            addButton.setPreferredSize(new Dimension(70, 35)); // set size

            addTab.add(getNewTabNameField); // add text field
            addTab.add(addButton); // add button

            addTab.setBackground(backgroundColor); // set color
            addTab.setOpaque(true); // apply color changes

            // set last requests panel
            JPanel lastRequestsPanel = new JPanel(); // create new panel
            lastRequestsPanel.setLayout(new GridBagLayout()); // set the layout manager
            lastRequestsPanel.setBackground(backgroundColor); // set the background color
            lastRequestsPanel.setOpaque(true); // apply color changes

            // set request grouping
            JTabbedPane requestGroups = new JTabbedPane(); // create new tabbed pane
            requestGroups.setBackground(backgroundColor); // set color
            requestGroups.setOpaque(true); // apply color changes
            requestGroups.addTab("last requests", lastRequestsPanel); 
            requestGroups.addTab(" + ", addTab);      


            requestsPanel.setLayout(new BorderLayout());
            requestsPanel.add(insomniaText, BorderLayout.NORTH);
            requestsPanel.add(requestGroups, BorderLayout.CENTER);

            
            frame.add(requestsPanel, BorderLayout.WEST);
        }



        
        /* set the new requests details panel */
        {
            // set the panel
            requestDetailsPanel.setLayout(new BorderLayout()); // set the layout manager
            requestDetailsPanel.setBackground(backgroundColor);
            requestDetailsPanel.setOpaque(true); 
            frame.add(requestDetailsPanel, BorderLayout.CENTER);


            // set the kind combo box
            JComboBox<String> kindComboBox = new JComboBox<>(); // create new combo box
            kindComboBox.setPreferredSize(new Dimension(92, 50)); // set size 
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
            JFormattedTextField getUrlTextField = new JFormattedTextField(" https:// ?! "); // create new text field
            getUrlTextField.setPreferredSize(new Dimension(300, 50)); // set size 
            getUrlTextField.setFont(getUrlTextField.getFont().deriveFont(17.0f)); // set text size
            getUrlTextField.setBackground(Color.WHITE); // set background color
            getUrlTextField.setForeground(Color.BLACK); // set foreground color
            getUrlTextField.setOpaque(true); // apply color changes

            // set send button
            JButton sendButton = new JButton("Send"); // create new button
            sendButton.setPreferredSize(new Dimension(57, 50)); // set size
            sendButton.setBackground(Color.WHITE); // set background color
            sendButton.setForeground(new Color(76, 159, 103)); // set foreground color
            sendButton.setOpaque(true); // apply color changes

            // set the save button
            JButton saveButton = new JButton("Save"); // create a new button
            saveButton.setPreferredSize(new Dimension(57, 50)); // set size
            saveButton.setBackground(Color.WHITE); // set background color
            saveButton.setForeground(new Color(76, 159, 103)); // set foreground color
            saveButton.setOpaque(true); // apply color changes

            // set the north panel of the request detail
            JPanel northPanel = new JPanel(); // creat new panel
            northPanel.setBackground(Color.WHITE); // set the back ground color
            northPanel.setOpaque(true); // apply color changes
            northPanel.setLayout(new FlowLayout(0, 0, 0)); // set the layout manager
            northPanel.add(kindComboBox); // add kind combo box
            northPanel.add(getUrlTextField); // add get url text field
            northPanel.add(sendButton); // add send button
            northPanel.add(saveButton); // add save button

            // add to the request detail panel
            requestDetailsPanel.add(northPanel, BorderLayout.NORTH);
        }




        /*  set request details  */
        {
            // set the tabs
            JTabbedPane requestDetails = new JTabbedPane(); // create new tabbed pane
            requestDetails.setBackground(backgroundColor); // set color
            requestDetails.setOpaque(true); // apply color changes
            requestDetailsPanel.add(requestDetails);

            // set body tab
            {

            JPanel bodyPanel = new JPanel(); // creat new panel
            bodyPanel.setBackground(backgroundColor); // set background color
            bodyPanel.setOpaque(true); // apply color changes
            bodyPanel.setLayout(new GridBagLayout()); // set layout manager
            GridBagConstraints gbc = new GridBagConstraints();
            requestDetails.add("Body", bodyPanel); // add body panel


            JComboBox<String> bodyKinds = new JComboBox<>(); // create new combo box
            bodyKinds.setBackground(backgroundColor); // set background color
            bodyKinds.setOpaque(true); // apply color changes
            bodyKinds.addItem("Form data"); 
            bodyKinds.addItem("JSON");
            bodyKinds.addItem("Binary Data");
            gbc.gridx = gbc.gridy = 0;
            gbc.weightx = 1; 
            gbc.fill = GridBagConstraints.HORIZONTAL;
            bodyPanel.add(bodyKinds, gbc);
           
            // set the body edit panel
            JPanel bodyEditPanel = new JPanel(); // create new panel
            bodyEditPanel.setLayout(new CardLayout()); // set layout manager
            bodyEditPanel.setBackground(backgroundColor); // set the background color
            bodyEditPanel.setOpaque(true); // apply color changes
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = gbc.weighty = 2;
            gbc.gridx = 0; gbc.gridy = 1;
            bodyPanel.add(bodyEditPanel, gbc); // add edit panel 
            
            // set the form data Panel
            JPanel formDataPanel = new JPanel(); // create new panel
            formDataPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0)); // set layout manager
            formDataPanel.setBackground(backgroundColor); // set background color
            formDataPanel.setOpaque(true); // apply color changes

            JFormattedTextField dataName = new JFormattedTextField("name...");
            dataName.setPreferredSize(new Dimension(224, 40));
            JFormattedTextField dataValue = new JFormattedTextField("value...");
            dataValue.setPreferredSize(new Dimension(224, 40));
            JRadioButton selectButton = new JRadioButton();
            JButton deletButton = new JButton("✘");
            deletButton.setFont(deletButton.getFont().deriveFont(17.0f));
            deletButton.setBackground(backgroundColor);
            deletButton.setForeground(Color.RED);
            deletButton.setOpaque(true);
            deletButton.setPreferredSize(new Dimension(40, 40));

            formDataPanel.add(dataName); formDataPanel.add(dataValue); 
            formDataPanel.add(selectButton); formDataPanel.add(deletButton);
    

            bodyEditPanel.add("form data", formDataPanel);

            
            // set Json panel
            JPanel jsonPanel = new JPanel(); // create new panel
            jsonPanel.setLayout(new GridLayout(1, 1, 7, 7)); //set layout manager
            jsonPanel.setBackground(backgroundColor); // set background color
            jsonPanel.setOpaque(true); // apply color changes

            JTextArea jsonTextArea = new JTextArea(); // creat new text area
            jsonTextArea.setFont(jsonTextArea.getFont().deriveFont(15.0f)); // set text size
            jsonPanel.add(jsonTextArea); // add text area

            bodyEditPanel.add("JSON", jsonPanel); // add body panel
        
            

            // set binary file panel
            JPanel binaryFilePanel = new JPanel(); // create new panel
            binaryFilePanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // set layout manager
            binaryFilePanel.setBackground(backgroundColor); // set background color
            binaryFilePanel.setOpaque(true); // apply color changes
            bodyEditPanel.add("binary file", binaryFilePanel); // add binary file panel

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

            }

           
            // set Auth tab
            {

            // set Auth panel
            JPanel authPanel = new JPanel(); // create new panel
            authPanel.setLayout(new GridBagLayout()); // set layout manager
            authPanel.setBackground(backgroundColor); // set background color
            authPanel.setOpaque(true); // apply changes
            GridBagConstraints gbc = new GridBagConstraints();
            requestDetails.addTab("Auth", authPanel); // add auth panel

            JLabel tokenLabel = new JLabel(" TOKEN "); // set text
            tokenLabel.setMinimumSize(new Dimension(100, 40)); // set size
            tokenLabel.setFont(tokenLabel.getFont().deriveFont(16.0f)); // set text size
            tokenLabel.setBackground(backgroundColor); tokenLabel.setForeground(Color.WHITE);
            tokenLabel.setOpaque(true); // apply color changes
            gbc.gridx = gbc.gridy = 0;
            authPanel.add(tokenLabel, gbc); // add to panel

            JFormattedTextField getTokenTextField = new JFormattedTextField(); // create new text field
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

            JFormattedTextField getPrefixTextField = new JFormattedTextField(); // create new text field
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
            requestDetails.add("Query", queryPanel); // add Query tab

            JFormattedTextField dataName = new JFormattedTextField("name...");
            dataName.setPreferredSize(new Dimension(224, 40));
            JFormattedTextField dataValue = new JFormattedTextField("value...");
            dataValue.setPreferredSize(new Dimension(224, 40));
            JRadioButton selectButton = new JRadioButton();
            JButton deletButton = new JButton("✘");
            deletButton.setFont(deletButton.getFont().deriveFont(17.0f));
            deletButton.setBackground(backgroundColor);
            deletButton.setForeground(Color.RED);
            deletButton.setOpaque(true);
            deletButton.setPreferredSize(new Dimension(40, 40));

            queryPanel.add(dataName); queryPanel.add(dataValue); 
            queryPanel.add(selectButton); queryPanel.add(deletButton);

            }



            // set Header tab
            {

            // set the Header Panel
            JPanel headerPanel = new JPanel(); // create new panel
            headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0)); // set layout manager
            headerPanel.setBackground(backgroundColor); // set background color
            headerPanel.setOpaque(true); // apply color changes
            requestDetails.add("Header", headerPanel); // add Query tab

            JFormattedTextField dataName = new JFormattedTextField("header...");
            dataName.setPreferredSize(new Dimension(224, 40));
            JFormattedTextField dataValue = new JFormattedTextField("value...");
            dataValue.setPreferredSize(new Dimension(224, 40));
            JRadioButton selectButton = new JRadioButton();
            JButton deletButton = new JButton("✘");
            deletButton.setFont(deletButton.getFont().deriveFont(17.0f));
            deletButton.setBackground(backgroundColor);
            deletButton.setForeground(Color.RED);
            deletButton.setOpaque(true);
            deletButton.setPreferredSize(new Dimension(40, 40));

            headerPanel.add(dataName); headerPanel.add(dataValue); 
            headerPanel.add(selectButton); headerPanel.add(deletButton);

            }
        }



        /*  set request response part  */
        {
            // set response panel
            responsePanel.setLayout(new BorderLayout(0, 0)); // set layout manager
            responsePanel.setBackground(backgroundColor); // set background color
            responsePanel.setOpaque(true); // apply color changes
            frame.add(responsePanel, BorderLayout.EAST); // add to the frame


            // set response status panel
            {
            
            JPanel responseStatusPanel = new JPanel(); // create new panel
            responseStatusPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // set layout manager
            responseStatusPanel.setBackground(Color.WHITE); // set back ground color
            responseStatusPanel.setOpaque(true); // apply color changes
            responsePanel.add(responseStatusPanel, BorderLayout.NORTH); // add to response panel


            // set status code label
            JLabel statusCodLabel = new JLabel("200 OK"); // create new label
            statusCodLabel.setHorizontalAlignment(SwingConstants.CENTER); // set alignment
            statusCodLabel.setFont(statusCodLabel.getFont().deriveFont(14.5f)); // set text size
            statusCodLabel.setMinimumSize(new Dimension(60, 30)); // set size
            statusCodLabel.setBorder(BorderFactory.createLineBorder(new Color(133, 183, 66), 6)); // set border
            statusCodLabel.setBackground(new Color(133, 183, 66));
            statusCodLabel.setForeground(Color.WHITE);
            statusCodLabel.setOpaque(true); // apply color changes
            responseStatusPanel.add(statusCodLabel); // add to panel

            // set time label
            JLabel responseTimeLabel = new JLabel("882ms"); // create new label
            responseTimeLabel.setHorizontalAlignment(SwingConstants.CENTER); // set alignment
            responseTimeLabel.setFont(responseTimeLabel.getFont().deriveFont(14.5f)); // set text size
            responseTimeLabel.setMinimumSize(new Dimension(60, 30)); // set size
            responseTimeLabel.setBorder(BorderFactory.createLineBorder(new Color(224, 224, 224), 6)); // set border
            responseTimeLabel.setBackground(new Color(224, 224, 224));
            responseTimeLabel.setForeground(new Color(102, 102, 102));
            responseTimeLabel.setOpaque(true); // apply color changes
            responseStatusPanel.add(responseTimeLabel); // add to panel

            // set size label
            JLabel responseSizeLabel = new JLabel("50 KB"); // create new label
            responseSizeLabel.setHorizontalAlignment(SwingConstants.CENTER); // set alignment
            responseSizeLabel.setFont(responseSizeLabel.getFont().deriveFont(14.5f)); // set text size
            responseSizeLabel.setMinimumSize(new Dimension(60, 30)); // set size
            responseSizeLabel.setBorder(BorderFactory.createLineBorder(new Color(224, 224, 224), 6)); // set border
            responseSizeLabel.setBackground(new Color(224, 224, 224));
            responseSizeLabel.setForeground(new Color(102, 102, 102));
            responseSizeLabel.setOpaque(true); // apply color changes
            responseStatusPanel.add(responseSizeLabel); // add to panel

            // set space label
            JLabel spacLabel = new JLabel();
            spacLabel.setPreferredSize(new Dimension(200, 30));
            spacLabel.setBackground(Color.WHITE);
            spacLabel.setOpaque(true);
            responseStatusPanel.add(spacLabel);


            // set response details
            {

                // set tabs 
                JTabbedPane responseDetailsTabs = new JTabbedPane();
                responseDetailsTabs.setBackground(backgroundColor);
                responseDetailsTabs.setOpaque(true);
                responsePanel.add(responseDetailsTabs, BorderLayout.CENTER);


                // set the message body tab
                {

                JPanel messageBodyPanel = new JPanel(); // creat new panel
                messageBodyPanel.setBackground(backgroundColor); // set background color
                messageBodyPanel.setOpaque(true); // apply color changes
                messageBodyPanel.setLayout(new GridBagLayout()); // set layout manager
                GridBagConstraints gbc = new GridBagConstraints();
                responseDetailsTabs.add("Message Body", messageBodyPanel); // add body panel
    
    
                JComboBox<String> bodyKinds = new JComboBox<>(); // create new combo box
                bodyKinds.setBackground(backgroundColor); // set background color
                bodyKinds.setOpaque(true); // apply color changes
                bodyKinds.addItem("RAW"); 
                bodyKinds.addItem("JSON");
                bodyKinds.addItem("Preview");
                gbc.gridx = gbc.gridy = 0;
                gbc.weightx = 1; 
                gbc.fill = GridBagConstraints.HORIZONTAL;
                messageBodyPanel.add(bodyKinds, gbc);


                // set the body edit panel
                JPanel messageBodyShowPanel = new JPanel(); // create new panel
                messageBodyShowPanel.setLayout(new CardLayout()); // set layout manager
                messageBodyShowPanel.setBackground(backgroundColor); // set the background color
                messageBodyShowPanel.setOpaque(true); // apply color changes
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weightx = gbc.weighty = 2;
                gbc.gridx = 0; gbc.gridy = 1;
                messageBodyPanel.add(messageBodyShowPanel, gbc); // add edit panel 


                // set JSON and RAW tab

                JPanel jsonAndRawPanel = new JPanel(); // create new panel
                jsonAndRawPanel.setLayout(new GridLayout(1, 1, 15, 15)); //set layout manager
                jsonAndRawPanel.setBackground(backgroundColor); // set background color
                jsonAndRawPanel.setOpaque(true); // apply color changes

                JLabel jsonAndRawLabel = new JLabel("a"); // creat new text area
                jsonAndRawLabel.setBorder(BorderFactory.createLineBorder(backgroundColor, 15));
                jsonAndRawLabel.setBackground(Color.WHITE);
                jsonAndRawLabel.setOpaque(true);
                jsonAndRawLabel.setFont(jsonAndRawLabel.getFont().deriveFont(15.0f)); // set text size
                jsonAndRawPanel.add(jsonAndRawLabel); // add text area

                messageBodyShowPanel.add("JSON and RAW", jsonAndRawPanel); // add body panel
                }
            }
            }
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