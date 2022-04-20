package com.yrnet.viewweb;

import junit.framework.TestCase;
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
