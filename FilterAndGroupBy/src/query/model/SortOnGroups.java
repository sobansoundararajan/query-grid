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
public class SortOnGroups {

    private final ColumnFormula columnFormula;
    private final SortingCriteria sortingCriteria;

    public SortOnGroups(ColumnFormula columnFormula, SortingCriteria sortingCriteria) {
        this.columnFormula = columnFormula;
        this.sortingCriteria = sortingCriteria;
    }

    public ColumnFormula getColumnFormula() {
        return columnFormula;
    }

    public SortingCriteria getSortingCriteria() {
        return sortingCriteria;
    }
}
