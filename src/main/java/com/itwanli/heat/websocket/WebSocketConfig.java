package com.itwanli.heat.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/*webSocket的配置类, 开启对webSocket的支持*/


@Configuration
public class WebSocketConfig {
	    /*@Bean
		这个是为了解决在有些情况下出现serverEndpointExporter为null的情况（StackOverflow上看到的）
	    public ServletContextAware endpointExporterInitializer(final ApplicationContext applicationContext) {
	        return new ServletContextAware() {

	            @Override
	            public void setServletContext(ServletContext servletContext) {
	                ServerEndpointExporter serverEndpointExporter = new ServerEndpointExporter();
	                serverEndpointExporter.setApplicationContext(applicationContext);
	                try {
	                    serverEndpointExporter.afterPropertiesSet();
	                } catch (Exception e) {
	                    throw new RuntimeException(e);
	                }
	            }
	        };
	    }*/

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

}
