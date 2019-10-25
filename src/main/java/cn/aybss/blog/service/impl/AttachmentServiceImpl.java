package cn.aybss.blog.service.impl;

import cn.aybss.blog.mapper.generator.AttachmentMapper;
import cn.aybss.blog.model.domain.Attachment;
import cn.aybss.blog.model.domain.AttachmentExample;
import cn.aybss.blog.service.AttachmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Override
    public List<Attachment> countAttachment() {
        return attachmentMapper.selectByExample(null);
    }

    @Override
    public PageInfo<Attachment> getAttachment(int page, int limit) {
        PageHelper.startPage(page,limit);
        AttachmentExample example = new AttachmentExample();
        example.setOrderByClause("id desc");
        List<Attachment> attachments = attachmentMapper.selectByExample(example);
        return new PageInfo<>(attachments);
    }

    @Override
    public void save(Attachment attachment) {
        attachmentMapper.insert(attachment);
    }

    @Override
    public Attachment findById(int id) {
        return attachmentMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(int id) {
        attachmentMapper.deleteByPrimaryKey(id);
    }


}
