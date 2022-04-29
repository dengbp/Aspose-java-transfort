package com.yrnet.appweb;

import junit.framework.TestCase;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author dengbp
 * @ClassName StringToList
 * @Description TODO
 * @date 4/28/22 4:19 PM
 */
public class StringToList extends TestCase {

    public void testSuf(){
        String dd = "1,32,33";
        List<String> d = Stream.of(dd.split(",")).collect(Collectors.toList());
        d.forEach(e-> System.out.println(e));
    }

    public void testSclip(){
        String dd = "/Users/dengbp/Documents/work/love-corner-workspace/document/view-web/target/report.docx";

        System.out.println(dd.substring(dd.lastIndexOf(".")));

    }
}
