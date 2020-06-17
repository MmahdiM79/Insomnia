import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.awt.datatransfer.*;





/**
 * This class represnt the Right part of the Insomnia app GUI
 * This part is for make a new request
 * 
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.2.0
 */
public class ResponseGUI extends JPanel
{
            /*  Fields  */

    // a panel for response time, size and status code
    private JPanel responseStatusPanel = new JPanel();

    // status code of the response
    private JLabel statusCodLabel = new JLabel();

    // time of the response
    private JLabel responseTimeLabel = new JLabel();

    // size of the response content
    private JLabel responseSizeLabel = new JLabel();


    // a tabbed pane for response details
    private JTabbedPane responseDetailsTabs = new JTabbedPane();

    
    // a panel for response body
    private JPanel responseBodyTabPanel = new JPanel();

    // a combo box for how to show the response body
    private JComboBox<String> viewTypesComboBox = new JComboBox<>();

    // a panel for response body show
    private JPanel responseBodyShowPanel = new JPanel(); 


    // a panel for show response body in raw format
    private JPanel rawPanel = new JPanel();

    // a text area for raw panel
    private JTextArea rawTextArea = new JTextArea();


    // a panel for priview
    private JPanel priviewPanel = new JPanel();


    // a panel for response headers
    private JPanel headersPanel = new JPanel();

    // a button for copy all headers
    private JButton nameValueLable = new JButton("<html><b>NAME&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; |&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;VALUE</b></html>");


    // a panel for time line
    private JPanel timeLinePanel = new JPanel();

    // a text area for time line
    private JTextArea timeLineTextArea = new JTextArea();



    // a handler for components
    private EventHandler mainHandler = new EventHandler();
        
    private Color backgroundColor;



    
    


         /* Constructor */

