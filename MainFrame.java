import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;





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



        
        /* set the new requests details panel */
        {
            
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



                // set Headers tab
                {
                
                // set header panel
                JPanel headersPanel = new JPanel(); 
                headersPanel.setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                headersPanel.setBackground(backgroundColor);
                headersPanel.setOpaque(true);
                responseDetailsTabs.add("Headers", headersPanel);

                
                // set headers table
                JTable headersTable = new JTable();
                }
            }



            /*  set menu bar  */
            {
                // set menu bar
                MenuBar frameMenuBar = new MenuBar();
                

                
                // set application menu
                Menu applicationMenu = new Menu("Application");
                applicationMenu.setShortcut(new MenuShortcut(KeyEvent.VK_1, false));
                
                MenuItem optionItem = new MenuItem("Options");
                optionItem.setShortcut(new MenuShortcut(KeyEvent.VK_O, false));
                applicationMenu.add(optionItem);

                MenuItem exitItem = new MenuItem("Exit");
                exitItem.setShortcut(new MenuShortcut(KeyEvent.VK_E, false));
                applicationMenu.add(exitItem);



                // set view menu
                Menu viewMenu = new Menu("View");
                viewMenu.setShortcut(new MenuShortcut(KeyEvent.VK_2, false));

                MenuItem toggleFullScreenItem = new MenuItem("Toggle Full Screen");
                toggleFullScreenItem.setShortcut(new MenuShortcut(KeyEvent.VK_U, false));
                viewMenu.add(toggleFullScreenItem);

                MenuItem toggleSidebarItem = new MenuItem("Toggle Sidebar");
                toggleSidebarItem.setShortcut(new MenuShortcut(KeyEvent.VK_U, true));
                viewMenu.add(toggleSidebarItem);



                // set help menu
                Menu helpMenu = new Menu("Help");
                helpMenu.setShortcut(new MenuShortcut(KeyEvent.VK_3, false));

                MenuItem aboutItem = new MenuItem("About");
                aboutItem.setShortcut(new MenuShortcut(KeyEvent.VK_B, false));
                helpMenu.add(aboutItem);

                MenuItem helpItem = new MenuItem("Help");
                helpItem.setShortcut(new MenuShortcut(KeyEvent.VK_H, false));
                helpMenu.add(helpItem);


                frameMenuBar.add(applicationMenu);
                frameMenuBar.add(viewMenu);
                frameMenuBar.add(helpMenu);
                frame.setMenuBar(frameMenuBar);
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