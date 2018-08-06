/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author admin
 */
public class Sort {

    private final LinkedHashMap<Integer,SortingCriteria> sortingCondition;

    public Sort(LinkedHashMap<Integer,SortingCriteria> sortingCondition) {
        this.sortingCondition = sortingCondition;
    }
    public LinkedHashMap<Integer,SortingCriteria> getSortingCondition() {
        return sortingCondition;
    }

}
