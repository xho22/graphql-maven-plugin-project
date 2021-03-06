package com.graphql_java_generator.plugin.test.helper;

import java.io.File;

import com.graphql_java_generator.plugin.Logger;
import com.graphql_java_generator.plugin.Packaging;
import com.graphql_java_generator.plugin.PluginConfiguration;
import com.graphql_java_generator.plugin.PluginMode;

import lombok.Getter;

/**
 * 
 * @author EtienneSF
 */
@Getter
public class PluginConfigurationTestHelper implements PluginConfiguration {

	// All getters are generated thanks to Lombok, see the '@Getter' class annotation
	final Logger log;
	public PluginMode mode = null;
	public String packageName = null;
	public Packaging packaging = null;
	public File mainResourcesFolder = null;
	public String schemaFilePattern = null;
	public File schemaPersonalizationFile = null;
	public String sourceEncoding = null;
	public File targetClassFolder = null;
	public File targetSourceFolder = null;

	/**
	 * @param caller
	 *            Used to retrieve the appropriate Log4j logger
	 */
	public PluginConfigurationTestHelper(Object caller) {
		log = new Log4jLogger(caller);
	}

	@Override
	public boolean getGenerateJPAAnnotation() {
		return true;
	}
}
