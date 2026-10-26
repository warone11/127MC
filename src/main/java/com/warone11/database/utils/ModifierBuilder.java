package com.warone11.database.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.warone11.database.utils.SQLModifier.ModifierResult;
import com.warone11.database.utils.SQLModifier.Operator;
import com.warone11.database.utils.SQLModifiers.ComparingColumns;
import com.warone11.database.utils.SQLModifiers.Order;
import com.warone11.database.utils.SQLModifiers.Pager;

public class ModifierBuilder {

    public static ModifierResult allAND(SQLModifier... modifiers) {
        if(modifiers == null)
            throw new NullPointerException("modifiers is null");
        List<Object> params= new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        
        for (int i = 0; i < modifiers.length; i++) {
            if(modifiers[i] == null)
                throw new NullPointerException("modifiers in position "+i+" is null");
            ModifierResult result= modifiers[i].toQuery();
            
            if (i != 0&& modifiers[i].canOperate()) {
                builder.append(" AND ");
            }
            builder.append(result.query);
            params.addAll(Arrays.asList(result.params));
            builder.append(" ");
        }

        return new ModifierResult(builder.toString(), params.toArray());
    }
    public static ModifierResult allOR(SQLModifier... modifiers) {
        if(modifiers == null)
            throw new NullPointerException("modifiers is null");
        List<Object> params= new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        
        for (int i = 0; i < modifiers.length; i++) {
            if(modifiers[i] == null)
                throw new NullPointerException("modifiers in position "+i+" is null");
            ModifierResult result= modifiers[i].toQuery();
            
            if (i != 0&& modifiers[i].canOperate()) {
                builder.append(" OR ");
            }
            builder.append(result.query);
            params.addAll(Arrays.asList(result.params));
            builder.append(" ");
        }
        
        return new ModifierResult(builder.toString(), params.toArray());
    }
    public static ModifierResult custom(Order order, Pager pager ,SQLModifier firstModifier,Operator... operators) {
        if(operators == null)
            throw new NullPointerException("modifiers is null");
        if(firstModifier == null)
            throw new NullPointerException("firstModifier is null");
        if(!firstModifier.canOperate())
            throw new IllegalArgumentException(firstModifier.getClass()+" cannot be used with operators");
        List<Object> params= new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        ModifierResult firstResult= firstModifier.toQuery();
        builder.append(firstResult.query);
        params.addAll(Arrays.asList(firstResult.params));
        for (int i = 0; i < operators.length; i++) {
            if(operators[i] == null)
                throw new NullPointerException("operators in position "+i+" is null");
            ModifierResult result= operators[i].modifier;
            builder.append(operators[i].operator);
            builder.append(result.query);
            params.addAll(Arrays.asList(result.params));
            builder.append(" ");
        }
        if(order!=null) {
            builder.append(order.toQuery().query);
            builder.append(" ");
        }
        if(pager!=null) {
            builder.append(pager.toQuery().query);
            builder.append(" ");
        }
        return new ModifierResult(builder.toString(), params.toArray());
        
    }
    public static ModifierResult custom(Order order,SQLModifier firstModifier,Operator... operators) {
        return custom(order,null,firstModifier,operators);
    }
    public static ModifierResult custom(Pager pager ,SQLModifier firstModifier,Operator... operators) {
        return custom(null,pager,firstModifier,operators);
    }
    public static ModifierResult custom(SQLModifier firstModifier,Operator... operators) {
        return custom(null,null,firstModifier,operators);
    }

}
