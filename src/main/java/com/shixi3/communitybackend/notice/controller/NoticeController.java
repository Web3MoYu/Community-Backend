package com.shixi3.communitybackend.notice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shixi3.communitybackend.auth.util.SecurityUtil;
import com.shixi3.communitybackend.common.model.CommonResult;
import com.shixi3.communitybackend.notice.entity.Notice;
import com.shixi3.communitybackend.notice.mapper.NoticeMapper;
import com.shixi3.communitybackend.notice.service.NoticeService;
import com.shixi3.communitybackend.notice.vo.NoticeVo;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequestMapping("/notice")
@RestController
public class NoticeController {
    @Resource
    private NoticeMapper noticeMapper;
    @Resource
    private NoticeService noticeService;

    /**
     * 分页查询公告信息
     *
     * @param page     当前页数
     * @param pageSize 页面大小
     * @param title    公告标题
     * @return 公告分页信息
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('notice:list') or hasAuthority('wx_user')")
    public CommonResult<Page<NoticeVo>> getNotices(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int pageSize, @RequestParam(required = false) String title) {
        Page<NoticeVo> result = new Page<>(page, pageSize);
        result = noticeMapper.page(result, title);
        return CommonResult.success(result);
    }

    /**
     * 新增公告信息
     *
     * @param notice 公告信息
     * @return 新增信息
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('notice:add')")
    public CommonResult<String> add(@RequestBody Notice notice) {
        notice.setCreator(SecurityUtil.getUserID());
        notice.setUpdateTime(new Date());
        notice.setCreateTime(new Date());
        noticeService.save(notice);
        return CommonResult.success("新增公告成功");
    }

    /**
     * 获取公告信息
     *
     * @param noticeId 公告id
     * @return 公告信息
     */
    @GetMapping("/getOne/{noticeId}")
    @PreAuthorize("hasAuthority('notice:list')")
    public CommonResult<Notice> getOne(@PathVariable Long noticeId) {
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Notice::getNoticeId, noticeId);
        return CommonResult.success(noticeService.getOne(wrapper));
    }

    /**
     * 修改公告信息
     *
     * @param notice 公告信息
     * @return 修改信息
     */
    @PutMapping("/edit")
    @PreAuthorize("hasAuthority('notice:update')")
    public CommonResult<String> update(@RequestBody Notice notice) {
        notice.setUpdateTime(new Date());
        boolean save = noticeService.updateById(notice);
        if (save) {
            return CommonResult.success("修改成功");
        }
        return CommonResult.error(500, "修改失败");
    }

    /**
     * 删除一条公告信息
     *
     * @param noticeId 公告id
     * @return 删除信息
     */
    @DeleteMapping("/delete/{noticeId}")
    @PreAuthorize("hasAuthority('notice:delete')")
    public CommonResult<String> delete(@PathVariable Long noticeId) {
        boolean delete = noticeService.removeById(noticeId);
        if (delete) {
            return CommonResult.success("删除成功");
        }
        return CommonResult.error(500, "删除失败");
    }

    /**
     * 批量删除公告
     *
     * @param ids 公告id列表
     * @return 批量删除信息
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('notice:delete')")
    public CommonResult<String> deleteByIds(@RequestBody List<Long> ids) {
        boolean delete = noticeService.removeBatchByIds(ids);
        if (delete) {
            return CommonResult.success("批量删除成功");
        }
        return CommonResult.error(500, "批量删除失败");
    }

}
