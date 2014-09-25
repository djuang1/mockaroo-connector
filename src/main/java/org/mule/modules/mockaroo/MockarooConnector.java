package org.mule.modules.mockaroo;

import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.display.*;
import org.mule.api.annotations.rest.*;
import org.apache.commons.httpclient.HttpClient;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;

import java.io.IOException;

/**
 * Mockaroo Anypoint Connector
 *
 * @author MuleSoft, Inc.
 */
@Connector(name="mockaroo", schemaVersion="1.0", friendlyName="Mockaroo")
public abstract class MockarooConnector
{
	public static final String BASE_URI = "http://www.mockaroo.com/api";

    @RestHttpClient
    private HttpClient httpClient;
    
    /**
     * apiKey Provided by Mockaroo during API registration process
     */
    @Configurable
    @RestUriParam("apiKey")
    @Summary("The API Key")
    @FriendlyName("API Key")
    private String apiKey;
    
    /**
     * format Output format of data. Either json or csv
     */
    @Configurable
    @RestUriParam("format")
    @Summary("The output format. Either json or csv")
    @FriendlyName("Output Format")
    private String format;
    
    public MockarooConnector()
    {
        httpClient = new HttpClient();
    }

    /**
     * Set API Key
     *
     * @param apiKey Mockaroo API key (see http://www.mockaroo.com/api/docs)
     */
    public void setApiKey(String apiKey)
    {
        this.apiKey = apiKey;
    }

    public String getApiKey()
    {
        return this.apiKey;
    }
    
    /**
     * Set Format
     *
     * @param format Output format of data. Either json or csv
     */
    public void setFormat(String format)
    {
        this.format = format;
    }

    public String getFormat()
    {
        return this.format;
    }
    
    /**
     * Set property
     *
     * @param httpClient
     */

    public void setHttpClient(HttpClient httpClient){
        this.httpClient = httpClient;
    }

    public HttpClient getHttpClient(){
        return this.httpClient;
    }
    
    /**
     * getMockaroo
     *
     * {@sample.xml ../../../doc/mockaroo-connector.xml.sample mockaroo:get-mockaroo}
     *
     * @param callback The name of a javascript function to call in the response. If specified, the response will be in jsonp format.
     * @param count The number of rows to generate. Defaults to 1, or if a saved schema is used, the number of rows specified on the saved schema. When json format is requested, the result will be an object when size = 1, an array when size > 1
     * @param include_header Only relevant for csv format. Set to false to omit the header row. Defaults to true.
     * @param fields Field types to return in data formatted as a json array
     * @return String returns Mockaroo data in what ever format specified.
     * @throws IOException Thrown in the event of a communications error
     *
     */
    @Processor
    @Summary("Get Mockaroo")
    @RestCall(uri = BASE_URI + "/generate.{format}?key={apiKey}", method = HttpMethod.GET)
    public abstract String getMockaroo(@RestQueryParam("callback") @FriendlyName("Callback") String callback,
    		@FriendlyName("Count") @Default("1") @RestQueryParam("count") String count,
    		@FriendlyName("Include Header") @Default("true") @RestQueryParam("include_header") String include_header,
    		@FriendlyName("Fields") @Optional @RestQueryParam("fields") String fields) 
                    		   throws IOException;
}