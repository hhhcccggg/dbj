package com.zwdbj.server.mobileapi.config;

import com.zwdbj.server.es.common.ESIndex;
import com.zwdbj.server.es.common.ESType;
import com.zwdbj.server.es.service.ESUtilService;
import com.zwdbj.server.mobileapi.service.video.service.VideoService;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * es初始化
 */
@Component
public class ElasticsearchInit implements ApplicationRunner {

    @Autowired
    private ESUtilService esUtilService;

    @Autowired
    private VideoService videoService;
    @Autowired
    ProductService productServiceImpl;


    @Override
    public void run(ApplicationArguments args) throws Exception {
       
       // createVideo();
       // createStore();
    }

    /**
     * 创建视频索引和数据
     *
     * @throws IOException
     */
    private void createVideo() throws IOException {
        String index = ESIndex.VIDEO;
        String type = ESIndex.VIDEO_TYPE;
        if (!esUtilService.isIndex(index)) {
            return;
        }
        Map<String, Object> mapping = new HashMap<>();
        Map<String, Object> properties = new HashMap<>();
        properties.put("id", ESType.getLongt());
        properties.put("createTime", ESType.getDate());
        properties.put("title", ESType.getIk_max_word());
        properties.put("coverImageUrl", ESType.getText());
        properties.put("coverImageWidth", ESType.getText());
        properties.put("coverImageHeight", ESType.getText());
        properties.put("firstFrameUrl", ESType.getText());
        properties.put("firstFrameWidth", ESType.getText());
        properties.put("firstFrameHeight", ESType.getText());
        properties.put("videoUrl", ESType.getText());
        properties.put("linkPets", ESType.getText());
        properties.put("tags", ESType.getIk_max_word());
        properties.put("longitude", ESType.getText());
        properties.put("latitude", ESType.getText());
        properties.put("location", ESType.getGeo_point());
        properties.put("address", ESType.getText());
        properties.put("recommendIndex", ESType.getLongt());
        properties.put("playCount", ESType.getLongt());
        properties.put("commentCount", ESType.getLongt());
        properties.put("heartCount", ESType.getLongt());
        properties.put("shareCount", ESType.getLongt());
        properties.put("musicId", ESType.getLongt());
        properties.put("status", ESType.getLongt());
        properties.put("categoryId", ESType.getLongt());
        properties.put("linkProductCount", ESType.getLongt());
        properties.put("rejectMsg", ESType.getText());
        properties.put("userId", ESType.getLongt());
        properties.put("userNickName", ESType.getIk_max_word());
        properties.put("userAvatarUrl", ESType.getText());
        properties.put("type", ESType.getText());
        mapping.put("properties", properties);
        List<Map<String, String>> maps = videoService.selectES();
        esUtilService.createIndexImportData(mapping, maps, index, type, "id");
    }

    /**
     * 创建商品索引和数据
     *
     * @throws IOException
     */
    private void createProduct() throws IOException {
        String index = ESIndex.PRODUCT;
        String type = ESIndex.PRODUCT_TYPE;
        if (!esUtilService.isIndex(index)) {
            return;
        }
        Map<String, Object> mapping = new HashMap<>();
        Map<String, Object> properties = new HashMap<>();
        properties.put("skuId", ESType.getLongt());
        properties.put("id", ESType.getLongt());
        properties.put("storeId", ESType.getLongt());
        properties.put("grade", ESType.getLongt());
        properties.put("originalPrice", ESType.getLongt());
        properties.put("promotionPrice", ESType.getLongt());
        properties.put("brandId", ESType.getLongt());
        properties.put("categoryId", ESType.getLongt());
        properties.put("inventory", ESType.getLongt());
        properties.put("sales", ESType.getLongt());
        properties.put("productType", ESType.getLongt());
        properties.put("attrs", ESType.getText());
        properties.put("attimageUrlsrs", ESType.getText());
        properties.put("videoUrl", ESType.getText());
        properties.put("numberId", ESType.getText());
        properties.put("detailDescription", ESType.getText());
        properties.put("ruleDescription", ESType.getText());
        properties.put("notes", ESType.getText());
        properties.put("productDetailType", ESType.getText());
        properties.put("name", ESType.getIk_max_word());
        properties.put("subName", ESType.getText());
        properties.put("searchName", ESType.getText());
        properties.put("marketName", ESType.getText());
        properties.put("sellerPoint", ESType.getText());
        properties.put("weight", ESType.getLongt());
        properties.put("limitPerPerson", ESType.getLongt());
        properties.put("createTime", ESType.getDate());
        properties.put("supportCoin", ESType.getBooleant());
        mapping.put("properties", properties);
        List<Map<String, String>> maps = productServiceImpl.selectEs();
        esUtilService.createIndexImportData(mapping, maps, index, type, "id");
    }

