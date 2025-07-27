package comy.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import comy.dao.ReferenceText;
import comy.mapper.ReferenceTextMapper;
import comy.service.ReferenceTextService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;
@Service
public class ReferenceTextServiceImpl extends ServiceImpl<ReferenceTextMapper,ReferenceText> implements ReferenceTextService {
    @Override
    public ReferenceText getText() {
        List<ReferenceText> all = this.list();
        if (all.isEmpty()) return null;
        Random random = new Random();
        return all.get(random.nextInt(all.size()));
    }
}
