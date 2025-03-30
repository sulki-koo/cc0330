package cookcloud.pubsub;

import org.springframework.context.annotation.Configuration;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.websocket.server.ServerContainer;
import jakarta.websocket.server.ServerEndpointConfig;

@Configuration
public class WebSocketInitializer implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		Object serverContainerObj = servletContext.getAttribute("jakarta.websocket.server.ServerContainer");

		if (serverContainerObj instanceof ServerContainer) {
			ServerContainer serverContainer = (ServerContainer) serverContainerObj;
			try {
				serverContainer.addEndpoint(ServerEndpointConfig.Builder.create(PubSubServer.class, "/pubsub").build());
				System.out.println("[WebSocket] ✅ PubSubServer successfully registered.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("[WebSocket] ❌ ServerContainer is NOT available. WebSocket initialization failed.");
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("[WebSocket] Context destroyed.");
	}
}
