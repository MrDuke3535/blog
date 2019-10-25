package cn.aybss.blog.mapper.generator;

import cn.aybss.blog.model.domain.Link;
import cn.aybss.blog.model.domain.LinkExample;

import java.util.List;

public interface LinksMapper {

    public List<Link> selectByExample(LinkExample example);

    public Link selectByPrimaryKey(int linkId);

}
