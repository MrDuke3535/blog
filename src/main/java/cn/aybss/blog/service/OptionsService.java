package cn.aybss.blog.service;

import cn.aybss.blog.model.domain.Options;

import java.util.List;
import java.util.Map;

public interface OptionsService {
    public void save(Map<String,String> optionsMap);

    public void saveOption(String key,String value);

    public List<Options> selectMap();
}
