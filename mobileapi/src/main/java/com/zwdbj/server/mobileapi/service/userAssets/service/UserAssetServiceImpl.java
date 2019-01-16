package com.zwdbj.server.mobileapi.service.userAssets.service;

import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.ecwid.consul.v1.ConsulClient;
import com.zwdbj.server.mobileapi.service.pay.alipay.service.AlipayBizService;
import com.zwdbj.server.mobileapi.service.userAssets.mapper.IUserAssetMapper;
import com.zwdbj.server.mobileapi.service.userAssets.model.*;
import com.zwdbj.server.pay.alipay.AlipayService;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.consulLock.unit.Lock;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserAssetServiceImpl implements IUserAssetService {
    @Autowired
    IUserAssetMapper userAssetMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private AlipayService alipayService;

    @Transactional
    public ServiceStatusInfo<Long> getCoinsByUserId(long userId) {
        boolean isExist = this.userAssetIsExistOrNot(userId);
        if (!isExist) {
            this.greatUserAsset(userId);
        }
        /*String key = "USERASSET_"+userId;
        Long coins;
        //加入缓存
        if (!this.stringRedisTemplate.hasKey(key)){
            coins= this.userAssetMapper.getCoinsByUserId(userId);
            this.stringRedisTemplate.opsForValue().set(key,String.valueOf(coins));
        }else {
            coins = Long.valueOf(this.stringRedisTemplate.opsForValue().get(key));
        }*/
        Long coins = this.userAssetMapper.getCoinsByUserId(userId);

        return new ServiceStatusInfo<>(0, "", coins);
    }

    @Transactional
    public ServiceStatusInfo<Long> getCoinsByUserId() {
        long userId = JWTUtil.getCurrentId();
        return getCoinsByUserId(userId);
    }

    @Transactional
    public int updateUserAsset(long coins) {
        long userId = JWTUtil.getCurrentId();
        return updateUserAsset(userId, coins);
    }

    @Transactional
    public int updateUserAsset(long userId, long coins) {
        long totalCoins = coins;
        if (coins<=0)totalCoins=0;
        int result = this.userAssetMapper.updateUserAsset(userId, coins,totalCoins);
        return result;
    }

    @Transactional
    public int greatUserAsset() {
        long userId = JWTUtil.getCurrentId();
        int result = this.greatUserAsset(userId);
        return result;
    }

    @Transactional
    public int greatUserAsset(long userId) {
        long id = UniqueIDCreater.generateID();
        int result = this.userAssetMapper.greatUserAsset(id, userId);
        this.getCoinsByUserId(userId);
        return result;
    }

    @Transactional
    public boolean userAssetIsExistOrNot(long userId) {
        int result = this.userAssetMapper.userAssetIsExistOrNot(userId);
        return result != 0;
    }


    /**
     * coinType
     */

    @Transactional
    public ServiceStatusInfo<UserCoinTypeModel> getUserCoinType(String type) {
        long userId = JWTUtil.getCurrentId();
        return getUserCoinType(userId, type);
    }

    @Transactional
    public ServiceStatusInfo<UserCoinTypeModel> getUserCoinType(long userId, String type) {
        boolean isExist = this.userCoinTypeIsExist(userId, type);
        if (!isExist) {
            this.greatUserCoinType(userId, type);
        }
        /*Long coins ;
        String key = "USERASSET_"+type+"_"+userId;
        if (!this.stringRedisTemplate.hasKey(key)){
            coins= this.userAssetMapper.getUserCoinType(userId,type);
            this.stringRedisTemplate.opsForValue().set(key,String.valueOf(coins));
        }else {
            coins = Long.valueOf(this.stringRedisTemplate.opsForValue().get(key));
        }*/
        UserCoinTypeModel model = this.userAssetMapper.getUserCoinType(userId, type);
        if (model != null) model.setMoney(model.getCoins() * 10);
        return new ServiceStatusInfo<>(0, "", model);
    }

    @Transactional
    public int greatUserCoinType(long userId, String type) {
        long id = UniqueIDCreater.generateID();
        int result = this.userAssetMapper.greatUserCoinType(id, userId, type);
        return result;

    }

    @Transactional
    public boolean userCoinTypeIsExist(long userId, String type) {
        int result = this.userAssetMapper.userCoinTypeIsExist(userId, type);
        return result != 0;
    }

    @Transactional
    public int updateUserCoinType(String type, int num) {
        long userId = JWTUtil.getCurrentId();
        return this.updateUserCoinType(userId, type, num);
    }

    @Transactional
    public int updateUserCoinType(long userId, String type, int num) {
        int result = this.userAssetMapper.updateUserCoinType(userId, type, num);
        return result;
    }

    @Transactional
    public int updateUserCoinTypeForEnCash(long userId, String type, int num) {
        int result = this.userAssetMapper.updateUserCoinTypeForEnCash(userId, type, num, -num);
        return result;
    }

    //coinDetails

    @Transactional
    public List<UserCoinDetailsModel> getUserCoinDetails(long userId) {
        List<UserCoinDetailsModel> userCoinDetailsModels = this.userAssetMapper.getUserCoinDetails(userId);
        List<UserCoinDetailsModel> u = new ArrayList<>();
        for (UserCoinDetailsModel model : userCoinDetailsModels) {
            if (model.getType().equals("ENCASH")) {
                u.add(model);
            } else if (model.getType().equals("TASK")) {
                u.add(model);
            } else if (model.getType().equals("INCOME")) {
                u.add(model);
            } else if (model.getType().equals("OTHER")) {
                u.add(model);
            } else if (model.getType().equals("PAY") && model.getStatus().equals("SUCCESS")) {
                u.add(model);
            }
        }
        return u;
    }

    @Transactional(readOnly = true)
    public List<UserCoinDetailsModel> getUserCoinDetails() {
        long userId = JWTUtil.getCurrentId();
        return getUserCoinDetails(userId);
    }

    @Override
    @Transactional
    public long addUserCoinDetail(UserCoinDetailAddInput input) {
        long userId = JWTUtil.getCurrentId();
        return addUserCoinDetail(userId, input);
    }

    @Override
    @Transactional
    public long addUserCoinDetail(long userId, UserCoinDetailAddInput input) {
        long id = UniqueIDCreater.generateID();
        this.userAssetMapper.addUserCoinDetail(id, userId, input);
        return id;
    }

    @Transactional
    public int addUserCoinDetailForEnCash(long userId, UserCoinDetailAddInput input, String tradeNo) {
        long id = UniqueIDCreater.generateID();
        int result = this.userAssetMapper.addUserCoinDetailForEnCash(id, userId, input, tradeNo);
        return result;
    }

    /**
     * 根据交易号查询金币是否
     *
     * @param tradeNo
     * @param tradeType
     * @return
     */
    @Override
    public boolean findCoinDetailByTrade(String tradeNo, String tradeType) {
        int a = this.userAssetMapper.findCoinDetailByTrade(tradeNo, tradeType);
        return a > 0;
    }

    /**
     * 内部
     *
     * @param userId
     * @param input
     * @return
     */
    @Transactional
    public int addUserCoinDetailSuccess(long userId, UserCoinDetailAddInput input) {
        long id = UniqueIDCreater.generateID();
        int result = this.userAssetMapper.addUserCoinDetailSuccess(id, userId, input);
        return result;
    }

    @Override
    public long addUserCoinDetailOnce(long userId, UserCoinDetailAddInput input) {
        long id = UniqueIDCreater.generateID();
        int result = this.userAssetMapper.addUserCoinDetail(id, userId, input);
        if (result == 1 && input.getStatus().equals("SUCCESS")) {
            boolean a = this.userCoinTypeIsExist(userId, "PAY");
            if (!a) this.greatUserCoinType(userId, "PAY");
            result = this.updateUserCoinType(userId, input.getType(), input.getNum());
            if (result == 1) {
                boolean b = this.userAssetIsExistOrNot(userId);
                if (!b) this.greatUserAsset(userId);
                result = this.updateUserAsset(userId, input.getNum());
                if (result == 1) {
                    return id;
                }
            }
        }
        return 0;
    }

    @Override
    @Transactional
    public int updateUserCoinDetail(UserCoinDetailModifyInput input) {
        UserAssetNumAndStatus u = this.userAssetMapper.findUserCoinDetailById(input.getId());
        int result = 0;
        if ("PROCESSING".equals(u.getStatus())) {
            result = this.userAssetMapper.updateUserCoinDetail(input);
            if (result == 1 && input.getStatus().equals("SUCCESS")) {
                boolean a = this.userCoinTypeIsExist(u.getUserId(), "PAY");
                if (!a) this.greatUserCoinType(u.getUserId(), "PAY");
                result = this.updateUserCoinType(u.getUserId(), input.getType(), u.getNum());
                if (result == 1) {
                    boolean b = this.userAssetIsExistOrNot(u.getUserId());
                    if (!b) this.greatUserAsset(u.getUserId());
                    result = this.updateUserAsset(u.getUserId(), u.getNum());
                    return result;
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }


    public List<BuyCoinConfigModel> findAllBuyCoinConfigs(String type) {
        return this.userAssetMapper.findAllBuyCoinConfigs(type);
    }

    public void userIsExist(long userId) {
        boolean a = this.userCoinTypeIsExist(userId, "TASK");
        if (!a) this.greatUserCoinType(userId, "TASK");
        boolean b = this.userCoinTypeIsExist(userId, "PAY");
        if (!b) this.greatUserCoinType(userId, "PAY");
        boolean c = this.userCoinTypeIsExist(userId, "OTHER");
        if (!c) this.greatUserCoinType(userId, "OTHER");
        boolean d = this.userCoinTypeIsExist(userId, "INCOME");
        if (!d) this.greatUserCoinType(userId, "INCOME");
        boolean e = this.userAssetIsExistOrNot(userId);
        if (!e) this.greatUserAsset(userId);

    }


    //视频的打赏详情

    /**
     * 视频的打赏详情
     */
    public ServiceStatusInfo<List<VideoTipDetails>> getVideoTipDetails(Long videoId) {
        List<VideoTipDetails> result = null;
        try {
            result = this.userAssetMapper.findVideoTipDetails(videoId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询视频打赏详情失败" + e.getMessage(), null);
        }
    }

    @Transactional
    public int addVideoTipDetail(long videoId, long userId, int tipCoins) {
        long id = UniqueIDCreater.generateID();
        int result = this.userAssetMapper.addVideoTipDetail(id, videoId, userId, tipCoins);
        return result;
    }

    /**
     * 绑定支付宝账号
     *
     * @param input
     * @param userId 谁需要绑定支付宝账号
     * @return 返回成功或失败
     */
    public ServiceStatusInfo<Object> bindAliAccount(AliAccountBindInput input, long userId) {
        ServiceStatusInfo<AlipaySystemOauthTokenResponse> accessTokenRes = this.alipayService.accessToken(input.getAuthCode(), input.getUserId());
        if (!accessTokenRes.isSuccess()) {
            return new ServiceStatusInfo<>(1, accessTokenRes.getMsg(), null);
        }
        ServiceStatusInfo<AlipayUserInfoShareResponse> userInfoRes = this.alipayService.userInfo(accessTokenRes.getData().getAccessToken());
        if (!userInfoRes.isSuccess()) {
            return new ServiceStatusInfo<>(1, userInfoRes.getMsg(), null);
        }
        BandingThirdInput bandingThirdInput = new BandingThirdInput();
        bandingThirdInput.setAccessToken(accessTokenRes.getData().getAccessToken());
        bandingThirdInput.setAvatarUrl(userInfoRes.getData().getAvatar());
        bandingThirdInput.setExpireIn(Long.parseLong(accessTokenRes.getData().getExpiresIn()));
        String nickName = userInfoRes.getData().getNickName();
        if (nickName == null) nickName = "支付宝已绑定";
        bandingThirdInput.setName(nickName);
        bandingThirdInput.setUniqueId(userInfoRes.getData().getUserId());
        bandingThirdInput.setType("ALIPAY");
        ServiceStatusInfo<Integer> result = this.bandingThird(userId, bandingThirdInput);
        if (result.isSuccess()) {
            return new ServiceStatusInfo<>(0, "", null);
        }
        return new ServiceStatusInfo<>(1, result.getMsg(), null);
    }

    /**
     * 提现：绑定第三方支付平台
     *
     * @param input
     * @return
     */
    public ServiceStatusInfo<Integer> bandingThird(long userId, BandingThirdInput input) {
        try {
            List<EnCashAccountModel> models = this.getMyEnCashAccounts(userId);
            if (models.size() > 0) return new ServiceStatusInfo<>(1, "已经绑定了提现账号", 0);
            long id = UniqueIDCreater.generateID();
            if (input.getName() == null) input.setName("");
            int result = this.userAssetMapper.bandingThird(id, userId, input);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "绑定失败", null);
        }
    }


    /**
     * @param id core_enCashAccounts中的ID
     * @return
     */
    public ServiceStatusInfo<Integer> unBandingThird(long id) {
        try {
            int result = this.userAssetMapper.unBandingThird(id);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "解绑失败", 0);
        }
    }

    /**
     * 获取我的提现账户
     *
     * @return
     */
    public List<EnCashAccountModel> getMyEnCashAccounts() {
        long userId = JWTUtil.getCurrentId();
        return getMyEnCashAccounts(userId);
    }

    public List<EnCashAccountModel> getMyEnCashAccounts(long userId) {
        List<EnCashAccountModel> models = this.userAssetMapper.getMyEnCashAccounts(userId);
        return models;
    }

    /**
     * 根据id获取提现账户
     */
    public EnCashAccountModel getEnCashAccountById(long id) {
        return this.userAssetMapper.getEnCashAccountById(id);
    }

    /**
     * 提现
     *
     * @param input
     * @return
     */
    @Transactional
    public ServiceStatusInfo<Integer> enCashMyCoins(EnCashInput input) {

        //校验提现账户是否正确后存在
        EnCashAccountModel enCashAccountModel = this.getEnCashAccountById(input.getPayAccountId());
        if (enCashAccountModel == null || enCashAccountModel.getUniqueId() == null || enCashAccountModel.getUniqueId().length() == 0) {
            return new ServiceStatusInfo<>(1, "没有找到提现账户", null);
        }
        long userId = JWTUtil.getCurrentId();
        if (userId != enCashAccountModel.getUserId()) return new ServiceStatusInfo<>(1, "没有找到提现账户", null);
        ConsulClient consulClient = new ConsulClient("localhost", 8500);	// 创建与Consul的连接
        Lock lock = new Lock(consulClient, "mobileapi","enCash-lockKey:"+String.valueOf(userId));
        try {
            if (lock.lock(true,500L,1)){
                long id = UniqueIDCreater.generateID();
                int coins = input.getRmbs() / 10;
                long allCoins = this.getUserCoinType(userId, "INCOME").getData().getCoins();
                if (allCoins<=0)return new ServiceStatusInfo<>(1, "没有足够的金币进行提现", null);
                if (allCoins < coins) {
                    return new ServiceStatusInfo<>(1, "没有足够的金币进行提现", null);
                }
                //增加提现详情
                int result = this.userAssetMapper.addEnCashDetail(id, userId, coins, input);
                int result2 = 0;
                if (result == 1) {
                    UserCoinDetailAddInput userCoinDetailAddInput = new UserCoinDetailAddInput();
                    userCoinDetailAddInput.setNum(-coins);
                    userCoinDetailAddInput.setTitle("提现" + coins + "金币");
                    userCoinDetailAddInput.setType("ENCASH");
                    userCoinDetailAddInput.setExtraData("");
                    //提现时增加金币详情
                    result = this.addUserCoinDetailForEnCash(userId, userCoinDetailAddInput, String.valueOf(id));
                    if (result == 1) {
                        //提现时更新类别金币总数
                        result = this.updateUserCoinTypeForEnCash(userId, "INCOME", -coins);
                        if (result == 1) {
                            //提现时更新金币总数
                            result2 = this.updateUserAsset(userId, -coins);
                        }
                    }
                }
                if (result2 == 0) {
                    return new ServiceStatusInfo<>(1, "提现失败", result2);
                } else {
                    return new ServiceStatusInfo<>(0, "", result2);
                }
            }

        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "提现失败", null);
        }finally {
            lock.unlock();
        }
        return new ServiceStatusInfo<>(1, "提现失败" , null);

    }


    @Override
    public BuyCoinConfigModel findCoinConfigByProductId(String productId, String type) {
        return this.userAssetMapper.findCoinConfigByProductId(productId, type);
    }

    /**
     * 用户支付订单减去的金币
     *
     * @param coins          需要支付的金币数
     * @param userId         用户id
     * @param productOrderId 订单id
     * @return true 为成功
     */
    @Transactional
    @Override
    public boolean minusUserCoins(int coins, long userId, long productOrderId) {

        //获取用户金币类型数量详情
        int task = (int) this.getUserCoinType(userId, "TASK").getData().getCoins();
        int pay = (int) this.getUserCoinType(userId, "PAY").getData().getCoins();
        int other = (int) this.getUserCoinType(userId, "OTHER").getData().getCoins();
        int AllCoins = coins;

        if (task >= coins) {
            //task类型的金币大于等于订单支付，则全部用task支付
            UserCoinDetailAddInput addTaskInput = new UserCoinDetailAddInput();
            addTaskInput.setNum(AllCoins);
            addTaskInput.setType("ORDER");
            addTaskInput.setTitle("支付订单消费");
            addTaskInput.setExtraData(String.valueOf(productOrderId));

            //修改用户金币明细
            this.addUserCoinDetailSuccess(userId, addTaskInput);
            this.updateUserCoinType(userId, "TASK", -coins);
            this.updateUserAsset(userId, -AllCoins);

            return true;
        } else {
            if (task != 0) {
                //减去task的金币后仍需要支付的金币数
                coins = coins - task;
                this.updateUserCoinType(userId, "TASK", -task);
            }
            if (pay >= coins) {
                UserCoinDetailAddInput addPayInput = new UserCoinDetailAddInput();
                addPayInput.setNum(AllCoins);
                addPayInput.setType("ORDER");
                addPayInput.setTitle("支付订单消费");
                addPayInput.setExtraData(String.valueOf(productOrderId));

                this.addUserCoinDetailSuccess(userId, addPayInput);
                this.updateUserCoinType(userId, "PAY", -coins);
                this.updateUserAsset(userId, -AllCoins);

                return true;
            } else {
                if (pay != 0) {
                    coins = coins - pay;//减去pay的金币后仍需要支付的金币数
                    this.updateUserCoinType(userId, "PAY", -pay);
                }
                if (other >= coins) {
                    UserCoinDetailAddInput addOtherInput = new UserCoinDetailAddInput();
                    addOtherInput.setNum(AllCoins);
                    addOtherInput.setType("ORDER");
                    addOtherInput.setTitle("支付订单消费");
                    addOtherInput.setExtraData(String.valueOf(productOrderId));;

                    this.addUserCoinDetailSuccess(userId, addOtherInput);
                    this.updateUserCoinType(userId, "OTHER", -coins);
                    this.updateUserAsset(userId, -AllCoins);

                    return true;
                } else {
                    if (other != 0) {
                        coins = coins - other;//减去other的金币后还需要支付的金币数
                        this.updateUserCoinType(userId, "OTHER", -other);
                    }
                    UserCoinDetailAddInput addIncomeInput = new UserCoinDetailAddInput();
                    addIncomeInput.setNum(AllCoins);
                    addIncomeInput.setType("ORDER");
                    addIncomeInput.setTitle("支付订单消费");
                    addIncomeInput.setExtraData(String.valueOf(productOrderId));
                    this.addUserCoinDetailSuccess(userId, addIncomeInput);
                    this.updateUserCoinType(userId, "INCOME", -coins);
                    this.updateUserAsset(userId, -AllCoins);
                    return true;
                }
            }
        }
    }


    /**
     * 判断用户是否为当天第一次打赏
     */
    public boolean isFirstPlayTout(long userId){
        int result = this.userAssetMapper.isFirstPlayTout(userId);
        return result==0;
    }

    /**
     * 用户任务增加金币
     */

    @Transactional
    public void userPlayCoinTask(UserCoinDetailAddInput input,long userId,String type,int coins){
        // TODO 改变金币任务状态 参数要加入任务的id,处理任务
        String key = "user_everydayTask:"+userId;
        ConsulClient consulClient = new ConsulClient("localhost", 8500);    // 创建与Consul的连接
        Lock lock = new Lock(consulClient, "mobileapi",  key);
        try {
            if (lock.lock(true, 500L, 1)){
                this.addUserCoinDetail(userId,input);
                this.updateUserCoinType(userId,type,coins);
                this.updateUserAsset(userId,coins);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }
}