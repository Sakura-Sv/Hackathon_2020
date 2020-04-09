package com.testdb.demo.entity.message;

import com.testdb.demo.entity.message.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessagePage {

    private List<Message> messages;
    private long currentPage;
    private long size;
    private long pages;

}
