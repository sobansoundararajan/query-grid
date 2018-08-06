/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import grid.DataTypes;
import query.control.*;
import query.model.*;
import grid.DateFormat;
import grid.Grid;
import query.exception.QueriedException;
import grid.StringValue;
import grid.Value;
import java.io.*;
import java.text.ParseException;
import java.util.*;

/**
 *
 * @author admin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    private static LinkedList<DateFormat> dfList = new LinkedList();
    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, Condition> conditions = new LinkedHashMap();

    static {
        conditions.put("equals", Condition.EQUALS);
        conditions.put("does not equals", Condition.DOESNOTEQUALS);
        conditions.put("greater than", Condition.GREATERTHAN);
        conditions.put("greater than or equal to", Condition.GREATERTHANOREQUALTO);
        conditions.put("less than", Condition.LESSTHAN);
        conditions.put("less than or equal to", Condition.LESSTHANOREQUALTO);
        conditions.put("begines with", Condition.BEGINESWITH);
        conditions.put("does not begines with", Condition.DOESNOTBEGINWITH);
        conditions.put("ends with", Condition.ENDSWITH);
        conditions.put("does not ends with", Condition.DOESNOTENDSWITH);
        conditions.put("contains", Condition.CONTAINS);
        conditions.put("does not contains", Condition.DOESNOTCONTAINS);
        conditions.put("matches", Condition.MATCHES);
        conditions.put("does not matches", Condition.DOESNOTMATCHES);
    }

    private static void print(QueriedResult queriedResult) {
        if (queriedResult.getNextAction().isEmpty()) {
            if (queriedResult.getValue() != null) {
                for (Value value : queriedResult.getValue()) {

                    System.out.print(value.getValue() + " ");
                }
            }
            System.out.println(queriedResult.getRow());
            Map<FunctionCondition, Value> functionMap = queriedResult.getFunctionMap();
            if (!functionMap.isEmpty()) {
                for (Map.Entry<FunctionCondition, Value> entry : functionMap.entrySet()) {
                    System.out.println(entry.getKey().getFunction() + " " + entry.getKey().getCol() + " " + entry.getValue().getValue());
                }
            }
        } else {
            if (queriedResult.getValue() != null) {
                for (Value value : queriedResult.getValue()) {

                    System.out.print(value.getValue() + " ");
                }
            }
            Map<FunctionCondition, Value> functionMap = queriedResult.getFunctionMap();
            if (!functionMap.isEmpty()) {
                for (Map.Entry<FunctionCondition, Value> entry : functionMap.entrySet()) {
                    System.out.println(entry.getKey().getFunction() + " " + entry.getKey().getCol() + " " + entry.getValue().getValue());
                }
            }
            for (QueriedResult vn : queriedResult.getNextAction()) {
                Main.print(vn);
            }
        }
    }

    private static void filter(Grid grid, QueriedRange range) throws Exception {
        Collection<Filter> filterConditionList = new ArrayList();
        int op = 1;
        while (op != 0) {
            System.out.println("Enter the col Number");
            int col = scanner.nextInt();
            scanner.nextLine();
            for (String str : conditions.keySet()) {
                System.out.println(str);
            }
            String condition = scanner.nextLine().toLowerCase();
            String value = scanner.nextLine();
            Filter c = new Filter(col, conditions.get(condition), value);
            filterConditionList.add(c);
            System.out.println("0-Finish");
            op = scanner.nextInt();
            scanner.nextLine();
        }
        FilterAction filterAction = new FilterAction(filterConditionList);
        filterAction.execute(grid, range);
    }

    private static void groupBy(Grid grid, QueriedRange range) throws Exception {
        List<GroupByCondition> groupByConditionList = new LinkedList();
        int op = 1;
        while (op != 0) {
            GroupByCriteria groupByCriteria = null;
            System.out.println("Enter col:");
            int col = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Select \nNORMAL\nMONTH\nWEEKDAYS\nYEAR\nQUTERLY\nRANGE");
            String criteria = scanner.nextLine();
            switch (criteria) {
                case "NORMAL": {
                    groupByCriteria = new Normal();
                    break;
                }
                case "MONTH": {
                    groupByCriteria = new Month();
                    break;
                }
                case "WEEKDAYS": {
                    groupByCriteria = new WeekDays();
                    break;
                }
                case "QUTERLY": {
                    groupByCriteria = new Quterly();
                    break;
                }
                case "RANGE": {
                    List<Double> rangeList = new ArrayList();
                    System.out.print("Enter The Ranges : ");
                    String str = scanner.nextLine();
                    String[] strArr = str.split(" ");
                    for (int i = 0; i < strArr.length; i++) {
                        rangeList.add(Double.valueOf(strArr[i]));
                    }
                    groupByCriteria = new Range(rangeList);
                    break;
                }
            }
            if (groupByCriteria == null) {
                throw new QueriedException("GroupBy Criteria Not Set");
            }
            groupByConditionList.add(new GroupByCondition(groupByCriteria, col));
            System.out.println("0-Finish");
            op = scanner.nextInt();
            scanner.nextLine();
        }
        GroupByAction groupByAction = new GroupByAction(groupByConditionList);
        groupByAction.execute(grid, range);
    }

    private static void sort(Grid grid, QueriedRange range) throws Exception {
        System.out.println("Enter the cols:");
        LinkedHashMap<Integer, SortingCriteria> sortingConditionMap = new LinkedHashMap();
        int op = 1;
        while (op != 0) {
            System.out.println("Sort ASCENDING/DESCENDING");
            SortingCriteria ascOrDec = SortingCriteria.valueOf(scanner.next());
            int col = scanner.nextInt();
            scanner.nextLine();
            sortingConditionMap.put(col, ascOrDec);
            System.out.println("0-finish");
            op = scanner.nextInt();
            scanner.nextLine();
        }
        Sort sortingCondition = new Sort(sortingConditionMap);
        SortAction sortAction = new SortAction(sortingCondition);
        sortAction.execute(grid, range);

    }

    private static void function(Grid grid, QueriedRange range) throws Exception {
        int op = 1;
        while (op != 0) {
            System.out.println("Select Functions\nSUM\nAVERAGE\nMAXIMUM\nMINIMUM\nCOUNT");
            Function function = Function.valueOf(scanner.next());
            System.out.println("Enter col");
            int col = scanner.nextInt();
            FunctionCondition functionCondition = new FunctionCondition(col, function);
            range.getQueriedResult().getFunctionMap().put(functionCondition, new StringValue(DataTypes.String, ""));
            System.out.println("0-Finish");
            op = scanner.nextInt();
            scanner.nextLine();
        }
        FunctionAction.excute(grid, range);
    }

    private static void filterOnFunctions(Grid grid, QueriedRange range) throws Exception {
        System.out.println("Maximum Level is :" + range.getMaxLevel() + "\nEnter the Level :");
        int level = scanner.nextInt();
        scanner.nextLine();
        int op = 1;
        Map<Integer, List<FunctionFilter>> filterOnFunctionsConditionList = new HashMap();
        while (op != 0) {
            System.out.println("Select Functions\nSUM\nAVERAGE\nMAXIMUM\nMINIMUM\nCOUNT");
            Function function = Function.valueOf(scanner.next());
            System.out.println("Enter col");
            int col = scanner.nextInt();
            scanner.nextLine();
            FunctionCondition functionCondition = new FunctionCondition(col, function);
            for (String str : conditions.keySet()) {
                System.out.println(str);
                if (str.equals("less than or equal to")) {
                    break;
                }
            }
            String condition = scanner.nextLine().toLowerCase();
            String value = scanner.nextLine();
            FunctionFilter filterOnFunctionCondition = new FunctionFilter(functionCondition, conditions.get(condition), value);
            filterOnFunctionsConditionList.computeIfAbsent(level, (k) -> new LinkedList()).add(filterOnFunctionCondition);
            System.out.println("0-Finish");
            op = scanner.nextInt();
            scanner.nextLine();
            FunctionFilterAction filterOnFunctionsAction = new FunctionFilterAction(filterOnFunctionsConditionList);
            filterOnFunctionsAction.execute(grid, range);
        }
    }

    private static void sortOnFunctions(QueriedRange range) throws QueriedException {
        System.out.println("Maximum Level is :" + range.getMaxLevel() + "\nEnter the Level :");
        int level = scanner.nextInt();
        scanner.nextLine();
        int op = 1;
        Map<Integer, List<FunctionSort>> functionConditionMap = new HashMap();
        while (op != 0) {
            System.out.println("Sort ASCENDING/DESCENDING");
            SortingCriteria ascOrDec = SortingCriteria.valueOf(scanner.next());
            System.out.println("Select Functions\nSUM\nAVERAGE\nMAXIMUM\nMINIMUM\nCOUNT");
            Function function = Function.valueOf(scanner.next());
            System.out.println("Enter col");
            int col = scanner.nextInt();
            scanner.nextLine();
            FunctionCondition functionCondition = new FunctionCondition(col, function);
            functionConditionMap.computeIfAbsent(level, (k) -> new LinkedList()).add(new FunctionSort(functionCondition, ascOrDec));
            System.out.println("0-Finish");
            op = scanner.nextInt();
            scanner.nextLine();
        }
        FunctionSortAction functionSortAction = new FunctionSortAction(functionConditionMap);
        functionSortAction.execute(range);
    }

    private static void reEvaluate(Grid grid, QueriedRange range) throws Exception {
        RefershResult refershResult = new RefershResult();
        refershResult.execute(range, grid);
    }

    private static void reset(QueriedRange range) {
        range.setQueriedResult(null);
    }

    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException, Exception {
        // TODO code application logic here
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\admin\\Desktop\\CSVinputs2.csv"));
        dfList.add(DateFormat.d);
        dfList.add(DateFormat.m);
        dfList.add(DateFormat.y);
        String line;
        Grid grid = new Grid();
        int row = 0;
        while ((line = br.readLine()) != null) {
            Value v;
            line = line.substring(1, line.length() - 1);
            String[] in = line.split("\",\"");
            for (int i = 0; i < in.length; i++) {
                v = ValueParser.TypeandValue(in[i], dfList);
                grid.set(row, i, v);
            }

            row++;
        }

        System.out.println("Enter the range");
        int startRow, endRow, startCol, endCol;
        startRow = scanner.nextInt();
        endRow = scanner.nextInt();
        startCol = scanner.nextInt();
        endCol = scanner.nextInt();
        QueriedRange range = new QueriedRange(startRow, endRow, startCol, endCol);
        Main.print(range.getQueriedResult());
        int op = 4;
        while (op != 0) {
            System.out.println("1-Filter\n2-GroupBy\n3-Sort\n4-Function\n5-Filter On Functions\n6-Sort on Functions\n7-ReEvaluate\n8-Reset(to NULL)\n0-Exit");
            op = scanner.nextInt();
            scanner.nextLine();
            if (op == 0) {
                break;
            } else if (op == 1) {
                Main.filter(grid, range);
            } else if (op == 2) {
                Main.groupBy(grid, range);
            } else if (op == 3) {
                Main.sort(grid, range);
            } else if (op == 4) {
                Main.function(grid, range);
            } else if (op == 5) {
                Main.filterOnFunctions(grid, range);
            } else if (op == 6) {
                Main.sortOnFunctions(range);
            } else if (op == 7) {
                Main.reEvaluate(grid, range);
            } else if (op == 8) {
                Main.reset(range);
            }
            Main.print(range.getQueriedResult());
        }

    }
}
