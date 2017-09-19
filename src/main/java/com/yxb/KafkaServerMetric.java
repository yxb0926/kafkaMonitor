package com.yxb;


/**
 * Created by yxb on 2017/9/19.
 */
public class KafkaServerMetric {
    private String ipAndPort;
    private JmxConnection jmxConn;

    public KafkaServerMetric(String ipAndPort){
        this.ipAndPort = ipAndPort;
        this.jmxConn   = new JmxConnection(this.ipAndPort);

        this.jmxConn.init();
    }


    public Object getMessagesInPerSec(String topic, String objAttr){
        String objNameStr = getMessagesInPerSecObjectName(topic);
        Object val        = jmxConn.getObjectValues(objNameStr, objAttr);

        return val;
    }


    public Object getMessagesInPerSec(String objAttr){
        String objNameStr = getMessagesInPerSecObjectName();
        Object val        = jmxConn.getObjectValues(objNameStr, objAttr);

        return val;
    }

    public Object getByteInPerSec(String topic, String objAttr){
        String objNameStr = getBytesInPerSecStrObjectName(topic);
        Object val        = jmxConn.getObjectValues(objNameStr, objAttr);

        return val;
    }

    public Object getByteInPerSec(String objAttr){
        String objNameStr = getBytesInPerSecStrObjectName();
        Object val        = jmxConn.getObjectValues(objNameStr, objAttr);

        return val;
    }

    public Object getByteOutPerSec(String topic, String objAttr){
        String objNameStr = getBytesOutPerSecStrObjectName(topic);
        Object val        = jmxConn.getObjectValues(objNameStr, objAttr);

        return val;
    }

    public Object getByteOutPerSec(String objAttr){
        String objNameStr = getBytesOutPerSecStrObjectName();
        Object val        = jmxConn.getObjectValues(objNameStr, objAttr);

        return val;
    }


    private String getMessagesInPerSecObjectName (String topicName){
        String objectNameStr = "kafka.server:type=BrokerTopicMetrics," +
                "name=MessagesInPerSec,topic=" + topicName;

        return objectNameStr;
    }

    private String getMessagesInPerSecObjectName (){
        String objectNameStr = "kafka.server:type=BrokerTopicMetrics," +
                "name=MessagesInPerSec";

        return objectNameStr;
    }

    private String getBytesInPerSecStrObjectName (String topicName){
        String objectNameStr = "kafka.server:type=BrokerTopicMetrics," +
                "name=BytesInPerSec,topic=" + topicName;

        return objectNameStr;
    }

    private String getBytesInPerSecStrObjectName(){
        String objectNameStr = "kafka.server:type=BrokerTopicMetrics," +
                "name=BytesInPerSec";

        return objectNameStr;
    }

    private String getBytesOutPerSecStrObjectName (String topicName){
        String objectNameStr = "kafka.server:type=BrokerTopicMetrics," +
                "name=BytesOutPerSec,topic=" + topicName;

        return objectNameStr;
    }

    private String getBytesOutPerSecStrObjectName(){
        String objectNameStr = "kafka.server:type=BrokerTopicMetrics," +
                "name=BytesOutPerSec";

        return objectNameStr;
    }
}
