package com.hwsoft.common.product;

public enum ProductRepayRecordStatus {

    /**
     * 未偿还
     */
    UNREPAID() {

        @Override
        public String toString() {
            return "未偿还";
        }
    },
    /**
     * 正常偿还
     */
    REPAID() {

        @Override
        public String toString() {
            return "已偿还";
        }
    },
    /**
     * 逾期
     */
    OVER_DUE() {

        @Override
        public String toString() {
            return "逾期";
        }
    },
    /**
     * 逾期结清
     */
    OVERDUE_CLOSED() {

        @Override
        public String toString() {
            return "逾期结清";
        }
    };
	
}
