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
public class FilterOnGroups {

    private final ColumnFormula columnFormula;
    private final ConditionOperator conditionOperator;
    private final Value value;

    public FilterOnGroups(ColumnFormula columnFormula, ConditionOperator conditionOperator, Value value) {
        this.columnFormula = columnFormula;
        this.conditionOperator = conditionOperator;
        this.value = value;
    }

    public ColumnFormula getColumnFormula() {
        return columnFormula;
    }

    public ConditionOperator getConditionOperator() {
        return conditionOperator;
    }

    public Value getValue() {
        return value;
    }
}
