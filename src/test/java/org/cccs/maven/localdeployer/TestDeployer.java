package org.cccs.maven.localdeployer;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.cccs.maven.localdeployer.goal.Deploy;
import org.cccs.maven.localdeployer.manager.TomcatManager;
import org.cccs.maven.localdeployer.manager.FileManager;

import java.io.File;

/**
 * User: Craig Cook
 * Date: Nov 4, 2008
 * Time: 7:17:50 PM
 */
public class TestDeployer {

    Deploy deployer;
    TomcatManager tomcat = new TomcatManager("/usr/local/apache-tomcat");
    FileManager file = new FileManager();

    @BeforeClass
    public void begin() {
        deployer = new Deploy();
        deployer.setDirectory("/usr/local/apache-tomcat");
    }


    //@Test
    public void testDeleteJsp(){
        file.delete(new File("/usr/local/apache-tomcat/webapps/cache-test"), ".jsp");
    }

    //@Test
    public void testCopyJsp(){
        //file.copyDirectory(new File("/Users/BoyCook/Code/bt-dso/cache-test/trunk/src/main/webapp"), new File("/usr/local/apache-tomcat/webapps/cache-test"), ".jsp");
        file.copyDirectory(new File("/Users/BoyCook/Code/bt-dso/cache-test/trunk/src/main/webapp"), new File("/usr/local/apache-tomcat/webapps/cache-test"), ".jsp");
    }

    //@Test
    public void testTomcatCatalinaStart() {
        System.out.println("Starting tomcat - waiting...");

        System.out.println("JAVA_HOME: " + System.getenv("JAVA_HOME"));
        System.out.println("LOGGING_CONFIG: " + System.getenv("LOGGING_CONFIG"));

        try {
            tomcat.startTomcat();
        } catch (Exception e) {
            System.out.println("TEST Error - startTomcat(): " + e.getMessage());
            e.printStackTrace();
        }
    }

    //@Test
    public void testTomcatStart() throws Exception {
        System.out.println("Starting tomcat - waiting...");
        //deployer.runTomcat(Tomcat.START);
    }

    //@Test
    public void testTomcatStop() throws Exception {
        System.out.println("Stopping tomcat - waiting...");
        //deployer.runTomcat(Tomcat.STOP);
    }
}