package com.warone11.database.utils;

import com.warone11.database.utils.SQLModifiers.Order;
import com.warone11.database.utils.SQLModifiers.Pager;

public abstract class SQLModifier {
    public abstract ModifierResult toQuery();
    public abstract boolean canOperate();
    public final static class ModifierResult{
        public final String query;
        public final Object[] params;
        public ModifierResult(String query, Object... params){
            this.query = query;
            this.params = params;
        }
    }
    public static final class Operator{
        public final ModifierResult modifier;
        public final String operator;
        private Operator(SQLModifier modifier, String operator){
            if(!modifier.canOperate())
                throw new IllegalArgumentException(modifier.getClass()+" cannot be used with operators");
            
            this.modifier=modifier.toQuery();
            this.operator=operator;
        }
        public static Operator AND(SQLModifier modifier){
            return new Operator(modifier, " AND ");
        }
        public static Operator OR(SQLModifier modifier){
            return new Operator(modifier, " OR ");
        }
    }
    
}
