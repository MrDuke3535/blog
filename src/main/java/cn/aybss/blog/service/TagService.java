package cn.aybss.blog.service;

import cn.aybss.blog.model.domain.Tag;

import java.util.List;

public interface TagService {

    public List<Tag> findTags();

    public void save(Tag tag);

    public void update(Tag tag);

    public Tag findByTagId(int tagId);

    public void delete(int tagId);

}
