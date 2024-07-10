package com.shixi3.communitybackend.notice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shixi3.communitybackend.notice.entity.Notice;
import com.shixi3.communitybackend.notice.vo.NoticeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
    Page<NoticeVo> page(@Param("page") Page<NoticeVo> page, @Param("title") String title);
}
