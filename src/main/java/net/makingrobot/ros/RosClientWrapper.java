package net.makingrobot.ros;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jilk.ros.MessageHandler;
import com.jilk.ros.Service;
import com.jilk.ros.message.Log;
import com.jilk.ros.rosapi.message.Empty;
import com.jilk.ros.rosapi.message.GetTime;
import com.jilk.ros.rosbridge.ROSBridgeClient;

import lombok.extern.slf4j.Slf4j;
import net.makingrobot.ros.message.TurtlePose;

@Slf4j
@Component
public class RosClientWrapper {

	private ROSBridgeClient client;
	
	@Autowired
	private AppConfig config;
	
	private TurtlePose lastPost;
	
	@PostConstruct
    private void open() {
		ROSBridgeClient client = new ROSBridgeClient(config.getRosBridgeUrl());
        client.connect();
        
        try {
            testService(client);
        }
        catch (RuntimeException ex) {
            ex.printStackTrace();
        }
        
        com.jilk.ros.Topic<Log> logTopic = 
                new com.jilk.ros.Topic<Log>("/rosout", Log.class, client);
        
        logTopic.subscribe(new MessageHandler<Log>() {

			@Override
			public void onMessage(Log message) {
				log.info(message.msg);
			}
        	
        });
        
        com.jilk.ros.Topic<TurtlePose> poseTopic = 
                new com.jilk.ros.Topic<TurtlePose>("/turtle1/pose", TurtlePose.class, client);
        
        poseTopic.subscribe(new MessageHandler<TurtlePose>() {

			@Override
			public void onMessage(TurtlePose message) {
				if (!message.equals(lastPost)) {
					log.info(String.format("x=%.1f, y=%.1f, theta=%.1f, linear=%.1f, angular=%.1f", 
							message.x, message.y, message.theta, message.linear_velocity, message.angular_velocity));
					lastPost = message;
				}
			}
        	
        });
    }
	
	 public static void testService(ROSBridgeClient client) {
        try {
            Service<Empty, GetTime> timeService =
                    new Service<Empty, GetTime>("/rosapi/get_time", Empty.class, GetTime.class, client); 
            timeService.verify();
            System.out.println("Time (secs): " + timeService.callBlocking(new Empty()).time.secs);
            
            com.jilk.ros.Topic<Log> logTopic = 
                    new com.jilk.ros.Topic<Log>("/rosout", Log.class, client);
            logTopic.verify();
            
            System.out.println("Nodes");
            for (String s : client.getNodes())
                System.out.println("    " + s);
            
            System.out.println("-----------------");
            System.out.println("Topics");
            for (String s : client.getTopics()) {
                System.out.println("    " + s);
            }
            System.out.println("-----------------");
            
            System.out.println("Services");
            for (String s : client.getServices()) {
                System.out.println("    " + s);
            }
        }
        catch (InterruptedException ex) {
            System.out.println("Process was interrupted.");
        }
    }
 
	@PreDestroy
	private void close() {
		if (client != null) {
			client.disconnect();
		}
	}
}
