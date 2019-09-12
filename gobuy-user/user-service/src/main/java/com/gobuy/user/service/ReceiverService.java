package com.gobuy.user.service;


import com.gobuy.auth.entity.UserInfo;
import com.gobuy.user.interceptor.LoginInterceptor;
import com.gobuy.user.mapper.ReceiverMapper;
import com.gobuy.user.pojo.Receiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ReceiverService {

    @Autowired
    private ReceiverMapper receiverMapper;

    // 查询收件人信息
    public List<Receiver> queryAddress() {
        UserInfo user = LoginInterceptor.getLoginUser();
        if (user == null || user.getId() == null)
            return null;

        Example example = new Example(Receiver.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", user.getId());

        return receiverMapper.selectByExample(example);
    }

    // 编辑收件人信息
    public Boolean editAddress(Receiver receiver) {
        UserInfo user = LoginInterceptor.getLoginUser();
        if (user == null || user.getId() == null)
            return null;

        return receiverMapper.updateByPrimaryKeySelective(receiver) == 1;
    }

    // 新增收件人信息
    public Boolean addAddress(Receiver receiver) {
        UserInfo user = LoginInterceptor.getLoginUser();
        if (user == null || user.getId() == null)
            return null;

        receiver.setUserId(user.getId());
        return receiverMapper.insert(receiver) == 1;
    }

    // 删除收件人信息
    public Boolean deleteAddress(Integer id) {
        UserInfo user = LoginInterceptor.getLoginUser();
        if (user == null || user.getId() == null)
            return null;

        Receiver record = new Receiver();
        record.setId(id);
        record.setUserId(user.getId());

        return receiverMapper.delete(record) == 1;
    }
}// end
