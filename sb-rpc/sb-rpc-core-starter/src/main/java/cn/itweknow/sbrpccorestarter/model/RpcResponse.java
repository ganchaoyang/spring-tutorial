package cn.itweknow.sbrpccorestarter.model;

/**
 * @author ganchaoyang
 * @date 2018/10/26 18:28
 * @description
 */
public class RpcResponse {

    private String requestId;

    private Throwable error;

    private Object result;

    public RpcResponse() {
    }

    public RpcResponse(String requestId, Throwable error, Object result) {
        this.requestId = requestId;
        this.error = error;
        this.result = result;
    }

    public String getRequestId() {
        return requestId;
    }

    public RpcResponse setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public Throwable getError() {
        return error;
    }

    public RpcResponse setError(Throwable error) {
        this.error = error;
        return this;
    }

    public Object getResult() {
        return result;
    }

    public RpcResponse setResult(Object result) {
        this.result = result;
        return this;
    }

    public boolean isError() {
        return error != null;
    }

    @Override
    public String toString() {
        return "RpcResponse{" +
                "requestId='" + requestId + '\'' +
                ", error=" + error +
                ", result=" + result +
                '}';
    }
}
