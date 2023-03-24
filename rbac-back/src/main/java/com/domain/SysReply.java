package com.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huiyuan080
 * @date 2023/3/17 
 * @apiNote 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysReply implements Serializable {
    /**
    * 评论编号
    */
    private Integer replyId;

    /**
    * 帖子编号
    */
    private Integer topicId;

    /**
    * 作者
    */
    private String author;

    /**
    * 内容
    */
    private String content;

    /**
    * 发表时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createDate;

    private static final long serialVersionUID = 1L;
}