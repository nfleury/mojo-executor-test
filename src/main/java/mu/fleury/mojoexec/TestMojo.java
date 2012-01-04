package mu.fleury.mojoexec;

import static org.twdata.maven.mojoexecutor.MojoExecutor.artifactId;
import static org.twdata.maven.mojoexecutor.MojoExecutor.configuration;
import static org.twdata.maven.mojoexecutor.MojoExecutor.element;
import static org.twdata.maven.mojoexecutor.MojoExecutor.executeMojo;
import static org.twdata.maven.mojoexecutor.MojoExecutor.executionEnvironment;
import static org.twdata.maven.mojoexecutor.MojoExecutor.goal;
import static org.twdata.maven.mojoexecutor.MojoExecutor.name;
import static org.twdata.maven.mojoexecutor.MojoExecutor.groupId;
import static org.twdata.maven.mojoexecutor.MojoExecutor.plugin;
import static org.twdata.maven.mojoexecutor.MojoExecutor.version;

import java.io.File;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;


/**
 * 
 * @author nicholas
 * @goal test
 */
public class TestMojo extends AbstractMojo{
	
	/**
	 * Dependencies output directory
	 *
	 * @parameter default-value="/tmp/dependencies"
	 * @required
	 */
	private File outputDirectory; 
	
	/**
	 * Dependencies output directory
	 *
	 * @parameter expression="${silent}"
	 */	
	private boolean silent;
	
	
	/**
	 * The Maven Session Object
	 *
	 * @parameter expression="${session}"
	 * @required
	 * @readonly
	 */
	protected MavenSession session;

	/**
	 * The Maven PluginManager Object
	 *
	 * @component
	 * @required
	 */
	protected BuildPluginManager pluginManager;

	
	/**
	 * The Maven Project Object
	 *
	 * @parameter expression="${project}"
	 * @required
	 * @readonly
	 */
	protected MavenProject project;
	
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		
		unpackDependencies();
		getLog().info("Dependencies should have been unpacked here:"+outputDirectory);
	}

	
	
	private void unpackDependencies() throws MojoExecutionException{
		
		
		
		executeMojo(
			    plugin(
			        groupId("org.apache.maven.plugins"),
			        artifactId("maven-dependency-plugin"),
			        version("2.4")
			    ),
			    goal("unpack-dependencies"),
			    configuration(
			        element("outputDirectory", outputDirectory.getAbsolutePath())
			    ),
			    executionEnvironment(
			        project,
			        session,
			        pluginManager
			    )
			);

		
	}
	


	
	
}
