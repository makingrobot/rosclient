package net.makingrobot.ros.message;

import com.jilk.ros.message.Message;
import com.jilk.ros.message.MessageType;

@MessageType(string = "geometry_msgs/Quaternion")
public class Quaternion extends Message {

	public double x;
	public double y;
	public double z;
	public double w;
	
}
