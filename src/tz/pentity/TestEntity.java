package tz.pentity;

import tz.pdb.DB;
import tz.pdb.drivers.sqlite.SQLiteDriver;
import tz.pentity.api.fields.EFTID;
import tz.pentity.api.fields.EntityFieldTypes;

@REntity(name = "test")
public class TestEntity {
	
	@REField(type = "id")
	public int id;
	
	@REField(type = "VARCHAR", size = 255, additionals = {"not null"})
	public String name;
	
	public static void main(String[] args) {
		EntityFieldTypes.addType(new EFTID());
		DB.create(DB.DEFAULT_DB, "db/test.db", null, null, new SQLiteDriver());
		Entity.install("test");
	}

}
