


/** 
 * This enum reprsent HTTP requset kinds
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.2.0
 */
public enum RequestKinds 
{
    // kinds
    GET,
    POST,
    PUT,
    DELETE;




            /*  Methods  */

    /**
     * This class return the kind of the given {@code String}
     * 
     * 
     * @param kind : name of the kind
     * @return {@code null} if the given {@code String} dosen't mean any kind
     */
    public static RequestKinds getKind(String kind)
    {
        kind = kind.replaceAll(" ", "").toUpperCase();


        switch (kind)
        {
            case "GET":
                return GET;

            case "POST":
                return POST;

            case "PUT":
                return PUT;

            case "DELETE":
                return DELETE;

            default:
                return null;
        }
    }


    /**
     * This method return the {@code String} of given kind
     * (a kind of {@code toString()} method)
     * 
     * 
     * @param kind : kind of request
     * @return a {@code String} that reprsent the given request kind 
     */
    public static String getKind(RequestKinds kind)
    {
        switch (kind)
        {
            case GET:
                return "GET";
            
            case POST:
                return "POST";

            case PUT:
                return "PUT";

            case DELETE:
                return "DELETE";

            default:
                return null;
        }
    }
}