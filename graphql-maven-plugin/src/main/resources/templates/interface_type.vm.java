package ${package};

/**
 * @author generated by graphql-maven-plugin
 */
public interface ${object.name} #if($object.implementz.size()>0)implements #foreach($impl in $object.implementz)$impl#if($foreach.hasNext), #end#end#end {
#foreach ($field in $object.fields)

	public void set${field.pascalCaseName}(${field.type.javaClassSimpleName} ${field.name});

	public ${field.type.javaClassSimpleName} get${field.pascalCaseName}();
#end
}