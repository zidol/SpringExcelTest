package com.test.excel.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Controller
public class FaceBookAPI {
	@Bean
    MappingJackson2JsonView jsonView(){
        return new MappingJackson2JsonView();
    }

	    @RequestMapping(value="/excel/facebookPageCrawling.do", method=RequestMethod.POST)  
	    public ModelAndView facebookPageCrawling(HttpServletRequest request, HttpServletResponse response) throws Exception {        
	        String token = "EAAG2MQ9FnZAcBAJ3ylxZAkxgaQM1GCcWlDVZCkkoPnvZCYEl6acs6GewJ8r3qltZAHRQkZCmAZCxePybEAvgVRrZBnZB7YTOeiG1GnNwHSQdRgrwb4WUDPc4UZBrBgDIWPKcV4L8S3cuBUhgNNl8agwO8Wk7y8yLNHbs82XaotHJumFrsqIfLpIE8DCA1QuEejNEMZD";
	        String user_id = "2407624505968991";
	        URL url = new URL("https://graph.facebook.com/" + user_id + "?fields=posts%7Bcreated_time%2Cmessage%2Cpicture%2Cpermalink_url%7D&access_token=" +  token); 
	        HttpsURLConnection con = (HttpsURLConnection) url.openConnection(); 
	 
	        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream())); 
	        String inputLine; 
	        StringBuffer sb = new StringBuffer(); 
	        
	        while ((inputLine = in.readLine()) != null) { 
	            sb.append(inputLine);
	        } 
	        in.close(); 
	        String result = sb.toString();
	        ModelAndView mv = new ModelAndView("jsonView");
	 
	        mv.addObject("model", result);
	        
	        return mv;
	    }

}
