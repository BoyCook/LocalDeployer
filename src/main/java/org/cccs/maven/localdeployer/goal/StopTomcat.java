package org.cccs.maven.localdeployer.goal;

/**
 * User: Craig Cook
 * Date: Nov 11, 2008
 * Time: 12:24:21 PM
 */

import org.apache.maven.plugin.MojoExecutionException;
import org.cccs.maven.localdeployer.manager.TomcatManager;

/**
 * Simple plugin to copy packaged war to deployment dir
 *
 * @goal stoptomcat
 */
public class StopTomcat extends AbstractGoal{

    public void execute() throws MojoExecutionException {

        if (!ignore) {
            TomcatManager tomcat = new TomcatManager(directory);
            tomcat.stop();
        }
    }
}
