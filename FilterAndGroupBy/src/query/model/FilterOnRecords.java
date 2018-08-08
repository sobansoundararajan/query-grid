/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.model;

/**
 *
 * @author admin
 */
public class FilterOnRecords {

    private final int col;
    private final ConditionOperator conditionOperator;
    private final String value;

    public FilterOnRecords(int col, ConditionOperator conditionOperator, String value) {
        this.col = col;
        this.conditionOperator = conditionOperator;
        this.value = value;
    }

    public int getCol() {
        return col;
    }

    public ConditionOperator getConditionOperator() {
        return conditionOperator;
    }

    public String getValue() {
        return value;
    }

}
