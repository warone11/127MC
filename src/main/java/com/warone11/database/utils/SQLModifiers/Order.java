package com.warone11.database.utils.SQLModifiers;

import com.warone11.database.utils.SQLModifier;

public class Order extends SQLModifier{
    public enum OrderType{
        ASC,
        DESC
    }

    private String column;
    private OrderType orderType;
    private Order order=null;
    private Order(String column, OrderType orderType) {
        this.column = column;
        this.orderType = orderType;
    }
    private Order(Order order,String column, OrderType orderType) {
        this.order=order;
        this.column = column;
        this.orderType = orderType;
    }
    public static Order by(String column, OrderType orderType) {
        if(orderType==null)
            throw new IllegalArgumentException("Order type can't be null");
        if(column==null||column.isBlank())
            throw new IllegalArgumentException("Column name can't be null or empty");
        return new Order(column, orderType);
    }
    public static Order by(String column) {
        return by(column, OrderType.ASC);
    }
    public static Order by(Order order, String column, OrderType orderType) {
        if(orderType==null)
            throw new IllegalArgumentException("Order type can't be null");
        if(column==null||column.isBlank())
            throw new IllegalArgumentException("Column name can't be null or empty");
        return new Order(order,column, orderType);
    }
    public static Order by(Order order, String column) {
        return by(order,column, OrderType.ASC);
    }
    @Override
    public ModifierResult toQuery() {
        if(order==null)
        return new ModifierResult("ORDER BY "+column + " " + orderType.toString());
        else
        return new ModifierResult(order.toQuery().query.concat(", "+column + " " + orderType.toString()));
    }
    @Override
    public boolean canOperate() {
        return false;
    }
    
}
