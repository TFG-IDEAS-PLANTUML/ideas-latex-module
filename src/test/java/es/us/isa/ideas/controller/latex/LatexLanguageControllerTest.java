package es.us.isa.ideas.controller.latex;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.nio.charset.Charset;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.us.isa.ideas.module.common.AppResponse;
import org.assertj.core.util.Files;
import org.checkerframework.checker.nullness.qual.AssertNonNullIfNonNull;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LatexLanguageControllerTest {
 
    @Autowired
    private WebApplicationContext context;    
	
	MockMvc mockMvc;
    static String latexDocument;
	static MockHttpSession session;
    
    @BeforeAll
    public static void loadDocument(){
        File file=new File("src/main/resources/testfiles/SimpleLatexDocument.tex");
        if(file.exists())
            latexDocument=Files.contentOf(file, Charset.defaultCharset());
    } 

	@BeforeEach
    public void setup() {
        if(mockMvc==null){
            mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
          .apply(SharedHttpSessionConfigurer.sharedHttpSession())
          .apply(SecurityMockMvcConfigurers.springSecurity())
          .build();
        }
        
    }

    @Test
    @Order(1) 
    public void successfullLatexCompilation() throws Exception{
        String filename="SimpleLatexDocument.tex";
        String fileUri="src/main/resources/testfiles/"+filename;
        MvcResult result = mockMvc.perform(
                    post("/language/operation/compileToPDF/execute")
                    .param("content",latexDocument)
                    .param("fileUri",fileUri))                
                .andExpect(
                    status().is(HttpStatus.OK.value()))
                .andReturn();
        session=(MockHttpSession) result.getRequest().getSession();
        String content = result.getResponse().getContentAsString();
        assertNotEquals(content, "");
        System.out.println(content);
        ObjectMapper om=new ObjectMapper();
        AppResponse response=om.readValue(content,AppResponse.class);
        assertNotNull(response);
        assertNotNull(response.getContext());
        assertNotEquals("",response.getContext());
        File f=new File(fileUri);
        File output=new File(response.getContext()+"/"+filename);
        if(f.exists())
            com.google.common.io.Files.copy(f, output);
    } 


    @Test
    @Order(2) 
    public void successfullPerformActualLatexCompilation() throws Exception
    {
        MvcResult result = mockMvc.perform(
                    post("/language/operation/doActualCompilation/execute")
                    .param("content",latexDocument)
                    .param("fileUri","myworkspace/myproject/SimpleLatexDocument.tex")
                    .session(session)        )
               .andExpect(
                    status().is(HttpStatus.OK.value()))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        assertNotEquals(content, "");
        System.out.println(content);
        
    }
}
