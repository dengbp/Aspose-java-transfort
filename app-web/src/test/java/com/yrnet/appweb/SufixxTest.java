package com.yrnet.appweb;

import junit.framework.TestCase;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author dengbp
 * @ClassName SufixxTest
 * @Description TODO
 * @date 4/19/22 3:47 PM
 */
public class SufixxTest extends TestCase{

    public void testSuf(){


        String fileName = "report.pdf";
        System.out.println(fileName.substring(fileName.lastIndexOf(".")));
    }

}
