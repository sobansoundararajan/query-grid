/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.control;

import query.formula.Formula;
import grid.Grid;
import grid.Value;
import java.text.ParseException;
import query.model.*;
import java.util.*;
import query.exception.QueriedException;

/**
 *
 * @author admin
 */
public class GroupByAction {

    private final List<ColumnFormula> columnFormulaList;

    public GroupByAction(List<ColumnFormula> columnFormulaList) {
        this.columnFormulaList = columnFormulaList;
    }

    public void execute(Grid grid, QueriedRange range) throws Exception {

        GroupBy groupBy = new GroupBy(columnFormulaList);
        QueriedResult queriedResult = range.getQueriedResult();
        groupByAction(grid, range, queriedResult, columnFormulaList);
        range.setQueriedResult(queriedResult);
        range.addGroupByList(groupBy);
    }

    private void groupByAction(Grid grid, QueriedRange range, QueriedResult queriedResult, List<ColumnFormula> columnFormulaList) throws ParseException, QueriedException {
        if (queriedResult.getNextAction().isEmpty()) {
            HashMap<List<Value>, List<Integer>> tempMap = new HashMap();
            for (Integer row : queriedResult.getRow()) {
                List<Value> groupKey = new LinkedList();
                for (ColumnFormula condition : columnFormulaList) {
                    Value value = grid.get(row + range.getStartRow(), condition.getCol() + range.getStartCol());
                    Formula formula = condition.getFormula();
                    List<Value> valueList = new LinkedList();
                    valueList.add(value);
                    groupKey.add(formula.getValue(valueList));
                }
                tempMap.computeIfAbsent(groupKey, k -> new LinkedList()).add(row);
            }
            queriedResult.setRow(new LinkedList());
            queriedResult.setFunctionMap(new HashMap());
            for (Map.Entry<List<Value>, List<Integer>> entry : tempMap.entrySet()) {
                queriedResult.addNextAction(new QueriedResult(entry.getValue(), entry.getKey()));

            }
        } else {

            for (QueriedResult vn : queriedResult.getNextAction()) {
                groupByAction(grid, range, vn, columnFormulaList);
            }
        }
    }
}
