/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.model;

import java.util.*;
import java.util.function.BiFunction;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class GroupBy {

    private final List<GroupByCondition>groupByConditionList;

    public GroupBy(List<GroupByCondition>groupByConditionList) {
        this.groupByConditionList = groupByConditionList;
    }
    
    public List<GroupByCondition> getGroupByConditionList() {
        return groupByConditionList;
    }

}
