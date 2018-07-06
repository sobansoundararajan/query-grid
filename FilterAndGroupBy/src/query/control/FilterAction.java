/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.control;

import query.model.ConditionsList;
import grid.Grid;
import grid.Value;
import query.model.*;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 *
 * @author admin
 */
public class FilterAction {

    static Map<ConditionsList, BiFunction<Value, String, Boolean>> conditionMap = new EnumMap<ConditionsList, BiFunction<Value, String, Boolean>>(ConditionsList.class);

    public void filter(Grid grid, QueriedRange range, Collection<Condition> conList) throws Exception {
        Filter filter = new Filter(conList);
        QueriedResult orginalResult = range.getQueriedResult();
        QueriedResult clonedResult = new QueriedResult(orginalResult.getRow(), orginalResult.getValue());
        List<Integer> chiledNodes = new LinkedList ();
        FilterAction.getCloneAndChiledNodes(orginalResult, clonedResult,chiledNodes);
        chiledNodes = FilterAction.getFilteredRows(grid, range, conList, chiledNodes);
        FilterAction.refineQueriedResult(clonedResult, chiledNodes);
        filter.setQueriedResult(clonedResult);
        range.addResult(filter);
    }

    private static void getCloneAndChiledNodes(QueriedResult orginalResult, QueriedResult clonedResult,List<Integer>chiledNodes) {
        if (!orginalResult.getNextAction().isEmpty()) {
            for (QueriedResult nextQR : orginalResult.getNextAction()) {
                QueriedResult nextQR1 = new QueriedResult(nextQR.getRow(), nextQR.getValue());
                clonedResult.addNextAction(nextQR1);
                FilterAction.getCloneAndChiledNodes(nextQR, nextQR1,chiledNodes);
            }
        }
        else
            chiledNodes.addAll(orginalResult.getRow());
    }

    private static List<Integer> getFilteredRows(Grid grid, QueriedRange range, Collection<Condition> conList, List<Integer> rowList) {
        List<Integer> temp = new LinkedList();
        for (Integer row : rowList) {
            boolean flag = true;
            for (Condition c : conList) {
                Value v = grid.get(row + range.getStartRow(), c.getCol() + range.getStartCol());
                BiFunction<Value, String, Boolean> test = conditionMap.get(c.getCondition());
                if (!test.apply(v, c.getValue())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                temp.add(row);
            }
        }
        return temp;
    }

    private static void refineQueriedResult(QueriedResult clonedResult, List<Integer> filteredRows) {
        if (!clonedResult.getNextAction().isEmpty()) {
            for (QueriedResult nextQR1 : clonedResult.getNextAction()) {
                FilterAction.refineQueriedResult(nextQR1, filteredRows);
            }

        } else {
            List<Integer> temp = new LinkedList();
            for (Integer row : filteredRows) {
                if (clonedResult.getRow().contains(row)) {
                    temp.add(row);
                }
            }
            if (!temp.isEmpty()) {
                clonedResult.setRow(temp);
            }
        }
    }

    static {
        conditionMap.put(ConditionsList.EQUALS, (v1, v2) -> Utility.equals(v1, v2));
        conditionMap.put(ConditionsList.DOESNOTEQUALS, (v1, v2) -> !Utility.equals(v1, v2));
        conditionMap.put(ConditionsList.GREATERTHAN, (v1, v2) -> Utility.greaterThan(v1, v2));
        conditionMap.put(ConditionsList.GREATERTHANOREQUALTO, (v1, v2) -> !Utility.lessThan(v1, v2));
        conditionMap.put(ConditionsList.LESSTHAN, (v1, v2) -> Utility.lessThan(v1, v2));
        conditionMap.put(ConditionsList.LESSTHANOREQUALTO, (v1, v2) -> !Utility.greaterThan(v1, v2));
        conditionMap.put(ConditionsList.BEGINESWITH, (v1, v2) -> Utility.beginsWith(v1, v2));
        conditionMap.put(ConditionsList.DOESNOTBEGINWITH, (v1, v2) -> !Utility.beginsWith(v1, v2));
        conditionMap.put(ConditionsList.ENDSWITH, (v1, v2) -> Utility.endsWith(v1, v2));
        conditionMap.put(ConditionsList.DOESNOTENDSWITH, (v1, v2) -> !Utility.endsWith(v1, v2));
        conditionMap.put(ConditionsList.CONTAINS, (v1, v2) -> Utility.contains(v1, v2));
        conditionMap.put(ConditionsList.DOESNOTCONTAINS, (v1, v2) -> !Utility.contains(v1, v2));
        conditionMap.put(ConditionsList.MATCHES, (v1, v2) -> Utility.equals(v1, v2));
        conditionMap.put(ConditionsList.DOESNOTMATCHES, (v1, v2) -> !Utility.equals(v1, v2));
    }
}
