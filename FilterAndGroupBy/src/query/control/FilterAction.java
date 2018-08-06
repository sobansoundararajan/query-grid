/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.control;

import query.model.Condition;
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

    static Map<Condition, BiFunction<Value, String, Boolean>> conditionMap = new EnumMap<Condition, BiFunction<Value, String, Boolean>>(Condition.class);
    private final Collection<Filter> conList;

    public FilterAction(Collection<Filter> conList) {
        this.conList = conList;
    }
    
    public void execute(Grid grid, QueriedRange range) throws Exception {
        //Filter filter = new Filter(conList);
        range.getFilterConList().addAll(conList);
        QueriedResult queriedResult = range.getQueriedResult();
        FilterAction.filterAction(grid, range, queriedResult, conList);
        range.setQueriedResult(queriedResult);
        if (!range.getQueriedResult().getFunctionMap().isEmpty()) {
            FunctionAction.excute(grid, range);
        }
    }

    private static void filterAction(Grid grid, QueriedRange range, QueriedResult queriedResult, Collection<Filter> conList) {
        if (!queriedResult.getNextAction().isEmpty()) {
            List<QueriedResult> nodesToBeAdded = new LinkedList ();
            for (QueriedResult nextOR : queriedResult.getNextAction()) {
                FilterAction.filterAction(grid, range, nextOR, conList);
                if (!(nextOR.getNextAction().isEmpty() && nextOR.getRow().isEmpty())) {
                    nodesToBeAdded.add(nextOR);
                }
            }
            queriedResult.setNextAction(nodesToBeAdded);
        } else {
            List<Integer> temp = new LinkedList();
            for (Integer row : queriedResult.getRow()) {
                boolean flag = true;
                for (Filter c : conList) {
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
            queriedResult.setRow(temp);

        }
    }

    static {
        conditionMap.put(Condition.EQUALS, (v1, v2) -> Utility.equals(v1, v2));
        conditionMap.put(Condition.DOESNOTEQUALS, (v1, v2) -> !Utility.equals(v1, v2));
        conditionMap.put(Condition.GREATERTHAN, (v1, v2) -> Utility.greaterThan(v1, v2));
        conditionMap.put(Condition.GREATERTHANOREQUALTO, (v1, v2) -> !Utility.lessThan(v1, v2));
        conditionMap.put(Condition.LESSTHAN, (v1, v2) -> Utility.lessThan(v1, v2));
        conditionMap.put(Condition.LESSTHANOREQUALTO, (v1, v2) -> !Utility.greaterThan(v1, v2));
        conditionMap.put(Condition.BEGINESWITH, (v1, v2) -> Utility.beginsWith(v1, v2));
        conditionMap.put(Condition.DOESNOTBEGINWITH, (v1, v2) -> !Utility.beginsWith(v1, v2));
        conditionMap.put(Condition.ENDSWITH, (v1, v2) -> Utility.endsWith(v1, v2));
        conditionMap.put(Condition.DOESNOTENDSWITH, (v1, v2) -> !Utility.endsWith(v1, v2));
        conditionMap.put(Condition.CONTAINS, (v1, v2) -> Utility.contains(v1, v2));
        conditionMap.put(Condition.DOESNOTCONTAINS, (v1, v2) -> !Utility.contains(v1, v2));
        conditionMap.put(Condition.MATCHES, (v1, v2) -> Utility.equals(v1, v2));
        conditionMap.put(Condition.DOESNOTMATCHES, (v1, v2) -> !Utility.equals(v1, v2));
    }
}
