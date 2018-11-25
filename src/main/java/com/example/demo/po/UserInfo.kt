package com.example.demo.po

import java.sql.Timestamp


class UserInfo {
    var id: Int = 0//  id:1
    var username: String? = null// username:admin
    var password: String? = null// password:123456
    var email: String? = null// email:875891115@qq.com
    var phone: String? = null// phone:18711852313
    var receipt_code: String? = null// receipt_code:null收款码
    var role_id: Int = 0// role_id:1
    var recommend_code: String? = null// recommend_code:null
    var age: Int = 0// age:25
    var address: String? = null// address:广东深圳
    var status: Int = 0// status:1 1已推荐未注册 2已注册正常  3账户不安全 4账户被禁用 5账户被删除
    var register_time: Timestamp? = null// register_time:2018-11-12
    var recommend_user_id: Int = 0// 推荐人Id
    var recommend_time: Timestamp? = null// 推荐时间

    override fun toString(): String {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", receipt_code='" + receipt_code + '\'' +
                ", role_id=" + role_id +
                ", recommend_code='" + recommend_code + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", register_time=" + register_time +
                ", recommend_user_id=" + recommend_user_id +
                ", recommend_time=" + recommend_time +
                '}'
    }
    public fun getStatusStr():String{
        //1已推荐未注册 2已注册正常  3账户不安全 4账户被禁用 5账户被删除
        when(status){
            1->return "已推荐未注册"
            2->return "正常"
            3->return "账户不安全"
            4->return "被禁用"
            5->return "账户被删除"
        }
        return "异常"
    }

}