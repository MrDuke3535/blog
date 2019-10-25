package cn.aybss.blog.service.impl;

import cn.aybss.blog.mapper.generator.LinksMapper;
import cn.aybss.blog.model.domain.Link;
import cn.aybss.blog.service.LinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinksServiceImpl implements LinksService {

    @Autowired
    private LinksMapper linksMapper;

    @Override
    public List<Link> findLinks() {
        return linksMapper.selectByExample(null);
    }

    @Override
    public Link findLinks(int linkId) {
        return linksMapper.selectByPrimaryKey(linkId);
    }
}
