package tz.pentity.api;

import tz.pdb.api.fields.DBDefineField;
import tz.pdb.api.statements.DBCreate;
import tz.pentity.REField;
import tz.preflects.ReflectField;

public interface EntityFieldType {

	public String name();
	
	public void field(DBDefineField refield, DBCreate create, REField define, ReflectField field);
	
}