    /**
     * Create a frame for respose part
     * 
     * @param bgColor : back grouc color
     */
    public ResponseGUI(Color bgColor) 
    {
        super();
        super.setMaximumSize(new Dimension(925, 600));
        super.setLayout(new BorderLayout(0, 0)); // set layout manager
        super.setBackground(backgroundColor); // set background color
        super.setOpaque(true); // apply color changes



        // set back ground color
        this.backgroundColor = bgColor;
        




        // set response status panel        
        responseStatusPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // set layout manager
        responseStatusPanel.setBackground(Color.WHITE); // set back ground color
        responseStatusPanel.setOpaque(true); // apply color changes
        this.add(responseStatusPanel, BorderLayout.NORTH); // add to response panel


        // set status code label
        statusCodLabel.setHorizontalAlignment(SwingConstants.CENTER); // set alignment
        statusCodLabel.setFont(statusCodLabel.getFont().deriveFont(14.5f)); // set text size
        statusCodLabel.setMinimumSize(new Dimension(60, 30)); // set size
        statusCodLabel.setBorder(BorderFactory.createLineBorder(new Color(133, 183, 66), 6)); // set border
        statusCodLabel.setBackground(new Color(133, 183, 66));
        statusCodLabel.setForeground(Color.WHITE);
        statusCodLabel.setOpaque(true); // apply color changes
        responseStatusPanel.add(statusCodLabel); // add to panel


        // set time label
        responseTimeLabel.setHorizontalAlignment(SwingConstants.CENTER); // set alignment
        responseTimeLabel.setFont(responseTimeLabel.getFont().deriveFont(14.5f)); // set text size
        responseTimeLabel.setMinimumSize(new Dimension(60, 30)); // set size
        responseTimeLabel.setBorder(BorderFactory.createLineBorder(new Color(224, 224, 224), 6)); // set border
        responseTimeLabel.setBackground(new Color(224, 224, 224));
        responseTimeLabel.setForeground(new Color(102, 102, 102));
        responseTimeLabel.setOpaque(true); // apply color changes
        responseStatusPanel.add(responseTimeLabel); // add to panel


        // set size label
        responseSizeLabel.setHorizontalAlignment(SwingConstants.CENTER); // set alignment
        responseSizeLabel.setFont(responseSizeLabel.getFont().deriveFont(14.5f)); // set text size
        responseSizeLabel.setMinimumSize(new Dimension(60, 30)); // set size
        responseSizeLabel.setBorder(BorderFactory.createLineBorder(new Color(224, 224, 224), 6)); // set border
        responseSizeLabel.setBackground(new Color(224, 224, 224));
        responseSizeLabel.setForeground(new Color(102, 102, 102));
        responseSizeLabel.setOpaque(true); // apply color changes
        responseStatusPanel.add(responseSizeLabel); // add to panel


        // set space label
        JLabel spaceLabel = new JLabel();
        spaceLabel.setPreferredSize(new Dimension(200, 30));
        spaceLabel.setBackground(Color.WHITE);
        spaceLabel.setOpaque(true);
        responseStatusPanel.add(spaceLabel);





        responseDetailsTabs.setBackground(backgroundColor);
        responseDetailsTabs.setOpaque(true);
        this.add(responseDetailsTabs, BorderLayout.CENTER);


        responseBodyTabPanel.setBackground(backgroundColor); // set background color
        responseBodyTabPanel.setOpaque(true); // apply color changes
        responseBodyTabPanel.setLayout(new BorderLayout()); // set layout manager
        responseDetailsTabs.add("Message Body", responseBodyTabPanel); // add body panel


        viewTypesComboBox.setMaximumSize(new Dimension(925, 30));
        viewTypesComboBox.setBackground(backgroundColor); // set background color
        viewTypesComboBox.setOpaque(true); // apply color changes
        viewTypesComboBox.addItem("RAW");
        viewTypesComboBox.addItem("Preview");
        viewTypesComboBox.addActionListener(mainHandler);
        responseBodyTabPanel.add(viewTypesComboBox, BorderLayout.NORTH);


        // set the body edit panel
        responseBodyShowPanel.setLayout(new CardLayout()); // set layout manager
        responseBodyShowPanel.setBackground(backgroundColor); // set the background color
        responseBodyShowPanel.setOpaque(true); // apply color changes
        responseBodyTabPanel.add(responseBodyShowPanel, BorderLayout.CENTER); // add edit panel 


        rawPanel.setLayout(new GridLayout(1, 1, 15, 15)); //set layout manager
        rawPanel.setBackground(backgroundColor); // set background color
        rawPanel.setOpaque(true); // apply color changes

        rawTextArea.setFont(rawTextArea.getFont().deriveFont(15.0f)); // set text size
        rawTextArea.setBorder(BorderFactory.createLineBorder(backgroundColor, 30));
        rawTextArea.setBackground(backgroundColor);
        rawTextArea.setForeground(Color.WHITE);
        rawTextArea.setLineWrap(true);
        rawTextArea.setEditable(false); 

        rawPanel.add(rawTextArea); // add text area

        JScrollPane rawPanelScrollPane = new JScrollPane(rawPanel);
        rawPanelScrollPane.setBackground(backgroundColor);
        rawPanelScrollPane.setOpaque(true);

        responseBodyShowPanel.add("raw", rawPanelScrollPane); // add body panel


        
        priviewPanel.setBackground(backgroundColor);
        priviewPanel.setOpaque(true);

        JScrollPane priviewPanelScrollPane = new JScrollPane(priviewPanel);
        priviewPanelScrollPane.setBackground(backgroundColor);
        priviewPanelScrollPane.setOpaque(true);

        responseBodyShowPanel.add("priview", priviewPanel);


 
        headersPanel.setLayout(new BoxLayout(headersPanel, BoxLayout.Y_AXIS));
        headersPanel.setBackground(backgroundColor);
        headersPanel.setOpaque(true);
        
        // set headers table
        nameValueLable.setHorizontalAlignment(SwingConstants.CENTER);
        nameValueLable.setBackground(backgroundColor);
        nameValueLable.setForeground(Color.WHITE);
        nameValueLable.setFont(nameValueLable.getFont().deriveFont(18.0f));
        nameValueLable.setMinimumSize(new Dimension(900, 55));
        nameValueLable.setOpaque(true);
        nameValueLable.addActionListener(mainHandler);
        headersPanel.add(nameValueLable);

        JScrollPane headersPanelScrollPane = new JScrollPane(headersPanel);
        headersPanelScrollPane.setBackground(backgroundColor);
        headersPanelScrollPane.setOpaque(true);

        responseDetailsTabs.add("Headers", headersPanelScrollPane);



        timeLinePanel.setLayout(new GridLayout(1, 1, 15, 15)); //set layout manager
        timeLinePanel.setBackground(backgroundColor); // set background color
        timeLinePanel.setOpaque(true); // apply color changes

        timeLineTextArea.setFont(timeLineTextArea.getFont().deriveFont(15.0f)); // set text size
        timeLineTextArea.setBorder(BorderFactory.createLineBorder(backgroundColor, 30));
        timeLineTextArea.setBackground(backgroundColor);
        timeLineTextArea.setForeground(Color.WHITE);
        timeLineTextArea.setLineWrap(true);
        timeLineTextArea.setEditable(false); 
        timeLinePanel.add(timeLineTextArea); // add text area

        JScrollPane timeLinePanelScrollPane = new JScrollPane(timeLinePanel);
        timeLinePanelScrollPane.setBackground(backgroundColor);
        timeLinePanelScrollPane.setOpaque(true);

        responseDetailsTabs.add("Time Line", timeLinePanelScrollPane);
    }









