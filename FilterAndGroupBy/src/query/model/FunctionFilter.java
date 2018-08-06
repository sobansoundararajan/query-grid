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
public class FunctionFilter {
    private final FunctionCondition fuctionCondition;
    private final Condition condition;
    private final String value;

    public FunctionFilter(FunctionCondition fuctionCondition, Condition condition, String value) {
        this.fuctionCondition = fuctionCondition;
        this.condition = condition;
        this.value = value;
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
}
