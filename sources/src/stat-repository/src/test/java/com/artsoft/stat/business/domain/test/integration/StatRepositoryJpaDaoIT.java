/*
 * ArtSoft 2015.
 */

package com.artsoft.stat.business.domain.test.integration;


import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.persistence.ApplyScriptBefore;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.protocol.servlet.arq514hack.descriptors.api.application.ApplicationDescriptor;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.persistence20.PersistenceDescriptor;
import org.jboss.shrinkwrap.descriptor.api.persistence20.PersistenceUnit;
import org.jboss.shrinkwrap.descriptor.api.persistence20.Properties;
import org.jboss.shrinkwrap.descriptor.api.persistence20.Property;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.artsoft.stat.business.domain.dao.ResolutionDao;
import com.artsoft.stat.business.domain.dao.StatisticDao;
import com.artsoft.stat.business.domain.model.ResolutionEntity;
import com.artsoft.stat.business.domain.model.StatisticEntity;
import com.artsoft.stat.business.domain.test.utils.EntitiesHelper;
import com.artsoft.utils.io.FileHandler;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;


/**
 * The integration test of statistic repository component.
 *
 * <p>
 * The source code of this test class is mostly based on Arquillian Persistence Extension. The
 * commented code is legacy code which was based only on private library using DbUnit framework.
 * </p>
 */
@Test(groups = "integration")
public class StatRepositoryJpaDaoIT extends Arquillian
{
    private static final String CURRENT_MODULE_NAME_REGEX = "^stat-repository((?!javadoc).)*\\.jar$";
    private static final String CURRENT_MODULE_PATH = "target";
    private static final String[] LIBS_REQUIRED_BY_ARQUILLIAN_TESTS = new String[] {
        "org.dbunit:dbunit",
        "commons-configuration:commons-configuration",
        "com.google.inject:guice",
        "com.artsoft:utils"};
    private static final String ARQUILLIAN_COMPONENT_WITH_TESTS_NAME = "test.jar";
    private static final String EAR_COMPONENT_FOR_ITEST_NAME = "stat-repository_it.ear";
    private static final String JPA_JTA_DATA_SOURCE = "default";

    private static final String EAR_COMPONENT_FOR_ITEST_VERSION = "6";
    private static final String JPA_PERSISTENCE_FILE_NAME = "persistence.xml";
    private static final String JPA_PERSISTENCE_FILE_PATH = "/META-INF/" + JPA_PERSISTENCE_FILE_NAME;
    private static final String EAR_APPLICATION_FILE_NAME = "application.xml";
    private static final String POM_MAVEN_FILE_NAME = "pom.xml";
    private static final String TEST_CLASSES_PATH = "./target/test-classes";

    @Inject
    private ResolutionDao resolutionDao;
    @Inject
    private StatisticDao statisticDao;
    @PersistenceContext
    private EntityManager em;
    @Inject
    private UserTransaction utx;


