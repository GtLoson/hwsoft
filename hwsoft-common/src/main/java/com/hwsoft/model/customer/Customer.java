package com.hwsoft.model.customer;

import com.hwsoft.common.customer.CustomerSource;
import com.hwsoft.common.version.AppOSType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 *
 * @author tzh
 */
@Entity
@Table(name = "customer")
public class Customer implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 5638434148468910889L;

  /**
   * id
   */
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /**
   * 用户名
   */
  @Column(name = "username", length = 32, nullable = true)
  private String username;

  /**
   * 是否可用
   */
  @Column(name = "enable", length = 16, nullable = true)
  private Boolean enable = true;

  /**
   * 注册版本
   */
  @Column(name = "register_os_type", nullable = true)
  @Enumerated(EnumType.STRING)
  private AppOSType registerOSType;

  /**
   * 用户来源, 后台添加，手机注册
   */
  @Column(name = "source", nullable = true)
  @Enumerated(EnumType.STRING)
  private CustomerSource customerSource;

  /**
   * 手机号
   */
  @Column(name = "mobile", length = 11, nullable = true, unique = true)
  private String mobile;

  /**
   * 手机是否认证
   */
  @Column(name = "mobile_auth")
  private boolean mobileAuth = false;
  /**
   * 身份证号码
   */
  @Column(name = "id_card", length = 18, nullable = true)
  private String idCard;

  /**
   * 身份证是否验证
   */
  @Column(name = "id_card_auth")
  private boolean idCardAuth = false;

  /**
   * 密码
   */
  @Column(name = "password", length = 64)
  private String password;

  /**
   * 支付密码
   */
  @Column(name = "pay_password", length = 64)
  private String payPassword;

  /**
   * 是否设置支付密码
   */
  @Column(name = "has_pay_password")
  private Boolean hasPayPassword;

  /**
   * 注册时间
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "reg_time", nullable = true)
  private Date regTime;

  /**
   * 渠道号
   */
  @Column(name = "channel_id")
  private Integer channelId;

  /**
   * 真实姓名
   */
  @Column(name = "real_name", length = 32)
  private String realName;

  /**
   * 推荐人手机号
   */
  @Column(name = "recommend_mobile", length = 11, nullable = true)
  private String recommender;

  @Transient
  private CustomerInfo customerInfo;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getIdCard() {
    return idCard;
  }

  public void setIdCard(String idCard) {
    this.idCard = idCard;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPayPassword() {
    return payPassword;
  }

  public void setPayPassword(String payPassword) {
    this.payPassword = payPassword;
  }

  public Date getRegTime() {
    return regTime;
  }

  public void setRegTime(Date regTime) {
    this.regTime = regTime;
  }

  public Integer getChannelId() {
    return channelId;
  }

  public void setChannelId(Integer channelId) {
    this.channelId = channelId;
  }

  public boolean isMobileAuth() {
    return mobileAuth;
  }

  public void setMobileAuth(boolean mobileAuth) {
    this.mobileAuth = mobileAuth;
  }

  public boolean isIdCardAuth() {
    return idCardAuth;
  }

  public void setIdCardAuth(boolean idCardAuth) {
    this.idCardAuth = idCardAuth;
  }

  public CustomerInfo getCustomerInfo() {
    return customerInfo;
  }

  public void setCustomerInfo(CustomerInfo customerInfo) {
    this.customerInfo = customerInfo;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public Boolean getEnable() {
    return enable;
  }

  public void setEnable(Boolean enable) {
    this.enable = enable;
  }

  public AppOSType getRegisterOSType() {
    return registerOSType;
  }

  public void setRegisterOSType(AppOSType registerOSType) {
    this.registerOSType = registerOSType;
  }

  public Boolean getHasPayPassword() {
    return hasPayPassword;
  }

  public void setHasPayPassword(Boolean hasPayPassword) {
    this.hasPayPassword = hasPayPassword;
  }

  public CustomerSource getCustomerSource() {
    return customerSource;
  }

  public void setCustomerSource(CustomerSource customerSource) {
    this.customerSource = customerSource;
  }

  public String getRecommender() {
    return recommender;
  }

  public void setRecommender(String recommender) {
    this.recommender = recommender;
  }

  @Override
  public String toString() {
    return "Customer{" +
        "id=" + id +
        ", username='" + username + '\'' +
        ", enable=" + enable +
        ", registerOSType=" + registerOSType +
        ", customerSource=" + customerSource +
        ", mobile='" + mobile + '\'' +
        ", mobileAuth=" + mobileAuth +
        ", idCard='" + idCard + '\'' +
        ", idCardAuth=" + idCardAuth +
        ", password='" + password + '\'' +
        ", payPassword='" + payPassword + '\'' +
        ", hasPayPassword=" + hasPayPassword +
        ", regTime=" + regTime +
        ", channelId=" + channelId +
        ", realName='" + realName + '\'' +
        ", recommender='" + recommender + '\'' +
        '}';
  }
}
