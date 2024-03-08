package com.chudk.yao.enums;

import lombok.Getter;

@Getter
public enum DbType {
    sqlite3("./db/data.db"),
    mysql("root:123456@tcp(172.18.3.234:33306)/yao_ai?charset=utf8mb4&parseTime=True&loc=Local"),
    ;
    final String connStr;
    DbType(String str){
        this.connStr = str;
    }

}
