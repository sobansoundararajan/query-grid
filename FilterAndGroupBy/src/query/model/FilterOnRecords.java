/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.model;

import grid.Value;
import query.formula.Formula;

/**
 *
 * @author admin
 */
public class FilterOnRecords {

    private final Formula formula;
    private final int col;
    private final ConditionOperator conditionOperator;
    private final Value value;

    public FilterOnRecords(Formula formula, int col, ConditionOperator conditionOperator, Value value) {
        this.formula = formula;
        this.col = col;
        this.conditionOperator = conditionOperator;
        this.value = value;
    }

    public Formula getFormula() {
        return formula;
    }

    public int getCol() {
        return col;
    }

    public ConditionOperator getConditionOperator() {
        return conditionOperator;
    }

    public Value getValue() {
        return value;
    }

}
