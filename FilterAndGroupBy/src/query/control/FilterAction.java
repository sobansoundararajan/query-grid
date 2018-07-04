/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.control;

import query.model.ConditionsList;
import grid.Grid;
import grid.Value;
import query.model.Condition;
import query.model.QueriedRange;
import query.model.Filter;
import query.model.FilterResult;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import query.model.VisibleRows;

/**
 *
 * @author admin
 */
public class FilterAction {

    static Map<ConditionsList, BiFunction<Value, String, Boolean>> conditionMap = new EnumMap<ConditionsList, BiFunction<Value, String, Boolean>>(ConditionsList.class);

    public void filter(Grid grid, QueriedRange range, Collection<Condition> conList) {
        Filter f = new Filter(conList);
        Set<Set<Integer>> input = range.getVisibleRows();

        Set<Set<Integer>> vr = new HashSet();
        for (Set<Integer> rowList : input) {
            Set<Integer> temp = new HashSet();
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
            vr.add(temp);
        }
        VisibleRows visibleRows = new VisibleRows(vr);
        FilterResult filterResult = new FilterResult(visibleRows);
        f.setFilterResult(filterResult);
        range.addResult(f);
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
