/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filterandgroupby;

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
    static Scanner s=new Scanner(System.in);
    public static List<List>inputs=new LinkedList<List>();
    public static String []conditions={"Equals","Does Not Equals","Greater Than","Greater Than or EqualTo","Less Than","Less Than or EqualTo","Begines With","Does Not Begin With","Ends with","Does Not End With","Contains","Does Not Contains","Matches","Does Not Matchs"};
    public static void print(Range r)
    {
       for(Set<Integer>p:r.getVisibleRows())
       {
           for(Integer row:p)
           {
               for(int i=r.sc;i<=r.ec;i++)
               {
                    Value v=(Value) inputs.get(row).get(i);
                    System.out.print(v.getValue()+" ");
               }
               System.out.println();
           }
       }
    }

    public static void filter(Range r)
    {
        Collection<Condition>conList=new LinkedList<Condition>();
        int op=1;
        while(op!=0)
        {
            System.out.println("Enter the col Number");
            int col=s.nextInt();
            for(int i=0;i<conditions.length;i++)
            {
                System.out.println(i+"-"+conditions[i]);
            }
            int con=s.nextInt();
            s.nextLine();
            String condition=s.nextLine();
            Condition c=new Condition(col+r.sc,con,condition);
            conList.add(c);
            System.out.println("0-Finish");
            op=s.nextInt();
            s.nextLine();
        }
        UtilityForFilter f=new UtilityForFilter();
        f.filter(r,conList);
    }
    public static void groupBy(Range r)
    {
        UtilityForGroupBy gb=new UtilityForGroupBy();
        System.out.print("Enter the col Number");
        String input=s.nextLine();
        System.out.print(input);
        String [] inArr=input.split(" ");
        Collection<Integer>colList=new LinkedList<Integer>();
        for(int i=0;i<inArr.length;i++)
            colList.add(Integer.valueOf(inArr[i])+r.sc);
        gb=new UtilityForGroupBy();
       GroupByResult g=gb.groupBy(r,colList);
       Map<List<Object>, VisibleRows>map=g.getGroupByMap();
       for(Map.Entry<List<Object>,VisibleRows>entry:map.entrySet())
       {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue().getVisibleRows());
       }
    }
    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
        // TODO code application logic here
         BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\admin\\Desktop\\CSVinputs.csv"));
  
         
         LinkedList<DateFormat>dfList=new LinkedList<DateFormat>();
		dfList.add(DateFormat.d);
		dfList.add(DateFormat.m);
		dfList.add(DateFormat.y);
         String line;
         
         while((line=br.readLine())!=null)
         {
             Value v;
             line=line.substring(1,line.length()-1);
             String []in=line.split("\",\"");
             List<Value>row=new LinkedList<Value>();
             for(int i=0;i<in.length;i++)
             {
                v=Source.TypeandValue(in[i],dfList);
                row.add(v);
             }
             inputs.add(row);
         }
        
         System.out.println("Enter the range");
         int sr,er,sc,ec;
         sr=s.nextInt();
         er=s.nextInt();
         sc=s.nextInt();
         ec=s.nextInt();
         Range r=new Range(sr,er,sc,ec);
         Main.print(r);
         int op=4;
         while(op!=0)
         {
             System.out.println("1-Filter\n2-GroupBy\n0-Exit");
             op=s.nextInt();
             s.nextLine();
            if(op==1)
            {
                Main.filter(r);
            }
            else if(op==2)
            {
                Main.groupBy(r);
                
            }   
               Main.print(r);
         }
    }
    
}
