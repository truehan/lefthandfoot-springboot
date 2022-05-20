package com.tencent.wxcloudrun.dto;


import lombok.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement
public class TextMessageRequest {
    @XmlElement(name = "ToUserName")
    private String toUserName;
    @XmlElement(name = "FromUserName")
    private String fromUserName;
    @XmlElement(name = "CreateTime")
    private String createTime;
    @XmlElement(name = "MsgType")
    private String msgType;
    @XmlElement(name = "PicUrl")
    private String picUrl;
    @XmlElement(name = "MediaId")
    private String mediaId;
    @XmlElement(name = "MsgId")
    private String msgId;
    @XmlElement(name = "MsgDataId")
    private String msgDataId;
    @XmlElement(name = "Idx")
    private String idx;
}
