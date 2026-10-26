package com.warone11.database.utils.SQLModifiers;

import com.warone11.database.utils.SQLModifier;

public class ComparingColumns extends SQLModifier {
    public enum Comparator{
        EQUALS,
        GREATER_THAN,
        LESS_THAN,
        LESS_THAN_OR_EQUALS,
        GREATER_THAN_OR_EQUALS,
        LIKE,
        NOT_LIKE,
        NOT_EQUALS
    }
    private static String ComparatorToString(Comparator comparator, boolean isNull){
        if(comparator == null)
            throw new IllegalArgumentException("Null comparator");
        switch (comparator){
            case EQUALS:
                return isNull?"IS":"=";
            case GREATER_THAN:
                if(isNull) throw new IllegalArgumentException("Can't compare null with >");
                return ">";
            case LESS_THAN:
                if(isNull) throw new IllegalArgumentException("Can't compare null with <");
                return "<";
            case LESS_THAN_OR_EQUALS:
                if(isNull) throw new IllegalArgumentException("Can't compare null with <=");
                return "<=";
            case GREATER_THAN_OR_EQUALS:
                if(isNull) throw new IllegalArgumentException("Can't compare null with >=");
                return ">=";
            case LIKE:
                if(isNull) throw new IllegalArgumentException("Can't compare null with LIKE");
                return "LIKE";
            case NOT_LIKE:
                if(isNull) throw new IllegalArgumentException("Can't compare null with NOT LIKE");
                return "NOT LIKE";
            case NOT_EQUALS:
                return isNull?"IS NOT":"!=";
            default:
                throw new IllegalArgumentException("Invalid comparator");
        }
        
    }
    private String column;
    private Comparator comparator;
    private Object value;
    public ComparingColumns(String column, Comparator comparator, Object value) {
        this.column = column;
        this.comparator = comparator;
        this.value = value;
    }
    public static ComparingColumns of(String column, Comparator comparator, Object value){
        if(column == null || comparator == null)
            throw new NullPointerException("Column and comparator can't be null");
        
        if(column.isBlank())
            throw new IllegalArgumentException("Column can't be blank");
        return new ComparingColumns(column, comparator, value);
    }
    @Override
    public ModifierResult toQuery() {
        if(value==null)
        return new ModifierResult(column+" "+ComparatorToString(comparator, true)+" NULL");
        else{
            return new ModifierResult(column+" "+ComparatorToString(comparator, false)+" ?", value);
        }
    }
    @Override
    public boolean canOperate() {
        return true;
    }
    
}