    /**
     * Create ear archive for integration test needs.
     *
     * @return the ear archive
     */
    @Deployment
    public static EnterpriseArchive createEarArchive()
    {
        // @formatter:off
        /*
         *     |------------------------------------------------------------------------|
         *     |                  EAR_COMPONENT_FOR_ITEST_NAME (.ear)                   |
         *     |                                                                        |
         * --->+    |----------|    |-------------------------------------------------| |
         *     |--->+ test.war +--->+ lib/ARQUILLIAN_COMPONENT_WITH_TESTS_NAME (.jar) | |
         *     |    |----------|    |------------------------+------------------------| |
         *     |                                             |                          |
         *     |                                            \|/                         |
         *     |                         |-------------------+-------------------|      |
         *     |                         | lib/COMPONENT_UNDER_ITEST_NAME (.jar) |      |
         *     |                         |---------------------------------------|      |
         *     |                                                                        |
         *     | |--------------------------------------------------------------------| |
         *     | | lib/*.jar                                                          | |
         *     | |--------------------------------------------------------------------| |
         *     |------------------------------------------------------------------------|
         *
         * - EAR_COMPONENT_FOR_ITEST_NAME (.ear) - Main Arquillian archive created for deployment
         *    process:
         *      * test.war - Internal www application automatically added by Arquillian for
         *         communication purpose,
         *      - lib/ - Libraries:
         *           * ARQUILLIAN_COMPONENT_WITH_TESTS_NAME (.jar) - Component with test classes
         *              and test resources,
         *           * COMPONENT_UNDER_ITEST_NAME (.jar) - The target component under test
         *           * arquilian*.jar - Internal Arquillian jar libraries added automatically by
         *              Arquillian framework,
         *           * *.jar - Libraries required by testing classes read from pom,
         *           * *.jar - Libraries required by target component read from pom,
         */
        // @formatter:on

        try {
            PomEquippedResolveStage pomResolver = Maven.resolver().loadPomFromFile(POM_MAVEN_FILE_NAME);

            List<File> libsList = new ArrayList<>();


            // Get current jar module by use module build in maven's target folder.
            File jarUnderTest = FileHandler.findFileModuleInPath(CURRENT_MODULE_PATH, CURRENT_MODULE_NAME_REGEX);

            // get lib dependencies from POM required by module under the test
            File[] libs = pomResolver.importRuntimeDependencies().resolve().withTransitivity().asFile();
            Collections.addAll(libsList, libs);

            // get lib dependencies from POM required by Arquillian test class
            for (String lib : LIBS_REQUIRED_BY_ARQUILLIAN_TESTS) {
                libs = pomResolver.resolve(lib).withTransitivity().asFile();
                Collections.addAll(libsList, libs);
            }

            /*
             * Test classes and test resources need to be added manually when create a
             * EnterpriseArchive. The Servlet Protocol does not currently default any of the modules
             * and
             * don't know where to add it.
             */
            JavaArchive jarWithTest = ShrinkWrap.create(ExplodedImporter.class, ARQUILLIAN_COMPONENT_WITH_TESTS_NAME)
                .importDirectory(new File(TEST_CLASSES_PATH))
                .as(JavaArchive.class);


            EnterpriseArchive ear = ShrinkWrap.create(EnterpriseArchive.class, EAR_COMPONENT_FOR_ITEST_NAME)
                .addAsLibraries(jarUnderTest)
                .addAsLibraries(libsList.toArray(new File[libsList.size()]))
                .addAsLibraries(jarWithTest)
                .addAsManifestResource(createApplicationXml(), EAR_APPLICATION_FILE_NAME);

            return ear;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error during deployment test unit.", e);
        }
    }


    private static StringAsset createApplicationXml()
    {
        StringAsset asset = new StringAsset(Descriptors.create(ApplicationDescriptor.class)
            .version(EAR_COMPONENT_FOR_ITEST_VERSION)
            .displayName(EAR_COMPONENT_FOR_ITEST_NAME)
            .exportAsString());

        return asset;
    }


