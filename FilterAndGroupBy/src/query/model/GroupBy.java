/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.model;

import java.util.*;

/**
 *
 * @author admin
 */
public class GroupBy {

    private final List<ColumnFormula> columnFormulaList;

    public GroupBy(List<ColumnFormula> columnFormulaList) {
        this.columnFormulaList = columnFormulaList;
    }

    public List<ColumnFormula> getcolumnFormulaList() {
        return columnFormulaList;
    }

}
