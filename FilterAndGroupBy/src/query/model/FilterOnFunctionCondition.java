/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.model;

import grid.Value;

/**
 *
 * @author admin
 */
public class FilterOnFunctionCondition {
    private final FunctionCondition fuctionCondition;
    private final Condition condition;
    private final String value;
    private final  int level;

    public FilterOnFunctionCondition(FunctionCondition fuctionCondition, Condition condition, String value,int level) {
        this.fuctionCondition = fuctionCondition;
        this.condition = condition;
        this.value = value;
        this.level=level;
    }

    public FunctionCondition getFuctionCondition() {
        return fuctionCondition;
    }

    public Condition getCondition() {
        return condition;
    }

    public String getValue() {
        return value;
    }
    
    public int getLevel() {
        return level;
    }
}
