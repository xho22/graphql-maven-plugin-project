package ${package};

/**
 * @author generated by graphql-java-generator
 * @see https://github.com/graphql-java-generator/graphql-java-generator
 */
import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import graphql.GraphQL;
import graphql.TypeResolutionEnvironment;
import graphql.language.FieldDefinition;
import graphql.language.InterfaceTypeDefinition;
import graphql.language.ObjectTypeDefinition;
import graphql.language.Type;
import graphql.language.TypeName;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import graphql.schema.TypeResolver;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

/**
 * Based on the https://www.graphql-java.com/tutorials/getting-started-with-spring-boot/ tutorial
 * 
 * @author EtienneSF
 */
@Component
public class GraphQLProvider {

	/** The logger for this instance */
	protected Logger logger = LogManager.getLogger();

	@Autowired
	GraphQLDataFetchers graphQLDataFetchers;

	private GraphQL graphQL;

	@Bean
	public GraphQL graphQL() {
		return graphQL;
	}

	@PostConstruct
	public void init() throws IOException {
		Resource res;
		StringBuffer sdl = new StringBuffer();
#foreach ($schemaFile in $schemaFiles)
		res = new ClassPathResource("/${schemaFile}");
		Reader reader = new InputStreamReader(res.getInputStream(), Charset.forName("UTF8"));
		sdl.append(FileCopyUtils.copyToString(reader));
#end
		this.graphQL = GraphQL.newGraphQL(buildSchema(sdl.toString())).build();
	}

	private GraphQLSchema buildSchema(String sdl) {
		TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);

#if ($interfaces.size() > 0)
		// Add of the CharacterImpl type definition
#end
#foreach ($interface in $interfaces)
		typeRegistry.add(get${interface.classSimpleName}ImplType(typeRegistry));
#end

		RuntimeWiring runtimeWiring = buildWiring();
		SchemaGenerator schemaGenerator = new SchemaGenerator();
		return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
	}

	private RuntimeWiring buildWiring() {
		// Thanks to this thread :
		// https:// stackoverflow.com/questions/54251935/graphql-no-resolver-definied-for-interface-union-java
		//
		// Also see sample :
		// https://github.com/graphql-java/graphql-java-examples/tree/master/http-example
		return RuntimeWiring.newRuntimeWiring()
#foreach ($dataFetcherDelegate in $dataFetcherDelegates)
			// Data fetchers for ${dataFetcherDelegate.name}
#foreach ($dataFetcher in $dataFetcherDelegate.dataFetchers)
			.type(newTypeWiring("${dataFetcher.field.owningType.name}").dataFetcher("${dataFetcher.field.name}", graphQLDataFetchers.${dataFetcherDelegate.camelCaseName}${dataFetcher.pascalCaseName}()))
#if ($dataFetcher.field.owningType.class.simpleName == "InterfaceType")
			.type(newTypeWiring("${dataFetcher.field.owningType.concreteClassSimpleName}").dataFetcher("${dataFetcher.field.name}", graphQLDataFetchers.${dataFetcherDelegate.camelCaseName}${dataFetcher.pascalCaseName}()))
#end
#end
#end
#if ($interfaces.size() > 0)
			//
			// Let's link the interface types to the concrete types
#end
#foreach ($interface in $interfaces)
			.type("${interface.name}", typeWiring -> typeWiring.typeResolver(get${interface.name}Resolver()))
#end
			.build();
	}

#foreach ($interface in $interfaces)
	private ObjectTypeDefinition get${interface.classSimpleName}ImplType(TypeDefinitionRegistry typeRegistry) {
		InterfaceTypeDefinition def${interface.classSimpleName} = (InterfaceTypeDefinition) typeRegistry.getType("${interface.classSimpleName}").get();
		ObjectTypeDefinition.Builder def${interface.classSimpleName}Impl = ObjectTypeDefinition.newObjectTypeDefinition();
		def${interface.classSimpleName}Impl.name("${interface.classSimpleName}Impl");
		for (FieldDefinition fieldDef : def${interface.classSimpleName}.getFieldDefinitions()) {
			def${interface.classSimpleName}Impl.fieldDefinition(fieldDef);
		}
		// Let's precise that the new type is an implementation for this interface
		TypeName typeName = TypeName.newTypeName("${interface.classSimpleName}").build();
		def${interface.classSimpleName}Impl.implementz(typeName);

		return def${interface.classSimpleName}Impl.build();
	}
#end

	
#foreach ($interface in $interfaces)
	private TypeResolver get${interface.name}Resolver() {
		return new TypeResolver() {
			@Override
			public GraphQLObjectType getType(TypeResolutionEnvironment env) {
				Object javaObject = env.getObject();
				String ret = null;

#foreach ($implementingType in ${interface.implementingTypes})
				if (javaObject instanceof ${implementingType.name}) {
					ret = "${implementingType.name}";
				} else
#end
				{
					throw new RuntimeException("Can't resolve javaObject " + javaObject.getClass().getName());
				}
				logger.trace("Resolved type for javaObject {} is {}", javaObject.getClass().getName());
				return env.getSchema().getObjectType(ret);
			}
		};
	}

#end
}