    private void createStore() throws IOException {
        String index = ESIndex.SHOP;
        String type = ESIndex.SHOP_TYPE;
        if (!esUtilService.isIndex(index)) {
            return;
        }
        Map<String, Object> mapping = new HashMap<>();
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", ESType.getIk_max_word());
        properties.put("contactName", ESType.getIk_max_word());
        properties.put("contactPhone", ESType.getKeyword());
        properties.put("latitude", ESType.getDouble());
        properties.put("longitude", ESType.getDouble());
        properties.put("location", ESType.getGeo_point());
        properties.put("address", ESType.getIk_max_word());
        properties.put("grade", ESType.getInteger());
        properties.put("status", ESType.getInteger());
        properties.put("mainConverImage", ESType.getKeyword());
        properties.put("coverImages", ESType.getKeyword());
        properties.put("logoUrl", ESType.getKeyword());
        properties.put("cityId", ESType.getInteger());
        properties.put("cityLevel", ESType.getKeyword());
        properties.put("stopService", ESType.getBooleant());
        properties.put("reviewed", ESType.getBooleant());
        properties.put("type", ESType.getKeyword());
        properties.put("qq", ESType.getKeyword());
        properties.put("expireTime", ESType.getKeyword());
        properties.put("level", ESType.getInteger());
        properties.put("legalSubjectId", ESType.getLongt());
        properties.put("categoryId", ESType.getLongt());
        Map<String, Object> storeProductsProperties = new HashMap<>();

        storeProductsProperties.put("productId", ESType.getLongt());
        storeProductsProperties.put("skuId", ESType.getLongt());
        storeProductsProperties.put("storeId", ESType.getLongt());
        storeProductsProperties.put("name", ESType.getIk_max_word());
        storeProductsProperties.put("productType", ESType.getLongt());
        storeProductsProperties.put("productDetailType", ESType.getKeyword());

        properties.put("storeProducts", ESType.getArray(storeProductsProperties));

        Map<String, Object> serviceScopesProperties = new HashMap<>();
        serviceScopesProperties.put("id", ESType.getLongt());
        serviceScopesProperties.put("storeId", ESType.getLongt());
        serviceScopesProperties.put("serviceScopeId", ESType.getLongt());
        serviceScopesProperties.put("notes", ESType.getKeyword());
        serviceScopesProperties.put("status", ESType.getInteger());
        serviceScopesProperties.put("categoryName", ESType.getKeyword());

        properties.put("serviceScopes", ESType.getArray(serviceScopesProperties));

        Map<String, Object> extraServicesProperties = new HashMap<>();
        extraServicesProperties.put("id", ESType.getLongt());
        extraServicesProperties.put("storeId", ESType.getLongt());
        extraServicesProperties.put("extraServiceId", ESType.getLongt());
        serviceScopesProperties.put("status", ESType.getInteger());
        serviceScopesProperties.put("categoryName", ESType.getKeyword());

        properties.put("extraServices", ESType.getArray(extraServicesProperties));

        Map<String, Object> openingHoursProperties = new HashMap<>();

        openingHoursProperties.put("id", ESType.getLongt());
        openingHoursProperties.put("day", ESType.getInteger());
        openingHoursProperties.put("storeId", ESType.getLongt());
        openingHoursProperties.put("openTime", ESType.getInteger());
        openingHoursProperties.put("closeTime", ESType.getInteger());
        properties.put("openingHours", ESType.getArray(openingHoursProperties));

        Map<String, Object> businessSellerReviewModelsProperties = new HashMap<>();

        businessSellerReviewModelsProperties.put("id", ESType.getInteger());
        businessSellerReviewModelsProperties.put("createTime", ESType.getInteger());
        businessSellerReviewModelsProperties.put("identifyId", ESType.getKeyword());
        businessSellerReviewModelsProperties.put("keyId", ESType.getKeyword());
        businessSellerReviewModelsProperties.put("title", ESType.getKeyword());
        businessSellerReviewModelsProperties.put("reviewData", ESType.getKeyword());
        businessSellerReviewModelsProperties.put("status", ESType.getInteger());
        businessSellerReviewModelsProperties.put("rejectMsg", ESType.getKeyword());
        businessSellerReviewModelsProperties.put("legalSubjectId", ESType.getLongt());
        properties.put("businessSellerReviewModels", ESType.getArray(businessSellerReviewModelsProperties));

        mapping.put("properties", properties);


        esUtilService.createIndex(mapping, ESIndex.SHOP, ESIndex.SHOP_TYPE);
    }

}