            /*  Method  */

    /**
     * Set status code and Message in GUI
     * 
     * @param responseCode : status code of the request
     * @param responseMessage : message of the response
     */
    public void setResponseCodeAndMessage(int responseCode, String responseMessage)
    {
        Color colorToChange;

        if (responseCode/100 == 2)
            colorToChange = Color.GREEN;

        else if (responseCode/100 == 3)
            colorToChange = Color.ORANGE;

        else
            colorToChange = Color.RED;


        statusCodLabel.setBorder(BorderFactory.createLineBorder(colorToChange, 6));
        statusCodLabel.setBackground(colorToChange);
        statusCodLabel.setText("" + responseCode + " " + responseMessage);
    }


    /**
     * Set size of the response content length in GUI
     * 
     * @param responseSize : size of the response content
     */
    public void setContentLength(long responseSize)
    {
        responseSizeLabel.setText("" + responseSize + " kb");
        responseSizeLabel.updateUI();
    }


    /**
     * Set time that request taken to be answered
     * 
     * @param time : time in 'ms'
     */
    public void setTime(long time)
    {
        responseTimeLabel.setText("" + time + " ms");
        responseTimeLabel.updateUI();
    }


    /**
     * This method set the response headers to the GUI
     * 
     * @param headers : keys and values of response headers
     */
    public void setHeaders(HashMap<String, String> headers)
    {
        headersPanel.removeAll();
        headersPanel.add(nameValueLable);

        for (String key : headers.keySet())
        {
            if (headers.get(key).length() > 25)
                continue;

            headersPanel.add(new NameValuedeHeader(key, headers.get(key)));
        }
        headersPanel.updateUI();
    }


