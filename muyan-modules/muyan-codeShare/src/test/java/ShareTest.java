import com.muyan.CodeShareApplication;
import com.muyan.domain.ResponseResult;
import com.muyan.domain.dto.ExpireEnum;
import com.muyan.domain.dto.ShareInfoResponse;
import com.muyan.domain.entity.Share;
import com.muyan.domain.vo.CodeShareVo;
import com.muyan.domain.vo.ShareExtVo;
import com.muyan.domain.vo.ShareVo;
import com.muyan.service.CodeShareService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(classes = CodeShareApplication.class)
public class ShareTest {

    @Resource
    private CodeShareService codeShareService;

    @Test
    public void testShareCreate() {
        Share share = new Share();
        share.setCodeId(1815002424036884482L);
        share.setExpire(ExpireEnum.NoLimit);
        ResponseResult<ShareVo> result = codeShareService.createShare(share);
        log.info("相应结果:{}", result);
    }

    @Test
    public void testShareInfoExt() {
        ResponseResult<ShareExtVo> shareInfo = codeShareService.getShareInfo(1871568534214975489L);
        log.info("响应结果:{}", shareInfo);

        ResponseResult<ShareInfoResponse> shareCode = codeShareService.getShareCode(1871568534214975489L, null);
        log.info("响应结果:{}", shareCode);
    }
}
