import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Scanner;
import java.io.*;





/**
 * This class is the main frame of the app
 * This class is a connector between difrent parts of gui
 * 
 * 
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.1.0
 */
public class MainFrame 
{
            /*  Feilds  */

    // main frame
    private static JFrame frame = new JFrame(" Insomnia ");



    // request details panel
    private static LastRequestsGUI lastRequestsPanel;

    // new request panel
    private static NewRequestGUI newRequestPanel;

    // response panel
    private static ResponseGUI responsePanel;



    // menu bar of main fram
    private static MenuBar frameMenuBar = new MenuBar();


    // application menu
    private static Menu applicationMenu = new Menu("Application");
    private static MenuItem followRedirectsItem = new MenuItem("Follow redirects:   ☑️");


    // view menu
    private static Menu viewMenu = new Menu("View");
    private static MenuItem toggleFullScreenItem = new MenuItem("Toggle Full Screen");
    private static MenuItem toggleSidebarItem = new MenuItem("Toggle Sidebar");


    // help  menu
    private static Menu helpMenu = new Menu("Help");
    private static MenuItem aboutItem = new MenuItem("About");
    private static MenuItem helpItem = new MenuItem("Help");




    // a handler for events
    private static EventHandler handler = new EventHandler();

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
        frame.setMinimumSize(new Dimension(1200, 600)); // set minimum size
        frame.setLocation(150, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        



        // create last requests panel
        lastRequestsPanel = new LastRequestsGUI(backgroundColor);
        frame.add(lastRequestsPanel, BorderLayout.WEST);

        // create new request panel
        newRequestPanel = new NewRequestGUI(backgroundColor);
        frame.add(newRequestPanel, BorderLayout.CENTER);

        // create last request panel
        responsePanel = new ResponseGUI(backgroundColor);
        frame.add(responsePanel, BorderLayout.EAST);
       

        // load last requests
        DataBase.init();
        lastRequestsPanel.loadRequests(DataBase.getRequests());

        
        // set application menu
        applicationMenu.setShortcut(new MenuShortcut(KeyEvent.VK_1, false));
        followRedirectsItem.setShortcut(new MenuShortcut(KeyEvent.VK_R, false));
        followRedirectsItem.addActionListener(handler);
        applicationMenu.add(followRedirectsItem);


        // set view menu
        viewMenu.setShortcut(new MenuShortcut(KeyEvent.VK_2, false));
        toggleFullScreenItem.setShortcut(new MenuShortcut(KeyEvent.VK_U, false));
        toggleFullScreenItem.addActionListener(handler);
        viewMenu.add(toggleFullScreenItem);
        toggleSidebarItem.setShortcut(new MenuShortcut(KeyEvent.VK_L, false));
        toggleSidebarItem.addActionListener(handler);
        viewMenu.add(toggleSidebarItem);



        // set help menu
        helpMenu.setShortcut(new MenuShortcut(KeyEvent.VK_3, false));
        aboutItem.setShortcut(new MenuShortcut(KeyEvent.VK_B, false));
        aboutItem.addActionListener(handler);
        helpMenu.add(aboutItem);
        helpItem.setShortcut(new MenuShortcut(KeyEvent.VK_P, false));
        helpItem.addActionListener(handler);
        helpMenu.add(helpItem);


        frameMenuBar.add(applicationMenu);
        frameMenuBar.add(viewMenu);
        frameMenuBar.add(helpMenu);
        frame.setMenuBar(frameMenuBar);
    }
    

    /**
     * This method set view of the center panel with selected request
     * 
     * @param details : details of request
     */
    public static void openRequest(String details)
    {
        newRequestPanel.setPanel(details);
    }


