package cn.aybss.blog.service;

import cn.aybss.blog.model.domain.Attachment;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AttachmentService {

    public List<Attachment> countAttachment();

    public PageInfo<Attachment> getAttachment(int page, int limit);

    public void save(Attachment attachment);

    public Attachment findById(int id);

    public void delete(int id);

}
