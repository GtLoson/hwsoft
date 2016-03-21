/**
 *
 */
package com.hwsoft.common.staff;

/**
 * @author tzh
 */
public enum StaffRoleType {

    STAFF_ADMIN() {
        @Override
        public String toString() {
            return "管理员";
        }
    },
    STAFF_NOMAL() {
        @Override
        public String toString() {
            return "普通用户";
        }
    },
    STAFF_RUN() {
        @Override
        public String toString() {
            return "运营人员";
        }
    },
    STAFF_FINANCE() {
        @Override
        public String toString() {
            return "财务人员";
        }
    },
    STAFF_CUSTOMER_SERVICE() {
        @Override
        public String toString() {
            return "客服人员";
        }
    }
}
