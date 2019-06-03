package com.liang.guli.educommon;

public interface ResultCode {
    int OK = 20000;
    int ERROR = 20001;
    int LOGIN_ERROR = 2003;//用户名或密码错误
    int ACCESS_ERROR = 2004;//权限不足
    int REMOTE_ERROR = 2005;//远程调用失败
    int REPEAT_ERROR = 2006;//重复操作
    int SQL_ERROR=2007;//SQL语法错误
}
