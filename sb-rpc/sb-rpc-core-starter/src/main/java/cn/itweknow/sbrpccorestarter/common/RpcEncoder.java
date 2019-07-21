package cn.itweknow.sbrpccorestarter.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * RPC 编码器
 *
 */
public class RpcEncoder extends MessageToByteEncoder {

    private Class<?> genericClass;

    /**
     * 构造函数传入向反序列化的class
     * @param genericClass
     */
    public RpcEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public void encode(ChannelHandlerContext ctx, Object inob, ByteBuf out)
            throws Exception {
        //序列化
        if (genericClass.isInstance(inob)) {
            byte[] data = SerializationUtil.serialize(inob);
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }
}