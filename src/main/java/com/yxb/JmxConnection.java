package com.yxb;


import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by yxb on 2017/9/18.
 */
public class JmxConnection {
    private String jmxURL;
    private String ipAndPort = "127.0.0.1:9999";
    private MBeanServerConnection conn;

    public JmxConnection(String ipAndPort){
        this.ipAndPort = ipAndPort;
    }

    public boolean init(){
        jmxURL = "service:jmx:rmi:///jndi/rmi://" +ipAndPort+ "/jmxrmi";
        System.out.println("Begin to connect:"+jmxURL);
        try {
            JMXServiceURL serviceURL = new JMXServiceURL(jmxURL);
            JMXConnector  connector  = JMXConnectorFactory.connect(serviceURL,null);
            conn = connector.getMBeanServerConnection();
            if(conn == null){
                System.out.println("Get Connection return null!");
                return  false;
            }
            System.out.println("Connection ok!");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Object getObjectValues(String objectName, String objAttr){
        ObjectName objName = null;
        try {
            objName = new ObjectName(objectName);
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
            return null;
        }

        Object val = getAttribute(objName, objAttr);

        return val;

    }


    private Object getAttribute(ObjectName objName, String objAttr){
        if(conn== null)
        {
            System.out.println("jmx connection is null!");
            return null;
        }

        try {
            return conn.getAttribute(objName,objAttr);
        } catch (MBeanException e) {
            e.printStackTrace();
            return null;
        } catch (AttributeNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (ReflectionException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
