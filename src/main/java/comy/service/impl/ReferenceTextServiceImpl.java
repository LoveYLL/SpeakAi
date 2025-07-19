package comy.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import comy.dao.ReferenceText;
import comy.mapper.ReferenceTextMapper;
import comy.service.ReferenceTextService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ReferenceTextServiceImpl extends ServiceImpl<ReferenceTextMapper,ReferenceText> implements ReferenceTextService {
    @Override
    public ReferenceText getText() {
        Random random = new Random();
        int randomId = random.nextInt(20) + 1;
        return this.baseMapper.selectById(randomId);
    }
}
