/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.us.isa.ideas.controller.latex;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author japarejo
 */
public class LatexCompilationResult {
        
    private String file;
    private String filePath;
    private String inputPath;
    private String outputPath;
    
    private String errors;
    private String output;
    private int exitCode;
    
    private List<String> outputFiles;
    
    private long duration;
    

    public LatexCompilationResult(String file, String filePath, String inputPath, String outputPath) {
        this.file = file;
        this.filePath=filePath;
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.duration=0;
        this.errors="";
        this.output="";
        this.outputFiles=Collections.EMPTY_LIST;
        this.exitCode=Integer.MIN_VALUE;
    }

    /**
     * @return the file
     */
    public String getFile() {
        return file;
    }

    /**
     * @return the inputPath
     */
    public String getInputPath() {
        return inputPath;
    }

    /**
     * @return the outputPath
     */
    public String getOutputPath() {
        return outputPath;
    }

    /**
     * @return the errors
     */
    public String getErrors() {
        return errors;
    }

    /**
     * @param errors the errors to set
     */
    public void setErrors(String errors) {
        this.errors = errors;
    }

    /**
     * @return the outputs
     */
    public String getOutput() {
        return output;
    }

    /**
     * @param outputs the outputs to set
     */
    public void setOutput(String output) {
        this.output = output;
    }

    /**
     * @return the outputFiles
     */
    public List<String> getOutputFiles() {
        return outputFiles;
    }

    /**
     * @param outputFiles the outputFiles to set
     */
    public void setOutputFiles(List<String> outputFiles) {
        this.outputFiles = outputFiles;
    }

    public int getExitCode() {
        return exitCode;
    }

    public void setExitCode(int exitCode) {
        this.exitCode = exitCode;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getDuration() {
        return duration;
    }
    
    
    public String generateHTMLMessage(){
        StringBuilder builder=new StringBuilder();
        String filename;
        builder.append("<b>Execution duration:</b>"+duration+" ms<br>\n");
        
        builder.append("<h3>Latex compilation output:</h3>\n");
        builder.append("<p><pre>"+output+"</pre></p>\n");
        
        builder.append("<h3>Errors found:</h3>\n");
        builder.append("<p><pre>"+errors+"</pre></p>\n");
        
        builder.append("<h3>Files generated:</h3>\n");
        builder.append("<ul>\n");
        for(String outpuFile:outputFiles){
            filename=outpuFile.substring(outpuFile.lastIndexOf("\\")+outpuFile.lastIndexOf("/")+2);           
            builder.append("   <li>");
            if(outpuFile.endsWith(".pdf"))
                builder.append("<a href='files/get/"+filePath+LatexLanguageController.OUTPUT_FOLDER+"/"+filename+"' target='_blank'><img src='js/dyntree/skin/../../../css/dyntree/skin/ePDF.png\'>"
                                +filename+
                                "</a>");
            else
                builder.append(filename);                        
            builder.append("</li>\n");
        }
        builder.append("</ul>\n");
        
        return builder.toString();
                
    }
    
}
