package tz.pentity.api;

import java.sql.ResultSet;

import tz.pdb.api.statements.DBInsert;
import tz.pdb.api.statements.DBUpdate;

public interface EntityType {

	public String entityType();
	
	public void entityLoad(ResultSet result);
	
	public void entitySave(DBUpdate update);
	
	public void entityInsert(DBInsert insert);
	
}
