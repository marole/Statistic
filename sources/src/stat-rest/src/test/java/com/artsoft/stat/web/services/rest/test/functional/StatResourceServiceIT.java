/*
 * ArtSoft 2014.
 */

package com.artsoft.stat.web.services.rest.test.functional;


import static org.testng.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.protocol.servlet.arq514hack.descriptors.api.application.ApplicationDescriptor;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.testng.annotations.Test;

import com.artsoft.utils.io.FileHandler;
import com.eviware.soapui.SoapUIProTestCaseRunner;


/**
 * The integration test of StatResourceService component.
 */
@Test(groups = "functional")
public class StatResourceServiceIT extends Arquillian
{
    private static final String CURRENT_MODULE_NAME_REGEX = "^stat-rest((?!javadoc).)*\\.war$";
    private static final String CURRENT_MODULE_PATH = "target";
    private static final String EJB_MAVEN_ARTEFACT_ID = "com.artsoft:stat-logic";
    private static final String EAR_COMPONENT_FOR_ITEST_NAME = "stat-rest_ft.ear";

    private static final String EAR_COMPONENT_FOR_ITEST_VERSION = "6";
    private static final String POM_MAVEN_FILE_NAME = "pom.xml";
    private static final String EAR_APPLICATION_FILE_NAME = "application.xml";
    private static final String WAR_MODULE_CONTEXT_ROOT = "/stat-rest";

    @ArquillianResource
    private URL serverUrl;


    /**
     * Instantiates a new statistic resource service it.
     */
    public StatResourceServiceIT()
    {
    }


    /**
     * Create ear archive for functional test needs.
     *
     * @return the ear archive
     */
    @Deployment(testable = false)
    public static EnterpriseArchive createEarArchive()
    {
        try {
            List<File> libsList = new ArrayList<>();
            PomEquippedResolveStage pomResolver = Maven.resolver().offline().loadPomFromFile(POM_MAVEN_FILE_NAME);

            // Get current war module by use module build in maven's target folder
            File warModuleFile = FileHandler.findFileModuleInPath(CURRENT_MODULE_PATH, CURRENT_MODULE_NAME_REGEX);
            WebArchive warUnderTest = ShrinkWrap.create(ZipImporter.class, warModuleFile.getName())
                .importFrom(warModuleFile).as(WebArchive.class);

            // Get ejb module and libraries required by ejb module by use POM file.
            File ejbModule = pomResolver.resolve(EJB_MAVEN_ARTEFACT_ID).withoutTransitivity().asSingleFile();
            File[] ejbLibs = pomResolver.resolve(EJB_MAVEN_ARTEFACT_ID).withTransitivity().asFile();
            for (File lib : ejbLibs) {
                if (lib.getName().equalsIgnoreCase(ejbModule.getName())) {
                    continue;
                }
                Collections.addAll(libsList, lib);
            }

            // Get lib dependencies from POM required by module under the test.
            File[] libs = pomResolver.importRuntimeDependencies().resolve().withTransitivity().asFile();
            for (File lib : libs) {
                Collections.addAll(libsList, lib);
            }

            StringAsset appDescr = createApplicationXml(warUnderTest.getName(), ejbModule.getName());

            EnterpriseArchive ear = ShrinkWrap.create(EnterpriseArchive.class, EAR_COMPONENT_FOR_ITEST_NAME)
                .addAsModule(warUnderTest)
                .addAsModule(ejbModule)
                .addAsLibraries(libsList.toArray(new File[0]))
                .addAsManifestResource(appDescr, EAR_APPLICATION_FILE_NAME);

            return ear;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error during deployment test unit.", e);
        }
    }


    private static StringAsset createApplicationXml(final String warModuleName, final String ejbModuleName)
    {
        StringAsset asset = new StringAsset(Descriptors.create(ApplicationDescriptor.class)
            .version(EAR_COMPONENT_FOR_ITEST_VERSION)
            .displayName(EAR_COMPONENT_FOR_ITEST_NAME)
            .ejbModule(ejbModuleName)
            .webModule(warModuleName, WAR_MODULE_CONTEXT_ROOT)
            .libraryDirectory("lib")
            .exportAsString());

        return asset;
    }


    /**
     * Functional test by use SoapUi tool.
     *
     * @throws Exception the exception
     */
    @Test
    @RunAsClient
    public void testSoapUi() throws Exception
    {
        // given
        SoapUIProTestCaseRunner runner = new SoapUIProTestCaseRunner();
        runner.setProjectFile("src/test/resources/soapUI/soapui-project.xml");
        runner.setProjectPassword("12maol09");
        runner.setHost(serverUrl.getHost() + ":" + 8443);
        runner.setPrintReport(true);
        runner.setJUnitReport(true);
        runner.setOutputFolder("target/surefire-reports/soapUI");


        // when
        boolean result = runner.run();


        // then
        assertTrue(result);
    }
}
