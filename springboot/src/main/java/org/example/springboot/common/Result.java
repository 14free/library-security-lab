package org.example.springboot.common;


public class Result {
    private static final String SUCCESS="200";
    private static final String ERROR="500";
    private String code;
    private String msg;
    private Object data;

    public Result() {
    }

    public Result(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result success(){
        Result result=new Result();
        result.setCode(SUCCESS);
        result.setMsg("操作成功");
        return result;
    }
    public static Result success(Object data){
        Result result=new Result();
        result.setCode(SUCCESS);
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }
    public static Result error(String msg){
        Result result=new Result();
        result.setCode(ERROR);
        result.setMsg(msg != null ? msg : "操作失败");
        return result;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
