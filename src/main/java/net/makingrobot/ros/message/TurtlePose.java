package net.makingrobot.ros.message;

import com.jilk.ros.message.Message;
import com.jilk.ros.message.MessageType;

@MessageType(string = "turtlesim/Pose")
public class TurtlePose extends Message {

	public double x;
	public double y;
	public double theta;
	public double linear_velocity;
	public double angular_velocity;
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof TurtlePose))
			return false;
		
		TurtlePose other = (TurtlePose)obj;
		
		return Math.abs(x - other.x) < 0.1 && 
				Math.abs(y - other.y) < 0.1 && 
				Math.abs(theta - other.theta) < 0.1 && 
				Math.abs(linear_velocity - other.linear_velocity) < 0.1 && 
				Math.abs(angular_velocity - other.angular_velocity) < 0.1;
	}
}
