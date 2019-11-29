package com.yiqishun.mqdemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = RabbitConfig.QUEUE_SMALL_PROGRAM)
public class MsgReceiver {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitHandler
    public void process(@Payload String content) {
        MqMessage mqMessage=JSON.parseObject(content,MqMessage.class);
        if(mqMessage.getMsgType().equals("MSG_GOODS_UPDATE")){
            System.out.println("---------易企顺商品发生修改");
            Goods goods=JSON.parseObject(mqMessage.getContent(),Goods.class);
            //根据商品id，重新拉取商品信息

        }
        if(mqMessage.getMsgType().equals("MSG_ORDER_SEND")){
            System.out.println("---------易企顺订单发货");
            Order order=JSON.parseObject(mqMessage.getContent(),Order.class);
            //根据易企顺订单号改变商户系统订单状态

        }
        if(mqMessage.getMsgType().equals("MSG_ORDER_CANCEL")){
            System.out.println("---------易企顺订单取消");
            Order order=JSON.parseObject(mqMessage.getContent(),Order.class);
            //根据易企顺订单号改变商户系统订单状态

        }
        if(mqMessage.getMsgType().equals("MSG_GOODS_ON")){
            System.out.println("---------易企顺商品上架");
            Shelf shelf=JSON.parseObject(mqMessage.getContent(),Shelf.class);
            System.out.println("上架商品id"+shelf.getGoods_id());
            //根据商品id改变商户系统商品上下架状态

        }
        if(mqMessage.getMsgType().equals("MSG_GOODS_OFF")){
            System.out.println("---------易企顺商品下架");
            Shelf shelf=JSON.parseObject(mqMessage.getContent(),Shelf.class);
            System.out.println("下架商品id"+shelf.getGoods_id());
            //根据商品id改变商户系统商品上下架状态

        }


    }
}
