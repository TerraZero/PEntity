package tz.pentity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tz.core.logger.Log;
import tz.pdb.DB;
import tz.pdb.SQLPlaceholder;
import tz.pdb.api.DBDriver;
import tz.pdb.api.statements.DBCreate;
import tz.pdb.api.statements.DBSelect;
import tz.pentity.api.EntityType;
import tz.pentity.api.fields.EntityFieldTypes;
import tz.preflects.InfoWrapper;
import tz.preflects.ReflectField;
import tz.preflects.Reflects;

public class Entity {
	
	private static List<InfoWrapper<REntity>> entities;
	private static DBDriver driver;
	
	public static List<InfoWrapper<REntity>> entities() {
		return Entity.entities(false);
	}
	
	public static List<InfoWrapper<REntity>> entities(boolean force) {
		if (force || Entity.entities == null) {
			Entity.entities = new ArrayList<InfoWrapper<REntity>>();
			Reflects.forAnnotation(REntity.class, (wrapper) -> {
				Entity.entities.add(wrapper);
			});
		}
		return Entity.entities;
	}
	
	public static InfoWrapper<REntity> entity(String name) {
		List<InfoWrapper<REntity>> entities = Entity.entities();
		if (entities != null) {
			for (InfoWrapper<REntity> entity : entities) {
				if (Entity.name(entity).equals(name)) {
					return entity;
				}
			}
		}
		return null;
	}
	
	public static String name(InfoWrapper<REntity> wrapper) {
		if (wrapper.annotation().name().length() > 0) {
			return wrapper.annotation().name();
		} else {
			return wrapper.reflect().name();
		}
	}
	
	public static DBDriver driver() {
		if (Entity.driver == null) {
			Entity.driver = DB.driver();
		}
		return Entity.driver;
	}
	
	public static String table(InfoWrapper<REntity> info) {
		String table = info.annotation().table();
		if (table.length() == 0) {
			table = info.annotation().name();
			if (table.length() == 0) {
				table = info.reflect().name();
			}
		}
		return table;
	}

	public static void install(String name) {
		InfoWrapper<REntity> install = Entity.entity(name);
		if (install == null) {
			Log.critical(Log.ident("Entity"), "Entity type [0] can not be installed because definition is not found", name);
		} else {
			String table = Entity.table(install);
			
			// check if table is exist
			for (String t : Entity.driver().info().tables()) {
				if (t.equals(table)) {
					return;
				}
			}
			
			DBCreate create = Entity.driver().create(table);
			List<ReflectField> fields = install.reflect().fields(REField.class);
			for (ReflectField field : fields) {
				REField define = field.annotation(REField.class);
				create.field(EntityFieldTypes.field(Entity.driver(), create, define, field));
			}
			create.execute();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static<entity extends EntityType> List<entity> load(String name, int... ids) {
		InfoWrapper<REntity> info = Entity.entity(name);
		List<entity> entities = null;
		if (info == null) {
			Log.critical(Log.ident("Entity"), "Entity type [0] can not be loaded because definition is not found", name);
		} else {
			DBSelect select = Entity.driver().select(Entity.table(info), "e");
			select.selectAll();
			if (ids.length > 0) {
				select.where("e.id", "!ids", "IN");
				select.placeholder("!ids", SQLPlaceholder.toValue(ids));
			}
			ResultSet result = select.execute();
			entities = new ArrayList<entity>(ids.length);
			try {
				while (result.next()) {
					entity entity = (entity)info.reflect().newInstantiate();
					entity.entityLoad(result);
					entities.add(entity);
				}
			} catch (SQLException e) {
				Log.warning(Log.ident("Entity"), "Error by creating the entity [0]", name);
			}
		}
		return entities;
	}
	
	public static<entity extends EntityType> void save(entity entity) {
		
	}
	
}
