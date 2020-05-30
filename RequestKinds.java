


/** 
 * This enum reprsent HTTP requset kinds
 * 
 * @author Mohammad Mahdi Malmasi
 * @version 0.1.2
 */
public enum RequestKinds 
{
    // kinds
    GET,
    POST,
    PUT,
    DELET;


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

            case "DELET":
                return DELET;

            default:
                return null;
        }
    }
}