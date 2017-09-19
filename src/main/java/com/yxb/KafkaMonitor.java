package com.yxb;


import org.apache.commons.cli.*;

import static com.yxb.Utils.getNowDate;
import static java.lang.Thread.sleep;

/**
 * Created by yxb on 2017/9/18.
 */
public class KafkaMonitor {

    public static void printTopicStdout(String ipAndPort, String topic, Integer cnt) throws InterruptedException {
        KafkaServerMetric kafkaServerMetric = new KafkaServerMetric(ipAndPort);

        System.out.println("################################################");
        System.out.println("## Topic Name: " + topic );
        System.out.println("################################################");


        for (int i=0; i<cnt; i++){
            sleep(1000);
            Object countMsg  = kafkaServerMetric.getMessagesInPerSec(topic, "Count");
            Object oneMRate  = kafkaServerMetric.getMessagesInPerSec(topic,"OneMinuteRate");
            Object fiveMRate = kafkaServerMetric.getMessagesInPerSec(topic,"FiveMinuteRate");
            String nowTime   = getNowDate();

            Object countInByte     = (Long) kafkaServerMetric.getByteInPerSec(topic, "Count")/1000/1000 ;
            Object oneMRateInByte  = (Double) kafkaServerMetric.getByteInPerSec(topic, "OneMinuteRate")/1000;
            Object fiveMRateInByte = (Double) kafkaServerMetric.getByteInPerSec(topic, "FiveMinuteRate")/1000;

            Object countOutByte     = (Long) kafkaServerMetric.getByteOutPerSec(topic, "Count")/1000/1000;
            Object oneMRateOutByte  = (Double) kafkaServerMetric.getByteOutPerSec(topic, "OneMinuteRate")/1000;
            Object fiveMRateOutByte = (Double) kafkaServerMetric.getByteOutPerSec(topic, "FiveMinuteRate")/1000;


            String headStr = "+-----  time  -----+------ This Topic MPS --------+---------- Net In KB ----------+---------- Net Out KB ---------+\n";
            String titStr  = "|                  |--msgSum--|--msg1M--|--msg5M--|--InSum(MB)--|--In1M--|--In5M--|--InSum(MB)--|--In1M--|--In5M--|\n";
            String footStr = "+-------------------------------------------------+-------------------------------+-------------------------------+\n";

            if (i % 10 == 0){
                System.out.print(headStr);
                System.out.print(titStr);
                System.out.print(footStr);
            }

            System.out.printf("%-18s",nowTime);
            System.out.printf("%10d",countMsg);
            System.out.printf("%10.2f",oneMRate);
            System.out.printf("%10.2f",fiveMRate);

            System.out.printf("%14d", countInByte);
            System.out.printf("%9.2f", oneMRateInByte);
            System.out.printf("%9.2f", fiveMRateInByte);

            System.out.printf("%14d", countOutByte);
            System.out.printf("%9.2f", oneMRateOutByte);
            System.out.printf("%9.2f\n", fiveMRateOutByte);

        }
    }

    public static void printALLStdout(String ipAndPort, Integer cnt) throws InterruptedException {
        KafkaServerMetric kafkaServerMetric = new KafkaServerMetric(ipAndPort);
        System.out.println("################################################");
        System.out.println("## ALL Topic Performance Info." );
        System.out.println("################################################");

        for (int i=0; i<cnt; i++){
            sleep(1000);
            Object countMsg  = kafkaServerMetric.getMessagesInPerSec("Count");
            Object oneMRate  = kafkaServerMetric.getMessagesInPerSec("OneMinuteRate");
            Object fiveMRate = kafkaServerMetric.getMessagesInPerSec("FiveMinuteRate");
            String nowTime   = getNowDate();

            Object countInByte     = (Long) kafkaServerMetric.getByteInPerSec("Count")/1000/1000;
            Object oneMRateInByte  = (Double) kafkaServerMetric.getByteInPerSec("OneMinuteRate")/1000;
            Object fiveMRateInByte = (Double) kafkaServerMetric.getByteInPerSec("FiveMinuteRate")/1000;

            Object countOutByte     = (Long) kafkaServerMetric.getByteOutPerSec("Count")/1000/1000;
            Object oneMRateOutByte  = (Double) kafkaServerMetric.getByteOutPerSec("OneMinuteRate")/1000;
            Object fiveMRateOutByte = (Double) kafkaServerMetric.getByteOutPerSec("FiveMinuteRate")/1000;


            String headStr = "+-----  time  -----+------ All  Topic MPS --------+---------- Net In KB ----------+--------- Net Out KB ----------+\n";
            String titStr  = "|                  |--msgSum--|--msg1M--|--msg5M--|--InSum(MB)--|--In1M--|--In5M--|--InSum(MB)--|--In1M--|--In5M--|\n";
            String footStr = "+-------------------------------------------------+-------------------------------+-------------------------------+\n";

            if (i % 10 == 0){
                System.out.print(headStr);
                System.out.print(titStr);
                System.out.print(footStr);
            }

            System.out.printf("%-18s",nowTime);
            System.out.printf("%10d",countMsg);
            System.out.printf("%10.2f",oneMRate);
            System.out.printf("%10.2f",fiveMRate);

            System.out.printf("%14d", countInByte);
            System.out.printf("%9.2f", oneMRateInByte);
            System.out.printf("%9.2f", fiveMRateInByte);

            System.out.printf("%14d", countOutByte);
            System.out.printf("%9.2f", oneMRateOutByte);
            System.out.printf("%9.2f\n", fiveMRateOutByte);

        }
    }


    public static void main(String[] args) throws InterruptedException, ParseException {
        String ipAndPort;
        Integer cnt = 100;

        Options options = new Options();
        options.addOption("help",false,"The help information");
        options.addOption("brokerlist",true,"The broker list string in\n" +
                "                                           the form HOST1:PORT1");
        options.addOption("topic", true, "The topic you want monitor, Don't assign means " +
                "                                           monitor all Topic.");
        options.addOption("n", true, "The times for monitor printout.Default 100.");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options,args);

        if(cmd.hasOption("help")) {
            //调用默认的help函数打印
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "java -jar kafkaMonitor-1.0.jar [OPTION]", options );
            return;
        }

        if (cmd.hasOption("brokerlist")){
            ipAndPort = cmd.getOptionValue("brokerlist");
        } else {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "java -jar kafkaMonitor-1.0.jar  [OPTION]", options );
            return;
        }

        if (cmd.hasOption("n")){
            cnt = Integer.parseInt(cmd.getOptionValue("n"));
        }

        if(cmd.hasOption("topic")){
            String topicName = cmd.getOptionValue("topic");
            printTopicStdout(ipAndPort, topicName, cnt);
        }else{
            printALLStdout(ipAndPort, cnt);
        }
    }
}
