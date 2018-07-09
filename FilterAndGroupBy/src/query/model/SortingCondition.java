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
public class SortingCondition {
    private final SortingCriteria ascOrDec;
    private final List<Integer> col;

    public SortingCondition(SortingCriteria ascOrDec, List<Integer> col) {
        this.ascOrDec = ascOrDec;
        this.col = col;
    }

    public SortingCriteria getSortingCriteria() {
        return ascOrDec;
    }

    public List<Integer> getCol() {
        return col;
    }
    
}
