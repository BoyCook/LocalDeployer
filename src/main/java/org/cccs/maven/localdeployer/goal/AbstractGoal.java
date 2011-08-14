package org.cccs.maven.localdeployer.goal;

import org.apache.maven.plugin.AbstractMojo;

/**
 * User: Craig Cook
 * Date: Nov 7, 2008
 * Time: 3:59:45 PM
 */
public abstract class AbstractGoal extends AbstractMojo {

    /**
     * The greeting to display.
     *
     * @parameter expression="Hello world"
     */
    public String greeting;

    /**
     * The tomcat dir location
     *
     * @parameter expression="/usr/local/apache-tomcat"
     */
    public String directory;

    /**
     * The webapp name
     *
     * @parameter expression="${project.build.finalName}"
     */
    public String appname;

    /**
     * The source war location
     *
     * @parameter expression="${project.build.directory}"
     */
    public String source;

    /**
     * Start tomcat
     *
     * @parameter expression=false;
     */
    public boolean starttomcat;

    /**
     * Stop tomcat
     *
     * @parameter expression=false;
     */
    public boolean stoptomcat;

    /**
     * Time to pause after tomcat stop
     *
     * @parameter expression=3000;
     */
    public int pausetime;

    /**
     * Ignore commands - saves modifying all of the execution configuration
     *
     * @parameter expression=false;
     */
    public boolean ignore;    

    public AbstractGoal(){
        getLog().info("LOCAL-DEPLOYER: " + greeting + " brought to you by Craig Cook");
    }

    public void sleep(String msg){
        //Dodgy code - TODO parse exec response
        try {
            if (pausetime > 0) {
                getLog().info(msg);
                Thread.sleep(pausetime);
            }
        } catch (Exception e) {
            System.out.println("Thread sleep error: " + e.getMessage());
        }
    }
}
