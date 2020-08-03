package com.thumbing.shared.constants;

/**
 * @author Stan Sai
 * @date 2020-06-28
 */
public class EntityConstants {
    //主键
    public final static String ID = "id";

    //创建时间
    public final static String CREATE_TIME = "create_time";

    //最后修改时间
    public final static String LAST_MODIFY_TIME = "last_modify_time";

    //版本号
    public final static String VERSION = "version";

    //逻辑删除
    public final static String IS_DELETE = "is_delete";

    //创建时间
    public final static String CREATE_TIME_PROPERTY = "createTime";

    //最后修改时间
    public final static String LAST_MODIFY_TIME_PROPERTY = "lastModifyTime";


    public final static String DELETION =  "set is_delete = 1 where id = ? and version=?";

    public final static String NO_VERSION_DELETION =  "set is_delete = 1 where id = ?";
}
