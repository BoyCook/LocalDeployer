package org.cccs.maven.localdeployer.manager;

import java.net.URL;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Host;
//import org.apache.catalina.logger.SystemOutLogger;
import org.apache.catalina.startup.Embedded;
import org.apache.catalina.Container;
import org.apache.catalina.startup.*;

/**
 * User: Craig Cook
 * Date: Nov 6, 2008
 * Time: 3:45:54 PM
 */
public class TomcatManager {

    private String directory;
    private Embedded embedded = null;
    private Host host = null;

    //TODO implement use of org.apache.catalina
    public TomcatManager(String directory) {
        this.directory = directory;        
    }

    public void start() {
        try {
            String startSh = directory + "/bin/startup.sh";
            Runtime r = Runtime.getRuntime();
            System.out.println(r.exec(startSh));
        } catch (Exception e) {
            System.out.println("Error starting tomcat: " + e.getMessage());
        }
    }

    public void stop() {
        try {
            String stopSh = directory + "/bin/shutdown.sh";
            Runtime r = Runtime.getRuntime();
            System.out.println(r.exec(stopSh));
        } catch (Exception e) {
            System.out.println("Error stopping tomcat: " + e.getMessage());
        }
    }



    public void startTomcat() throws Exception {

        try {
            Engine engine = null;
            // Set the home directory
            System.setProperty("catalina.home", directory);
            System.setProperty("java.util.logging.config.file", directory + "/conf/logging.properties");

            /*

            // Create an embedded server
            embedded = new Embedded();

            embedded.setCatalinaHome(directory);

            // print all log statments to standard error
            //embedded.setDebug(0);
            //embedded.setLogger(new SystemOutLogger());

            // Create an engine
            engine = embedded.createEngine();
            engine.setDefaultHost("localhost");

            // Create a default virtual host
            host = embedded.createHost("localhost", directory + "/webapps");
            engine.addChild(host);

            // Create the ROOT context
            Context context = embedded.createContext("", directory + "/webapps/ROOT");
            host.addChild(context);

            // Install the assembled container hierarchy
            embedded.addEngine(engine);

            // Assemble and install a default HTTP connector
            Connector connector = embedded.createConnector("", 8080, false);
            embedded.addConnector(connector);
            // Start the embedded server
            embedded.start();
            */
        } catch (Exception e) {
            System.out.println("Tomcat start error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}