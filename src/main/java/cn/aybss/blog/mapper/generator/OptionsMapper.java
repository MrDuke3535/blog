package cn.aybss.blog.mapper.generator;

import cn.aybss.blog.model.domain.Options;
import cn.aybss.blog.model.domain.OptionsExample;

import java.util.List;

public interface OptionsMapper {

    public List<Options> selectByExample(OptionsExample example);

    public int insert(Options options);

    public int updateByPrimaryKeySelective(Options options);

}
