package cn.itweknow.sbelkstart.common.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author ganchaoyang
 * @date 2019/5/1215:45
 */
public class BaseRequest implements Serializable {

    @NotNull(message = "渠道不能为空！")
    private String channel;


    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "BaseRequest{" +
                "channel='" + channel + '\'' +
                '}';
    }
}
