package com.warone11.database.utils.SQLModifiers;

import com.warone11.database.utils.SQLModifier;

public class Pager extends SQLModifier{
    private int page;
    private int size;
    private Pager(int page, int size) {
        if(page<1) throw new IllegalArgumentException("page must be greater than 0");
        if(size<1) throw new IllegalArgumentException("size must be greater than 0");
        this.page = page;
        this.size = size;
    }
    public static Pager of(int page, int size) {
        return new Pager(page,size);
    }

    @Override
    public ModifierResult toQuery() {
        return new ModifierResult("LIMIT "+size+" OFFSET "+(page-1)*size);
    }
    @Override
    public boolean canOperate() {
        return false;
    }
    
}
