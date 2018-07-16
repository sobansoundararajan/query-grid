/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.model;

import java.util.List;

/**
 *
 * @author admin
 */
public class GroupByCondition {
    private final GroupByCriteria groupByCriteria;
    private final int col;
    public GroupByCondition(GroupByCriteria groupByCriteria, int col) {
        this.groupByCriteria = groupByCriteria;
        this.col = col;
    }

    public GroupByCriteria getGroupByCriteria() {
        return groupByCriteria;
    }

    public int getCol() {
        return col;
    }
    
}
