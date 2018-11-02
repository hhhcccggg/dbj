package com.zwdbj.server.adminserver.service.operateComments.service;

import com.zwdbj.server.adminserver.service.comment.service.CommentService;
import com.zwdbj.server.adminserver.service.user.service.UserService;
import com.zwdbj.server.adminserver.service.video.service.VideoService;
import com.zwdbj.server.adminserver.utility.UniqueIDCreater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class OperateService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    UserService userService;
    @Autowired
    VideoService videoService;
    @Autowired
    CommentService commentService;
    public String getNickName(int length){
        String gg = "天,地,玄,黄,宇,宙,洪,荒,日,月,盈,昃,辰,宿,列,张,寒,来,暑,往,秋,收,冬,藏,闰,馀,成,岁,律,吕,调,阳,云," +
                "腾,致,雨,露,结,为,霜,金,生,丽,水,玉,出,昆,冈,剑,号,巨,阙,珠,称,夜,光,果,珍,李,柰,菜,重,芥,姜,海,咸,河," +
                "淡,鳞,潜,羽,翔,龙,师,火,帝,鸟,官,人,皇,始,制,文,字,乃,服,衣,裳,推,位,让,国,有,虞,陶,唐,吊,民,伐,罪,周," +
                "发,殷,汤,坐,朝,问,道,垂,拱,平,章,爱,育,黎,首,臣,伏,戎,羌,遐,迩,一,体,率,宾,归,王,鸣,凤,在,竹,白,驹,食," +
                "场,化,被,草,木,赖,及,万,方,盖,此,身,发,四,大,五,常,恭,惟,鞠,养,岂,敢,毁,伤,女,慕,贞,洁,男,效,才,良,知," +
                "过,必,改,得,能,莫,忘,罔,谈,彼,短,靡,恃,己,长,信,使,可,复,器,欲,难,量,墨,悲,丝,染,诗,赞,羔,羊,景,行,维," +
                "贤,克,念,作,圣,德,建,名,立,形,端,表,正,空,谷,传,声,虚,堂,习,听,祸,因,恶,积,福,缘,善,庆,尺,璧,非,宝,寸," +
                "阴,是,竞,资,父,事,君,曰,严,与,敬,孝,当,竭,力,忠,则,尽,命,临,深,履,薄,夙,兴,温,凊,似,兰,斯,馨,如,松,之," +
                "盛,川,流,不,息,渊,澄,取,映,容,止,若,思,言,辞,安,定,笃,初,诚,美,慎,终,宜,令,荣,业,所,基,籍,甚,无,竟,学," +
                "优,登,仕,摄,职,从,政,存,以,甘,棠,去,而,益,咏,乐,殊,贵,贱,礼,别,尊,卑,上,和,下,睦,夫,唱,妇,随,外,受,傅," +
                "训,入,奉,母,仪,诸,姑,伯,叔,犹,子,比,儿,孔,怀,兄,弟,同,气,连,枝,交,友,投,分,切,磨,箴,规,仁,慈,隐,恻,造," +
                "次,弗,离,节,义,退,颠,沛,匪,亏,性,静,情,逸,心,动,神,疲,守,真,志,满,逐,物,意,移,坚,持,雅,操,好,爵,自,縻," +
                "都,邑,华,夏,东,西,二,京,背,邙,面,洛,浮,渭,据,泾,宫,殿,盘,郁,楼,观,飞,惊,图,写,禽,兽,画,彩,仙,灵,甲,帐," +
                "对,楹,肆,筵,设,席,鼓,瑟,吹,笙,升,阶,纳,陛,弁,转,疑,星,右,通,广,内,左,达,承,明,亦,聚,群,英,杜,漆,书,壁," +
                "经,府,罗,将,相,路,侠,槐,卿,户,封,八,县,家,给,千,兵,高,冠,陪,驰,誉,丹,青,九,州,禹,迹,百,郡,秦,并,岳,宗," +
                "泰,岱,禅,主,云,亭,雁,门,紫,塞,鸡,田,赤,城,昆,池,碣,石,巨,野,洞,庭,旷,远,绵,邈,岩,岫,杳,冥,治,本,于,农," +
                "务,资,稼,穑,俶,载,南,亩,我,艺,新,劝,赏,孟,轲,敦,素,史,鱼,秉,直,庶,几,中,庸,劳,谦,谨,敕,聆,音,察,理,鉴," +
                "貌,辨,色,贻,厥,嘉,猷,勉,其,祗,植,省,躬,讥,诫,宠,增,抗,极,殆,辱,近,耻,林,皋,幸,即,两,疏,见,机,解,组,谁," +
                "逼,索,居,闲,处,沉,默,寂,寥,求,古,寻,论,散,虑,逍,遥,欣,晚,翠,梧,桐,蚤,凋,陈,根,委,翳,落,叶,飘,摇,游,独," +
                "运,凌,摩,读,玩,市,寓,目,老,少,异,粮,侍,巾,夕,寐,蓝,笋,象,床,弦,歌,酒,宴,接,杯,举,觞,矫,手,顿,足,悦,豫," +
                "且,康,执,热,愿,凉,驴,犊,特,骇,跃,超,诛,斩,贼,盗,捕,获,叛,亡,布,射,僚,丸,嵇,琴,阮,恬,笔,伦,纸,钧,巧,任," +
                "钓,释,纷,利,俗,竝,皆,佳,妙,毛,施,淑,姿,工,妍,笑,年,矢,每,催,曦,晖,朗,曜,璇,玑,悬,晦,庄,徘,徊,语,助,者";
        String[] names = gg.split(",");
        StringBuilder nickName=new StringBuilder();
        for (int i = 0; i <length ; i++) {
            int r = (int)(Math.random()*700);
            String b = names[r];
           nickName.append(b);
        }
        return nickName.toString();
    }

    public void newVestUser1(){
        String a = String.valueOf(UniqueIDCreater.generateID()).substring(9,17);
        String phone ="56"+a;
        String avatarUrl ="http://res.pet.zwdbj.com/default_avatar.png";
        String nickName = "爪子用户";
        this.userService.newVestUser(phone,avatarUrl,nickName);
    }
    public void newVestUser2(){
        String a = String.valueOf(UniqueIDCreater.generateID()).substring(9,17);
        String phone ="56"+a;
        int avatar = this.getRandom(80)+1;
        String avatarUrl ="http://dev.hd.res.pet.zwdbj.com/1%20%28"+avatar+"%29.jpg";
        String nickName = this.getNickName(3);
        this.userService.newVestUser(phone,avatarUrl,nickName);
    }

    public void redisComments(){
        String  comments = "真是小可爱呢~>给你一个么么哒>有种说不出的萌>我想偷你回家呢>请问这么可爱的小宝贝在哪里才" +
                "可以买到>哈哈哈哈哈哈哈笑屎我了>天啦噜萌化我了>每一天我都在这个APP里浪费光阴>老板都不知道我每天" +
                "为什么脸上含笑,完全是因为我在爪子App上找到了生活的意义.>我想给你铲屎！>妈耶怎么会有这么可爱的小东西>" +
                "口特!>每一天来撸你>老板问我为什么拿着手机往脸上蹭>吸你一把,么么哒.>阔爱~~~~~~>萌化了,希望多出一点视频.>" +
                "火了你还是我的小宝贝吗？>火钳留名，你会是下一个小明星噢.>你是吃可爱多长大的吗?>没有什么能抵挡我来看" +
                "你,毕竟我是无限流量!>太可爱了，这是什么品种?>在哪里可以买到这种小宝贝?>我也好想养一只~>想养但是怕脱" +
                "毛,所以还是就这里云养宠吧！>哈哈哈哈，被玩坏了>嘤嘤嘤太萌啦>怎么那么Q>告诉我它在做甚么>眼睛好漂亮啊>" +
                "想吸>我可以偷走吗>呜呜呜呜好可爱啊,想悄悄把它踹进荷包里>我家那位怎么不长这样,生气!!!>我疯了!这是什么" +
                "天使宝贝啊!!>小小的好软一只呀>真想亲亲它>拍得好好看啊,不行了,回去我也要拍拍我家的>请问她的表情是在" +
                "说:干嘛拍我吗?>它叫什么名字哇>单方面宣布我是你家的头号粉丝儿>Emmmmm我想给它介绍个对象>别人家的就是" +
                "可爱>每天都来吸一吸你家主子的神颜>看起来好乖啊,不像我家的皮得不行>心都酥了>请问这样的萌宠,国家包分配" +
                "吗?>默默点了个赞来表达我的喜欢>幸福就是天天见,请保持这个更新速度>滴~打卡,按时来吸一吸";
        stringRedisTemplate.opsForValue().set("REDIS_COMMENTS",comments,7, TimeUnit.DAYS);
    }

    public int getRandom(int length){
        int random = (int)(Math.random()*length);
        return random;
    }

    public String getRedisComment(){
        if (this.stringRedisTemplate.hasKey("REDIS_COMMENTS")){
           String comment =  this.stringRedisTemplate.opsForValue().get("REDIS_COMMENTS");
           String[] comments = comment.split(">");
           int random = this.getRandom(comments.length);
           String contentTxt = comments[random];
           if (contentTxt==null || contentTxt.length()==0) contentTxt=".......";
           return contentTxt;
        }else {
            this.redisComments();
        }
        return "好好玩呀!";
    }

    public Long getVestUserId(){
        List<Long> vestUsers = this.userService.getVestUserIds();
        int random = this.getRandom(vestUsers.size());
        return vestUsers.get(random);
    }

    public Long getRandomVideoId(){
        List<Long> randomVideoIds = this.videoService.getRandomVideoIds();
        int random = this.getRandom(randomVideoIds.size());
        return randomVideoIds.get(random);
    }

    public void commentVideo(Long videoId){
        Long userId= this.getVestUserId();
        String contentTxt = this.getRedisComment();
        Long id = UniqueIDCreater.generateID();
        this.commentService.greatComment(id,userId,contentTxt,videoId);

    }

}
