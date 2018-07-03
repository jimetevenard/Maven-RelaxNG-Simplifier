package com.jimetevenard.xml.relaxng.simplify;
import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.thaiopensource.validate.ValidationDriver;
import com.thaiopensource.validate.prop.rng.RngProperty;

@Mojo(name = "simplify")
public class Simplify extends AbstractMojo {
	
	
	@Parameter( defaultValue = "${project.basedir}", required = true, readonly = true )
	private File basedir;
	
	@Parameter
	private Schema[] schemas;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		for (Schema schema : schemas) {
			InputSource schemaSrc = new 
		}

		try {
			String simplifiedSchema = getSimplifiedSchema(test);
			
			//TODO écrire le fichier
		} catch (SAXException | IOException e) {
			throw new MojoFailureException("An exception occured simplifying the schema", e);
		}

	}

	public static String getSimplifiedSchema(InputSource schema) throws SAXException, IOException {

		ValidationDriver driver = new ValidationDriver();
		driver.loadSchema(schema); // TODO
		String simple = driver.getSchemaProperties().get(RngProperty.SIMPLIFIED_SCHEMA);

		return simple;
	}

}
