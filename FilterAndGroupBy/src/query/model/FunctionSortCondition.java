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
public class FunctionSortCondition {
    private final  List<FunctionCondition> functionConditionList;
    private final SortingCriteria sortingCriteria;
    private final int level;

    public FunctionSortCondition(List<FunctionCondition> functionConditionList, SortingCriteria sortingCriteria, int level) {
        this.functionConditionList = functionConditionList;
        this.sortingCriteria = sortingCriteria;
        this.level = level;
    }

    public List<FunctionCondition> getFunctionConditionList() {
        return functionConditionList;
    }

    public SortingCriteria getSortingCriteria() {
        return sortingCriteria;
    }

    public int getLevel() {
        return level;
    }
    
    
}
