package com.zwdbj.server.operate.oprateService;

import com.zwdbj.server.discoverapiservice.videorandrecommend.service.VideoRandRecommendService;
import com.zwdbj.server.service.comment.service.CommentService;
import com.zwdbj.server.service.pet.service.PetService;
import com.zwdbj.server.service.user.service.UserService;
import com.zwdbj.server.service.userDeviceTokens.service.UserDeviceTokensService;
import com.zwdbj.server.service.video.service.VideoService;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    @Autowired
    VideoRandRecommendService videoRandRecommendService;

    private Logger logger = LoggerFactory.getLogger(OperateService.class);

    public String getNickName(){
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
        int length= this.getRandom(2,5);
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
        this.stringRedisTemplate.opsForValue().set("2019-02-10u","7245",50,TimeUnit.DAYS);
        this.stringRedisTemplate.opsForValue().set("2019-02-11u","6171",50,TimeUnit.DAYS);
        int[] userNums = {7118,6252,7933,8903,8296,8671,8646,7568,6524,8968,8734,8462,7967,8941,8868,8632,7861,6841,8398,8666,6457,8057,
                6976,7973,8496,8705,6623,7650,7641,7644,8619,7339,6297,4315,7543,7876,7920,5505,5934,5678};
        for(int i = 1;i<10;i++){
            this.stringRedisTemplate.opsForValue().set("2019-01-0"+i+"u",String.valueOf(userNums[i-1]),30,TimeUnit.DAYS);
            this.stringRedisTemplate.opsForValue().set("2019-02-0"+i+"u",String.valueOf(userNums[i+30]),50,TimeUnit.DAYS);
        }
        for(int i = 10;i<32;i++){
            this.stringRedisTemplate.opsForValue().set("2019-01-"+i+"u",String.valueOf(userNums[i-1]),30,TimeUnit.DAYS);
        }
        this.videoNumber();
    }

    public void videoNumber(){
        this.stringRedisTemplate.opsForValue().set("2019-02-10v","1095",50,TimeUnit.DAYS);
        this.stringRedisTemplate.opsForValue().set("2019-02-11v","879",50,TimeUnit.DAYS);
        int[] videoNums = {1335,807,897,651,891,447,1275,1467,861,1068,684,405,558,444,450,1245,456,1011,852,483,1065,567,474,627,375,1377,1194,
                378,921,393,609,681,996,867,768,1146,711,1140,327,789};
        for(int i = 1;i<10;i++){
            this.stringRedisTemplate.opsForValue().set("2019-01-0"+i+"v",String.valueOf(videoNums[(i-1)]),30,TimeUnit.DAYS);
            this.stringRedisTemplate.opsForValue().set("2019-02-0"+i+"v",String.valueOf(videoNums[(i+30)]),50,TimeUnit.DAYS);
        }
        for(int i = 10;i<32;i++){
            this.stringRedisTemplate.opsForValue().set("2019-01-"+i+"v",String.valueOf(videoNums[(i-1)]),30,TimeUnit.DAYS);
        }
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
    public void newVestUser2(){
        String a = String.valueOf(UniqueIDCreater.generateID()).substring(9,17);
        String[] ss = {"3","5","7","8","9"};
        String s =ss[this.getRandom(0,5)];
        String phone ="1"+s+a;
        int avatar = this.getRandom(0,80)+1;
        String avatarUrl ="http://dev.hd.res.pet.zwdbj.com/1%20%28"+avatar+"%29.jpg";
        String nickName = this.getNickName();
        this.userService.newVestUser(phone,avatarUrl,nickName);
    }

    public String getNewPhone(){
        String phone="";
        int b = this.getRandom(0,14);
        int c = this.getRandom(0,13);
        String a = String.valueOf(UniqueIDCreater.generateID()).substring(b,b+4)+String.valueOf(UniqueIDCreater.generateID()).substring(c,c+5);
        int[] ss = {3,5,7,8,9};
        int s =ss[this.getRandom(0,5)];
        if (s==9){
            phone="199"+a.substring(0,8);
        }else{
            phone ="1"+s+a;
        }
        return phone;
    }
    public String getOneOnlyPhone(){
        String phone = "";
        boolean isExit = false;
        do {
            phone = this.getNewPhone();
            isExit = this.userService.phoneIsExit(phone);
        }while (isExit);
        return phone;
    }


    /**
     * 4:微信 8:QQ 16:微博
     * @param type
     */
    public void newThirdUsers(int type, Date createTime){
        String phone = this.getOneOnlyPhone();
        int avatar = this.getRandom(0,80)+1;
        String avatarUrl ="http://dev.hd.res.pet.zwdbj.com/1%20%28"+avatar+"%29.jpg";
        String s1 = UUID.randomUUID().toString().replace("-", "");
        String s2 = UUID.randomUUID().toString().replace("-", "");
        int device = this.getRandom(0,2);
        String nickName = this.getNickName();
        String thirdOpenId="";
        String accessToken="";
        if (type==4){
            String s3 = UUID.randomUUID().toString().replace("-", "");
            String s4 = UUID.randomUUID().toString().replace("-", "");
            String s5 = UUID.randomUUID().toString().replace("-", "");
            if (device==0){
                thirdOpenId="oILKI0"+s1.substring(4,26);
            }else if (device==1){
                thirdOpenId="oxzu51"+s1.substring(3,25);
            }
            accessToken="15_"+s2+"_"+s3+"_"+s4+s5.substring(10,19);


        }else if (type==8){
            thirdOpenId=s1;
            accessToken=s2;

        }else if (type==16){
            thirdOpenId=String.valueOf(UniqueIDCreater.generateID()).substring(5,15);
            accessToken="2.00"+s1.substring(3,31);

        }
        if ("2000-12-12".equals(new SimpleDateFormat("yyyy-MM-dd").format(createTime))){
            this.userService.newThirdUsers(phone,avatarUrl,nickName,thirdOpenId,device,type,accessToken);
        }
        this.userService.newThirdUsers2(phone,avatarUrl,nickName,thirdOpenId,device,type,accessToken,createTime);
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

    public void  newDeviceToken(long userId,int device){
        String s1 = UUID.randomUUID().toString().replace("-", "");
        String s2 = UUID.randomUUID().toString().replace("-", "");
        if (device==0){
            s1 = s1+s2.substring(12,20);
            this.userDeviceTokensService.newDeviceToken(userId,s1,"android");
        }else {
            s1 = s1+s2.substring(7,26);
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
                "我～>第一次见，真可爱> 确实很萌呀~>可爱又乖巧，好喜欢！>我有个大胆的想法>这也太美了吧>好想养一只，就是" +
                "太贵了>被惊艳到了>矮油  萌翻了！>可爱极了~！>真干净，喜欢这种毛色>好帅气呢>哈哈，这小模样好滑稽好可爱！" +
                "！>莫名觉得可爱,很棒！>好想要一只啊,好想拥有一只啊>毛色真好啊！亮！>你养得好！我真想摸摸>长腿公主哦" +
                "！>很不错>家族看到你的发型决定将你升级为族宠>请问，你这个的品种是？>哈哈哈哈哈哈哈>哈哈哈>呆萌>笑到肚" +
                "子疼>充满阳光的笑容>绝对是治愈系的>嘿嘿>转发>萌萌哒>奶凶奶凶的小家伙>真是可爱死了吧>在委屈的小眼神>它" +
                "这是怎么了~~~~~>它看到了什么？>好喜欢,你是要萌死我吗?>厉害厉害>咋啦？老弟>好漂亮>好可爱啊啊啊啊啊啊啊" +
                "啊>萌化了>有一种狗叫做别人家的狗>好漂亮呀>好美>太美了>漂亮>帅呀>太帅了>飘逸>路过>神配合>太优秀了！>莫" +
                "名的喜感>看一次笑一次>火了火了>哈哈哈哈笑死我了>我竟然看了好几遍……有毒>确认过眼神>好乖呀>可怜的小可" +
                "爱   哈哈哈>我家也是>这颜值更高了>卡哇伊！！！！>好看>聪明，能吃>完全符合>想到了我家毛孩子>挺可爱的>我" +
                "的天！真乖，真听话！>好帅、>好聪明！！>厉害了>超可爱>太懂事了吧>跟我家的那个一样>给你点个赞>点赞>真听" +
                "话>666666666666>好可爱！！！！！好聪明！！！！               >么么哒~！>太聪明了>腻害>哇，真听话>有灵" +
                "性啊>开心笑呵呵>好干净、>小黄好可爱>可以>哇，我的天，真的太卡哇伊了啊！！！真的想抱走>心都要被可爱融化" +
                "了>喜欢没道理>这…………>好肉呀>哎呀！！！>我也想要一个>喜欢你开心，宝贝真可爱>爱死你了>好漂亮，好喜欢" +
                ">宝宝好可爱>可爱的乖宝宝>小可爱、>好萌啊，看着都想摸>转发，转发，转发>好治愈>好像拥有你>超喜欢>成精" +
                "了>呵呵，真厉害，好萌的狗狗>怎么可以这么乖>卡哇伊~~~~>活得不如宠物系列>我真的没笑………………哈哈哈" +
                "哈>机智得一匹>宠辱不惊>你笑了没啊！！！哈哈哈>我竟然没有笑>这就是差距>嗨起来>有毒吧>来呀，跳起来呀>好" +
                "嗨哟，感觉人生已经到达了高潮>哈哈哈哈哈乐死我了>没毛病>太优秀了>有对象的艾特对象，没对象的在这评论>摇摇" +
                "晃晃~~~>一脸懵逼>干啥呢？小老弟>好可爱吖>要养>老夫的少女心啊~都化了~>对于可爱的宠物无法抵抗>超级喜欢>可" +
                "爱，肉肉的小爪子>萌化了~！！！！>好呆呀这只>你就是呆萌本呆了>同款肤色>啊！~~~~~>立！刻！马！上！把！他" +
                "！抱！上！>求关注>不要太萌>不要太可爱>是！谁！带！的！头！>可爱的很>目测会火，火了叫我>我也会去试试" +
                "？>这是怎么训练出来的呀？>好样的>厉害厉害>我不敢养，怕他嫌我笨>会玩>乖巧>好好玩，感觉要飞起来了>宝贝：" +
                "我做错了什么？>你牛>快来我怀里>莫名感觉有节奏>打卡>又看了一遍>睡前打卡>不行了，我要对它亲亲 抱抱 举高" +
                "高>小天使>啊啊啊！！！好蠢萌的步伐！>好肉啊>我把塔偷走，它会报警不>露出了姨母般的微笑>快到我的碗里来>快" +
                "来快来！~>捕捉一只小可爱>你家住哪儿？我要偷了>快来小可爱>对着屏幕亲了无数下>这个配音绝对了！！>儿子一岁" +
                "多已经沉迷于这个视频无法自拔>请给我来一打>卡姿兰大眼睛>好大的眼睛>我看了不止三遍>水灵的小眼睛>哇哇哇，" +
                "兼职吸引人，太可爱了>哈哈哈哈哈，好搞笑>和谐>笑得好开心>意料之外>好逗，哈哈哈哈>哇~~~    >它怎么做这么" +
                "乖，还这么配合>这是洋娃娃吗？>生活终于对我这只小喵咪下手了>天哪，心真的被萌化了，真化了。>主人你想干啥" +
                "？>好坏呀>我可以打包带走吗？>会玩儿>可好玩了>哈哈，太可爱了，呆萌呆萌的>我家那只也是这样的>这就是为嘛铲" +
                "屎官的日常>成精了>转发 嘻嘻嘻嘻>你好>搞笑>笑了～～>转发 太逗了吧>好厉害,太喜欢这只宠物了>好啊" +
                ">转发 没毛病>卖给我多少钱>棒棒哒>这些都是真的吗>学到了 哈哈哈>转发 6666>哈哈哈,看看这个>厉害了" +
                ">转发 太牛了>笑死我了,哈哈哈哈哈!>嗨>好嗨皮!>这是在撒娇吗>转发分享>有点肥>可爱到爆了>哈哈,好可爱的宠物" +
                ">hggz～～？？？>我也要养一只～>内心戏很丰富 哈哈>每次看到萌宠都会很想她>你给我起来>像我好朋友>戏精" +
                ">你这是要萌死我吗？>厉害厉害>咋了？老弟>好大,么么哒>太可爱了趴>工作>敲萌～～>转发 待亲>转发  亲嘴>舒服" +
                ">我家的也是这样……>啊,这是什么>在想什么呢>没有抵抗力>眼睛很漂亮>真是超级可爱～>怎么回事,小老弟" +
                ">怀疑人生>难受,整天刷这些小宠物,鼻血都不够用了>好可爱啊,小宠物>多少一只？亲嘴>好阔爱 亲嘴" +
                ">哇!!!萌萌的!!>好看就是萌>这是什么品种>很可爱,笔芯～>^_^yy>嗯,可爱666>可爱加漂亮>小脸蛋怎么这么可爱" +
                ">心化了>好可爱>可以送给我吗～～真好>转发 小宝贝>啊>我一定会 关注的,真萌>小可爱>多拍点,不够看啊" +
                ">音乐好听～>萌死我了哎呦>可爱的不得了>多大了这个？>我也想要一只!>它是什么品种？>忍住,别笑" +
                ">转发  打扮一下>牛>被自己迷倒了>这个app里任何一只动物都不能按动物来看待>这年头宠物都成精了>绝了" +
                ">都想上热门啊 哈哈>都逆天了>神啊>买了佛冷>漂亮  亲嘴>!!!>好逗>我怎么这么好看～>飘过～" +
                ">为啥别人家的宠物都这么好看>卧槽!（此评论虽然才2个字,但语法严谨,用词工整,结构巧妙,读起来朗朗上口," +
                "可谓言简意赅,足见评论人扎实的文字功底,以及信手拈来的写作技巧和惨绝人寰的创作能力,实在是佩服）" +
                ">我笑>可耐啊>一堆肉>太顽皮,太可爱了>对于可爱的宠物无法抵抗>妈呀这太可爱了>过来让我亲亲>啊!鼻血!" +
                ">我家也有>手动点个赞>它的主人一定是个有钱人>我也想要一只一摸一样的>这个名字好熟悉>不得了不得了" +
                ">会火>糟了  是心动的感觉>哈哈 厉害了>转发  别人家的>终于遇见一个不撒狗粮的了>我竟然让一只宠物给撩了" +
                ">嗯,可以哦［>天哪,太可爱了>快到怀里来～>要摸摸头>别人家的都很乖,我家的恨不得房顶给掀了>我的心哦>点赞" +
                ">这个才是真爱>我想咬一口>好想抱抱它>哇怎么可以这样,我女儿看了要我买回家>受不了了>太可爱了^_^" +
                ">这是充了vip的节奏>不简单啊>真是优秀～>人才啊～>哇还是这么可爱>看你>哈哈哈 毛色洋气>用我老公跟你换一只～" +
                ">hmm 被你玩坏了>我爱这个小可爱>同款同款 哈哈>不多说,就是这么可爱>为什么你们的都那么乖>羞羞～" +
                ">公的还是母的？>卡哇伊>怎么办？想咬一口>喜欢>对宠物一点抵抗力都没有>这样的给我来一打!>太像我家的那只了" +
                ">哇塞～>沙发>来不及看,先评论>前排求翻>这都是凭实力的>来了老弟>可以可以>阔以阔以>没错>到位>技术到位>帅" +
                ">想亲他一口>所有的宠物都那么逗吗>笑出声了>嘛也>真会玩>很实在啊>羡慕!好口耐!>这是颜值米的说了" +
                ">又刷到了 真开心>秀儿";
        stringRedisTemplate.opsForValue().set("REDIS_COMMENTS",comments);

    }

    public int getRandom(int startIndex,int length){
        int random = (int)(Math.random()*(length-startIndex))+startIndex;
        return random;
    }

    public String getRedisComment(){
        if (this.stringRedisTemplate.hasKey("REDIS_COMMENTS")){
           String comment =  this.stringRedisTemplate.opsForValue().get("REDIS_COMMENTS");
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
            logger.info("视频id:"+videoIds+",评论内容为："+contentTxt);
            int gg=0;
            if (this.redisTemplate.hasKey(videoIds + ":")){
                List<String>  list = this.redisTemplate.opsForList().range(videoIds + ":", 0, -1);
                if (list==null || list.size()==0)return gg;
                if (list.contains(contentTxt)){
                    return gg;
                }else {
                    this.redisTemplate.opsForList().leftPush(videoIds + ":",contentTxt);
                }
            } else {
                this.redisTemplate.opsForList().leftPush(videoIds + ":",contentTxt);
            }
            gg = this.commentService.greatComment(userId, contentTxt, videoId);
            return gg;

    }
    public void get1300Videos(){
        this.videoService.get1300Videos();
    }

    public void allReviewedVideosToCache() {
        List<Long> ids = this.videoService.getAllReviewedVideos();
        logger.info("读取到的视频数量："+ids.size());
        for (Long id:ids) {
            this.videoRandRecommendService.pushNewVideo(id);
        }
    }

    public void allVideoNum(){
        this.stringRedisTemplate.opsForValue().set("OPERATE_ALL_VIDEO_NUM","13309");
    }


}
