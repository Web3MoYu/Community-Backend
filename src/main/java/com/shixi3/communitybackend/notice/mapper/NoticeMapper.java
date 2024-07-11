package com.shixi3.communitybackend.notice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shixi3.communitybackend.notice.entity.Notice;
import com.shixi3.communitybackend.notice.vo.NoticeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
    Page<NoticeVo> page(@Param("page") Page<NoticeVo> page, @Param("title") String title);

    @Select("select content, notice_id\n" +
            "from notice\n" +
            "order by create_time desc\n" +
            "limit 6")
    List<Notice> listFiveNoticesByTime();
}
