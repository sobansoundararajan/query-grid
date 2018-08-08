/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query.control;

import query.model.ConditionOperator;
import grid.Grid;
import grid.Value;
import query.model.*;
import java.util.*;
import java.util.function.BiFunction;

/**
 *
 * @author admin
 */
public class FilterOnRecordsAction {

    static Map<ConditionOperator, BiFunction<Value, String, Boolean>> conditionMap = new EnumMap<ConditionOperator, BiFunction<Value, String, Boolean>>(ConditionOperator.class);
    private final Collection<FilterOnRecords> filterOnRecordsList;

    public FilterOnRecordsAction(Collection<FilterOnRecords> filterOnRecordsList) {
        this.filterOnRecordsList = filterOnRecordsList;
    }

    public void execute(Grid grid, QueriedRange range) throws Exception {
        range.getFilterOnRecordsList().addAll(filterOnRecordsList);
        QueriedResult queriedResult = range.getQueriedResult();
        filterAction(grid, range, queriedResult, filterOnRecordsList);
        range.setQueriedResult(queriedResult);
    }

    private void filterAction(Grid grid, QueriedRange range, QueriedResult queriedResult, Collection<FilterOnRecords> filterOnRecordsList) {
        if (!queriedResult.getNextAction().isEmpty()) {
            List<QueriedResult> nodesToBeAdded = new LinkedList();
            for (QueriedResult nextOR : queriedResult.getNextAction()) {
                filterAction(grid, range, nextOR, filterOnRecordsList);
                if (!(nextOR.getNextAction().isEmpty() && nextOR.getRow().isEmpty())) {
                    nodesToBeAdded.add(nextOR);
                }
            }
            queriedResult.setNextAction(nodesToBeAdded);
            queriedResult.inValidateColumnFormula();
        } else {
            List<Integer> temp = new LinkedList();
            for (Integer row : queriedResult.getRow()) {
                boolean flag = true;
                for (FilterOnRecords filterOnRecords : filterOnRecordsList) {
                    Value value = grid.get(row + range.getStartRow(), filterOnRecords.getCol() + range.getStartCol());
                    BiFunction<Value, String, Boolean> test = conditionMap.get(filterOnRecords.getConditionOperator());
                    if (!test.apply(value, filterOnRecords.getValue())) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    temp.add(row);
                }
            }
            queriedResult.setRow(temp);
            queriedResult.inValidateColumnFormula();
        }
    }

    static {
        conditionMap.put(ConditionOperator.EQUALS, (v1, v2) -> Utility.equals(v1, v2));
        conditionMap.put(ConditionOperator.DOESNOTEQUALS, (v1, v2) -> !Utility.equals(v1, v2));
        conditionMap.put(ConditionOperator.GREATERTHAN, (v1, v2) -> Utility.greaterThan(v1, v2));
        conditionMap.put(ConditionOperator.GREATERTHANOREQUALTO, (v1, v2) -> !Utility.lessThan(v1, v2));
        conditionMap.put(ConditionOperator.LESSTHAN, (v1, v2) -> Utility.lessThan(v1, v2));
        conditionMap.put(ConditionOperator.LESSTHANOREQUALTO, (v1, v2) -> !Utility.greaterThan(v1, v2));
        conditionMap.put(ConditionOperator.BEGINESWITH, (v1, v2) -> Utility.beginsWith(v1, v2));
        conditionMap.put(ConditionOperator.DOESNOTBEGINWITH, (v1, v2) -> !Utility.beginsWith(v1, v2));
        conditionMap.put(ConditionOperator.ENDSWITH, (v1, v2) -> Utility.endsWith(v1, v2));
        conditionMap.put(ConditionOperator.DOESNOTENDSWITH, (v1, v2) -> !Utility.endsWith(v1, v2));
        conditionMap.put(ConditionOperator.CONTAINS, (v1, v2) -> Utility.contains(v1, v2));
        conditionMap.put(ConditionOperator.DOESNOTCONTAINS, (v1, v2) -> !Utility.contains(v1, v2));
        conditionMap.put(ConditionOperator.MATCHES, (v1, v2) -> Utility.equals(v1, v2));
        conditionMap.put(ConditionOperator.DOESNOTMATCHES, (v1, v2) -> !Utility.equals(v1, v2));
    }
}
