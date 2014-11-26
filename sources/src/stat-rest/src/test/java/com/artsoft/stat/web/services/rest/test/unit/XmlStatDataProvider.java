/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.web.services.rest.test.unit;


import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.testng.annotations.DataProvider;
import org.xml.sax.InputSource;


/**
 * The Class XmlStatDataProvider.
 */
public class XmlStatDataProvider
{
    /**
     * The simple NamespaceContext class.
     */
    public static class SimpleNamespaceContext implements NamespaceContext
    {
        private static final Map<String, String> NAME_SPACE_MAP = new HashMap<String, String>() {
            private static final long serialVersionUID = 1L;

            {
                put(ERYK_XML_PREFIX, ERYK_XML_NAMESPACE);
            }
        };

        
        /**
         * Instantiates a new simple namespace context.
         */
        public SimpleNamespaceContext()
        {
        }
        
        
        @Override
        public String getNamespaceURI(final String prefix)
        {
            return NAME_SPACE_MAP.get(prefix);
        }


        @Override
        public String getPrefix(final String arg0)
        {
            return null;
        }


        @Override
        public Iterator<?> getPrefixes(final String arg0)
        {
            return null;
        }
    }


    /** The drEryk statistic namespace. */
    public static final String ERYK_XML_NAMESPACE = "http://www.artsoft.com/stats";

    /** The drEryk statistic namespace prefix. */
    public static final String ERYK_XML_PREFIX = "art";

    /** The XPath of name node for statistic xml. */
    public static final String STAT_NAME_PATH = "/" + ERYK_XML_PREFIX + ":statistic/" + ERYK_XML_PREFIX + ":name";

    /** The XPath of value node for statistic xml. */
    public static final String STAT_VALUE_PATH = "/" + ERYK_XML_PREFIX + ":statistic/" + ERYK_XML_PREFIX + ":value";

    /** The XPath of resolution width node for statistic xml. */
    public static final String STAT_WIDTH_PATH = "/" + ERYK_XML_PREFIX + ":statistic/" + ERYK_XML_PREFIX
        + ":resolution/" + ERYK_XML_PREFIX + ":width";

    /** The XPath of resolution height node for statistic xml. */
    public static final String STAT_HEIGHT_PATH = "/" + ERYK_XML_PREFIX + ":statistic/" + ERYK_XML_PREFIX
        + ":resolution/" + ERYK_XML_PREFIX + ":height";


    private final XPath xPath;


    /**
     * Instantiates a new xml statistic data provider.
     */
    public XmlStatDataProvider()
    {
        xPath = XPathFactory.newInstance().newXPath();
        xPath.setNamespaceContext(new SimpleNamespaceContext());
    }


    /**
     * Correct statistic object provider.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "correctStatisticStringProvider")
    public static Object[][] correctStatisticStringProvider()
    {
        return new Object[][] {
            { "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<statistic xmlns=\"" + ERYK_XML_NAMESPACE + "\">"
                + "<name>cos</name>"
                + "<value>111</value>"
                + "<resolution>"
                + "<width>1</width>"
                + "<height>600</height>"
                + "</resolution>"
                + "</statistic>" },
            { "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<statistic xmlns=\"" + ERYK_XML_NAMESPACE + "\">"
                + "<name>text</name>"
                + "<value>textValue</value>"
                + "<resolution>"
                + "<width>100</width>"
                + "<height>99</height>"
                + "</resolution>"
                + "</statistic>" }
        };
    }


    /**
     * Correct statistic object provider.
     * 
     * @return the object[][]
     */
    @DataProvider(name = "incorrectStatisticStringProvider")
    public static Object[][] incorrectStatisticStringProvider()
    {
        return new Object[][] {
            { "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<statistic xmlns=\"" + ERYK_XML_NAMESPACE + "\">"
                + "<name>cos</name>"
                + "<resolution>"
                + "<width>1</width>"
                + "<height>600</height>"
                + "</resolution>"
                + "</statistic>" },
            { "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<statistic xmlns=\"" + ERYK_XML_NAMESPACE + "\">"
                + "<name>text</name>"
                + "<value>textValue</value>"
                + "<resolution>"
                + "<width>100</width>"
                + "</resolution>"
                + "</statistic>" },
            { "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<statistic xmlns=\"" + ERYK_XML_NAMESPACE + "\">"
                + "<name>text</name>"
                + "<value>textValue</value>"
                + "<resolution>"
                + "<width>-1</width>"
                + "<height>99</height>"
                + "</resolution>"
                + "</statistic>" }
        };
    }


    /**
     * Gets the node value for given XPath as number.
     * 
     * @param xPathStr the xPath string
     * @param xmlDoc the xml doc
     * @return the node from xml as number
     * @throws XPathExpressionException the x path expression exception
     */
    public double getNodeFromXmlAsNumber(final String xPathStr, final String xmlDoc) throws XPathExpressionException
    {
        InputSource is = new InputSource(new StringReader(xmlDoc));
        double result = (double)xPath.evaluate(xPathStr, is, XPathConstants.NUMBER);

        return result;
    }


    /**
     * Gets the node value for given XPath as string.
     * 
     * @param xPathStr the x path string
     * @param xmlDoc the xml doc
     * @return the node from xml as string
     * @throws XPathExpressionException the x path expression exception
     */
    public String getNodeFromXmlAsString(final String xPathStr, final String xmlDoc) throws XPathExpressionException
    {
        InputSource is = new InputSource(new StringReader(xmlDoc));
        String result = (String)xPath.evaluate(xPathStr, is, XPathConstants.STRING);

        return result;
    }
}