    @SuppressFBWarnings("UPM_UNCALLED_PRIVATE_METHOD")
    private static StringAsset createPersistenceXml() throws Exception
    {
        try (InputStream is = StatRepositoryJpaDaoIT.class.getResourceAsStream(JPA_PERSISTENCE_FILE_PATH)) {
            PersistenceDescriptor descriptor = Descriptors.importAs(PersistenceDescriptor.class).fromStream(is);
            for (PersistenceUnit<PersistenceDescriptor> item : descriptor.getAllPersistenceUnit()) {
                if (JPA_JTA_DATA_SOURCE.equals(item.getName())) {
                    item.jtaDataSource(Config.DB_JNDI);
                    // setPropertyInPersistenceUnit(item, "hibernate.hbm2ddl.auto",
                    // HIBERNATE_HBM2DDL_AUTO_VALUE);
                    break;
                }
            }

            return new StringAsset(descriptor.exportAsString());
        }
        catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        }
    }


    @SuppressFBWarnings("UPM_UNCALLED_PRIVATE_METHOD")
    private static void setPropertyInPersistenceUnit(final PersistenceUnit<PersistenceDescriptor> persistenceUnit,
        final String propertyName, final String propertyValue)
    {
        final Properties<PersistenceUnit<PersistenceDescriptor>> properties = persistenceUnit.getOrCreateProperties();

        for (Property<Properties<PersistenceUnit<PersistenceDescriptor>>> property : properties.getAllProperty())
        {
            if (property.getName().equals(propertyName)) {
                property.value(propertyValue);
                return;
            }
        }

        properties.createProperty().name(propertyName).value(propertyValue);
    }


    /**
     * Test Resolution DAO's find method.
     *
     * @throws Exception the exception
     */
    @Test
    @UsingDataSet(Config.ALL_ENTITIES_DB_XML)
    public void testResolutionFind() throws Exception
    {
        // given

        // when
        ResolutionEntity entity = resolutionDao.find(1);

        // then
        EntitiesHelper.assertEqualsReference(entity, 1);
    }


    /**
     * Test Resolution DAO's find method by width and height parameters.
     *
     * @throws Exception the exception
     */
    @Test
    @UsingDataSet(Config.ALL_ENTITIES_DB_XML)
    public void testResolutionFindByWidhtHeight() throws Exception
    {
        // given

        // when
        ResolutionEntity entity = resolutionDao.findByWidthAndHeight(100, 200);

        // then
        EntitiesHelper.assertEqualsReference(entity, 1);
    }


    /**
     * Test Resolution DAO's find method returns null.
     *
     * @throws Exception the exception
     */
    @Test
    @UsingDataSet(Config.ALL_ENTITIES_DB_XML)
    public void testResolutionFindNull() throws Exception
    {
        // given

        // when
        ResolutionEntity entity = resolutionDao.find(99);

        // then
        Assert.assertNull(entity);
    }


    /**
     * Test persistent simple resolution entity.
     *
     * @throws Exception the exception
     */
    @Test
    @ApplyScriptBefore(Config.TRUNCATE_ALL_DB_SQL)
    @ShouldMatchDataSet(Config.RESOLUTION_ENTITY_DB_XML)
    public void testResolutionPersist() throws Exception
    {
        // given

        // when - transaction handled by Arquillian Persistence Extension
        // beginTransaction();
        ResolutionEntity testEntity = EntitiesHelper.newResolutionEntity();
        resolutionDao.create(testEntity);
        // commitTransaction();

        // then - checking by @ShouldMatchDataSet
        // String[] filterTable = { Schema.RESOLUTION_TABLE_NAME };
        // EntitiesHelper.assertWithDb(Config.DB_TEST_DATASET, filterTable);
    }


    /**
     * Test Resolution DAO's find method.
     *
     * @throws Exception the exception
     */
    @Test
    @UsingDataSet(Config.ALL_ENTITIES_DB_XML)
    public void testStatisticFind() throws Exception
    {
        // given

        // when
        StatisticEntity entity = statisticDao.find(1);

        // then
        EntitiesHelper.assertEqualsReference(entity);
    }


    /**
     * Test persist simple statistic entity.
     *
     * @throws Exception the exception
     */
    @Test
    @ApplyScriptBefore(Config.TRUNCATE_ALL_DB_SQL)
    @ShouldMatchDataSet(Config.ALL_ENTITIES_DB_XML)
    public void testStatisticPersist() throws Exception
    {
        // given
        // dbHelper.clearData(Config.DB_TEST_DATASET);

        // when
        // beginTransaction();
        StatisticEntity testEntity = EntitiesHelper.newStatisticEntity();
        statisticDao.create(testEntity);
        // commitTransaction();

        // then
        // String[] filterTable = { Schema.RESOLUTION_TABLE_NAME, Schema.STATISTIC_TABLE_NAME };
        // EntitiesHelper.assertWithDb(Config.DB_TEST_DATASET, filterTable);
    }


    /**
     * Begin transaction.
     * This method is never call by Arquillian tests due to use Arquillian Persistence Extension.
     *
     * @throws NotSupportedException the not supported exception
     * @throws SystemException the system exception
     */
    @SuppressFBWarnings("UPM_UNCALLED_PRIVATE_METHOD")
    private void beginTransaction() throws NotSupportedException, SystemException
    {
        utx.begin();
        em.joinTransaction();
    }


    /**
     * Commit transaction.
     * This method is never call by Arquillian tests due to use Arquillian Persistence Extension.
     *
     * @throws SecurityException the security exception
     * @throws IllegalStateException the illegal state exception
     * @throws RollbackException the rollback exception
     * @throws HeuristicMixedException the heuristic mixed exception
     * @throws HeuristicRollbackException the heuristic rollback exception
     * @throws SystemException the system exception
     */
    @SuppressFBWarnings("UPM_UNCALLED_PRIVATE_METHOD")
    private void commitTransaction() throws SecurityException, IllegalStateException, RollbackException,
        HeuristicMixedException, HeuristicRollbackException, SystemException
    {
        utx.commit();
        em.clear();
    }
}
