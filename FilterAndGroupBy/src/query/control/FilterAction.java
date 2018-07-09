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
        List<Integer> chiledNodes = new LinkedList();
        FilterAction.filterAction(grid, range, orginalResult, clonedResult, conList, clonedResult.getNextAction());
        filter.setQueriedResult(clonedResult);
        range.addResult(filter);
    }

    private static void filterAction(Grid grid, QueriedRange range, QueriedResult orginalResult, QueriedResult clonedResult, Collection<Condition> conList, List<QueriedResult> nextAction) {
        if (!orginalResult.getNextAction().isEmpty()) {
            for (QueriedResult nextOR : orginalResult.getNextAction()) {
                QueriedResult nextCR = new QueriedResult(nextOR.getRow(), nextOR.getValue());
                clonedResult.addNextAction(nextCR);
                FilterAction.filterAction(grid, range, nextOR, nextCR, conList, clonedResult.getNextAction());
            }
        } else {
            List<Integer> temp = new LinkedList();
            for (Integer row : orginalResult.getRow()) {
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
            if (temp.isEmpty()) {
                nextAction.remove(clonedResult);
            } else {
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