    /**
     * This method save a request and add a button for it in gui
     * 
     * 
     * @param gpName : name of the request group
     * @param reqName : name of the request
     * @param reqKind : request method
     * @param reqDetails : a string of request details
     */
    public static void save(String gpName, String reqName, String reqKind, String reqDetails)
    {
        if (gpName.equals(""))
            gpName = "last requests";
        lastRequestsPanel.saveRequest(reqKind, reqName, gpName, reqDetails);
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


    /**
     * @return a combo box of groups
     */
    public static JComboBox<String> getGroups()
    {
        return lastRequestsPanel.getGroupsComboBox();
    }


    /**
     * This method updata the gui respones part
     * 
     * 
     * @param statusCode : status code of response
     * @param responseMessage : message of the request
     * @param responseTime : time of the response
     * @param contentLength : content length of re response
     * @param contentFormat : format of the response body
     * @param headers : headers of response
     */
    public static void updateGUI(int statusCode, String responseMessage, long responseTime, long contentLength, String contentFormat, HashMap<String, String> headers)
    {
        responsePanel.setTime(responseTime);
        responsePanel.setResponseCodeAndMessage(statusCode, responseMessage);
        responsePanel.setContentLength(contentLength);
        responsePanel.setHeaders(headers);
        
        responsePanel.update(contentFormat);
    }


    /**
     * This method shows errors 
     */
    public static void showError()
    {
        Scanner in = null;

        try{ in = new Scanner(new File(DataBase.getErrorsLogGuiPath())); }
        catch (FileNotFoundException ex) {}

        String message = in.nextLine();
        
        JOptionPane.showMessageDialog(frame, message, "error", JOptionPane.ERROR_MESSAGE);
    }










    // an inner class for handling events
    private static class EventHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {
            if (e.getSource().equals(followRedirectsItem))
            {
                Insomnia.setFollowRedirects(!Insomnia.isFollowRedirects());

                if (Insomnia.isFollowRedirects())
                    followRedirectsItem.setLabel("Follow redirects:   ☑️");
                else 
                    followRedirectsItem.setLabel("Follow redirects:   ◻️");
            }


            if (e.getSource().equals(toggleFullScreenItem))
            {
                if (frame.getExtendedState() == 0) 
                    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                else
                    frame.setExtendedState(0);
            }


            if (e.getSource().equals(toggleSidebarItem))
            {
                if (lastRequestsPanel.isVisible())
                    lastRequestsPanel.setVisible(false);
                else 
                    lastRequestsPanel.setVisible(true);
            }


            if (e.getSource().equals(helpItem))
            {
                JFrame helpTextFrame = new JFrame("Help");

                helpTextFrame.setLocationRelativeTo(frame);
                helpTextFrame.setMinimumSize(new Dimension(450, 300));
                helpTextFrame.setLocation(500, 250);
                helpTextFrame.setResizable(false);

                JPanel helpFramePanel = new JPanel(new GridLayout(1, 1, 30, 30));
                helpFramePanel.setBackground(backgroundColor);
                helpFramePanel.setOpaque(true);
                helpTextFrame.add(helpFramePanel);

                JTextArea helpTextArea = new JTextArea();
                helpTextArea.setBackground(backgroundColor);
                helpTextArea.setForeground(Color.WHITE);
                helpTextArea.setOpaque(true);
                helpTextArea.setEditable(false);

                helpTextArea.setFont(helpTextArea.getFont().deriveFont(20));
                helpTextArea.setText("   Insomnia  \n\n" +
                                     "you can send a http request with this app\n" + 
                                     "supported methods for your reqeusts are: GET, POST, PUT and DELETE\n" +
                                     "if you want to send a file as your request form data body, please\n" + 
                                     "write '(FILE)' at the end of the key and give the ablsoute path of\n" +
                                     "your file as value.\n" + 
                                     "in headers tab, if you click on one them, the value and key of that\n" + 
                                     "will be copy on your clipboard.\n" +
                                     "you can set follow redirect and system tray in application menu in \n" +
                                     "menu bar");
                helpFramePanel.add(helpTextArea);

                helpTextFrame.setVisible(true);
            }


            if (e.getSource().equals(aboutItem))
                JOptionPane.showMessageDialog(frame, "you can find me at https://github.com/MmahdiM79 ;)", "contact us", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}