package cn.aybss.blog.mapper.generator;

import cn.aybss.blog.model.domain.Attachment;
import cn.aybss.blog.model.domain.AttachmentExample;

import java.util.List;

public interface AttachmentMapper {

    public List<Attachment> selectByExample(AttachmentExample example);

    public int insert(Attachment record);

    public Attachment selectByPrimaryKey(Integer id);

    public int deleteByPrimaryKey(Integer id);

}
