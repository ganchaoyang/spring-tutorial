package cn.itweknow.sbswagger.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author ganchaoyang
 * @date 2019/3/1013:55
 */
@ApiModel("用户实体")
public class User {

    /**
     * 用户Id
     */
    @ApiModelProperty("用户id")
    private int id;

    /**
     * 用户名
     */
    @ApiModelProperty("用户姓名")
    private String name;


    /**
     * 用户地址
     */
    @ApiModelProperty("用户地址")
    private String address;

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public User setAddress(String address) {
        this.address = address;
        return this;
    }
}
