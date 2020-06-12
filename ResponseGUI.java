import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;





/**
 * This class represnt the Right part of the Insomnia app GUI
 * This part is for make a new request
 * 
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.0.0
 */
public class ResponseGUI extends JPanel
{
            /*  Fields  */

    private JPanel responseStatusPanel = new JPanel();

    private JLabel statusCodLabel = new JLabel();

    private JLabel responseTimeLabel = new JLabel();

    private JLabel responseSizeLabel = new JLabel();



    private JTabbedPane responseDetailsTabs = new JTabbedPane();

    private JPanel messageBodyTabPanel = new JPanel();

    private JComboBox<String> bodyKindsComboBox = new JComboBox<>();

    private JPanel messageBodyShowPanel = new JPanel(); 

    private JPanel rawPanel = new JPanel();

    private JPanel headersPanel = new JPanel();



        
    private Color backgroundColor;

    private ResponseGUI THIS;


    
    

    public ResponseGUI(Color bgColor) 
    {
        super();
        super.setLayout(new BorderLayout(0, 0)); // set layout manager
        super.setBackground(backgroundColor); // set background color
        super.setOpaque(true); // apply color changes
        THIS = this;



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


        messageBodyTabPanel.setBackground(backgroundColor); // set background color
        messageBodyTabPanel.setOpaque(true); // apply color changes
        messageBodyTabPanel.setLayout(new BorderLayout()); // set layout manager
        responseDetailsTabs.add("Message Body", messageBodyTabPanel); // add body panel


        bodyKindsComboBox.setMaximumSize(new Dimension(925, 30));
        bodyKindsComboBox.setBackground(backgroundColor); // set background color
        bodyKindsComboBox.setOpaque(true); // apply color changes
        bodyKindsComboBox.addItem("RAW");
        bodyKindsComboBox.addItem("Preview");
        messageBodyTabPanel.add(bodyKindsComboBox, BorderLayout.NORTH);


        // set the body edit panel
        messageBodyShowPanel.setLayout(new CardLayout()); // set layout manager
        messageBodyShowPanel.setBackground(backgroundColor); // set the background color
        messageBodyShowPanel.setOpaque(true); // apply color changes
        messageBodyTabPanel.add(messageBodyShowPanel, BorderLayout.CENTER); // add edit panel 


        rawPanel.setLayout(new GridLayout(1, 1, 15, 15)); //set layout manager
        rawPanel.setBackground(backgroundColor); // set background color
        rawPanel.setOpaque(true); // apply color changes
        messageBodyShowPanel.add("raw", rawPanel); // add body panel


 
        headersPanel.setLayout(new BoxLayout(headersPanel, BoxLayout.Y_AXIS));
        headersPanel.setBackground(backgroundColor);
        headersPanel.setOpaque(true);
        responseDetailsTabs.add("Headers", headersPanel);

        
        // set headers table
        JTable headersTable = new JTable();

    }
}