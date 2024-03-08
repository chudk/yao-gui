package com.chudk.yao.model;

import com.chudk.yao.enums.DbType;
import com.chudk.yao.enums.EnvType;
import com.chudk.yao.enums.SessionStoreType;
import lombok.Data;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Env {
/*    private DbType YAO_DB_DRIVER = DbType.sqlite3;
    private String YAO_DB_PRIMARY = "./db/yao.db";
    private EnvType YAO_ENV = EnvType.development;
    private String YAO_HOST = "0.0.0.0";
    private String YAO_LANG = "zh-cn";
    private String YAO_LOG_MODE = "TEXT";
    private String YAO_LOG = "./logs/application.log";
    private String YAO_PORT = "5099";
    private SessionStoreType YAO_SESSION_STORE = SessionStoreType.file;
    private String YAO_SESSION_FILE = "./db/.session";
    private String YAO_SESSION_HOST = "127.0.0.1";
    private String YAO_SESSION_PORT = "6379";
    private String YAO_SESSION_PASSWORD;
    private String YAO_SESSION_USERNAME;
    private String YAO_SESSION_DB = "1";
    private String YAO_STUDIO_PORT = "5077";*/
    private LinkedHashMap<String,String> props = new LinkedHashMap<>(10);

    public void validate(){
        String key = props.get("YAO_DB_DRIVER");
        Assert.notNull(DbType.valueOf(key), "无效的数据库类型");
        key = props.get("YAO_ENV");
        Assert.notNull(EnvType.valueOf(key), "无效的环境类型");
        key = props.get("YAO_SESSION_STORE");
        Assert.notNull(SessionStoreType.valueOf(key), "无效的缓存类型");
        key = props.get("YAO_LOG_MODE");
        Assert.isTrue("TEXT".equals(key), "暂不支持的日志类型");
    }

    public static Env load(String envStr){
        envStr = envStr.replace("\r\n","\n");
        String[] arr = envStr.split("\n");
        Env env = new Env();
        List<Field> fields = Arrays.asList(env.getClass().getDeclaredFields());
        String key = null,val = null;
        for(String str : arr){
            if(str.startsWith("#")){
                continue;
            }
            if(!str.contains("=")){
                continue;
            }
            int idx = str.indexOf("=");
            key = str.substring(0,idx);
            val = str.substring(idx+1);
            if(val.startsWith("\"")){
                val = val.substring(1, val.length()-1);
            }
            env.props.put(key, val);
        }
        return env;
    }

    public String format(){
        return this.props.entrySet().stream().map(ent->ent.getKey()+"=\""+ent.getValue()+"\"").collect(Collectors.joining("\n"));
    }
}
