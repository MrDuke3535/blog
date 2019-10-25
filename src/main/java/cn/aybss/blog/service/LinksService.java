package cn.aybss.blog.service;

import cn.aybss.blog.model.domain.Link;

import java.util.List;

public interface LinksService {

    public List<Link> findLinks();

    public Link findLinks(int linkId);

}
