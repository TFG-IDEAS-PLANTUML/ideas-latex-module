/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.us.isa.ideas.controller.latex;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 *
 * @author japarejo
 */
public class LatexCompilerTest {
    
    public LatexCompilerTest() {
    }
    

    /**
     * Test of compile method, of class LatexCompiler.
     */
    @Test
    public void testCompile_3args() throws Exception {
        System.out.println("compile");        
        
        File f=new File("src/main/resources/testfiles");
        File outputFolder=new File(f.getAbsolutePath()+"/latexOutput");
        FileUtils.deleteDirectory(outputFolder);
        outputFolder.mkdir();
        LatexCompiler instance = new LatexCompiler();
        LatexCompilationResult expResult = null;
        LatexCompilationResult result = instance.compile("SimpleLatexDocument.tex","", f.getAbsolutePath(), outputFolder.getAbsolutePath());
        assertNotNull(result);        
        assertTrue(result.getOutputFiles().size()>=0);
        System.out.println("===================================================");
        System.out.println("SALIDA:"+result.getOutput());
        System.out.println("===================================================");
        System.out.println("ERRORES:"+result.getErrors());
        System.out.println("===================================================");
        System.out.println("FICHEROS CREADOS:");
        for(String createdFile:result.getOutputFiles()){
            System.out.println("   "+createdFile);
        }
        System.out.println("===================================================");
        
    }
    
    
    @Test
    public void testCompile_IntermediateReport() throws Exception {
        System.out.println("compile");        
        
        File f=new File("src/main/resources/testfiles");
        File outputFolder=new File(f.getAbsolutePath()+"/latexOutput");
        FileUtils.deleteDirectory(outputFolder);
        outputFolder.mkdir();
        LatexCompiler instance = new LatexCompiler(true);
        LatexCompilationResult expResult = null;
        LatexCompilationResult result = instance.compile("IntermediateReport.tex","", f.getAbsolutePath(), outputFolder.getAbsolutePath());
        assertNotNull(result);        
        assertTrue(result.getOutputFiles().size()>=0);
        System.out.println("===================================================");
        System.out.println("SALIDA:"+result.getOutput());
        System.out.println("===================================================");
        System.out.println("ERRORES:"+result.getErrors());
        System.out.println("===================================================");
        System.out.println("FICHEROS CREADOS:");
        for(String createdFile:result.getOutputFiles()){
            System.out.println("   "+createdFile);
        }
        System.out.println("===================================================");
        
    }

    
    @Test
    public void canGetCompilerOutput() throws IOException{
        File f=new File("src/main/resources/testfiles/");
        File outputFolder=new File(f.getAbsolutePath()+"latexOutput");
        FileUtils.deleteDirectory(outputFolder);
        outputFolder.mkdir();
        LatexCompiler instance = new LatexCompiler();        
        LatexCompilationResult result = instance.compile("SimpleLatexDocument.tex","", f.getAbsolutePath(), outputFolder.getAbsolutePath());
        assertNotNull(result.getOutput());
        assertNotEquals("",result.getOutput());
    }
}