    /**
     * This method update the right part of the GUI
     * 
     * @param contentType : type of the resposne body
     */
    public void update(String contentType)
    {       
        // time line update
        try 
        {
            FileInputStream timeLineFile = new FileInputStream(new File(DataBase.getTimeLineGuiPath()));
            timeLineTextArea.setText(new String(timeLineFile.readAllBytes()));
            timeLinePanel.repaint();
            timeLinePanel.revalidate();
            timeLineFile.close();
        }
        catch (IOException e) {}


        // raw update
        try 
        {
            FileInputStream rawFile = new FileInputStream(new File(DataBase.getResponseBodyGuiPath()));
            rawTextArea.setText(new String(rawFile.readAllBytes()));
        }
        catch (IOException e) {}



        if (contentType != null)
        {
         
            if (contentType.equals("png") || contentType.equals("jpg"))
            {
                ImageIcon body = new ImageIcon(DataBase.getResponseBodyGuiPath());
                JLabel bodylLabel = new JLabel(body);
                priviewPanel.add(bodylLabel);
            }
            else if (contentType.equals("json"))
            {
                String body = null;
                try
                {
                    body = new String((new FileInputStream(new File(DataBase.getResponseBodyGuiPath()))).readAllBytes());
                }
                catch (IOException e) {}

                body = body.substring(1, body.length()-1);
                String[] pairs = body.split(",");

                String priviewText = "{ ";
                for (String pair : pairs)
                {
                    pair = pair.replace(":", ":  ");
                    priviewText = priviewText + "\n\t" + pair;
                }
                priviewText = priviewText + "\n}";


                JTextArea priviewTextArea = new JTextArea(priviewText);
                priviewTextArea.setBackground(backgroundColor);
                priviewTextArea.setForeground(Color.WHITE);
                priviewTextArea.setOpaque(true);

                priviewPanel.add(priviewTextArea);
            }
            else
                priviewPanel.removeAll();
        }
        else 
            priviewPanel.removeAll();
    }
















    // This class reprsent a button for a header field
    private class NameValuedeHeader extends JButton
    {
                /*  Fields  */

        // name of the header
        private String name;

        // value of the header
        private String value;

        // a handler for actions
        private EventHandler handler;





             /* Constructor */
        
        /**
         * Create a new button for a header field
         * 
         * 
         * @param name : name of the header
         * @param value : value of the header
         */
        public NameValuedeHeader(String name, String value)
        {
            super("<html><b>" + name + "&emsp;&emsp;&emsp;:&emsp;&emsp;&emsp;" + value + "</b></html>");


            this.name = name;
            this.value = value;
            this.handler = new EventHandler();


            super.setHorizontalAlignment(SwingConstants.LEFT);
            super.setMaximumSize(new Dimension(600, 42));
            super.setMinimumSize(new Dimension(600, 42));
            super.setBackground(backgroundColor);
            super.setForeground(Color.WHITE);
            super.setFont(super.getFont().deriveFont(12.0f));
            super.addActionListener(handler);
        }




                /*  Methods  */

        /**
         * @return a String of name and value of this header
         */
        @Override
        public String toString()
        {
            return "" + name + ": " + value;
        }
    }




    // This class do the event handeling of ResponseGUI class
    private class EventHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {
            if (e.getSource().equals(viewTypesComboBox))
            {
                if (viewTypesComboBox.getSelectedItem().equals("Preview"))
                    ((CardLayout) responseBodyShowPanel.getLayout()).show(responseBodyShowPanel, "priview");
                else if (viewTypesComboBox.getSelectedItem().equals("RAW"))
                    ((CardLayout) responseBodyShowPanel.getLayout()).show(responseBodyShowPanel, "raw");
            }


            if (e.getSource().equals(nameValueLable))
            {
                String stringToCopy = "";
                for (Component header : headersPanel.getComponents())
                {
                    if (!(header instanceof NameValuedeHeader))
                        continue;

                    stringToCopy = stringToCopy + ((NameValuedeHeader) header).toString() + "\n";
                }

                StringSelection stringSelection = new StringSelection(stringToCopy);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }


            if (e.getSource() instanceof NameValuedeHeader)
            {
                String stringToCopy = ((NameValuedeHeader) e.getSource()).toString();
                StringSelection stringSelection = new StringSelection(stringToCopy);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }
        }

    }
}