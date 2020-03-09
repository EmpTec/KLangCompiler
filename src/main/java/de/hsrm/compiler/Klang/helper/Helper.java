package de.hsrm.compiler.Klang.helper;

import java.util.Map;

import de.hsrm.compiler.Klang.nodes.StructDefinition;
import de.hsrm.compiler.Klang.types.Type;

public class Helper {
  public static String getErrorPrefix(int line, int col) {
    return "Error in line " + line + ":" + col + " ";
  }

  public static Type drillType(Map<String, StructDefinition> structs, String name, String[] path, int pathIndex) {
    // Search for the referenced field
    var structDef = structs.get(name);
    for (var field : structDef.fields) {
      if (field.name.equals(path[pathIndex])) {
        if (!field.type.isPrimitiveType()) {
          // this references a struct!

          // if we exhausted the path, this field type is our type
          if (pathIndex == path.length - 1) {
            return field.type;
          }

          // we did not exhaust the path, go on
          return drillType(structs, field.type.getName(), path, pathIndex + 1);
        } else {
          // this references a primitive, we hit bedrock!

          // make sure we exhausted the complete path
          if (pathIndex < path.length - 1) {
            throw new RuntimeException(field.name + " must be a struct but is of type " + field.type.getName() + ":");
          }

          // hooray, we exhausted the path, this field type is our type
          return field.type;
        }
      }
    }

    throw new RuntimeException("Struct " + structDef.name + " does not contain field " + path[pathIndex]);
  }

  public static int getFieldIndex(StructDefinition structDef, String fieldName) {
    for (int i = 0; i < structDef.fields.length; i++) {
      if (structDef.fields[i].name.equals(fieldName)) {
        return i;
      }
    }

    return -1;
  }

  public static int getFieldOffset(StructDefinition structDef, int fieldIndex) {
    return fieldIndex * 8;
  }

  public static int getFieldOffset(StructDefinition structDef, String fieldName)  {
    return getFieldIndex(structDef, fieldName) * 8;
  }

  public static int getFieldSizeBytes(StructDefinition structDef) {
    return structDef.fields.length * 8;
  }
}