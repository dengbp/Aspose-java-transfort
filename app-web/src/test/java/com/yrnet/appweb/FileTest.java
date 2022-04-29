package com.yrnet.appweb;

import junit.framework.TestCase;

import java.io.File;

/**
 * @author dengbp
 * @ClassName FileTest
 * @Description TODO
 * @date 4/28/22 5:00 PM
 */
public class FileTest extends TestCase {

    public void testFile(){
        File file = new File("/Users/dengbp/Downloads/交接资源(汪三丁院红).xlsx");
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getPath());
        System.out.println(file.getAbsoluteFile());
        System.out.println(file.getName());
    }
}
