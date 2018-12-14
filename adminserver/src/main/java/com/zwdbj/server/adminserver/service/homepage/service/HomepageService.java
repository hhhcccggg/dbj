package com.zwdbj.server.adminserver.service.homepage.service;

import com.zwdbj.server.adminserver.service.dailyIncreaseAnalysises.service.DailyIncreaseAnalysisesService;
import com.zwdbj.server.adminserver.service.homepage.model.AdFindHotTagsDto;
import com.zwdbj.server.adminserver.service.homepage.model.AdFindIncreasedDto;
import com.zwdbj.server.adminserver.service.homepage.model.AdFindIncreasedInput;
import com.zwdbj.server.adminserver.service.homepage.model.AdUserOrVideoGrowthDto;
import com.zwdbj.server.adminserver.service.tag.service.TagService;
import com.zwdbj.server.adminserver.service.user.service.UserService;
import com.zwdbj.server.adminserver.service.video.service.VideoService;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class HomepageService {

    @Autowired
    UserService userService;
    @Autowired
    VideoService videoService;
    @Autowired
    DailyIncreaseAnalysisesService dailyIncreaseAnalysisesService;
    @Autowired
    TagService tagService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    private Logger logger = LoggerFactory.getLogger(HomepageService.class);

    public AdFindIncreasedDto findIncreasedAd(AdFindIncreasedInput input) {
        long userId = JWTUtil.getCurrentId();
        List<String> roles = this.userService.getUserAuthInfo(userId).getRoles();
        boolean flag = false;
        for (String role : roles) {
            if ("datareport".equals(role)) flag = true;
        }
        AdFindIncreasedDto dto = this.userService.findIncreasedUserAd(input, flag);
        if (dto == null) return null;
        Long videoNum = this.videoService.findIncreasedVideoAd(input.getQuantumTime());
        Long verifingVideoNum = this.videoService.findIncreasedVideoingAd(input.getQuantumTime());
        long dau1 = this.userService.dau();
        if (flag) {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "v";
            if (this.stringRedisTemplate.hasKey(date)) {
                videoNum = Long.valueOf(this.stringRedisTemplate.opsForValue().get(date));
                logger.info("videoNum=" + videoNum);
            }
            dau1 = this.dailyIncreaseAnalysisesService.dau();
        }
        long dau = new Double(Math.ceil(dau1 / 3.5)).longValue();
        long mau = new Double(Math.ceil(dau1 * 4.5 / 3.5)).longValue();
        dto.setDau(dau);
        dto.setMau(mau);
        dto.setVideoNum(videoNum);
        dto.setVerifingVideoNum(verifingVideoNum);
        return dto;
    }

    public List<AdUserOrVideoGrowthDto> userGrowthAd(AdFindIncreasedInput input) {
        try {
            long userId = JWTUtil.getCurrentId();
            List<String> roles = this.userService.getUserAuthInfo(userId).getRoles();
            List<AdUserOrVideoGrowthDto> growthDtos = new ArrayList<>();
            boolean flag = false;
            for (String role : roles) {
                if ("datareport".equals(role)) flag = true;
            }
            if (input.getQuantumTime() == 0) {//查询当前日的增量
                //获取当前零时
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                String zeroTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(calendar.getTime());
                logger.info("zeroTime--" + zeroTime);
                //从redis中获取当前天的用户增量
                Map results = redisTemplate.opsForHash().entries(zeroTime + "userGrowth");
                AdUserOrVideoGrowthDto growthDto = new AdUserOrVideoGrowthDto();
                Set set = results.keySet();
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    logger.info("key----" + key);
                    Long value = Long.valueOf((String) results.get(key));
                    key = key.replace("userGrowth", "");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = simpleDateFormat.parse(key);
                    growthDto.setCreateTime(date);
                    growthDto.setGrowthed(value);
                    growthDtos.add(growthDto);
                }
                return growthDtos;

            }
            growthDtos = this.dailyIncreaseAnalysisesService.userGrowthAd(input, flag);
            return growthDtos;

        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }

    }


    //导出用户增长量excel表
    public void userGrowthAdExcel(AdFindIncreasedInput input, HttpServletResponse response) {
        //查询出数据
        List<AdUserOrVideoGrowthDto> results = userGrowthAd(input);
        String fileName = "";

        try {
            if (input.getQuantumTime() == 0) {
                fileName = "本日用户视频增长量.xls";
            } else if (input.getQuantumTime() == 1) {
                fileName = "本周用户增长量.xls";
            } else if (input.getQuantumTime() == 2) {
                fileName = "本月用户增长量.xls";
            } else if (input.getQuantumTime() == 7) {
                fileName = "上月用户增长量.xls";
            }
            HSSFWorkbook wb = null;
            String[] title = {fileName, "创建时间"};
            wb = getHSSFWorkbook("用户增长数量表", title, results, wb);

            //输出excel文件
            downLoadExcel(wb, response, fileName);


        } catch (Exception e) {
            logger.info(e.getMessage());
        }

    }


    public List<AdUserOrVideoGrowthDto> videoGrowthAd(AdFindIncreasedInput input) {
        try {
            long userId = JWTUtil.getCurrentId();
            List<String> roles = this.userService.getUserAuthInfo(userId).getRoles();
            List<AdUserOrVideoGrowthDto> growthDtos = new ArrayList<>();
            boolean flag = false;
            for (String role : roles) {
                if ("datareport".equals(role)) flag = true;
            }
            if (input.getQuantumTime() == 0) {//查询当前日的增量
                //获取当前零时
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                String zeroTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(calendar.getTime());
                //从redis中获取当前天的用户增量
                Map results = redisTemplate.opsForHash().entries(zeroTime + "videoGrowth");
                AdUserOrVideoGrowthDto growthDto = new AdUserOrVideoGrowthDto();
                Set set = results.keySet();
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    Long value = Long.valueOf((String) results.get(key));
                    key = key.replace("videoGrowth", "");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = simpleDateFormat.parse(key);
                    growthDto.setCreateTime(date);
                    growthDto.setGrowthed(value);
                    growthDtos.add(growthDto);
                }
                return growthDtos;

            }
            growthDtos = this.dailyIncreaseAnalysisesService.videoGrowthAd(input, flag);
            return growthDtos;

        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    //导出短视频增长量表
    public void videoGrowthAdExcel(AdFindIncreasedInput input, HttpServletResponse response) {
        //查询出数据
        List<AdUserOrVideoGrowthDto> results = videoGrowthAd(input);
        String fileName = "";

        try {
            if (input.getQuantumTime() == 0) {
                fileName = "本日视频增长量.xls";
            } else if (input.getQuantumTime() == 1) {
                fileName = "本周视频增长量.xls";
            } else if (input.getQuantumTime() == 2) {
                fileName = "本月视频增长量.xls";
            } else if (input.getQuantumTime() == 7) {
                fileName = "上月视频增长量.xls";
            }
            HSSFWorkbook wb = null;
            String[] title = {fileName, "创建时间"};
            wb = getHSSFWorkbook("视频增长数量表", title, results, wb);

            //输出excel文件
            downLoadExcel(wb, response, fileName);


        } catch (Exception e) {
            logger.info(e.getMessage());
        }

    }


    public List<AdFindHotTagsDto> findHotTags() {
        return this.tagService.findHotTags();
    }


    //下载excel
    public static void downLoadExcel(HSSFWorkbook wb, HttpServletResponse response, String fileName) throws Exception {

        OutputStream os = response.getOutputStream();
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("gb2312"), "iso8859-1"));
        response.setContentType("application/octet-stream;charset=utf-8");
        wb.write(os);
        os.close();
    }

    //获取HSSFWorkbook对象
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, String title[], List<AdUserOrVideoGrowthDto> values, HSSFWorkbook wb) {

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null) {
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);


        //声明列对象
        HSSFCell cell = null;

        int title_length = title.length;
        int values_size = values.size();

        //创建列明
        for (int i = 0; i < title_length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);

        }

        //创建内容
        for (int i = 0; i < values.size(); i++) {
            row = sheet.createRow(i + 1);
            //将内容按顺序赋给对应的列对象
            row.createCell(0).setCellValue(values.get(i).getGrowthed());
            row.createCell(1).setCellValue(values.get(i).getCreateTime());
        }
        return wb;
    }
}
