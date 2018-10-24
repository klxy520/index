package com.jc.cz_index.core.web.controller.test;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.cz_index.core.web.controller.BaseController;
import com.jc.cz_index.core.web.task.SyncTask;

@Controller
public class TestController extends BaseController {

    @Autowired
    private SyncTask syncTask;



    @RequestMapping("/testSyncTask")
    @ResponseBody
    public String testSyncTask(HttpServletRequest request) {
        syncTask.baseTask();
        return "success";
    }
}
