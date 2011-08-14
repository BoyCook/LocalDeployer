package org.cccs.maven.localdeployer.goal;

/*
 * User: Craig Cook
 * Date: Now 4, 2008
 * Time: 14:50:45 PM
 */

import org.apache.maven.plugin.MojoExecutionException;
import java.io.*;
import org.cccs.maven.localdeployer.manager.TomcatManager;
import org.cccs.maven.localdeployer.manager.FileManager;

/**
 * Simple plugin to copy packaged war to deployment dir
 *
 * @goal deploy
 */
public class Deploy extends AbstractGoal {    

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public void execute() throws MojoExecutionException {
        //Ignore flag defaults false
        if (!ignore) {
            String appDir = directory + "/webapps/" + appname;
            String warFile = appDir + ".war";

            TomcatManager tomcat = new TomcatManager(directory);
            FileManager file = new FileManager();

            if (stoptomcat) {
                tomcat.stop();
                sleep("Waiting " + pausetime + " for tomcat to stop...");
            }

            if (file.delete(new File(warFile))) {
                getLog().warn("Deleted: " + warFile);
            } else {
                getLog().warn("Failed to delete: " + warFile);
            }

            if (file.delete(new File(appDir))) {
                getLog().warn("Deleted: " + appDir);
            } else {
                getLog().warn("Failed to delete: " + appDir);
            }

            if (file.copy(new File(source + "/" + appname + ".war"), new File(warFile))) {
                getLog().info("Copied: " + source + "/" + appname + ".war to " + warFile);
            } else {
                getLog().info("Failed to copy: " + source + "/" + appname + ".war to " + warFile);
            }

            if (starttomcat) {
                getLog().info("Starting tomcat...");
                tomcat.start();
            }
        }
    }
}