package net.makingrobot.ros.message;

import com.jilk.ros.message.Message;
import com.jilk.ros.message.MessageType;

@MessageType(string = "geometry_msgs/Point")
public class Point extends Message {

	public double x;
	public double y;
	public double z;
	
}
