
package com.artsoft.stat.business.logic.test.integration;


import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;

import org.dbunit.DatabaseUnitException;
import org.dbunit.IDatabaseTester;
import org.dbunit.JndiDatabaseTester;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.artsoft.stat.business.domain.model.ResolutionEntity;
import com.artsoft.stat.business.domain.model.StatisticEntity;
import com.artsoft.stat.business.logic.api.StatisticServiceRemote;
import com.artsoft.stat.business.logic.test.utils.EntitiesHelper;
import com.artsoft.utils.database.DbTesterHelper;
import com.artsoft.utils.io.FileHandler;


/**
 * The integration test of StatisticServiceBean component.
 */
@Test(groups = "integration")
public class StatisticServiceBeanIT extends Arquillian
{
    private static final String CURRENT_MODULE_NAME_REGEX = "^stat-logic((?!javadoc).)*\\.jar$";
    private static final String CURRENT_MODULE_PATH = "target";
    private static final String[] LIBS_REQUIRED_BY_ARQUILLIAN_TESTS = new String[] {
        "org.dbunit:dbunit",
        "commons-configuration:commons-configuration",
        "com.google.inject:guice",
        "com.artsoft:utils" };
    private static final String TEST_CLASSES_PATH = "./target/test-classes";
    private static final String ARQUILLIAN_COMPONENT_WITH_TESTS_NAME = "test.jar";
    private static final String WAR_TEST_COMPONENT_NAME_FOR_ITEST = "stat_logic_itest.war";

    private static final String POM_MAVEN_FILE_NAME = "pom.xml";
    private static DbTesterHelper dbHelper;


    /** The Statistic service bean under the itest. */
    @EJB
    private StatisticServiceRemote statisticServiceBean;


    /**
     * Create war archive for itest needs.
     *
     * @return the war archive
     */
    @Deployment
    public static WebArchive createWarArchive()
    {
        try {
            List<File> libsList = new ArrayList<>();
            PomEquippedResolveStage pomResolver = Maven.resolver().offline().loadPomFromFile(POM_MAVEN_FILE_NAME);


            // Get current ejb module by use module build in maven's target folder.
            File ejbModuleUnderTest = FileHandler.findFileModuleInPath(CURRENT_MODULE_PATH, CURRENT_MODULE_NAME_REGEX);

            // Get lib dependencies required by ejb module from POM file.
            File[] libs = pomResolver.importRuntimeDependencies().resolve().withTransitivity().asFile();
            Collections.addAll(libsList, libs);

            // Get lib dependencies from POM required by Arquillian test class.
            for (String lib : LIBS_REQUIRED_BY_ARQUILLIAN_TESTS) {
                libs = pomResolver.resolve(lib).withTransitivity().asFile();
                Collections.addAll(libsList, libs);
            }

            // Test classes and test resources need to be added manually.
            JavaArchive jarWithTest = ShrinkWrap.create(ExplodedImporter.class, ARQUILLIAN_COMPONENT_WITH_TESTS_NAME)
                .importDirectory((new File(TEST_CLASSES_PATH)))
                .as(JavaArchive.class);

            // create war structure for itest
            WebArchive war = ShrinkWrap.create(WebArchive.class, WAR_TEST_COMPONENT_NAME_FOR_ITEST)
                .addAsLibraries(ejbModuleUnderTest)
                .addAsLibraries(jarWithTest)
                .addAsLibraries(libsList.toArray(new File[0]));

            return war;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error during deployment test unit.", e);
        }
    }


    /**
     * Setup database helper instance.
     *
     * @throws Exception throw in any problem with class setting up
     */
    @BeforeClass
    public void setUpClass() throws Exception
    {
        IDatabaseTester databaseTester = new JndiDatabaseTester(Config.DB_JNDI);

        dbHelper = new DbTesterHelper(databaseTester, DbTesterHelper.DbEngine.MySql);
        EntitiesHelper.setDbHelper(dbHelper);
    }


    /**
     * Tear down database handler.
     */
    @AfterClass
    public void tearDownClass()
    {
        dbHelper.tearDown();
    }


    /**
     * Test receiving a new correct statistic object.
     *
     * @param name the name
     * @param value the value
     * @param width the width
     * @param height the height
     * @param dbFileName database file name
     * @throws DatabaseUnitException database exception
     */
    @Test(dataProvider = "correctStatisticDataProvider", dataProviderClass = StatisticDataProvider.class)
    public void testCorrectStatisticReceived(final String name, final String value, final int width,
        final int height, final String dbFileName) throws DatabaseUnitException
    {
        // given
        dbHelper.clearData(dbFileName);
        StatisticEntity statisticEntity = new StatisticEntity(name, value, new ResolutionEntity(width, height));

        // when
        statisticServiceBean.newStatistic(statisticEntity);

        // then
        // String[] filterTable = { Schema.RESOLUTION_TABLE_NAME, Schema.STATISTIC_TABLE_NAME };
        // EntitiesHelper.assertWithDb(Config.ALL_ENTITIES_DB_XML, filterTable);
        EntitiesHelper.assertWithDb(dbFileName, null);
    }
}
