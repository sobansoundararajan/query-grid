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
public class Condition {

    private final int col;
    private final ConditionsList condition;
    private final String value;

    public Condition(int col, ConditionsList condition, String value) {
        this.col = col;
        this.condition = condition;
        this.value = value;
    }

    public int getCol() {
        return col;
    }

    public ConditionsList getCondition() {
        return condition;
    }

    public String getValue() {
        return value;
    }

}
