package tz.pentity;

import java.sql.ResultSet;
import java.sql.SQLException;

import tz.core.logger.Log;
import tz.pdb.DB;
import tz.pdb.api.statements.DBInsert;
import tz.pdb.api.statements.DBUpdate;
import tz.pdb.drivers.sqlite.SQLiteDriver;
import tz.pentity.api.EntityType;
import tz.pentity.api.fields.EFTID;
import tz.pentity.api.fields.EntityFieldTypes;

@REntity(name = "test")
public class TestEntity implements EntityType {
	
	@REField(type = "id")
	private int id;
	
	@REField(type = "VARCHAR", size = 255, additionals = {"not null"})
	private String name;
	
	public static void main(String[] args) {
		EntityFieldTypes.addType(new EFTID());
		DB.create(DB.DEFAULT_DB, "db/test.db", null, null, new SQLiteDriver());
		Entity.install("test");
		Entity.load("test");
	}

	@Override
	public String entityType() {
		return "test";
	}

	@Override
	public void entityLoad(ResultSet result) {
		try {
			this.id = result.getInt(1);
			this.name = result.getString(2);
		} catch (SQLException e) {
			Log.fatal(Log.ident("Entity", "TestEntity", "Load"), "Error by load a test entity.");
		}
	}

	@Override
	public void entitySave(DBUpdate update) {
		update.set("name", this.name);
	}

	@Override
	public void entityInsert(DBInsert insert) {
		insert.cols(this.name);
	}

}
