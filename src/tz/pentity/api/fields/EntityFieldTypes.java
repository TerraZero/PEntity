package tz.pentity.api.fields;

import java.util.HashMap;
import java.util.Map;

import tz.pdb.api.DBDriver;
import tz.pdb.api.fields.DBDefineField;
import tz.pdb.api.statements.DBCreate;
import tz.pentity.REField;
import tz.pentity.api.EntityFieldType;
import tz.preflects.ReflectField;

public class EntityFieldTypes {
	
	private static Map<String, EntityFieldType> types;
	
	public static EntityFieldType type(String type) {
		if (EntityFieldTypes.types != null) {
			return EntityFieldTypes.types.get(type);
		}
		return null;
	}
	
	public static void addType(EntityFieldType type) {
		if (EntityFieldTypes.types == null) {
			EntityFieldTypes.types = new HashMap<String, EntityFieldType>();
		}
		EntityFieldTypes.types.put(type.name().toLowerCase(), type);
	}
	
	public static DBDefineField field(DBDriver driver, DBCreate create, REField define, ReflectField field) {
		DBDefineField refield = driver.defineField();
		refield.name(EntityFieldTypes.name(define, field));
		refield.size(define.size());
		refield.additionals(define.additionals());
		refield.type(define.type());
		
		EntityFieldType type = EntityFieldTypes.type(define.type().toLowerCase());
		if (type != null) {
			type.field(refield, create, define, field);
		}
		
		return refield;
	}

	public static String name(REField define, ReflectField field) {
		if (define.name().length() > 0) {
			return define.name();
		} else {
			return field.name();
		}
	}
	
	public static String[] addAdditionals(String[] additionals, String add) {
		if (add != null && !EntityFieldTypes.hasAdditionals(additionals, add)) {
			String[] newAdditionals = new String[additionals.length + 1];
			for (int i = 0; i < additionals.length; i++) {
				newAdditionals[i] = additionals[i];
			}
			newAdditionals[additionals.length] = add;
			return newAdditionals;
		}
		return additionals;
	}

	public static String[] removeAdditionals(String[] additionals, String remove) {
		if (remove != null && EntityFieldTypes.hasAdditionals(additionals, remove)) {
			String[] newAdditionals = new String[additionals.length - 1];
			int c = 0;
			for (int i = 0; i < additionals.length; i++) {
				if (!additionals[i].equals(remove)) {
					newAdditionals[c++] = additionals[i];
				}
			}
			return newAdditionals;
		}
		return additionals;
	}
	
	public static boolean hasAdditionals(String[] additionals, String search) {
		for (String additional : additionals) {
			if (additional.equals(search)) return true;
		}
		return false;
	}
	
}
