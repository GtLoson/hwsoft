package com.hwsoft.common.productimage;

/**
 * 产品图片类型
 */
public enum ProductImageType {

    PRODUCT_DETAIL() {
        @Override
        public String toString() {
            return "产品详情";
        }
    }, RISK_CONTROL() {
        @Override
        public String toString() {
            return "风控信息";
        }
    }
}
