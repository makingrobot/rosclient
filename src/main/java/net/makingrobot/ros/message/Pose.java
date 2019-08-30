package net.makingrobot.ros.message;

import com.jilk.ros.message.Message;
import com.jilk.ros.message.MessageType;

@MessageType(string = "geometry_msgs/Pose")
public class Pose extends Message {
	
	public Point pointion;
	public Quaternion orientation;

}
