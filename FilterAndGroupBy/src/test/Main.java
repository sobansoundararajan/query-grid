/*
 * To change this license header, choose License Headers in Project Proptionerties.
 * To change this template file, choose Tools | Templates
 * and optionen the template in the editor.
 */
package test;

import query.formula.Normal;
import query.formula.Formula;
import query.formula.Range;
import query.formula.WeekDays;
import query.formula.Quarterly;
import query.control.*;
import query.model.*;
import grid.DateFormat;
import grid.Grid;
import query.exception.QueriedException;
import grid.Value;
import java.io.*;
import java.text.ParseException;
import java.util.*;
import query.formula.Average;
import query.formula.Count;
import query.formula.Maximum;
import query.formula.Minimum;
import query.formula.Sum;
import query.formula.Year;

/**
 *
 * @author admin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    private static LinkedList<DateFormat> dateFormat = new LinkedList();
    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, ConditionOperator> conditions = new LinkedHashMap();
    private static Map<String, Formula> formulaMap = new LinkedHashMap();

    static {
        conditions.put("equals", ConditionOperator.EQUALS);
        conditions.put("does not equals", ConditionOperator.DOESNOTEQUALS);
        conditions.put("greater than", ConditionOperator.GREATERTHAN);
        conditions.put("greater than or equal to", ConditionOperator.GREATERTHANOREQUALTO);
        conditions.put("less than", ConditionOperator.LESSTHAN);
        conditions.put("less than or equal to", ConditionOperator.LESSTHANOREQUALTO);
        conditions.put("begines with", ConditionOperator.BEGINESWITH);
        conditions.put("does not begines with", ConditionOperator.DOESNOTBEGINWITH);
        conditions.put("ends with", ConditionOperator.ENDSWITH);
        conditions.put("does not ends with", ConditionOperator.DOESNOTENDSWITH);
        conditions.put("contains", ConditionOperator.CONTAINS);
        conditions.put("does not contains", ConditionOperator.DOESNOTCONTAINS);
        conditions.put("matches", ConditionOperator.MATCHES);
        conditions.put("does not matches", ConditionOperator.DOESNOTMATCHES);
        formulaMap.put("SUM", new Sum());
        formulaMap.put("AVERAGE", new Average());
        formulaMap.put("COUNT", new Count());
        formulaMap.put("Maximum", new Maximum());
        formulaMap.put("Minimum", new Minimum());
        formulaMap.put("NORMAL", new Normal());
        formulaMap.put("QUARTERLY", new Quarterly());
        formulaMap.put("WEKDAYS", new WeekDays());
        formulaMap.put("YEAR", new Year());
    }

    private static void print(Grid grid,QueriedRange range,QueriedResult queriedResult) {
        if (queriedResult.getNextAction().isEmpty()) {
            if (queriedResult.getValue() != null) {
                for (Value value : queriedResult.getValue()) {

                    System.out.print(value.getValue() + " ");
                }
            }
            System.out.println(queriedResult.getRow());
            for(ColumnFormula columnFormula:range.getColumnFormulaList())
            {
                Value value=queriedResult.evaluatedFormula(grid, range.getStartRow(), range.getStartCol(), columnFormula);
                System.out.println(columnFormula.getFormula().toString()+" "+value.getValue());
            }
        } else {
            if (queriedResult.getValue() != null) {
                for (Value value : queriedResult.getValue()) {

                    System.out.print(value.getValue() + " ");
                }
            }
            for(ColumnFormula columnFormula:range.getColumnFormulaList())
            {
                Value value=queriedResult.evaluatedFormula(grid, range.getStartRow(), range.getStartCol(), columnFormula);
                System.out.println(columnFormula.getFormula().toString()+" "+value.getValue());
            }
            for (QueriedResult chiledNode : queriedResult.getNextAction()) {
                Main.print(grid,range,chiledNode);
            }
        }
    }

    private static void filter(Grid grid, QueriedRange range) throws Exception {
        Collection<FilterOnRecords> filterConditionList = new ArrayList();
        int option = 1;
        while (option != 0) {
            Formula formula;
            System.out.println("Select \nNORMAL\nMONTH\nWEEKDAYS\nYEAR\nQUARTERLY\nRANGE");
            String function = scanner.nextLine();
            if (function.equalsIgnoreCase("Range")) {
                List<Double> rangeList = new ArrayList();
                System.out.print("Enter The Ranges : ");
                String str = scanner.nextLine();
                String[] strArr = str.split(" ");
                for (int i = 0; i < strArr.length; i++) {
                    rangeList.add(Double.valueOf(strArr[i]));
                }
                formula = new Range(rangeList);
            } else {
                formula = formulaMap.get(function);
            }
            System.out.println("Enter the col Number");
            int col = scanner.nextInt();
            scanner.nextLine();
            for (String str : conditions.keySet()) {
                System.out.println(str);
            }
            String condition = scanner.nextLine().toLowerCase();
            String value = scanner.nextLine();
            FilterOnRecords filterOnRecords = new FilterOnRecords(formula, col, conditions.get(condition), value);
            filterConditionList.add(filterOnRecords);
            System.out.println("0-Finish");
            option = scanner.nextInt();
            scanner.nextLine();
        }
        FilterOnRecordsAction filterAction = new FilterOnRecordsAction(filterConditionList);
        filterAction.execute(grid, range);
    }

    private static void groupBy(Grid grid, QueriedRange range) throws Exception {
        List<ColumnFormula> groupByConditionList = new LinkedList();
        int option = 1;
        while (option != 0) {
            Formula formula;
            System.out.println("Enter col:");
            int col = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Select \nNORMAL\nMONTH\nWEEKDAYS\nYEAR\nQUARTERLY\nRANGE");
            String function = scanner.nextLine();
            if (function.equalsIgnoreCase("Range")) {
                List<Double> rangeList = new ArrayList();
                System.out.print("Enter The Ranges : ");
                String str = scanner.nextLine();
                String[] strArr = str.split(" ");
                for (int i = 0; i < strArr.length; i++) {
                    rangeList.add(Double.valueOf(strArr[i]));
                }
                formula = new Range(rangeList);
            } else {
                formula = formulaMap.get(function);
            }
            groupByConditionList.add(new ColumnFormula(col, formula));
            System.out.println("0-Finish");
            option = scanner.nextInt();
            scanner.nextLine();
        }
        GroupByAction groupByAction = new GroupByAction(groupByConditionList);
        groupByAction.execute(grid, range);
    }

    private static void sort(Grid grid, QueriedRange range) throws Exception {
        System.out.println("Enter the cols:");
        LinkedHashMap<Integer, SortingCriteria> sortingConditionMap = new LinkedHashMap();
        int option = 1;
        while (option != 0) {
            System.out.println("Sort ASCENDING/DESCENDING");
            SortingCriteria ascOrDec = SortingCriteria.valueOf(scanner.next());
            int col = scanner.nextInt();
            scanner.nextLine();
            sortingConditionMap.put(col, ascOrDec);
            System.out.println("0-finish");
            option = scanner.nextInt();
            scanner.nextLine();
        }
        SortOnRecords sortingCondition = new SortOnRecords(sortingConditionMap);
        SortOnRecordsAction sortAction = new SortOnRecordsAction(sortingCondition);
        sortAction.execute(grid, range);

    }

    private static void function(QueriedRange range) throws Exception {
        int option = 1;
        List<ColumnFormula> conditionOnGroupList = new LinkedList();
        while (option != 0) {
            System.out.println("Select Functions\nSUM\nAVERAGE\nMAXIMUM\nMINIMUM\nCOUNT\nNORMAL\nMONTH\nWEEKDAYS\nYEAR\nQUARTERLY\nRANGE");
            String function = scanner.next();
            Formula formula;
            System.out.println("Enter col");
            int col = scanner.nextInt();
            scanner.nextLine();
            if (function.equalsIgnoreCase("Range")) {
                List<Double> rangeList = new ArrayList();
                System.out.print("Enter The Ranges : ");
                String str = scanner.nextLine();
                String[] strArr = str.split(" ");
                for (int i = 0; i < strArr.length; i++) {
                    rangeList.add(Double.valueOf(strArr[i]));
                }
                formula = new Range(rangeList);
            } else {
                formula = formulaMap.get(function);
            }
            ColumnFormula functionCondition = new ColumnFormula(col, formula);
            conditionOnGroupList.add(functionCondition);
            System.out.println("0-Finish");
            option = scanner.nextInt();
            scanner.nextLine();
        }
        range.getColumnFormulaList().addAll(conditionOnGroupList);
    }

    private static void filterOnFunctions(Grid grid, QueriedRange range) throws Exception {
        System.out.println("Maximum Level is :" + range.getMaxLevel() + "\nEnter the Level :");
        int level = scanner.nextInt();
        scanner.nextLine();
        int option = 1;
        Map<Integer, List<FilterOnGroups>> filterOnFunctionsConditionList = new HashMap();
        while (option != 0) {
            System.out.println("Select Functions\nSUM\nAVERAGE\nMAXIMUM\nMINIMUM\nCOUNT");
            String function = scanner.next();
            System.out.println("Enter col");
            int col = scanner.nextInt();
            scanner.nextLine();
            ColumnFormula functionCondition = new ColumnFormula(col, formulaMap.get(function));
            for (String str : conditions.keySet()) {
                System.out.println(str);
                if (str.equals("less than or equal to")) {
                    break;
                }
            }
            String condition = scanner.nextLine().toLowerCase();
            String value = scanner.nextLine();
            FilterOnGroups filterOnFunctionCondition = new FilterOnGroups(functionCondition, conditions.get(condition), value);
            filterOnFunctionsConditionList.computeIfAbsent(level, (k) -> new LinkedList()).add(filterOnFunctionCondition);
            System.out.println("0-Finish");
            option = scanner.nextInt();
            scanner.nextLine();
            FilterOnGroupsAction filterOnFunctionsAction = new FilterOnGroupsAction(filterOnFunctionsConditionList);
            filterOnFunctionsAction.execute(grid, range);
        }
    }

    private static void sortOnFunctions(Grid grid,QueriedRange range) throws QueriedException {
        System.out.println("Maximum Level is :" + range.getMaxLevel() + "\nEnter the Level :");
        int level = scanner.nextInt();
        scanner.nextLine();
        int option = 1;
        Map<Integer, List<SortOnGroups>> functionConditionMap = new HashMap();
        while (option != 0) {
            System.out.println("Sort ASCENDING/DESCENDING");
            SortingCriteria ascOrDec = SortingCriteria.valueOf(scanner.next());
            System.out.println("Select Functions\nSUM\nAVERAGE\nMAXIMUM\nMINIMUM\nCOUNT");
            String function = scanner.next();
            System.out.println("Enter col");
            int col = scanner.nextInt();
            scanner.nextLine();
            ColumnFormula functionCondition = new ColumnFormula(col, formulaMap.get(function));
            functionConditionMap.computeIfAbsent(level, (k) -> new LinkedList()).add(new SortOnGroups(functionCondition, ascOrDec));
            System.out.println("0-Finish");
            option = scanner.nextInt();
            scanner.nextLine();
        }
        SortOnGroupsAction functionSortAction = new SortOnGroupsAction(functionConditionMap);
        functionSortAction.execute(grid,range);
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
        dateFormat.add(DateFormat.d);
        dateFormat.add(DateFormat.m);
        dateFormat.add(DateFormat.y);
        String line;
        Grid grid = new Grid();
        int row = 0;
        while ((line = br.readLine()) != null) {
            Value v;
            line = line.substring(1, line.length() - 1);
            String[] in = line.split("\",\"");
            for (int i = 0; i < in.length; i++) {
                v = ValueParser.TypeandValue(in[i], dateFormat);
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
        Main.print(grid,range,range.getQueriedResult());
        int option = 4;
        while (option != 0) {
            System.out.println("1-Filter\n2-GroupBy\n3-Sort\n4-Function\n5-Filter On Functions\n6-Sort on Functions\n7-ReEvaluate\n8-Reset(to NULL)\n0-Exit");
            option = scanner.nextInt();
            scanner.nextLine();
            if (option == 0) {
                break;
            } else if (option == 1) {
                Main.filter(grid, range);
            } else if (option == 2) {
                Main.groupBy(grid, range);
            } else if (option == 3) {
                Main.sort(grid, range);
            } else if (option == 4) {
                Main.function(range);
            } else if (option == 5) {
                Main.filterOnFunctions(grid, range);
            } else if (option == 6) {
                Main.sortOnFunctions(grid,range);
            } else if (option == 7) {
                Main.reEvaluate(grid, range);
            } else if (option == 8) {
                Main.reset(range);
            }
            Main.print(grid,range,range.getQueriedResult());
        }

    }
}
