import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;






public class LastRequestsGUI
{
            /*  Feilds  */
    private JFrame frame;
    private ArrayList<JButton> requests;





            /*  Methods  */
    
    /**
     * This method initialize this class
     * (a kind of constructor)
     */
    public LastRequestsGUI()
    {
        
        // set the insomnia label
        JLabel insomniaText = new JLabel("   Insomnia        "); // set text
        insomniaText.setHorizontalAlignment(SwingConstants.LEFT);
        insomniaText.setPreferredSize(new Dimension(200, 50));
        insomniaText.setFont (insomniaText.getFont ().deriveFont(25.0f)); // set text size
        insomniaText.setForeground(Color.WHITE); // set text color
        insomniaText.setBackground(new Color(102, 96, 178)); // set back ground color
        insomniaText.setOpaque(true); // apply color changes
        
        
        
        // set the '+' tab
        JPanel addTab = new JPanel();
        addTab.setLayout(new FlowLayout());

        
        JTextField getNewTabNameField = new JTextField(" new group name ... ");
        getNewTabNameField.setPreferredSize(new Dimension(300, 50));

        JButton addButton = new JButton(" Add ");
        addButton.setPreferredSize(new Dimension(80, 40));

        addTab.add(getNewTabNameField);
        addTab.add(addButton);

        addTab.setBackground(new Color(46, 47, 44));
        addTab.setOpaque(true);

        // set request grouping
        JTabbedPane requestGroups = new JTabbedPane();
        requestGroups.setBackground(new Color(46, 47, 44));
        requestGroups.setOpaque(true);
        requestGroups.addTab(" + ", addTab);
        
        
        
        BorderLayout mainLayout = new BorderLayout();
   

        
        frame = new JFrame();
        frame.setLayout(mainLayout);
        frame.add(insomniaText, BorderLayout.NORTH);
        frame.add(requestGroups, BorderLayout.CENTER);
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}