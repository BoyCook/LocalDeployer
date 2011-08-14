package org.cccs.maven.localdeployer.goal;

/**
 * User: Craig Cook
 * Date: Nov 7, 2008
 * Time: 3:57:46 PM
 */

import org.apache.maven.plugin.MojoExecutionException;
import org.cccs.maven.localdeployer.manager.FileManager;
import org.cccs.maven.localdeployer.manager.TomcatManager;
import java.io.File;

/**
 * Simple plugin to copy packaged war to deployment dir
 *
 * @goal copy
 */
public class Copy extends AbstractGoal {

    /**
     * Copy classes to WEB-INF/classes
     *
     * @parameter expression=false;
     */
    public boolean copyclasses;

    /**
     * Delete classes from webapps/${APP-NAME}/WEB-INF/classes
     *
     * @parameter expression=false;
     */
    public boolean deleteclasses;

    /**
     * Delete web (JSP) files from webapp/
     *
     * @parameter expression=false;
     */
    public boolean deleteweb;

    /**
     * Copy web (JSP) files to webapp/
     *
     * @parameter expression=false;
     */
    public boolean copyweb;

    public void execute() throws MojoExecutionException {
        //Ignore flag defaults false
        if (!ignore) {
            TomcatManager tomcat = new TomcatManager(directory);
            FileManager file = new FileManager();

            String srcClasses = source + "/classes";
            //String srcWeb = source + "/" + appname;
            String srcWeb = "src/main/webapp";

            String targetClasses = directory + "/webapps/" + appname + "/WEB-INF/classes";
            String targetWeb = directory + "/webapps/" + appname;

            if (stoptomcat) {
                tomcat.stop();
                sleep("Waiting " + pausetime + " for tomcat to stop...");
            }

            if (deleteclasses) {
                if (file.delete(new File(targetClasses))) {
                    getLog().warn("Deleted: " + targetClasses);
                } else {
                    getLog().warn("Failed to delete: " + targetClasses);
                }
            }

            if (copyclasses) {
                if (file.copyDirectory(new File(srcClasses), new File(targetClasses))) {
                    getLog().info("Copied: " + srcClasses + " to " + targetClasses);
                } else {
                    getLog().info("Failed to copy: " + srcClasses + " to " + targetClasses);
                }
            }

            if (deleteweb) {
                if (file.delete(new File(targetWeb), ".jsp")) {
                    getLog().warn("Deleted jsp's in: " + targetWeb);
                } else {
                    getLog().warn("Failed to delete jsp's: " + targetWeb);
                }
            }

            if (copyweb) {
                if (file.copyDirectory(new File(srcWeb), new File(targetWeb), ".jsp")) {
                    getLog().info("Copied jsp's in: " + srcWeb + " to " + targetWeb);
                } else {
                    getLog().info("Failed to copy jsp's in: " + srcWeb + " to " + targetWeb);
                }                
            }

            if (starttomcat) {
                getLog().info("Starting tomcat...");
                tomcat.start();
            }
        }
    }
}
