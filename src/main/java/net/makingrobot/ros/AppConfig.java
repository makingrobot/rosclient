package net.makingrobot.ros;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

    @Value("${app.rosbridge.url}")
    private String rosBridgeUrl;

    public String getRosBridgeUrl() {
    	return rosBridgeUrl;
    }
    
}
