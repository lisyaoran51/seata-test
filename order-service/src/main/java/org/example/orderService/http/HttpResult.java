package org.example.orderService.http;


public class HttpResult<T> {
    private Integer code;
    private String msg;
    private T data = null;
    private Boolean success;

    public HttpResult() {
    }

    public static <T> HttpResult<T> buildSuccess(T t) {
        HttpResult<T> result = new HttpResult();
        result.setCode(HttpException.SUCCESS.getCode());
        result.setMsg(HttpException.SUCCESS.getMsg());
        result.setData(t);
        result.setSuccess(true);
        return result;
    }
    public static <T> HttpResult<T> buildSuccess() {
        return (HttpResult<T>) buildSuccess((Object)null);
    }

    public static <T> HttpResult<T> buildError(String msg) {
        HttpResult<T> result = new HttpResult();
        result.setMsg(msg);
        result.setData((T) null);
        result.setSuccess(false);
        return result;
    }

    public static <T> HttpResult<T> buildError(HttpException exceptionEnum) {
        HttpResult<T> result = new HttpResult();
        result.setCode(exceptionEnum.getCode());
        result.setMsg(exceptionEnum.getMsg());
        result.setSuccess(false);
        result.setData((T) null);
        return result;
    }

    public static <T> HttpResult<T> buildError(Integer code, String msg) {
        HttpResult<T> result = new HttpResult();
        result.setCode(code);
        result.setMsg(msg);
        result.setSuccess(false);
        result.setData((T) null);
        return result;
    }

    public static <T> HttpResult<T> buildError(Integer code, String msg, T t) {
        HttpResult<T> result = new HttpResult();
        result.setCode(code);
        result.setMsg(msg);
        result.setSuccess(false);
        result.setData(t);
        return result;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public T getData() {
        return this.data;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public void setCode(final Integer code) {
        this.code = code;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public void setSuccess(final Boolean success) {
        this.success = success;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof HttpResult)) {
            return false;
        } else {
            HttpResult<?> other = (HttpResult)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label59: {
                    Object this$code = this.getCode();
                    Object other$code = other.getCode();
                    if (this$code == null) {
                        if (other$code == null) {
                            break label59;
                        }
                    } else if (this$code.equals(other$code)) {
                        break label59;
                    }

                    return false;
                }

                Object this$msg = this.getMsg();
                Object other$msg = other.getMsg();
                if (this$msg == null) {
                    if (other$msg != null) {
                        return false;
                    }
                } else if (!this$msg.equals(other$msg)) {
                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                Object this$success = this.getSuccess();
                Object other$success = other.getSuccess();
                if (this$success == null) {
                    if (other$success != null) {
                        return false;
                    }
                } else if (!this$success.equals(other$success)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof HttpResult;
    }

    @Override
    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $msg = this.getMsg();
        result = result * 59 + ($msg == null ? 43 : $msg.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        Object $success = this.getSuccess();
        result = result * 59 + ($success == null ? 43 : $success.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Result(code=" + this.getCode() + ", msg=" + this.getMsg() + ", data=" + this.getData() + ", success=" + this.getSuccess() + ")";
    }
}
