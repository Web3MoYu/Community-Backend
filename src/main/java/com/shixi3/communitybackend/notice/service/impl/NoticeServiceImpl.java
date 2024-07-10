package com.shixi3.communitybackend.notice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shixi3.communitybackend.notice.entity.Notice;
import com.shixi3.communitybackend.notice.mapper.NoticeMapper;
import com.shixi3.communitybackend.notice.service.NoticeService;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

}
