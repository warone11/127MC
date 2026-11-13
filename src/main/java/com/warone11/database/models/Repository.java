package com.warone11.database.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.warone11.database.DatabaseManager;
import com.warone11.database.utils.SQLModifier;
import com.warone11.database.utils.SQLModifier.ModifierResult;

public class Repository<T extends IDataBaseEntity> {
    protected final String[] columns;
    protected final String tableName;
    protected Connection con;
    private final T prefabEntity;
    public Repository(T prefabEntity,Connection con, String tableName, String[] columns){
        if(prefabEntity==null)
            throw new IllegalArgumentException("prefabEntity cannot be null");
        this.con = con;
        this.tableName = tableName;
        this.columns = columns;
        this.prefabEntity=prefabEntity;
    }
    public boolean insert(T values){
        String query = "INSERT INTO " + tableName + " (" + String.join(",", columns) + ") VALUES (";
        for (int i = 0; i < columns.length; i++) {
            if(i!=0) query += ",";
            query += "?";
        }
        query += ")";
        if(DatabaseManager.SHOW_SQL_QUERY) System.out.println(query);
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            values.getValues(stmt);
            return stmt.executeUpdate()==1;
        } catch (SQLException e) {
            if(DatabaseManager.SHOW_SQL_EXCEPTIONS) e.printStackTrace();
            return false;
        }
    }
    public boolean exists(ModifierResult modifier){
        String query = "SELECT 1 FROM "+tableName+" "+modifier.query;
        if(DatabaseManager.SHOW_SQL_QUERY) System.out.println(query);
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            for (int i = 0; i < modifier.params.length; i++) {
                stmt.setObject(i+1, modifier.params[i]);
            }
            return stmt.executeQuery().next();
        }catch(SQLException e){
            if(DatabaseManager.SHOW_SQL_EXCEPTIONS) e.printStackTrace();
            return false;
        }
        
    }
    public boolean exist(SQLModifier modifier){
        ModifierResult result =modifier.toQuery();
        if(modifier.canOperate())
            result=new ModifierResult("WHERE "+result.query,result.params);
        return exists(result);
    }
    public List<T> select(ModifierResult modifier){
        String query = "SELECT * FROM "+tableName+" "+modifier.query;
        if(DatabaseManager.SHOW_SQL_QUERY) System.out.println(query);
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            for (int i = 0; i < modifier.params.length; i++) {
                stmt.setObject(i+1, modifier.params[i]);
            }
            try (ResultSet rs = stmt.executeQuery()) {
                List<T> list = new ArrayList<>();
                while(rs.next()){
                    T createdEntity=(T)prefabEntity.create(rs);
                    if(createdEntity!=null)
                    list.add(createdEntity);
                }
                return list;
            } 
        }catch(SQLException e){
            if(DatabaseManager.SHOW_SQL_EXCEPTIONS) e.printStackTrace();
            return null;
        }
    }
    public List<T> select(SQLModifier modifier){
        ModifierResult result =modifier.toQuery();
        if(modifier.canOperate())
            result=new ModifierResult("WHERE "+result.query,result.params);
        return select(result);
    }
    public List<T> selectAll(){
        return select(new ModifierResult("WHERE 1=1"));
    }
    public int delete(ModifierResult modifier){
        String query = "DELETE FROM "+tableName+" "+modifier.query;
        if(DatabaseManager.SHOW_SQL_QUERY) System.out.println(query);
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            for (int i = 0; i < modifier.params.length; i++) {
                stmt.setObject(i+1, modifier.params[i]);
            }
            return stmt.executeUpdate();
        }catch(SQLException e){
            if(DatabaseManager.SHOW_SQL_EXCEPTIONS) e.printStackTrace();
            return -1;
        }
    }
    public int delete(SQLModifier modifier){
        ModifierResult result =modifier.toQuery();
        if(modifier.canOperate())
            result=new ModifierResult("WHERE "+result.query,result.params);
        return delete(result);
    }
    public int update(ModifierResult modifier, Map<String,Object> values){
        
        String query = "UPDATE "+tableName+" SET ";
        
        List<Entry<String,Object>> set =new ArrayList<>(values.entrySet());
        for(int i=0;i<set.size();i++){
            if(i!=0) query+=",";
            query+=set.get(i).getKey()+"=?";
        }
        query+=" "+modifier.query;
        if(DatabaseManager.SHOW_SQL_QUERY) System.out.println(query);
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            int index = 1;
            for (Entry<String, Object> entry : set) {
                stmt.setObject(index++, entry.getValue());
            }

            for (Object param : modifier.params) {
                stmt.setObject(index++, param);
            }
            return stmt.executeUpdate();
        }catch(SQLException e){
            if(DatabaseManager.SHOW_SQL_EXCEPTIONS) e.printStackTrace();
            return -1;
        }
    }
    public int update(SQLModifier modifier, Map<String,Object> values){
        ModifierResult result =modifier.toQuery();
        if(modifier.canOperate())
            result=new ModifierResult("WHERE "+result.query,result.params);
        return update(result,values);
    }
}
