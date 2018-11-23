package com.zwdbj.server.operate.oprateService;

import com.zwdbj.server.service.comment.service.CommentService;
import com.zwdbj.server.service.pet.service.PetService;
import com.zwdbj.server.service.user.service.UserService;
import com.zwdbj.server.service.userDeviceTokens.service.UserDeviceTokensService;
import com.zwdbj.server.service.video.service.VideoService;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class OperateService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    UserService userService;
    @Autowired
    VideoService videoService;
    @Autowired
    CommentService commentService;
    @Autowired
    PetService petService;
    @Autowired
    UserDeviceTokensService userDeviceTokensService;

    public String getNickName(int length){
        String gg = "天,地,玄,黄,宇,宙,洪,荒,日,月,盈,昃,辰,宿,列,张,寒,来,暑,往,秋,收,冬,藏,闰,馀,成,岁,律,吕,调,阳,云," +
                "腾,致,雨,露,结,为,霜,金,生,丽,水,玉,出,昆,冈,剑,号,巨,阙,珠,称,夜,光,果,珍,李,柰,菜,重,芥,姜,海,咸,河," +
                "淡,鳞,潜,羽,翔,龙,师,火,帝,鸟,官,人,皇,始,制,文,字,乃,服,衣,裳,推,位,让,国,有,虞,陶,唐,吊,民,伐,罪,周," +
                "发,殷,汤,坐,朝,问,道,垂,拱,平,章,爱,育,黎,首,臣,伏,戎,羌,遐,迩,一,体,率,宾,归,王,鸣,凤,在,竹,白,驹,食," +
                "场,化,被,草,木,赖,及,万,方,盖,此,身,发,四,大,五,常,恭,惟,鞠,养,岂,敢,女,慕,贞,洁,男,效,才,良,知,过,必," +
                "改,得,能,莫,忘,罔,谈,彼,短,靡,恃,己,长,信,使,可,复,器,欲,难,量,墨,悲,丝,染,诗,赞,羔,羊,景,行,维,贤,克," +
                "念,作,圣,德,建,名,立,形,端,表,正,空,谷,传,声,虚,堂,习,听,祸,因,恶,积,福,缘,善,庆,尺,璧,非,宝,寸,语,助," +
                "阴,是,竞,资,父,事,君,曰,严,与,敬,孝,当,竭,力,忠,则,尽,命,临,深,履,薄,夙,兴,温,凊,似,兰,斯,馨,如,松,之," +
                "盛,川,流,不,息,渊,澄,取,映,容,止,若,思,言,辞,安,定,笃,初,诚,美,慎,终,宜,令,荣,业,所,基,籍,甚,无,竟,学," +
                "优,登,仕,摄,职,从,政,存,以,甘,棠,去,而,益,咏,乐,殊,贵,贱,礼,别,尊,卑,上,和,下,睦,夫,唱,妇,随,外,受,傅," +
                "训,入,奉,母,仪,诸,姑,伯,叔,犹,子,比,儿,孔,怀,兄,弟,同,气,连,枝,交,友,投,分,切,磨,箴,规,仁,慈,隐,恻,造," +
                "次,弗,离,节,义,退,颠,沛,匪,亏,性,静,情,逸,心,动,神,疲,守,真,志,满,逐,物,意,移,坚,持,雅,操,好,爵,自,縻," +
                "都,邑,华,夏,东,西,二,京,背,邙,面,洛,浮,渭,据,泾,宫,殿,盘,郁,楼,观飞,惊,图,写,禽,兽,画,彩,仙,灵,甲,帐," +
                "对,楹,肆,筵,设,席,鼓,瑟,吹,笙,升,阶,纳,陛,弁,转,疑,星,右,通,广,内,左,达,承,明,亦,聚,群,英,杜,漆,书,壁," +
                "经,府,罗,将,相,路,侠,槐,卿,户,封,八,县,家,给,千,兵,高,冠,陪,驰,誉,丹,青,九,州,禹,迹,百,郡,秦,并,岳,宗," +
                "泰,岱,禅,主,云,亭,雁,门,紫,塞,鸡,田,赤,城,昆,池,碣,石,巨,野,洞,庭,旷,远,绵,邈,岩,岫,杳,冥,治,本,于,农," +
                "务,资,稼,穑,俶,载,南,亩,我,艺,新,劝,赏,孟,轲,敦,素,史,鱼,秉,直,庶,几,中,庸,劳,谦,谨,敕,聆,音,察,理,鉴," +
                "貌,辨,色,贻,厥,嘉,猷,勉,其,祗,植,省,躬,诫,宠,增,抗,极,殆,近,林,皋,幸,即,两,疏,见,机,解,组,谁,庄,徘,徊" +
                "逼,索,居,闲,处,沉,默,寂,寥,求,古,寻,论,散,虑,逍遥,欣,晚,翠,梧桐,蚤,凋,陈,根,委,翳,落,叶,飘,摇,游,独," +
                "运,凌,摩,读,玩,市,寓,目,老,少,异,粮,侍,巾,夕,寐,蓝,笋,象,床,弦,歌,酒,宴,接,杯,举,觞,矫,手,顿,足,悦,豫," +
                "且,康,执,热,愿,凉,驴,犊,特,骇,跃,超,获,布,僚,丸,嵇,琴,阮,恬,笔,伦,纸,钧,巧,任,钓,释,纷,利,俗,竝,皆,佳," +
                "妙,毛,施,淑,姿,工,妍,笑,年,矢,每,催,曦,晖,朗,曜,璇玑,悬,晦,,者";
        String[] names = gg.split(",");
        StringBuilder nickName=new StringBuilder();
        for (int i = 0; i <length ; i++) {
            int r = (int)(Math.random()*700);
            String b = names[r];
           nickName.append(b);
        }
        return nickName.toString();
    }

    public String getPetNickName(){
        String gg = "小贝,Angel,虎虎,雅虎,混混,小狼,小虎,李小熊,大奔,达达妹,咪妹,麒麟,谜底,KA,波利,彼利,贝茜" +
                ",黑妮,艾德,蓝莓,阿郎,阿东,阿sir,Ti,SAM,哈雷,NANA,悠悠,哈喽,宾果,但丁,大帝,依恋,优卡,壮壮," +
                "元帅,虫宝,丸子,深爱的小虎,虎子,杰克,虎子,虎虎,小贝,威廉,基诺,尼尼,尼卡,黑虎,奥巴,牛牛," +
                "虎虎,巴顿,小无敌,亚瑟,笨笨,莱克思,阳阳,墨脱,吉利,欢欢,王小豹,黑豹,黑妞,阿尔萨斯,悍虎,白龙," +
                "黑虎,大黑,毛豆,包子,果果,桃子,咖喱,鱼丸,戴维,法拉利,水牛,凯撒,钢铁侠,超人,贝贝,卷卷,嘟嘟," +
                "毛毛,肉肉,小白,当当,豆豆,旺财,卷卷,可乐,加菲,嘟嘟,滴滴,蛋蛋,鲁卡,哈比,小花,闪电,威廉,巴克," +
                "查理,雷神,哈瑞,米奇,卷卷,闹闹,卡尔,熊熊,奈奈,香香,海豚,可可,若若,圆圆,小可,瑞瑞,球球,米菲," +
                "卡哇伊,贝贝,巧克力,璐璐,淘淘,雪糕,西瓜, 大米,奇奇,波波,疯子,跳跳,虫虫,大哥,贝宝,肉肉,皮蛋," +
                "宝宝,大壮,二蛋,大款,多多,强子,土豪,飞飞,小乖,娜娜,臭臭,八戒,丫丫,妞妞,公主,小静,花花,美美," +
                "丽丽,小白,大白,雪糕,雪妮,花花,宝马,棉花,云朵,王子,小雪,奶糖,橙子,桔子,阳阳,小菊,小美,小乖," +
                "香香,美莎,依依,黑子,小奇,熊二,安安,兜兜,贱贱,凶凶,笨笨,呆呆,呼呼,小米,可乐,虎虎,笨猪,咪咪," +
                "猫咪,豆豆,果果,洛克,捷克,恺撒,道格,阿呆,香菇,李白,馒头,豆儿,鼎鼎,旺财,小白,肥肥,公爵,王子," +
                "乐乐,球球,胡圈,鲁班七号,胡豆,胡椒,叮叮,当当,爱米,豆豆,爱贝,贝尔,呆呆,扑扑,兜兜,贱贱,小黑," +
                "肉包,肥包,哎呦,小歪,卡卡,滚滚,娇娇,大熊,多多,枫枫,奈奈,香香,阿飞,梦琪";
        String[] names = gg.split(",");
        int  random = this.getRandom(0,names.length);
        return names[random];
    }

    public void userNumber(){
        this.stringRedisTemplate.opsForValue().set("2018-11-20u","120",30,TimeUnit.DAYS);
        this.stringRedisTemplate.opsForValue().set("2018-11-21u","556",30,TimeUnit.DAYS);
        this.stringRedisTemplate.opsForValue().set("2018-11-22u","929",30,TimeUnit.DAYS);
        this.stringRedisTemplate.opsForValue().set("2018-11-23u","3586",30,TimeUnit.DAYS);
        this.stringRedisTemplate.opsForValue().set("2018-11-24u","4589",30,TimeUnit.DAYS);
        this.stringRedisTemplate.opsForValue().set("2018-11-25u","3930",30,TimeUnit.DAYS);
        this.stringRedisTemplate.opsForValue().set("2018-11-26u","3677",30,TimeUnit.DAYS);
        this.stringRedisTemplate.opsForValue().set("2018-11-27u","4821",30,TimeUnit.DAYS);
        this.stringRedisTemplate.opsForValue().set("2018-11-28u","4987",30,TimeUnit.DAYS);
        this.stringRedisTemplate.opsForValue().set("2018-11-29u","3434",30,TimeUnit.DAYS);
        this.stringRedisTemplate.opsForValue().set("2018-11-30u","3809",30,TimeUnit.DAYS);
        this.stringRedisTemplate.opsForValue().set("2018-12-1u","2789",30,TimeUnit.DAYS);
        this.stringRedisTemplate.opsForValue().set("2018-12-2u","3144",30,TimeUnit.DAYS);
        this.stringRedisTemplate.opsForValue().set("2018-12-3u","2681",30,TimeUnit.DAYS);
        this.stringRedisTemplate.opsForValue().set("2018-12-4u","4499",30,TimeUnit.DAYS);
        this.stringRedisTemplate.opsForValue().set("2018-12-5u","6087",30,TimeUnit.DAYS);
        this.videoNumber();
    }

    public void videoNumber(){
        this.stringRedisTemplate.opsForValue().set("2018-11-20v","120",30,TimeUnit.DAYS);
        this.stringRedisTemplate.opsForValue().set("2018-11-21v","929",30,TimeUnit.DAYS);
        this.stringRedisTemplate.opsForValue().set("2018-11-22v","631",30,TimeUnit.DAYS);
        this.stringRedisTemplate.opsForValue().set("2018-11-23v","538",30,TimeUnit.DAYS);
        this.stringRedisTemplate.opsForValue().set("2018-11-24v","551",30,TimeUnit.DAYS);
        this.stringRedisTemplate.opsForValue().set("2018-11-25v","1061",30,TimeUnit.DAYS);
        this.stringRedisTemplate.opsForValue().set("2018-11-26v","588",30,TimeUnit.DAYS);
        this.stringRedisTemplate.opsForValue().set("2018-11-27v","482",30,TimeUnit.DAYS);
        this.stringRedisTemplate.opsForValue().set("2018-11-28v","798",30,TimeUnit.DAYS);
        this.stringRedisTemplate.opsForValue().set("2018-11-29v","549",30,TimeUnit.DAYS);
        this.stringRedisTemplate.opsForValue().set("2018-11-30v","724",30,TimeUnit.DAYS);
        this.stringRedisTemplate.opsForValue().set("2018-12-1v","446",30,TimeUnit.DAYS);
        this.stringRedisTemplate.opsForValue().set("2018-12-2v","629",30,TimeUnit.DAYS);
        this.stringRedisTemplate.opsForValue().set("2018-12-3v","402",30,TimeUnit.DAYS);
        this.stringRedisTemplate.opsForValue().set("2018-12-4v","585",30,TimeUnit.DAYS);
        this.stringRedisTemplate.opsForValue().set("2018-12-5v","609",30,TimeUnit.DAYS);

    }

    public void newVestUser1(){
        String a = String.valueOf(UniqueIDCreater.generateID()).substring(9,17);
        String[] ss = {"3","5","7","8","9"};
        String s =ss[this.getRandom(0,5)];
        String phone ="1"+s+a;
        String avatarUrl ="http://res.pet.zwdbj.com/default_avatar.png";
        String nickName = "爪子用户";
        this.userService.newVestUser(phone,avatarUrl,nickName);

    }
    public void newVestUser2(int length){
        String a = String.valueOf(UniqueIDCreater.generateID()).substring(9,17);
        String[] ss = {"3","5","7","8","9"};
        String s =ss[this.getRandom(0,5)];
        String phone ="1"+s+a;
        int avatar = this.getRandom(0,80)+1;
        String avatarUrl ="http://dev.hd.res.pet.zwdbj.com/1%20%28"+avatar+"%29.jpg";
        String nickName = this.getNickName(length);
        this.userService.newVestUser(phone,avatarUrl,nickName);
    }

    public void newPet(long userId){
        int a = this.getRandom(0,2);
        if (a==0)return;
        int avatar = this.getRandom(0,104)+1;
        String avatarUrl ="http://dev.hd.res.pet.zwdbj.com/3%20%28"+avatar+"%29.jpg";
        String nickName = this.getPetNickName();
        long categoryId = 0L;
        int s = this.getRandom(0,2);
        if (s==0){
            categoryId = 252118610506551296L;
        }else {
            categoryId = 252116228808773632L;
        }
        this.petService.newPet(avatarUrl,userId,nickName,categoryId);
    }

    public void  newDeviceToken(long userId){
        int a = this.getRandom(0,2);
        String s1 = UUID.randomUUID().toString().replace("-", "");
        String s2 = UUID.randomUUID().toString().replace("-", "");
        if (a==0){
            s1 = s1+s2.substring(12,19);
            this.userDeviceTokensService.newDeviceToken(userId,s1,"android");
        }else {
            s1 = s1+s2.substring(7,25);
            this.userDeviceTokensService.newDeviceToken(userId,s1,"ios");
        }

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
                "吗?>默默点了个赞来表达我的喜欢>幸福就是天天见,请保持这个更新速度>滴~打卡,按时来吸一吸>可爱～>转发了～>" +
                "y>这是什么品种啊>这只真是超可爱的>好可爱～>转发,想养一只>好可爱啊～>不行了～好想偷回家>666>好萌~>哈哈>" +
                "炒鸡好看>非常喜欢这个>好萌,好乖>为什么我笑了,是我笑点低吗>超级萌qwq>卡哇伊～>偷猫狗的有没有～>666666" +
                "6666666>这个要怎么买啊>路过>这个视频我看了好几遍～>太棒了，真是太入境了>人家就想摸摸>在这里在这里，我" +
                "我～>第一次见，真可爱";
        stringRedisTemplate.opsForValue().set("REDIS_COMMENTSS",comments);

    }

    public int getRandom(int startIndex,int length){
        int random = (int)(Math.random()*(length-startIndex))+startIndex;
        return random;
    }

    public String getRedisComment(){
        if (this.stringRedisTemplate.hasKey("REDIS_COMMENTSS")){
           String comment =  this.stringRedisTemplate.opsForValue().get("REDIS_COMMENTSS");
           String[] comments = comment.split(">");
           int random = this.getRandom(0,comments.length);
           String contentTxt = comments[random];
           if (contentTxt==null || contentTxt.length()==0) contentTxt=".......";
           return contentTxt;
        }else {
            this.redisComments();
        }
        return "好好玩呀!";
    }

    public Long getVestUserId1(){
        List<Long> vestUsers = this.userService.getVestUserIds1();
        int random = this.getRandom(0,vestUsers.size());
        return vestUsers.get(random);
    }
    public Long getVestUserId2(){
        List<Long> vestUsers = this.userService.getVestUserIds2();
        int random = this.getRandom(0,vestUsers.size());
        return vestUsers.get(random);
    }

    public Long getRandomVideoId(){
        List<Long> randomVideoIds = this.videoService.getRandomVideoIds();
        int random = this.getRandom(0,randomVideoIds.size());
        return randomVideoIds.get(random);
    }

    public int commentVideo1(Long videoId) {
        String videoIds = videoId.toString();
        Long userId = this.getVestUserId1();
        String contentTxt = this.getRedisComment();
        int gg=0;
        List<String> list = new ArrayList<>();
        if (this.redisTemplate.hasKey(videoIds + ":"))
             list = this.redisTemplate.opsForList().range(videoIds + ":", 0, -1);
        if (list.contains(contentTxt)) {
            return gg;
        } else {
            this.redisTemplate.opsForList().leftPush(videoIds + ":", contentTxt);
        }
        Long id = UniqueIDCreater.generateID();
        gg = this.commentService.greatComment(id, userId, contentTxt, videoId);
        return gg;
    }




}
