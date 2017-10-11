package cn.com.eju.deal.common.util;

/**   
* 数据字典对应的常量
* @author (li_xiaodong)
* @date 2016年4月7日 下午6:43:04
*/
public interface DictionaryConstants
{
    
    /**
    * 客户状态--已签约
    */
    int DIC_CODE_COMPANY_STATUS_Y = 12501;
    
    /**
    * 客户状态--未签约
    */
    int DIC_CODE_COMPANY_STATUS_N = 12502;
    
    /**
    * 门店状态--未锁定
    */
    int DIC_CODE_STORE_STATUS_N = 12601;
    
    /**
     * 门店状态--已锁定
     */
    int DIC_CODE_STORE_STATUS_Y = 12602;
    
    // 合同状态-待提交审核--> 草签
    int CONTRACT_STATUS_PENDING = 10401;
    
    // 合同状态-审核中
    int CONTRACT_STATUS_AUDITING = 10402;
    
    // 合同状态-审核通过
    int CONTRACT_STATUS_AUDIT_PASS = 10403;
    
    // 合同状态-审核未通过
    int CONTRACT_STATUS_AUDIT_NO_PASS = 10404;
    
    // 合同状态-作废
    int CONTRACT_STATUS_CANCEL = 10405;
    
    // 合同类型-A版
    int CONTRACT_TPYE_A = 10301;
    
    // 合同类型-B版
    int CONTRACT_TPYE_B = 10302;
    
    /**
     * 合同类型--C版本
     */
    int CONTRACT_TYPE_C = 10303;
    
    /**
     * 合同类型--A转B版本
     */
    int CONTRACT_TYPE_A_2_B = 10304;
    
    /**
     * 合作模式
     */
    int COOP_TYPE = 109;
    
    /**
    * 合作模式--A
    */
    int COOP_TYPE_A = 10901;
    
    /**
    * 合作模式--B
    */
    int COOP_TYPE_B = 10902;
    
    /**
     * 合作模式--A转B
     */
    int COOP_TYPE_A_2_B = 10903;
    
    //新房联动中的字段
    
    /**
   * 销售状态
   */ 
   String SALES_STATUS=String.valueOf(144);
    
   /**
    * 结佣方式
    */
   String PAY_KBN = String.valueOf(146);
   
   /**
    * 认证类型
    */
   String AUTHENTICATION_KBN = String.valueOf(147);
   
   /**
    * 佣金方式
    */
   String COMMISSION_KBN = String.valueOf(148);
   
   /**
    * 佣金方式--百分比
    */
   int COMMISSION_PERCENTAGE=1482;
   
   /**
    * 佣金方式--固定值
    */
   int COMMISSION_YUAN=1481;
   
   /**
    * 销售方式
    */
   String SALE_KBN = String.valueOf(149);
   
   /**
    * 报备方式
    */
   String REPORT_KBN = String.valueOf(150);
   
   /**
    * 朝向
    */
   String DIRECTION_KBN = String.valueOf(151);
   
   /**
    * 产权年限
    */
   String OWNYEAR_KBN = String.valueOf(153);
   
   /**
    * 装修情况
    */
   String DECORATION_KBN = String.valueOf(154);
   
   /**
    * 建筑类型
    */
   String TYPE_KBN = String.valueOf(155);
   
   /**
    * 供暖方式
    */
   String HEAT_KBN = String.valueOf(156);
   
   /**
    * 水电燃气
    */
   String HYDROPOWERGAS_KBN = String.valueOf(157);
   
   /**
    * 合作方
    */
   String PARTNER = String.valueOf(128);
   
   /**
    * 物业类型
    */
   String MGT_KBN = String.valueOf(134); 
   
   //楼盘审核状态
   /**
    * 楼盘审核状态-未审核
    */
   int ESTATE_AUDIT_NEED = 12901;
   /**
    * 楼盘审核状态-不通过
    */
   int ESTATE_AUDIT_NO_PASS = 12902;
   /**
    * 楼盘审核状态-通过
    */
   int ESTATE_AUDIT_PASS = 12903;
   /**
    * 楼盘审核状态-未提交
    */
   int ESTATE_AUDIT_NO_PENDING = 12904;
   //楼盘发布状态
   /**
    * 楼盘发布状态-已发布
    */
   int ESTATE_PUBLISH_YES = 13001;
   /**
    * 楼盘发布状态-未发布
    */
   int ESTATE_PUBLISH_NO = 13002;
   
   //楼盘图片
   /**
   * 楼盘效果图
   */ 
   int ESTATE_DESIGN_IMG = 15901;
   
   /**
    * 楼盘样板间
    */
   int ESTATE_TEMPLATE_IMG = 15902;
   
   /**
    * 楼盘地理位置
    */
   int ESTATE_MAP_IMG = 15903;
   
   /**
    * 楼盘区域规划
    */
   int ESTATE_DISTRICT_IMG = 15904;
   
   /**
    * 楼盘实景图
    */
   int ESTATE_REAL_IMG = 15905;
   
   /**
    * 楼盘户型图
    */
   int HOUSE_TYPE_IMG = 15906;
   
   /**
    * 楼盘样板间
    */
   int HOUSE_TEMPLATE_IMG = 15907;
   
   /**
*带看奖励
*/ 
int RELATION_REWARD=14101;                      
/**
*认筹奖励
*/ 
int PLEDGE_REWARD=14102;          
/**
* 大定(认购)奖励
*/ 
int SUBSCRIBED_REWARD=14103;          
/**
* 成销(成交)奖励
*/ 
int BARGAIN_REWARD=14104;          
    
}
