package com.jimetevenard.xml.relaxng.simplify;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.thaiopensource.resolver.catalog.CatalogResolver;
import com.thaiopensource.util.PropertyMapBuilder;
import com.thaiopensource.validate.ValidateProperty;
import com.thaiopensource.validate.ValidationDriver;
import com.thaiopensource.validate.prop.rng.RngProperty;

@Mojo(name = "simplify")
public class Simplify extends AbstractMojo {

	private static final String DEFAULT_SUFFIX = ".simplified.rng";
	private static final Charset UTF_8 = Charset.forName("UTF-8");

	@Parameter(defaultValue = "${project.basedir}", required = true, readonly = true)
	private File basedir;

	@Parameter
	private Schema[] schemas;
	
	@Parameter
	private String encoding;
	
	@Parameter
	private String[] catalogs;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		try {
			
			
			for (Schema schema : schemas) {
				File schemaFile = new File(basedir, schema.getSourcePath());
				File simplifiedSchemaFile = targetFile(schema,basedir);

				InputSource schemaSrc = new InputSource(schemaFile.toURI().toString());

				String simplifiedSchema = getSimplifiedSchema(schemaSrc);
				writeFile(simplifiedSchema, simplifiedSchemaFile);

			}

		} catch (SAXException | IOException e) {
			throw new MojoFailureException("An exception occured simplifying the schema", e);
		}

	}

	private File targetFile(Schema schema, File basedir2) {
		File simplifiedSchemaFile;
		if (schema.getTargetPath() != null) {
			// i.e. explicitely specified by the user's POM
			simplifiedSchemaFile = new File(basedir, schema.getTargetPath());
		} else {
			// i.e. unspecified => default filename
			String simplifiedFileName = schema.getSourcePath() + DEFAULT_SUFFIX;
			simplifiedSchemaFile = new File(basedir, simplifiedFileName);
		}
		return simplifiedSchemaFile;
	}

	private void writeFile(String simplifiedSchema, File simplifiedSchemaFile) throws IOException {
		if(!simplifiedSchemaFile.isFile()){
			simplifiedSchemaFile.getParentFile().mkdirs();
			simplifiedSchemaFile.createNewFile();
		}
		
		Charset encoding = this.encoding != null ? Charset.forName(this.encoding) : UTF_8;
		
		OutputStream outStream = new FileOutputStream(simplifiedSchemaFile);
		OutputStreamWriter writer = new OutputStreamWriter(outStream, encoding);

		writer.write(simplifiedSchema);
		writer.close();

	}

	public String getSimplifiedSchema(InputSource schema) throws SAXException, IOException {
		PropertyMapBuilder properties = new PropertyMapBuilder();
		
		CatalogResolver resolver = new CatalogResolver(catalogsURIs());
		properties.put(ValidateProperty.RESOLVER, resolver);
		
		ValidationDriver driver = new ValidationDriver(properties.toPropertyMap());
		
		/**
		 * TODO / Refacto :
		 * 
		 * Réutiliser le ValidationDriver et le monter en instance ?
		 * A voir si c'est possible dans la doc.
		 */
		
		driver.loadSchema(schema); 
		String simple = driver.getSchemaProperties().get(RngProperty.SIMPLIFIED_SCHEMA);

		return simple;
	}

	
	private List<String> catalogsURIs(){
		List<String> catalogsList = new ArrayList<>();
		for (String cat : this.catalogs) {
			catalogsList.add(new File(basedir, cat).toURI().toString());	
		}
		
		return catalogsList;
	}

}
