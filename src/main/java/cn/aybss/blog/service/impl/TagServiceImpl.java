package cn.aybss.blog.service.impl;

import cn.aybss.blog.mapper.generator.TagMapper;
import cn.aybss.blog.model.domain.Tag;
import cn.aybss.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<Tag> findTags() {
        return tagMapper.selectByExample(null);
    }

    @Override
    public void save(Tag tag) {
        tagMapper.insert(tag);
    }

    @Override
    public void update(Tag tag) {
        tagMapper.updateByPrimaryKeySelective(tag);
    }

    @Override
    public Tag findByTagId(int tagId) {
        return tagMapper.selectByPrimaryKey(tagId);
    }

    @Override
    public void delete(int tagId) {
        tagMapper.deleteByPrimaryKey(tagId);
    }
}
