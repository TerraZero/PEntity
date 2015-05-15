package tz.pentity.api.fields;

import tz.pdb.api.fields.DBDefineField;
import tz.pdb.api.statements.DBCreate;
import tz.pentity.REField;
import tz.pentity.api.EntityFieldType;
import tz.preflects.ReflectField;

public class EFTID implements EntityFieldType {

	@Override
	public String name() {
		return "id";
	}

	@Override
	public void field(DBDefineField refield, DBCreate create, REField define, ReflectField field) {
		create.keys(refield.name());
		refield.type("INT");
		refield.size(0);
		refield.additionals(EntityFieldTypes.addAdditionals(refield.additionals(), create.driver().info().autoIncrement()));
	}

}
