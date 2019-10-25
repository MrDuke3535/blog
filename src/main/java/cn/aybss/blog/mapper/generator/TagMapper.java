package cn.aybss.blog.mapper.generator;

import cn.aybss.blog.model.domain.ArticleTag;
import cn.aybss.blog.model.domain.Tag;
import cn.aybss.blog.model.domain.TagExample;

import java.util.List;

public interface TagMapper {

    public List<Tag> selectByExample(TagExample example);

    public int insert(Tag record);

    public int updateByPrimaryKeySelective(Tag record);

    public Tag selectByPrimaryKey(Integer tagId);

    public int deleteByPrimaryKey(Integer tagId);

}
