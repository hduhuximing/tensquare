package com.tensquare.sms.listener;

import com.aliyuncs.exceptions.ClientException;
import com.tensquare.sms.utils.JavaSmsApi;
import com.tensquare.sms.utils.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.Map;

/**
 * @author: 肖德子裕
 * @date: 2018/11/29 14:58
 * @description:
 */
@Component
@RabbitListener(queues = "sms")
public class SmsListener {
   /* @Autowired
    private SmsUtil smsUtil;*/

   /* @Value("${aliyun.sms.template_code}")
    private String template_code;*/

    /*@Value("${aliyun.sms.sign_name}")
    private String sign_name;*/

    @Autowired
    private JavaSmsApi javaSmsApi;

    @Value("${APIKEY}")
    private String APIKEY;

    /** 编码格式。发送编码格式统一用UTF-8 */
    private static String ENCODING = "UTF-8";

    @RabbitHandler
    public void executeSms(Map<String, String> map) {
        //System.out.println("手机号："+map.get("mobile"));
        //System.out.println("验证码："+map.get("checkcode"));
        String mobile = map.get("mobile");
        String checkcode = map.get("checkcode");
        try{
            //smsUtil.sendSms(mobile,template_code,sign_name,"{\"checkcode\":\""+checkcode+"\"}");
            String tpl_value = URLEncoder.encode("#code#", ENCODING) + "="
                    + URLEncoder.encode(checkcode, ENCODING) + "&"
                    + URLEncoder.encode("#company#", ENCODING) + "="
                    + URLEncoder.encode("云片网", ENCODING);
            javaSmsApi.tplSendSms(APIKEY,1,tpl_value,mobile);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
