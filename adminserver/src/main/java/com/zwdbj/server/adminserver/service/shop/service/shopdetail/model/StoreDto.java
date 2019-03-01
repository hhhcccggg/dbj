package com.zwdbj.server.adminserver.service.shop.service.shopdetail.model;

import com.zwdbj.server.adminserver.service.category.model.StoreServiceCategory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.*;

@ApiModel(value = "店铺基本信息")
public class StoreDto implements Serializable {

    private static final long serialVersionUID = 163897167013172088L;
    @ApiModelProperty(value = "店铺id")
    long id;
    @ApiModelProperty(value = "店铺名称")
    String name;
    @ApiModelProperty(value = "店主姓名")
    String contactName;
    @ApiModelProperty(value = "店主手机号")
    String contactPhone;
    @ApiModelProperty(value = "联系电话")
    String phone;
    @ApiModelProperty(value = "审核结果 通过，未通过")
    boolean reviewed;
    @ApiModelProperty(value = "审核状态 0：正常1：关闭,2审核中")
    int status;
    @ApiModelProperty(value = "审核结果原因")
    String reviewMsg;
    @ApiModelProperty(value = "店铺商标")
    String logoUrl;
    @ApiModelProperty(value = "封面图")
    String mainConverImage;
    @ApiModelProperty(value = "封面图地址")
    String coverImages;
    @ApiModelProperty(value = "额外服务范围")
    private List<StoreServiceCategory> extraServices;
    @ApiModelProperty(value = "营业时间")
    private List<OpeningHours> openingHours;
    @ApiModelProperty(value = "服务范围")
    private List<StoreServiceCategory> serviceScopes;
//    @ApiModelProperty(value = "开店时间，终端可用于直接显示")
//    private String openingHoursDisplay;
    @ApiModelProperty(value = "其他服务，终端可用于直接显示")
    private String extraServicesDisplay = "";
    @ApiModelProperty(value = "服务范围，终端可用于直接显示")
    private String serviceScopesDisplay;

    public List<StoreServiceCategory> getExtraServices() {
        return extraServices;
    }

    public void setExtraServices(List<StoreServiceCategory> extraServices) {
        this.extraServices = extraServices;
    }

    public List<OpeningHours> getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(List<OpeningHours> openingHours) {
        this.openingHours = openingHours;
    }

    public List<StoreServiceCategory> getServiceScopes() {
        return serviceScopes;
    }

    public void setServiceScopes(List<StoreServiceCategory> serviceScopes) {
        this.serviceScopes = serviceScopes;
    }


//    public String getOpeningHoursDisplay() {
//        openingHoursDisplay = "";
//        if (this.getOpeningHours() == null || this.getOpeningHours().size() == 0) {
//            return openingHoursDisplay;
//        }
//        HashMap<OpeningHours, String> map = new HashMap<>();
//        for (OpeningHours openingHours : this.openingHours) {
//            if (map.get(openingHours) == null) {
//                map.put(openingHours, openingHours.getDay() + ",");
//                continue;
//            }
//            map.put(openingHours, map.get(openingHours) + openingHours.getDay() + ",");
//
//        }
//        Set<OpeningHours> set = map.keySet();
//        Iterator<OpeningHours> iterator = set.iterator();
//        while (iterator.hasNext()) {
//            OpeningHours openingHours = iterator.next();
//            String s = map.get(openingHours);
//            String[] strs = s.split(",");
//            if (strs.length == 1) {
//                int openHour = openingHours.getOpenTime() / 3600;
//                int openMinute = (openingHours.getOpenTime() - openHour * 3600) / 60;
//                openingHoursDisplay = openingHoursDisplay + " 周" + strs[0] + " " + openHour + ":" + openMinute + "-";
//                int closeHour = openingHours.getCloseTime() / 3600;
//                int closeMinute = (openingHours.getCloseTime() - closeHour * 3600) / 60;
//                openingHoursDisplay = openingHoursDisplay + openHour + ":" + openMinute + "  ";
//                continue;
//            }
//            String week = "周";
//            for (String day : strs) {
//                week =week+ day + ",";
//            }
//            int openHour = openingHours.getOpenTime() / 3600;
//            int openMinute = (openingHours.getOpenTime() - openHour * 3600) / 60;
//
//            openingHoursDisplay = openingHoursDisplay + week + " " + openHour + ":" + openMinute + "-";
//            int closeHour = openingHours.getCloseTime() / 3600;
//            int closeMinute = (openingHours.getCloseTime() - closeHour * 3600) / 60;
//            openingHoursDisplay = openingHoursDisplay + openHour + ":" + openMinute + "  ";
//
//        }
//        return openingHoursDisplay;
//    }

    public String getExtraServicesDisplay() {
        if (this.getExtraServices() == null || this.getExtraServices().size() == 0) {
            extraServicesDisplay = "";
            return extraServicesDisplay;
        }
        List<String> names = new ArrayList<>();
        for (StoreServiceCategory a : this.getExtraServices()) {
            names.add(a.getCategoryName());
        }
        extraServicesDisplay = String.join(",", names);
        return extraServicesDisplay;
    }

    public String getServiceScopesDisplay() {
        if (this.getServiceScopes() == null || this.getServiceScopes().size() == 0) {
            serviceScopesDisplay = "";
            return serviceScopesDisplay;
        }
        List<String> names = new ArrayList<>();
        for (StoreServiceCategory a : this.getServiceScopes()) {
            names.add(a.getCategoryName());
        }
        serviceScopesDisplay = String.join(",", names);
        return serviceScopesDisplay;
    }

    public String getMainConverImage() {
        return mainConverImage;
    }

    public void setMainConverImage(String mainConverImage) {
        this.mainConverImage = mainConverImage;
    }

    public String getCoverImages() {
        return coverImages;
    }

    public void setCoverImages(String coverImages) {
        this.coverImages = coverImages;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReviewMsg() {
        return reviewMsg;
    }

    public void setReviewMsg(String reviewMsg) {
        this.reviewMsg = reviewMsg;
    }
}
