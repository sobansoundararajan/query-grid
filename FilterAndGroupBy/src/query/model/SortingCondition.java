/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.model;

import java.util.List;
import java.util.Map;

/**
 *
 * @author admin
 */
public class SortingCondition {

    private final Map<Integer,SortingCriteria> sortingCondition;

    public SortingCondition(Map<Integer,SortingCriteria> sortingCondition) {
        this.sortingCondition = sortingCondition;
    }
    public Map<Integer,SortingCriteria> getSortingCondition() {
        return sortingCondition;
    }

}
