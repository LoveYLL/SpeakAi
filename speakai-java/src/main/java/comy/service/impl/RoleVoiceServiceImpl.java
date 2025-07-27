package comy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import comy.dao.RoleVoice;
import comy.service.RoleVoiceService;
import comy.mapper.RoleVoiceMapper;
import org.springframework.stereotype.Service;

/**
* @author myh
* @description 针对表【role_voice(角色语音配置表)】的数据库操作Service实现
* @createDate 2025-07-22 15:06:08
*/
@Service
public class RoleVoiceServiceImpl extends ServiceImpl<RoleVoiceMapper, RoleVoice>
    implements RoleVoiceService{
    @Override
    public String getVoiceByRoleCode(String roleCode) {
        RoleVoice roleVoice = this.getOne(new QueryWrapper<RoleVoice>().eq("role_code", roleCode));
        return roleVoice != null ? roleVoice.getVoice() : null;
    }
}




