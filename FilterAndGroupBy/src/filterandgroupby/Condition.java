/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filterandgroupby;

/**
 *
 * @author admin
 */
public class Condition {
    private int col;
    private ConditionsEnum condition;
    private String value;

    public int getCol() {
        return col;
    }

    public ConditionsEnum getCondition() {
        return condition;
    }

    public String getValue() {
        return value;
    }

    public Condition(int col, ConditionsEnum condition, String value) {
        this.col = col;
        this.condition = condition;
        this.value = value;
    }
   
    
}
