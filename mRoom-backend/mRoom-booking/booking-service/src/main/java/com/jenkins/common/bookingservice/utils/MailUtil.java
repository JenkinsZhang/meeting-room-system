package com.jenkins.common.bookingservice.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

/**
 * @author JenkinsZhang
 * @date 29/04/2020
 */
@Component
public class MailUtil {
    private Logger logger = LoggerFactory.getLogger(MailUtil.class);

    @Autowired
    private JavaMailSender javaMailSender;


    public int sendBooking(String from,String[] to,String startTime, String endTime, String roomName, String address){

        try{
            String text = "You have a new meeting at <br> <b> "+roomName + ", "+ address +" </b>" +"<br>" + " from <b>" + startTime + " to " + endTime + "</b>";
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("New Meeting");
            helper.setText(text,true);
            javaMailSender.send(message);
            return 1;
        }catch (Exception e)
        {
            System.out.println(e);
            return -1;
        }
    }

    public int sendCancel(String from,String[] to,String startTime, String endTime, String roomName, String address){
        try{
            String text = "Your meeting at <br> <b> "+roomName + ", "+ address +" </b>" +"<br>" + " from <b>" + startTime + " to " + endTime + "</b>" +"<br> has been canceled";
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("Meeting Canceled");
            helper.setText(text,true);
            javaMailSender.send(message);
            return 1;
        }catch (Exception e)
        {
            System.out.println(e);
            return -1;
        }
    }

    public int sendEdit(String from,String[] to,String oldStartTime,String newStartTime, String oldEndTime,String newEndTime,
                        String oldRoomName,String newRoomName, String oldRoomAddress,String newRoomAddress){
        try{
            String text = "Your meeting at <br> <b> "+oldRoomName + ", "+ oldRoomAddress +" </b>" +"<br>" + " from <b>" + oldStartTime + " to " + oldEndTime + "</b>" +"<br> has been changed to: <br><br>"
                    + " <b> "+newRoomName + ", "+ newRoomAddress +" </b>" +"<br>" + " from <b>" + newStartTime + " to " + newEndTime + "</b>";
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("Meeting Changed");
            helper.setText(text,true);
            javaMailSender.send(message);
            return 1;
        }catch (Exception e)
        {
            System.out.println(e);
            return -1;
        }
    }
}
