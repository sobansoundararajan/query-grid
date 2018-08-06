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
public class FunctionSort {
    private final  FunctionCondition functionCondition;
    private final SortingCriteria sortingCriteria;
    public FunctionSort(FunctionCondition functionCondition, SortingCriteria sortingCriteria) {
        this.functionCondition = functionCondition;
        this.sortingCriteria = sortingCriteria;
    }

    public FunctionCondition getFunctionCondition() {
        return functionCondition;
    }

    public SortingCriteria getSortingCriteria() {
        return sortingCriteria;
    }
}
