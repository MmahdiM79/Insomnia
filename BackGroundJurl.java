import javax.swing.*;






/**
 * This class call {@link Jurl#main(String[])} in a tread with user given 
 * details in gui.
 * This class also set request response ditails in gui
 * 
 * 
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.1.0
 */
public class BackGroundJurl extends SwingWorker
{
            /*  Fields  */

    // a string for Jurl class
    private String reqDetailsString = null;
            
    // follow redirects or not
    private boolean followRedirects = true;

    // what to do?
    private String whatToDo = null;


    // stauts code of response
    private int statusCode;

    // message of the response
    private String responseMessage;

    // time of respnose
    private long responseTime;

    // size of the response body
    private long contentLength;

    // format of the response
    private String contentFormat;





    


         /* Constructor */

    /**
     * Create a new SwingWorker for sending new request in another tread
     * 
     * 
     * @param reqDetailsString : a string of request details
     * @param followRedirects : follow redirects or not
     * @param whatToDo : send or save
     */
    public BackGroundJurl(String reqDetailsString, boolean followRedirects, String whatToDo)
    {
        this.reqDetailsString = reqDetailsString;
        this.followRedirects = followRedirects;
        this.whatToDo = whatToDo;
    }
    





            /*  Methods  */

    /**
     * This method send reqeust and read it's response
     */
    protected Object doInBackground() throws Exception 
    {
        if (followRedirects)
                reqDetailsString = reqDetailsString + " -f";

        reqDetailsString = reqDetailsString + " --GUI-- -i";


        // send request
        Jurl.main(reqDetailsString.split(" "));


        // read response details
        statusCode = Jurl.getStatusCode();
        responseMessage = Jurl.getResponseMessage();
        responseTime = Jurl.getResponseTime();
        contentLength = Jurl.getContentLength();
        contentFormat = Jurl.getContentFormat();


        // show response in gui
        if (whatToDo.equals("send"))
            MainFrame.updateGUI(statusCode, responseMessage, responseTime, contentLength, contentFormat, Jurl.getResponseHeaders());
            
        return null;
    }

}