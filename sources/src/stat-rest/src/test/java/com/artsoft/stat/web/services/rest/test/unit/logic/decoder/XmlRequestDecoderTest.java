/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.web.services.rest.test.unit.logic.decoder;


import static com.artsoft.stat.web.services.rest.test.unit.XmlStatDataProvider.STAT_HEIGHT_PATH;
import static com.artsoft.stat.web.services.rest.test.unit.XmlStatDataProvider.STAT_NAME_PATH;
import static com.artsoft.stat.web.services.rest.test.unit.XmlStatDataProvider.STAT_VALUE_PATH;
import static com.artsoft.stat.web.services.rest.test.unit.XmlStatDataProvider.STAT_WIDTH_PATH;

import javax.xml.bind.JAXBException;
import javax.xml.xpath.XPathExpressionException;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.artsoft.stat.web.services.rest.domain.Statistic;
import com.artsoft.stat.web.services.rest.logic.decoder.RequestDecoder;
import com.artsoft.stat.web.services.rest.logic.decoder.XmlRequestDecoder;
import com.artsoft.stat.web.services.rest.test.unit.XmlStatDataProvider;


/**
 * The XmlRequestDecoder unit test class.
 */
public class XmlRequestDecoderTest
{
    private final XmlStatDataProvider dataProvider = new XmlStatDataProvider();
    private RequestDecoder requestDecoder;

    
    /**
     * Instantiates a new xml request decoder test.
     */
    public XmlRequestDecoderTest()
    {
    }
    
    
    /**
     * Test correct decode of statistic string.
     *
     * @param statistic the statistic string to decode
     * @throws XPathExpressionException If expression cannot be evaluated
     */
    @Test(dataProvider = "correctStatisticStringProvider", dataProviderClass = XmlStatDataProvider.class)
    public void testCorrectDecode(final String statistic) throws XPathExpressionException
    {
        // given
        String nameNodeValue = dataProvider.getNodeFromXmlAsString(STAT_NAME_PATH, statistic);
        String valueNodeValue = dataProvider.getNodeFromXmlAsString(STAT_VALUE_PATH, statistic);
        int widthNodeValue = (int)dataProvider.getNodeFromXmlAsNumber(STAT_WIDTH_PATH, statistic);
        int heightNodeValue = (int)dataProvider.getNodeFromXmlAsNumber(STAT_HEIGHT_PATH, statistic);

        // when
        Statistic statObj = requestDecoder.decode(statistic);

        // then
        Assert.assertEquals(nameNodeValue, statObj.getName(), "Name node value not equal.");
        Assert.assertEquals(valueNodeValue, statObj.getValue(), "Value node value not equal.");
        Assert.assertEquals(widthNodeValue, statObj.getResolution().getWidth(), "Width node value not equal.");
        Assert.assertEquals(heightNodeValue, statObj.getResolution().getHeight(), "Hight node value not equal.");
    }


    /**
     * Sets the up.
     *
     * @throws JAXBException if an error was encountered while creating the JAXBContext
     * @throws IllegalArgumentException if the array of class parameter is null
     */
    @BeforeMethod
    protected void setUp() throws IllegalArgumentException, JAXBException
    {
        requestDecoder = new XmlRequestDecoder(Statistic.class);
    }
}
