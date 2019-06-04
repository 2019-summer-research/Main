import okhttp3.Headers;

import javax.xml.crypto.Data;

/**
 * Setup of authentication headers and Content Type Transfer
 */
public class AuthHeaders {

    enum DataTypes
    {
        OCTET, JSON
    }

    private DataTypes DT = DataTypes.JSON;
    private Headers Header;
    private String SubscriptionKey;

    /**
     * Sets the Microsoft Subscription key
     * @param Key Ocp-Apim-Subscription-Key
     */
    public void SetSubscriptionKey (String Key)
    {
        SubscriptionKey = Key;
    }

    /**
     * Set DataType
     * @param type Type of data being uploaded to the server usually JSON
     */
    public void SetDataType (DataTypes type)
    {
        DT = type;
    }

    /**
     * Builds the headers with given values
     * @return Build Headers
     */
    Headers build ()
    {
        Header = new Headers.Builder().add("Content-Type",DT.name()).add("Ocp-Apim-Subscription-Key",SubscriptionKey).build();
        return Header;
    }

    /**
     * Get Compiled Header
     * @return Header
     */
    Headers getHeader (){
        return Header;
    }





}
