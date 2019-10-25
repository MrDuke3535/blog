package cn.aybss.blog.service.impl;

import cn.aybss.blog.mapper.generator.OptionsMapper;
import cn.aybss.blog.model.domain.Options;
import cn.aybss.blog.model.domain.OptionsExample;
import cn.aybss.blog.model.domain.OptionsExample.Criteria;
import cn.aybss.blog.service.OptionsService;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OptionsServiceImpl implements OptionsService {

    @Autowired
    private OptionsMapper optionsMapper;

    @Override
    public void save(Map<String, String> optionsMap) {
        if(optionsMap!=null&&!optionsMap.isEmpty()){
            optionsMap.forEach((k,v)->saveOption(k,v));
        }
    }

    @Override
    public void saveOption(String key, String value) {
        if(StrUtil.isNotEmpty(key)){
            OptionsExample example = new OptionsExample();
            Criteria criteria = example.createCriteria();
            criteria.andOptionNameEqualTo(key);
            List<Options> list = optionsMapper.selectByExample(example);
            if(list.size()==0||list==null){
                Options options = new Options();
                options.setOptionName(key);
                options.setOptionValue(value);
                optionsMapper.insert(options);
            }else {
                 Options options = new Options();
                 options.setOptionName(list.get(0).getOptionName());
                 options.setOptionValue(value);
                optionsMapper.updateByPrimaryKeySelective(options);
            }
        }
    }

    @Override
    public List<Options> selectMap() {
        return optionsMapper.selectByExample(null);
    }
}
