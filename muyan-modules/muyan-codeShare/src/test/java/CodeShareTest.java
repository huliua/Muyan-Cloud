import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.muyan.CodeShareApplication;
import com.muyan.constants.CodeShareConstants;
import com.muyan.domain.entity.CodeShareInfo;
import com.muyan.domain.vo.CodeShareInfoVo;
import com.muyan.mapper.CodeShareInfoMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-07-27 16:34
 */
@SpringBootTest(classes = CodeShareApplication.class)
public class CodeShareTest {

    @Resource
    private CodeShareInfoMapper codeShareInfoMapper;

    @Test
    public void test() {
        Page<CodeShareInfo> infoPage = new Page<>(1, 10);
        LambdaQueryWrapper<CodeShareInfo> queryWrapper = new LambdaQueryWrapper<>();
        Long userId = 1L;
        // 根据查询类型,拼接查询条件
        queryWrapper.eq(CodeShareInfo::getUserId, userId).or().eq(CodeShareInfo::getVisibility, "public");
        queryWrapper.orderByDesc(CodeShareInfo::getCreateTime);
        Page<CodeShareInfoVo> codesList = codeShareInfoMapper.getCodesListByUserId(infoPage, queryWrapper, 1L);
        codesList.getRecords().forEach(System.out::println);
    }
}
