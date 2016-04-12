package com.hwsoft.crawler.common.scheduler;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.QueueScheduler;

/**
 * Created by arvin on 16/4/13.
 */
public class ClothesProductScheduler extends QueueScheduler {

    public void push(Request request, Task task) {
        super.push(request, task);
    }
}
