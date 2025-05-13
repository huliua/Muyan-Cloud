package com.muyan.task;

import com.muyan.domain.ResponseResult;
import com.muyan.service.CodeShareService;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@EnableScheduling
public class ClearCodeInfoTask {

    @Resource
    private CodeShareService codeShareService;

    @Scheduled(cron = "0 0 */2 * * ?")
    public void clearCodeInfo() {
        ResponseResult<String> result = codeShareService.removeIncompleteInfo();
        log.info("clearCodeInfo result: {}", result);
    }
}
